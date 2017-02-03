package befaster.translators;

import static befaster.utils.Names.LINE_NUMBER_VARIABLE;

public class OnCommandTranslator implements BasicTranslator {

    @Override
    public boolean isCommand(String line) {
        return line.startsWith("ON");
    }

    @Override
    public String translate(String line) {
        line = line.replace("ON", "").replaceAll("\"", "").trim();
        final String[] onCommands = line.split("GOTO");
        final String variable = onCommands[0].trim();
        final String[] goToLines = onCommands[1].trim().split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < goToLines.length + 1; i++) {
            stringBuilder.append("if ((int)(");
            stringBuilder.append(variable);
            stringBuilder.append(") == ");
            stringBuilder.append(i);
            stringBuilder.append(") {");
            stringBuilder.append(LINE_NUMBER_VARIABLE);
            stringBuilder.append(" = ");
            stringBuilder.append(goToLines[i-1]);
            stringBuilder.append(";break;}");
        }
        return stringBuilder.toString();
    }
}
