package befaster.translators;

import befaster.utils.Dim;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ArraysNamesTranslator implements BasicTranslator {

    private final Set<Dim> arrays;

    public ArraysNamesTranslator(Set<Dim> arrays) {
        this.arrays = arrays;
    }

    @Override
    public boolean isCommand(String line) {
        return !getArraysNames(line).isEmpty();
    }

    @Override
    public String translate(String line) {
        List<String> arraysNames = getArraysNames(line);
        while (!arraysNames.isEmpty()) {
            arraysNames = getArraysNames(line);
            for (String arrayName : arraysNames) {
                final int startArrayIndex = line.indexOf(arrayName + "(");
                int openBracketsCount = 0;
                int closeBracketsCount = 0;
                int endArrayIndex = 0;

                for (int i = startArrayIndex; i < line.length(); i++) {
                    final char c = line.charAt(i);
                    if (c == '(') {
                        openBracketsCount++;
                    } else if (c == ')') {
                        closeBracketsCount++;
                    }
                    if (openBracketsCount != 0 && closeBracketsCount != 0 &&
                            openBracketsCount == closeBracketsCount) {
                        endArrayIndex = i;
                        break;
                    }
                }

                final String beginOfCommand = line.substring(0, startArrayIndex);
                final String endOfCommand = line.substring(endArrayIndex + 1, line.length());
                final String[] arrayArguments = line.substring(startArrayIndex + arrayName.length() + 1, endArrayIndex).split(",");

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Dim.PREFIX + arrayName);
                stringBuilder.append("[");

                for (int i = 0; i < arrayArguments.length; i++) {
                    stringBuilder.append("(int)(");
                    stringBuilder.append(arrayArguments[i]);
                    stringBuilder.append("-1)");
                    if (i < arrayArguments.length - 1) {
                        stringBuilder.append("][");
                    }
                }
                stringBuilder.append("]");
                String javaArrayDef = stringBuilder.toString();
                line = beginOfCommand + javaArrayDef + endOfCommand;
            }
        }
        return line;
    }

    private List<String> getArraysNames(String line) {
        final ArrayList<String> arrayNames = new ArrayList<>();
        for (Dim array : arrays) {
            if (line.contains(array.getName() + "(")) {
                arrayNames.add(array.getName());
            }
        }
        return arrayNames;
    }


}
