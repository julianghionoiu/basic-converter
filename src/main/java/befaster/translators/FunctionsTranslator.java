package befaster.translators;

public class FunctionsTranslator implements BasicTranslator {

    private DeclarationsAndInitializationsBuilder declarationsAndInitializationsBuilder;

    public FunctionsTranslator(DeclarationsAndInitializationsBuilder declarationsAndInitializationsBuilder) {
        this.declarationsAndInitializationsBuilder = declarationsAndInitializationsBuilder;
    }

    @Override
    public boolean isCommand(String line) {
        return line.matches(".*RND\\(1\\).*") ||
                line.matches(".*INT\\(.*") ||
                line.matches(".*VAL\\(.*") ||
                line.matches(".*LEN\\(.*") ||
                line.matches(".*TAB\\(.*") ||
                line.matches(".*CHR\\$\\(.*") ||
                line.matches(".*STR\\$\\(.*") ||
                line.matches(".*LEFT\\$\\(.*") ||
                line.matches(".*RIGHT\\$\\(.*") ||
                line.matches(".*MID\\$\\(.*") ||
                line.matches(".*ABS\\(.*") ||
                line.matches(".*SQR\\(.*") ||
                line.matches(".*ASC\\(.*");
    }

    @Override
    public String translate(String line) {
        if (line.matches(".*ASC\\(.*")) {
            declarationsAndInitializationsBuilder.withAscFunction();
        }
        if (line.matches(".*LEN\\(.*")) {
            declarationsAndInitializationsBuilder.withLenFunction();
        }
        if (line.matches(".*TAB\\(.*")) {
            declarationsAndInitializationsBuilder.withTabFunction();
        }
        if (line.matches(".*LEFT\\$\\(.*")) {
            declarationsAndInitializationsBuilder.withLeftFunction();
        }
        if (line.matches(".*RIGHT\\$\\(.*")) {
            declarationsAndInitializationsBuilder.withRightFunction();
        }
        if (line.matches(".*MID\\$\\(.*")) {
            declarationsAndInitializationsBuilder.withMidFunction();
        }
        return line.replace("RND(1)", "Math.random()")
                .replace("INT(", "(int)(")
                .replace("ABS(", "Math.abs(")
                .replace("SQR(", "Math.sqrt(")
                .replace("VAL(", "Double.parseDouble(")
                .replace("CHR$(", "(char)(")
                .replace("STR$(", "String.valueOf(");
    }

}
