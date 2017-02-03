package befaster.translators;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ForCommandTranslatorTest {

    private ForCommandTranslator forCommandTranslator;
    private Set<String> variablesNames;

    @Before
    public void setUp() throws Exception {
        variablesNames = new HashSet<>();
        forCommandTranslator = new ForCommandTranslator(variablesNames);
    }

    @Test
    public void translate_simple_for_command() throws Exception {
        assertThat(forCommandTranslator.translate("FOR N=1 TO 15"),
                is(equalTo("for (N=1; N <= 15; N++) { ")));
        assertThat(variablesNames, hasItem("N"));
    }

    @Test
    public void translate_for_command_with_variable_boundary() throws Exception {
        assertThat(forCommandTranslator.translate("FOR I=1 TO H"),
                is(equalTo("for (I=1; I <= H; I++) { ")));
        assertThat(variablesNames, hasItem("I"));
    }

    @Test
    public void translate_simple_for_command_with_step() throws Exception {
        assertThat(forCommandTranslator.translate("FOR O1= 1 TO 15 STEP 2"),
                is(equalTo("for (O1= 1; O1 <= 15; O1 = O1 + 2) { ")));
        assertThat(variablesNames, hasItem("O1"));
    }

    @Test
    public void translate_for_command_with_start_expression() throws Exception {
        assertThat(forCommandTranslator.translate("FOR Y=X+1 TO LEN(Q$)"),
                is(equalTo("for (Y=X+1; Y <= LEN(Q$); Y++) { ")));
    }

}