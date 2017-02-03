package befaster.translators;

import befaster.utils.VariableTypes;

import java.util.Set;

public class InputCommandTranslator implements BasicTranslator {


    private final Set<String> variableNames;
    private final PrintCommandTranslator printCommandTranslator;
    private final DeclarationsAndInitializationsBuilder declarationsAndInitializationsBuilder;

    public InputCommandTranslator(Set<String> variableNames,
                                  PrintCommandTranslator printCommandTranslator,
                                  DeclarationsAndInitializationsBuilder declarationsAndInitializationsBuilder) {
        this.variableNames = variableNames;
        this.printCommandTranslator = printCommandTranslator;
        this.declarationsAndInitializationsBuilder = declarationsAndInitializationsBuilder;
    }

    @Override
    public boolean isCommand(String line) {
        return line.startsWith("INPUT");
    }

    @Override
    public String translate(String line) {
        String inputLine = line.replace("INPUT", "").trim();
        StringBuilder stringBuilder = new StringBuilder();

        for (String inputCommand : inputLine.split(";")) {
            declarationsAndInitializationsBuilder.withScanner(true);
            if (inputCommand.contains("\"")) {
                final String printCommand = printCommandTranslator.translate(inputCommand);
                stringBuilder.append(printCommand);
                stringBuilder.append("\n");
            } else {
                final String[] variables = inputCommand.split(",");
                for (String variable : variables) {
                    stringBuilder.append("input = sc.next();");
                    stringBuilder.append("\n");
                    String input = VariableTypes.getType(variable).equals(VariableTypes.Types.NUMBER) ?
                            "Double.parseDouble(input);" : "input;";

                    String translated = String.format("%s = %s", variable, input);
                    stringBuilder.append(translated);
                    stringBuilder.append("\n");
                    variableNames.add(variable);
                }
            }
        }

        return stringBuilder.toString();
    }

}
