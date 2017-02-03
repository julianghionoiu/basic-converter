package befaster.translators;

public class REMCommandTranslator implements BasicTranslator {

    @Override
    public boolean isCommand(String line) {
        return line.startsWith("REM");
    }

    @Override
    public String translate(String line) {
        return line.replace("REM", "//").trim();
    }

}
