package befaster.translators;

public interface BasicTranslator {
    boolean isCommand(String line);
    String translate(String line);
}
