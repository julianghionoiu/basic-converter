package befaster.translators;

public class StopCommandTranslator implements BasicTranslator {

    @Override
    public boolean isCommand(String line) {
        return line.startsWith("STOP");
    }

    @Override
    public String translate(String line) {
        return "break;";
    }

}
