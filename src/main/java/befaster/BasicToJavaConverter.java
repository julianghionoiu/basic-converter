package befaster;


import befaster.builders.CaseStatementBuilder;
import befaster.builders.ForCommandBuilder;
import befaster.builders.JavaProgramBuilder;
import befaster.translators.*;
import befaster.utils.CommandsSplitter;
import befaster.utils.Dim;
import befaster.utils.LineNumberExtractor;
import com.google.googlejavaformat.java.*;
import com.google.googlejavaformat.java.Formatter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class BasicToJavaConverter {

    private List<BasicTranslator> translators;
    private List<BasicTranslator> preProcessingTranslators;
    private LineNumberExtractor lineNumberExtractor;
    private LinkedList<Object> globalDataList;
    private ForCommandTranslator forCommandTranslator;
    private Set<Dim> arrays;
    private Set<String> variablesNames;
    private DeclarationsAndInitializationsBuilder declarationsAndInitializationsBuilder;

    public void init() {
        globalDataList = new LinkedList<>();
        arrays = new HashSet<>();
        variablesNames = new HashSet<>();
        declarationsAndInitializationsBuilder = new DeclarationsAndInitializationsBuilder();
        lineNumberExtractor = new LineNumberExtractor();

        preProcessingTranslators = new ArrayList<>();
        FunctionsTranslator functionsTranslator = new FunctionsTranslator(declarationsAndInitializationsBuilder);
        preProcessingTranslators.add(functionsTranslator);
        preProcessingTranslators.add(new ArraysNamesTranslator(arrays));

        translators = new ArrayList<>();

        final PrintCommandTranslator printCommandTranslator = new PrintCommandTranslator(functionsTranslator);
        final VariableCommandTranslator variableCommandTranslator = new VariableCommandTranslator(variablesNames);
        translators.add(printCommandTranslator);
        translators.add(variableCommandTranslator);
        translators.add(new GoToCommandTranslator());
        translators.add(new REMCommandTranslator());
        translators.add(new StopCommandTranslator());
        translators.add(new NextCommandTranslator());
        translators.add(new OnCommandTranslator());
        translators.add(new ReadCommandTranslator());
        translators.add(new EndCommandTranslator());
        translators.add(new IfCommandTranslator(variableCommandTranslator, printCommandTranslator));
        translators.add(new DataCommandTranslator(globalDataList));
        translators.add(new InputCommandTranslator(variablesNames, printCommandTranslator, declarationsAndInitializationsBuilder));
        translators.add(new DimCommandTranslator(arrays));
        forCommandTranslator = new ForCommandTranslator(variablesNames);
        translators.add(forCommandTranslator);
    }

    public void translate(String name, List<String> lines) {

        final String translatedLines = translateLines(lines);

        final DeclarationsAndInitializations declAndInit = declarationsAndInitializationsBuilder
                .withArrays(arrays)
                .withGlobalDataList(globalDataList)
                .withVariableNames(variablesNames)
                .build();

        final JavaProgramBuilder javaProgramBuilder = new JavaProgramBuilder();
        final String firstLineNumber = lineNumberExtractor.getLineNumber(lines.get(0));

        javaProgramBuilder
                .withName(name)
                .withStartLineNumber(firstLineNumber)
                .withBody(translatedLines)
                .withDeclarationsAndInitializations(declAndInit);

        final String javaProgram = javaProgramBuilder.build();
        final String formatted = getFormatted(javaProgram);
        try {
            Files.write(Paths.get(name + ".java"), formatted.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFormatted(String source) {
        final Formatter formatter = new Formatter();
        try {
            return formatter.formatSource(source);
        } catch (FormatterException e) {
            e.printStackTrace();
            return source;
        }
    }

    public String translateLines(List<String> lines) {
        init();

        final List<String> translatedLines = new ArrayList<>();
        final LinkedList<ForCommandBuilder> forCommandBuilders = new LinkedList<>();

        for (String line : lines) {
            final String lineNumber = lineNumberExtractor.getLineNumber(line);
            final String prePrecessedLine = preProcessLine(line);
            final CaseStatementBuilder caseStatementBuilder = new CaseStatementBuilder().withLineNumber(lineNumber);

            final LinkedList<String> commands = Arrays.stream(CommandsSplitter.split(prePrecessedLine)).
                    map(String::trim).collect(Collectors.toCollection(LinkedList::new));

            if (!forCommandBuilders.isEmpty()) {
                forCommandBuilders.getLast().withFirstInnerLineNumber(lineNumber);
            }

            if (commands.getFirst().startsWith("FOR")) {
                final String translatedLine = handleStartForLoop(forCommandBuilders, lineNumber, commands);
                caseStatementBuilder.withTranslatedLine(translatedLine);
            } else if (commands.getLast().startsWith("NEXT")) {
                final ForCommandBuilder forCommandBuilder = forCommandBuilders.removeLast();
                if (commands.size() > 1) {
                    commands.removeLast();
                    final List<String> translatedCommands = this.translateCommands(commands);
                    forCommandBuilder.withLastTranslatedLine(String.join("\n", translatedCommands));
                }
                final String translatedLine = forCommandBuilder.withLastInnerLineNumber(lineNumber).build();
                caseStatementBuilder.withTranslatedLine(translatedLine).withLineNumber(forCommandBuilder.getLineNumber());
            } else {
                final List<String> translatedCommands = this.translateCommands(commands);
                if (!translatedCommands.isEmpty()) {
                    caseStatementBuilder.withTranslatedLine(String.join("\n", translatedCommands));
                }
            }

            final String caseStatement = caseStatementBuilder.build();

            if (forCommandBuilders.isEmpty()) {
                translatedLines.add(caseStatement);
            } else {
                forCommandBuilders.getLast().withTranslatedLine(caseStatement);
            }
        }

        return String.join("\n", translatedLines);
    }

    private String preProcessLine(String line) {
        String newLine = lineNumberExtractor.removeLineNumber(line);
        for (BasicTranslator preProcessingTranslator : preProcessingTranslators) {
            if (preProcessingTranslator.isCommand(newLine)) {
                newLine = preProcessingTranslator.translate(newLine);
            }
        }
        return newLine.replace("\\", "\\\\");
    }

    private String handleStartForLoop(LinkedList<ForCommandBuilder> forCommandBuilders, String lineNumber,
                                      LinkedList<String> commands) {
        final String forCommand = forCommandTranslator.translate(commands.removeFirst());

        final ForCommandBuilder forCommandBuilder = new ForCommandBuilder()
                .withLineNumber(lineNumber)
                .withForCommand(forCommand);

        if (commands.size() > 0 && commands.getLast().startsWith("NEXT")) {
            return forCommandBuilder
                    .withTranslatedLines(this.translateCommands(commands))
                    .simpleForCommand(true)
                    .build();
        } else {
            forCommandBuilders.add(forCommandBuilder);
            String outerLabel = "outer" + forCommandBuilders.size();
            forCommandBuilder.withLabel(outerLabel);
            return "";
        }
    }

    private List<String> translateCommands(List<String> commands) {
        return commands.stream().
                map(command -> {
                    final Optional<BasicTranslator> translator = this.getTranslator(command);
                    if (translator.isPresent()) {
                        return translator.get().translate(command);
                    } else {
                        return command + ";";
                    }
                }).
                collect(Collectors.toList());
    }


    private Optional<BasicTranslator> getTranslator(String command) {
        for (BasicTranslator translator : translators) {
            if (translator.isCommand(command.trim())) {
                return Optional.of(translator);
            }
        }
        return Optional.empty();
    }


}

