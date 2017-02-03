package befaster.builders;

import java.util.LinkedList;
import java.util.List;


public class ForCommandBuilder {
    private final LinkedList<String> translatedLines = new LinkedList<>();
    private String forCommand;
    private String firstInnerLineNumber;
    private String label;
    private String lineNumber;
    private String lastInnerLineNumber;
    private String lastTranslatedLine = "";
    private boolean simpleForCommand;


    public ForCommandBuilder withTranslatedLine(String translatedLine) {
        this.translatedLines.add(translatedLine);
        return this;
    }

    public ForCommandBuilder simpleForCommand(boolean simpleForCommand) {
        this.simpleForCommand = simpleForCommand;
        return this;
    }

    public ForCommandBuilder withTranslatedLines(List<String> translatedLines) {
        this.translatedLines.addAll(translatedLines);
        return this;
    }

    public ForCommandBuilder withForCommand(String forCommand) {
        this.forCommand = forCommand;
        return this;
    }

    public ForCommandBuilder withFirstInnerLineNumber(String firstInnerLineNumber) {
        if (this.firstInnerLineNumber == null) {
            this.firstInnerLineNumber = firstInnerLineNumber;
        }
        return this;
    }

    public ForCommandBuilder withLabel(String label) {
        this.label = label;
        return this;
    }

    public ForCommandBuilder withLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public ForCommandBuilder withLastInnerLineNumber(String lastInnerLineNumber) {
        this.lastInnerLineNumber = lastInnerLineNumber;
        return this;
    }

    public ForCommandBuilder withLastTranslatedLine(String lastTranslatedLine) {
        this.lastTranslatedLine = lastTranslatedLine + "\n";
        return this;
    }

    public String build() {
        if (simpleForCommand) {
            return buildSimple();
        } else {
            return buildComplex();
        }
    }

    public String getLineNumber() {
        return lineNumber;
    }

    private String buildComplex() {
        StringBuilder builder = new StringBuilder();
        builder.append(forCommand);
        builder.append("\n");
        builder.append("lineNumber = " + firstInnerLineNumber + ";");
        builder.append("\n");
        builder.append(label + ":");
        builder.append("\n");
        builder.append("while (true) {");
        builder.append("\n");
        builder.append("switch (lineNumber) {");
        builder.append("\n");
        builder.append(String.join("\n", translatedLines));
        builder.append("\n");
        builder.append(lastBreak());
        builder.append("\n");
        builder.append("}}}");
        return builder.toString();
    }

    private String lastBreak() {
        StringBuilder sb = new StringBuilder();
        sb.append("case ");
        sb.append(lastInnerLineNumber);
        sb.append(":\n");
        sb.append(lastTranslatedLine);
        sb.append("lineNumber = " + firstInnerLineNumber);
        sb.append(";\nbreak ");
        sb.append(label);
        sb.append(";");
        return sb.toString();
    }

    private String buildSimple() {
        StringBuilder builder = new StringBuilder();
        builder.append(forCommand);
        builder.append("\n");
        builder.append(String.join("\n", translatedLines));
        builder.append("\n");
        builder.append("}");
        return builder.toString();
    }

}



