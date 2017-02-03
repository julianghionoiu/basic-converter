package befaster.translators;

import befaster.utils.Dim;
import befaster.utils.VariableTypes;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.LinkedList;
import java.util.Set;

public class DeclarationsAndInitializations {

    private final String VARIABLES_TEMPLATE = "%s %s = %s;";

    private final Set<Dim> arrays;
    private final Set<String> variableNames;
    private final LinkedList<Object> globalDataList;
    private final Set<DeclarationsAndInitializationsBuilder.Function> functions;
    private final boolean scanner;

    DeclarationsAndInitializations(Set<Dim> arrays, Set<String> variableNames,
                                   LinkedList<Object> globalDataList, Set<DeclarationsAndInitializationsBuilder.Function> functions,
                                   boolean scanner) {
        this.arrays = arrays;
        this.variableNames = variableNames;
        this.globalDataList = globalDataList;
        this.functions = functions;
        this.scanner = scanner;
    }

    public String getArraysDeclarations() {
        StringBuilder stringBuilder = new StringBuilder();
        arrays.forEach(array -> {
                    final VariableTypes.Types type = VariableTypes.getType(array.getName());

                    stringBuilder.append(type.getName());
                    stringBuilder.append(" ");
                    stringBuilder.append(array.getNameWithPrefix());
                    for (int i = 0; i < array.getDimension(); i++) {
                        stringBuilder.append("[]");
                    }
                    stringBuilder.append(" = null;");
                }
         );
        return stringBuilder.toString();
    }

    public String getVariablesDeclarations() {
        StringBuilder stringBuilder = new StringBuilder();
        variableNames.forEach(variableName -> {
                    final VariableTypes.Types type = VariableTypes.getType(variableName);
                    stringBuilder.append(String.format(VARIABLES_TEMPLATE, type.getName(), variableName, type.getDefaultValue()));
                }
        );
        return stringBuilder.toString();
    }


    public String getGlobalData() {
        if (globalDataList.isEmpty()) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder("final LinkedList<Object> globalDataList = new LinkedList<>();");
        globalDataList.forEach(globalData -> {
                    if (NumberUtils.isNumber(globalData.toString())) {
                        stringBuilder.append(String.format("globalDataList.add(%s);", globalData));
                    } else {
                        stringBuilder.append(String.format("globalDataList.add(\"%s\");", globalData));
                    }
                }
        );
        return stringBuilder.toString();
    }

    public String getFunctions() {
        StringBuilder stringBuilder = new StringBuilder();
        functions.forEach(f -> stringBuilder.append(f.getJava()));
        return stringBuilder.toString();
    }

    public String getScannerDeclaration() {
        if (scanner) {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Scanner sc = new Scanner(System.in);");
            stringBuilder.append("\n");
            stringBuilder.append("String input = \"\";");
            return stringBuilder.toString();
        }
        return "";
    }

}
