package befaster.translators;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PrintCommandTranslatorTest {


    private FunctionsTranslator functionsTranslator;
    private PrintCommandTranslator printCommand;

    @Before
    public void setUp() throws Exception {

        functionsTranslator = new FunctionsTranslator(new DeclarationsAndInitializationsBuilder());
        printCommand = new PrintCommandTranslator(functionsTranslator);
    }

    @Test
    public void translate_simple_print_command() throws Exception {
        assertThat(printCommand.translate("PRINT \"LUNAR\""),
                is(equalTo("System.out.println(\"LUNAR\");")));
    }

    @Test
    public void translate_empty_print_command() throws Exception {
        assertThat(printCommand.translate("PRINT"),
                is(equalTo("System.out.println();")));
    }

    @Test
    public void translate_variable_print_command() throws Exception {
        String command = "PRINT L";
        String expectedResult = "System.out.println(L);";
        assertThat(printCommand.translate(command), is(equalTo(expectedResult)));
    }

    @Test
    public void translate_text_and_variable_print_command() throws Exception {
        String command = "PRINT \"FUEL OUT AT\";L";
        String expectedResult = "System.out.println(\"FUEL OUT AT\" + \" \" + L);";
        assertThat(printCommand.translate(command), is(equalTo(expectedResult)));
    }

    @Test
    public void translate_text_and_tab_print_command() throws Exception {
        String command = "PRINT TAB(5); \"FUEL OUT AT\"";
        String expectedResult = "System.out.println(TAB(5) + \" \" +  \"FUEL OUT AT\");";
        assertThat(printCommand.translate(command), is(equalTo(expectedResult)));
    }

    @Test
    public void translate_print_command_with_tab_and_variable() throws Exception {
        String command = "PRINT TAB(5*X)";
        String expectedResult = "System.out.println(TAB(5*X));";
        assertThat(printCommand.translate(command), is(equalTo(expectedResult)));
    }

}