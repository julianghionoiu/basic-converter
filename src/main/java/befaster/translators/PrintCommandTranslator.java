package befaster.translators;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrintCommandTranslator implements BasicTranslator {

    private final String QUOTE = "\"";
    private FunctionsTranslator functionsTranslator;

    public PrintCommandTranslator(FunctionsTranslator functionsTranslator) {
        this.functionsTranslator = functionsTranslator;
    }

    @Override
    public boolean isCommand(String line) {
        return line.startsWith("PRINT");
    }

    @Override
    public String translate(String command) {

        final List<String> arguments = new ArrayList<>();
        command = command.replace("PRINT", "").trim();
        String sysOutCommand = getSysOutCommand(command);
        final String[] split = command.split(";");
        for (String s : split) {
            if (s.startsWith("TAB(")) {
                arguments.add(s);
            } else if (s.startsWith(QUOTE)) {
                final int startQuote = s.indexOf(QUOTE);
                final int endQuote = s.indexOf(QUOTE, startQuote + 1);
                final String substring = s.substring(startQuote, endQuote + 1);
                arguments.add(substring);
            } else {
                arguments.add(functionsTranslator.translate(s));
            }
        }

        final StringJoiner stringJoiner = new StringJoiner(" + \" \" + ");
        arguments.forEach(stringJoiner::add);

        return sysOutCommand + "(" + stringJoiner.toString() + ");";
    }


    public static String TAB(double n) {
        StringBuilder tabBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            tabBuilder.append(" ");
        }
        return tabBuilder.toString();
    }

    public String getSysOutCommand(String command) {
        if (command.length() > 0 && command.charAt(command.length() - 1) == ';') {
            return "System.out.print";
        }
        return "System.out.println";
    }
}
