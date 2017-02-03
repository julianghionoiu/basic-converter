package befaster.translators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static befaster.utils.Names.LINE_NUMBER_VARIABLE;

public class IfCommandTranslator implements BasicTranslator {

    private final VariableCommandTranslator variableCommandTranslator;
    private final PrintCommandTranslator printCommandTranslator;

    public IfCommandTranslator(VariableCommandTranslator variableCommandTranslator, PrintCommandTranslator printCommandTranslator) {
        this.variableCommandTranslator = variableCommandTranslator;
        this.printCommandTranslator = printCommandTranslator;
    }

    @Override
    public boolean isCommand(String line) {
        return line.startsWith("IF");
    }

    @Override
    public String translate(String line) {
        final String[] commands = line.split("THEN");
        final String ifCondition = commands[0].trim();
        final String ifBody = commands[1].trim();

        String translatedCondition = ifCondition.replace("IF", "").replace("AND", "&&").trim();

        final boolean stringEquals = translatedCondition.contains("=\"");
        if (stringEquals) {
            translatedCondition = translatedCondition.replaceAll("=\"", ".equals(\"");
            final Pattern equalsPattern = Pattern.compile("(?<!(\\.equals\\())\"");
            Matcher equalsMatcher = equalsPattern.matcher(translatedCondition);
            if (equalsMatcher.find()) {
                translatedCondition = equalsMatcher.replaceAll( "\")");
            }
        }

        final boolean stringNotEquals = translatedCondition.contains("<>\"");
        if (stringNotEquals) {
            translatedCondition = translatedCondition.replaceAll("<>\"", ".equals(\"");
            final Pattern equalsPattern = Pattern.compile("(?<!(\\.equals\\())\"");
            Matcher equalsMatcher = equalsPattern.matcher(translatedCondition);
            if (equalsMatcher.find()) {
                translatedCondition = equalsMatcher.replaceAll( "\")");
            }
        }

        translatedCondition = translatedCondition.replace("<>", "!=");

        final Pattern equalsPattern = Pattern.compile("(?<!(<|!|>))=");
        Matcher equalsMatcher = equalsPattern.matcher(translatedCondition);
        if (equalsMatcher.find()) {
            translatedCondition = equalsMatcher.replaceAll( "==");
        }

        final Pattern numberPattern = Pattern.compile("^(\\d+)");
        Matcher numberMatcher = numberPattern.matcher(ifBody);
        String translatedBody = "";
        if (numberMatcher.find()) {
            translatedBody = LINE_NUMBER_VARIABLE + " = " + numberMatcher.group() + ";\nbreak;";
        }

        if (variableCommandTranslator.isCommand(ifBody)) {
            translatedBody = variableCommandTranslator.translate(ifBody);
        }

        if (printCommandTranslator.isCommand(ifBody)) {
            translatedBody = printCommandTranslator.translate(ifBody);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("if (");
        if (stringNotEquals) {
            sb.append("!");
        }
        sb.append(translatedCondition);
        sb.append(") { ");
        sb.append(translatedBody);
        sb.append(" }");
        return sb.toString();
    }

}
