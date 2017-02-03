package befaster.translators;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


public class InputCommandTranslatorTest {


    private final DeclarationsAndInitializationsBuilder declarationsAndInitializationsBuilder = new DeclarationsAndInitializationsBuilder();
    private PrintCommandTranslator printCommandTranslator;

    @Before
    public void setUp() throws Exception {
        printCommandTranslator = new PrintCommandTranslator(new FunctionsTranslator(declarationsAndInitializationsBuilder));
    }

    @Test
    public void translate_simple_input_commands() throws Exception {

        final Set<String> variablesNames = new HashSet<>();
        variablesNames.add("Y");
        final InputCommandTranslator inputCommandTranslator =
                new InputCommandTranslator(variablesNames, printCommandTranslator, declarationsAndInitializationsBuilder);

        final String commandInputNewString = "INPUT Y$ ";
        final String commandInputExistingNumber = "INPUT Y ";

        final String newString = inputCommandTranslator.translate(commandInputNewString);
        final String existingNumber = inputCommandTranslator.translate(commandInputExistingNumber);

        final String expectedNewString = "input = sc.next();\n" +
                "Y$ = input;\n";

        final String expectedExistingNumber = "input = sc.next();\n" +
                "Y = Double.parseDouble(input);\n";

        assertThat(newString, is(equalTo(expectedNewString)));
        assertThat(existingNumber, is(equalTo(expectedExistingNumber)));

    }

    @Test
    public void translate_input_command_with_print() throws Exception {
        String command = "INPUT \"WHAT IS YOUR AGE\";A";

        final String expected= "System.out.println(\"WHAT IS YOUR AGE\");\n" +
                "input = sc.next();\n" +
                "A = Double.parseDouble(input);\n";

        final InputCommandTranslator inputCommandTranslator =
                new InputCommandTranslator(new HashSet<>(), printCommandTranslator, declarationsAndInitializationsBuilder);
        final String translatedCommand = inputCommandTranslator.translate(command);
        assertThat(translatedCommand, is(equalTo(expected)));
    }


    @Test
    public void test_scanner_declaration() throws Exception {
        final InputCommandTranslator inputCommandTranslator =
                new InputCommandTranslator(new HashSet<>(), printCommandTranslator, declarationsAndInitializationsBuilder);

        assertThat(declarationsAndInitializationsBuilder.build().getScannerDeclaration(), is(equalTo("")));

        String command = "INPUT A";

        final String expected = "Scanner sc = new Scanner(System.in);\n" +
                "String input = \"\";";

        inputCommandTranslator.translate(command);
        assertThat(declarationsAndInitializationsBuilder.build().getScannerDeclaration(), is(equalTo(expected)));
    }


    @Test
    public void translate_many_input_commands() throws Exception {
        String command = "INPUT A,B";
        final InputCommandTranslator inputCommandTranslator =
                new InputCommandTranslator(new HashSet<>(), printCommandTranslator, declarationsAndInitializationsBuilder);
        final String translatedCommand = inputCommandTranslator.translate(command);

        final String expected =
                "input = sc.next();\n" +
                "A = Double.parseDouble(input);\n" +
                        "input = sc.next();\n" +
                        "B = Double.parseDouble(input);\n";

        assertThat(translatedCommand, is(equalTo(expected)));
    }


}