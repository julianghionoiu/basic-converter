package befaster.translators;

import static befaster.utils.Names.LINE_NUMBER_VARIABLE;

public class GoToCommandTranslator implements BasicTranslator {

    @Override
    public boolean isCommand(String line) {
        return line.startsWith("GOTO");
    }

    @Override
    public String translate(String line) {
        line = line.replace("GOTO", "").replaceAll("\"", "").trim();
        int lineNumber = Integer.parseInt(line);
        return String.format("%s = %s;\nbreak;", LINE_NUMBER_VARIABLE, lineNumber);
    }

}
