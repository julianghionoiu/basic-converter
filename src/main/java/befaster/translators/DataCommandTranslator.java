package befaster.translators;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.LinkedList;
import java.util.stream.Stream;

public class DataCommandTranslator implements BasicTranslator {

    private final LinkedList<Object> globalDataList;

    public DataCommandTranslator(LinkedList<Object> globalDataList) {
        this.globalDataList = globalDataList;
    }

    @Override
    public boolean isCommand(String line) {
        return line.startsWith("DATA");
    }

    @Override
    public String translate(String line) {
        line = line.replace("DATA", "").replaceAll("\"", "");
        Stream.of(line.split(",")).forEach(data -> {
            final String trimmed = data.trim();
            if (NumberUtils.isNumber(trimmed)) {
                globalDataList.add(Double.valueOf(trimmed));
            } else {
                globalDataList.add(trimmed);
            }
        });
        return "";
    }
}
