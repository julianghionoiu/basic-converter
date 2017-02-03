package befaster.translators;

import org.junit.Test;

import static befaster.utils.Names.LINE_NUMBER_VARIABLE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class GoToCommandTranslatorTest {

    @Test
    public void translate_simple_goto_command() throws Exception {
        final GoToCommandTranslator goToCommandTranslator = new GoToCommandTranslator();
        final String translate = goToCommandTranslator.translate("GOTO 330");
        final String expected = LINE_NUMBER_VARIABLE + " = 330;\nbreak;";
        assertThat(translate, is(equalTo(expected)));
    }
}