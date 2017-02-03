package befaster.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineNumberExtractor {


    private final Pattern pattern;

    public LineNumberExtractor() {
        pattern = Pattern.compile("^(\\d+)");
    }

    public String getLineNumber(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    public String removeLineNumber(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.replaceFirst("").trim();
        }
        return line;
    }
}
