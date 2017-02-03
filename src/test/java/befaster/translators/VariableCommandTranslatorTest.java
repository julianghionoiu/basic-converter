package befaster.translators;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class VariableCommandTranslatorTest {


    @Test
    public void translate_simple_variable_command() throws Exception {
        final Set<String> variablesNames = new HashSet<>();
        final VariableCommandTranslator variableCommand = new VariableCommandTranslator(variablesNames);

        assertThat(variableCommand.translate("L=0"), is(equalTo("L=0;")));
        assertThat(variableCommand.translate("L$=0"), is(equalTo("L$=0;")));

        assertThat(variablesNames.size(), is(equalTo(2)));
        assertThat(variablesNames, hasItems("L", "L$"));

    }

}