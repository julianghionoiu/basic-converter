package befaster.translators;

import java.util.Set;

public class ForCommandTranslator implements BasicTranslator {

    private final static String TEMPLATE = "for (%s; %s <= %s; %s) { ";

    private final Set<String> variablesNames;

    public ForCommandTranslator(Set<String> variablesNames) {
        this.variablesNames = variablesNames;
    }

    @Override
    public boolean isCommand(String line) {
        return line.startsWith("FOR");
    }

    @Override
    public String translate(String line) {

        line = line.replaceFirst("FOR", "").trim();
        final int equalsCharIndex = line.indexOf("=");

        String variableName = line.substring(0, equalsCharIndex);
        variablesNames.add(variableName);

        final String[] loop = line.split("TO");
        String startNumber = loop[0].trim();
        String endLoop = loop[1].trim();

        String step;
        if (loop[1].contains(" STEP ")) {
            final String[] stepSplit = loop[1].split("STEP");
            endLoop = stepSplit[0].trim();
            String stepNumber = stepSplit[1].trim();
            step = String.format("%s = %s + %s", variableName, variableName, stepNumber);
        } else {
            step = variableName + "++";
        }

        return String.format(TEMPLATE, startNumber, variableName, endLoop, step);
    }
}
