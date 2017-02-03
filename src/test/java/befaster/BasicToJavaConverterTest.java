package befaster;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BasicToJavaConverterTest {


    private BasicToJavaConverter basicToJavaConverter;

    @Before
    public void setUp() throws Exception {

        basicToJavaConverter = new BasicToJavaConverter();
        basicToJavaConverter.init();
    }

    @Test
    public void convert() throws Exception {

        String basicLine = "10 PRINT TAB(33);\"LUNAR\"";
        String expectedJavaLine = "case 10:\nSystem.out.println(TAB(33) + \" \" + \"LUNAR\");\n";

        final String convertedLine = basicToJavaConverter.translateLines(Collections.singletonList(basicLine));

        assertEquals(convertedLine, expectedJavaLine);

    }

    @Test
    public void print_command() throws Exception {

        String basicLine = "1060 PRINT \":--\";";
        String expectedJavaLine = "case 1060:\nSystem.out.print(\":--\");\n";

        final String convertedLine = basicToJavaConverter.translateLines(Collections.singletonList(basicLine));

        assertEquals(convertedLine, expectedJavaLine);

    }


    @Test
    public void variable_assign_with_RND_function_and_INT_cast() throws Exception {

        String basicLine = "999 A=INT(60000+(1000*RND(1))-(1000*RND(1)))";
        String expectedJavaLine = "case 999:\nA=(int)(60000+(1000*Math.random())-(1000*Math.random()));\n";

        final String convertedLine = basicToJavaConverter.translateLines(Collections.singletonList(basicLine));

        assertEquals(expectedJavaLine, convertedLine);

    }


    @Test
    public void print_with_INT_cast() throws Exception {

        String basicLine = "5 PRINT \" YOU MADE\";INT(V1-V2);\"RALLODS FROM TOURIST TRADE.\"";
        String expectedJavaLine = "case 5:\nSystem.out.println(\" YOU MADE\" + \" \" + (int)(V1-V2) + \" \" + \"RALLODS FROM TOURIST TRADE.\");\n";

        final String convertedLine = basicToJavaConverter.translateLines(Collections.singletonList(basicLine));

        assertEquals(expectedJavaLine, convertedLine);

    }

    @Test
    public void simple_FOR_command_in_one_line() throws Exception {
        String command = "5 FOR N=1 TO 15: PRINT \"HELLO\";: NEXT N";
        String expectedJavaLine = "case 5:\n" +
                "for (N=1; N <= 15; N++) { \n" +
                "System.out.print(\"HELLO\");\n" +
                "\n" +
                "}\n";

        final String convertedLine = basicToJavaConverter.translateLines(Collections.singletonList(command));

        assertEquals(expectedJavaLine, convertedLine);
    }

    @Test
    public void simple_FOR_command_in_many_lines() throws Exception {

        final List<String> lines = Arrays.asList("510 FOR J=1 TO 5",
                "515 PRINT \"HELLO\"",
                "517 NEXT J");

        String expected = "case 510:\n" +
                "for (J=1; J <= 5; J++) { \n" +
                "lineNumber = 515;\n" +
                "outer1:\n" +
                "while (true) {\n" +
                "switch (lineNumber) {\n" +
                "case 510:\n" +
                "\n" +
                "\n" +
                "case 515:\n" +
                "System.out.println(\"HELLO\");\n" +
                "\n" +
                "case 517:\n" +
                "lineNumber = 515;\n" +
                "break outer1;\n" +
                "}}}\n";

        final String translatedLines = basicToJavaConverter.translateLines(lines);
        assertEquals(expected, translatedLines);
    }

    @Test
    public void nested_FOR_command() throws Exception {

        final List<String> lines = Arrays.asList("510 FOR J=1 TO 5",
                "511 FOR K=1 TO 5",
                "515 PRINT \"HELLO\"",
                "516 NEXT K",
                "517 NEXT J");

        String expected = "case 510:\n" +
                "for (J=1; J <= 5; J++) { \n" +
                "lineNumber = 511;\n" +
                "outer1:\n" +
                "while (true) {\n" +
                "switch (lineNumber) {\n" +
                "case 510:\n" +
                "\n" +
                "\n" +
                "case 511:\n" +
                "for (K=1; K <= 5; K++) { \n" +
                "lineNumber = 515;\n" +
                "outer2:\n" +
                "while (true) {\n" +
                "switch (lineNumber) {\n" +
                "case 511:\n" +
                "\n" +
                "\n" +
                "case 515:\n" +
                "System.out.println(\"HELLO\");\n" +
                "\n" +
                "case 516:\n" +
                "lineNumber = 515;\n" +
                "break outer2;\n" +
                "}}}\n" +
                "\n" +
                "case 517:\n" +
                "lineNumber = 511;\n" +
                "break outer1;\n" +
                "}}}\n";

        final String translatedLines = basicToJavaConverter.translateLines(lines);
        assertEquals(expected, translatedLines);
    }

    @Test
    public void nested_FOR_command2() throws Exception {

        final List<String> lines = Arrays.asList("510 FOR J=1 TO 5",
                "511 FOR K=1 TO 5",
                "514 PRINT \"HELLO\"",
                "515 PRINT \"HELLO2\"",
                "516 NEXT K",
                "517 NEXT J");

        String expected = "case 510:\n" +
                "for (J=1; J <= 5; J++) { \n" +
                "lineNumber = 511;\n" +
                "outer1:\n" +
                "while (true) {\n" +
                "switch (lineNumber) {\n" +
                "case 510:\n" +
                "\n" +
                "\n" +
                "case 511:\n" +
                "for (K=1; K <= 5; K++) { \n" +
                "lineNumber = 514;\n" +
                "outer2:\n" +
                "while (true) {\n" +
                "switch (lineNumber) {\n" +
                "case 511:\n" +
                "\n" +
                "\n" +
                "case 514:\n" +
                "System.out.println(\"HELLO\");\n" +
                "\n" +
                "case 515:\n" +
                "System.out.println(\"HELLO2\");\n" +
                "\n" +
                "case 516:\n" +
                "lineNumber = 514;\n" +
                "break outer2;\n" +
                "}}}\n" +
                "\n" +
                "case 517:\n" +
                "lineNumber = 511;\n" +
                "break outer1;\n" +
                "}}}\n";

        final String translatedLines = basicToJavaConverter.translateLines(lines);
        assertEquals(expected, translatedLines);
    }

    @Test
    public void nested_FOR_command3() throws Exception {

        final List<String> lines = Arrays.asList("510 FOR J=1 TO 5",
                "511 FOR K=1 TO 5",
                "514 PRINT \"HELLO\"",
                "515 NEXT K",
                "516 PRINT \"HELLO2\"",
                "517 NEXT J");

        String expected = "case 510:\n" +
                "for (J=1; J <= 5; J++) { \n" +
                "lineNumber = 511;\n" +
                "outer1:\n" +
                "while (true) {\n" +
                "switch (lineNumber) {\n" +
                "case 510:\n" +
                "\n" +
                "\n" +
                "case 511:\n" +
                "for (K=1; K <= 5; K++) { \n" +
                "lineNumber = 514;\n" +
                "outer2:\n" +
                "while (true) {\n" +
                "switch (lineNumber) {\n" +
                "case 511:\n" +
                "\n" +
                "\n" +
                "case 514:\n" +
                "System.out.println(\"HELLO\");\n" +
                "\n" +
                "case 515:\n" +
                "lineNumber = 514;\n" +
                "break outer2;\n" +
                "}}}\n" +
                "\n" +
                "case 516:\n" +
                "System.out.println(\"HELLO2\");\n" +
                "\n" +
                "case 517:\n" +
                "lineNumber = 511;\n" +
                "break outer1;\n" +
                "}}}\n";

        final String translatedLines = basicToJavaConverter.translateLines(lines);
        assertEquals(expected, translatedLines);
    }


    @Test
    public void FOR_command_with_complex_NEXT() throws Exception {
        final List<String> lines = Arrays.asList("410 FOR Z=3 TO LEN(Q$)","415 IF MID$(Q$,Z,1)<>\"\\\" THEN PRINT MID$(Q$,Z,1);: NEXT Z");

        String expected = "case 410:\n" +
                "for (Z=3; Z <= LEN(Q$); Z++) { \n" +
                "lineNumber = 415;\n" +
                "outer1:\n" +
                "while (true) {\n" +
                "switch (lineNumber) {\n" +
                "case 410:\n" +
                "\n" +
                "\n" +
                "case 415:\n" +
                "if (!MID$(Q$,Z,1).equals(\"\\\\\")) { System.out.print(MID$(Q$,Z,1)); }\n" +
                "lineNumber = 415;\n" +
                "break outer1;\n" +
                "}}}\n";

        final String translatedLines = basicToJavaConverter.translateLines(lines);
        assertEquals(expected, translatedLines);
    }


}