package befaster.translators;

import befaster.utils.Dim;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class DeclarationsAndInitializationsBuilder {
    private Set<Dim> arrays;
    private Set<String> variableNames;
    private LinkedList<Object> globalDataList;
    private boolean scanner;
    private final Set<Function> functions = new HashSet<>();

    public DeclarationsAndInitializationsBuilder withArrays(Set<Dim> arrays) {
        this.arrays = arrays;
        return this;
    }

    public DeclarationsAndInitializationsBuilder withVariableNames(Set<String> variableNames) {
        this.variableNames = variableNames;
        return this;
    }

    public DeclarationsAndInitializationsBuilder withGlobalDataList(LinkedList<Object> globalDataList) {
        this.globalDataList = globalDataList;
        return this;
    }

    public DeclarationsAndInitializationsBuilder withAscFunction() {
        functions.add(Function.ASC);
        return this;
    }

    public DeclarationsAndInitializationsBuilder withLenFunction() {
        functions.add(Function.LEN);
        return this;
    }

    public DeclarationsAndInitializationsBuilder withLeftFunction() {
        functions.add(Function.LEFT);
        return this;
    }

    public DeclarationsAndInitializationsBuilder withRightFunction() {
        functions.add(Function.RIGHT);
        return this;
    }

    public DeclarationsAndInitializationsBuilder withMidFunction() {
        functions.add(Function.MID);
        return this;
    }

    public DeclarationsAndInitializationsBuilder withTabFunction() {
        functions.add(Function.TAB);
        return this;
    }

    public DeclarationsAndInitializationsBuilder withScanner(boolean scanner) {
        this.scanner = scanner;
        return this;
    }

    public DeclarationsAndInitializations build() {
        return new DeclarationsAndInitializations(arrays, variableNames, globalDataList, functions, scanner);
    }


    public enum Function {

        ASC("public static int ASC(String s) {" +
                "char c = s.charAt(0);" +
                "return (int)c;" +
                "}"),
        LEN("public static double LEN(String s) {" +
                "return s.length();" +
                "}"),
        LEFT("public static String LEFT$(String text, double length) {" +
                "return text.substring(0, (int)length);" +
                "}"),
        RIGHT("public static String RIGHT$(String text, double length) {" +
                "return text.substring(text.length() - (int)length, text.length());" +
                "}"),
        MID("public static String MID$ (String text, double start, double end) {" +
                "return text.substring((int)start, (int)start + (int)end);" +
                "}"),
        TAB("public static String TAB(double n) {StringBuilder tabBuilder = new StringBuilder();" +
                "for (int i = 0; i < n; i++) {" +
                "tabBuilder.append(\" \");" +
                "}" +
                "return tabBuilder.toString();" +
                "}");

        private final String java;

        Function(String java) {
            this.java = java;
        }

        public String getJava() {
            return java;
        }
    }

}