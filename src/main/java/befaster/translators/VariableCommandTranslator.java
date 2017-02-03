package befaster.translators;

import java.util.Set;

import static befaster.utils.Names.RESERVED_KEYWORDS;

public class VariableCommandTranslator implements BasicTranslator {

    private final Set<String> variablesNames;

    public VariableCommandTranslator(Set<String> variablesNames) {
        this.variablesNames = variablesNames;
    }

    @Override
    public boolean isCommand(String line) {
        if (line.matches("^[a-zA-Z0-9$]*=.*$")) {
            final String variableName = getVariableName(line);
            for (String reservedWord : RESERVED_KEYWORDS) {
                if (variableName.startsWith(reservedWord)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String translate(String line) {
        String variableName = getVariableName(line);
        variablesNames.add(variableName);
        return line + ";";
    }

    private String getVariableName(String line) {
        final int equalsCharIndex = line.indexOf("=");
        return line.substring(0, equalsCharIndex).trim();
    }
}
