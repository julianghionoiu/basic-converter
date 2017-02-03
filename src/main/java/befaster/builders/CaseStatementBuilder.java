package befaster.builders;

public class CaseStatementBuilder {

    private String lineNumber;
    private String translatedLine;

    public CaseStatementBuilder withLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public CaseStatementBuilder withTranslatedLine(String translatedLine) {
        this.translatedLine = translatedLine;
        return this;
    }


    public String build() {
        if (translatedLine != null && lineNumber != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("case ");
            sb.append(lineNumber);
            sb.append(":\n");
            sb.append(translatedLine);
            sb.append("\n");
            return sb.toString();
        } else {
            return "";
        }
    }
}
