package befaster.translators;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static befaster.utils.Names.LINE_NUMBER_VARIABLE;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;


public class IfCommandTranslatorTest {


    private IfCommandTranslator ifCommandTranslator;
    private Set<String> variablesNames;

    @Before
    public void setUp() throws Exception {
        variablesNames = new HashSet<>();
        final VariableCommandTranslator variableCommandTranslator = new VariableCommandTranslator(variablesNames);
        final DeclarationsAndInitializationsBuilder declarationsAndInitializationsBuilder = new DeclarationsAndInitializationsBuilder();
        final FunctionsTranslator functionsTranslator = new FunctionsTranslator(declarationsAndInitializationsBuilder);
        final PrintCommandTranslator printCommandTranslator = new PrintCommandTranslator(functionsTranslator);
        ifCommandTranslator = new IfCommandTranslator(variableCommandTranslator, printCommandTranslator);
    }

    @Test
    public void translate_simple_if_commands() throws Exception {
        final String simpleIfCommand = "IF G<=5 THEN 508";
        final String ifWithAndCommand = "IF H<>1 AND V=1 THEN 110";
        final String ifWithStringNotEquals = "IF LEFT$(A$,1)<>\"Y\" THEN 120";

        final String translatedSimpleIfCommand = ifCommandTranslator.translate(simpleIfCommand);
        final String translatedIfWithAndCommand = ifCommandTranslator.translate(ifWithAndCommand);
        final String translatedIfWithStringNotEquals = ifCommandTranslator.translate(ifWithStringNotEquals);
        final String expectedSimpleIfCommand = "if (G<=5) { " + LINE_NUMBER_VARIABLE + " = 508;\nbreak; }";
        final String expectedIfWithAndCommand = "if (H!=1 && V==1) { " + LINE_NUMBER_VARIABLE + " = 110;\nbreak; }";
        final String expectedIfWithStringNotEquals = "if (!LEFT$(A$,1).equals(\"Y\")) { " + LINE_NUMBER_VARIABLE + " = 120;\nbreak; }";
        assertThat(translatedSimpleIfCommand, is(equalTo(expectedSimpleIfCommand)));
        assertThat(translatedIfWithAndCommand, is(equalTo(expectedIfWithAndCommand)));
        assertThat(translatedIfWithStringNotEquals, is(equalTo(expectedIfWithStringNotEquals)));

    }

    @Test
    @Ignore
    public void translate_nested_if_command() throws Exception {
        String command = "IF J<>0 THEN IF J<>6 THEN 105";
    }

    @Test
    public void translate_if_command_with_print() throws Exception {
        String command = "IF W<=1.2 THEN PRINT \"PERFECT\"";
        final String translated = ifCommandTranslator.translate(command);
        final String expected = "if (W<=1.2) { System.out.println(\"PERFECT\"); }";
        assertThat(translated, is(equalTo(expected)));
    }

    @Test
    public void translate_if_command_with_var_assign() throws Exception {
        String command = "IF N(J,K)>999 THEN B=10";
        final String translated = ifCommandTranslator.translate(command);
        final String expected = "if (N(J,K)>999) { B=10; }";
        assertThat(translated, is(equalTo(expected)));
        assertThat(variablesNames, hasItem("B"));
    }

    @Test
    @Ignore
    public void translate_if_command_with_gosub() throws Exception {
        String command = "IF N(J,K)>99 THEN GOSUB 200";
    }

}