package befaster.translators;

public class EndCommandTranslator implements BasicTranslator {

    @Override
    public boolean isCommand(String line) {
        return line.startsWith("END");
    }

    @Override
    public String translate(String line) {
        return "System.exit(0);";
    }

}
