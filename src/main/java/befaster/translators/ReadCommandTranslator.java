package befaster.translators;

import befaster.utils.VariableTypes;

public class ReadCommandTranslator implements BasicTranslator {

    @Override
    public boolean isCommand(String line) {
        return line.startsWith("READ");
    }

    @Override
    public String translate(String line) {
        String variable = line.replace("READ", "").replaceAll("\"", "").trim();
        String type;
        if (variable.contains("[")) {
            type = VariableTypes.getType(variable.substring(0, variable.indexOf("["))).getName();
        } else {
            type = VariableTypes.getType(variable).getName();
        }
        return String.format("%s = (%s) globalDataList.removeFirst();", variable, type);
    }

}
