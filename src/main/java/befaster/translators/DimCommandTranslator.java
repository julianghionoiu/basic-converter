package befaster.translators;

import befaster.utils.Dim;
import befaster.utils.VariableTypes;

import java.util.Set;

public class DimCommandTranslator implements BasicTranslator {

    private final Set<Dim> arrays;

    public DimCommandTranslator(Set<Dim> arrays) {
        this.arrays = arrays;
    }

    @Override
    public boolean isCommand(String line) {
        return line.startsWith("DIM");
    }

    @Override
    public String translate(String line) {

        final String[] arrays = line.replaceFirst("DIM", "").trim().split("(?<=\\)),");

        final StringBuilder stringBuilder = new StringBuilder();
        for (String array : arrays) {
            final int startSize = array.indexOf("(");
            final int endSize = array.indexOf(")");

            final String arrayName = array.substring(0, startSize);
            final VariableTypes.Types type = VariableTypes.getType(arrayName);
            final String[] arrayArgs = array.substring(startSize + 1, endSize).split(",");
            this.arrays.add(new Dim(arrayName, arrayArgs.length));

            stringBuilder.append(Dim.PREFIX + arrayName);
            stringBuilder.append(" = new ");
            stringBuilder.append(type.getName());
            for (int i = 0; i < arrayArgs.length; i++) {
                stringBuilder.append("[(int)(");
                stringBuilder.append(arrayArgs[i]);
                stringBuilder.append(")]");
            }
            stringBuilder.append(";\n");
        }

        return stringBuilder.toString();
    }
}
