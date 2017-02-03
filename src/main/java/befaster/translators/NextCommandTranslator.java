package befaster.translators;

public class NextCommandTranslator implements BasicTranslator {

    @Override
    public boolean isCommand(String line) {
        return line.startsWith("NEXT");
    }

    @Override
    public String translate(String line) {
        return "";
    }

}
