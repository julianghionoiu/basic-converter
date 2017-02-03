package befaster.builders;

import befaster.translators.DeclarationsAndInitializations;

import static befaster.utils.Names.LINE_NUMBER_VARIABLE;

public class JavaProgramBuilder {
    private String name;
    private String body;
    private String startLineNumber;
    private DeclarationsAndInitializations declarationsAndInitializations;

    public JavaProgramBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public JavaProgramBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    public JavaProgramBuilder withStartLineNumber(String startLineNumber) {
        this.startLineNumber = startLineNumber;
        return this;
    }

    public JavaProgramBuilder withDeclarationsAndInitializations(DeclarationsAndInitializations declarationsAndInitializations) {
        this.declarationsAndInitializations = declarationsAndInitializations;
        return this;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("import java.util.*;");
        builder.append("public class ");
        builder.append(name);
        builder.append(" { public static void main(String[] args) { ");
        builder.append("int " + LINE_NUMBER_VARIABLE + " = ");
        builder.append(startLineNumber + ";");
        builder.append(declarationsAndInitializations.getGlobalData());
        builder.append(declarationsAndInitializations.getVariablesDeclarations());
        builder.append(declarationsAndInitializations.getArraysDeclarations());
        builder.append(declarationsAndInitializations.getScannerDeclaration());
        builder.append("while(true) {");
        builder.append("switch (");
        builder.append(LINE_NUMBER_VARIABLE);
        builder.append(") {");
        builder.append(body);
        builder.append("}}}");
        builder.append(declarationsAndInitializations.getFunctions());
        builder.append("}");
        return builder.toString();
    }
}