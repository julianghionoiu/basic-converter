package befaster.translators;

import org.junit.Test;

import static befaster.utils.Names.LINE_NUMBER_VARIABLE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class OnCommandTranslatorTest {

    @Test
    public void translate_simple_on_command() throws Exception {
        final OnCommandTranslator onCommandTranslator = new OnCommandTranslator();
        final String translate = onCommandTranslator.translate("ON X GOTO 790,820,910");
        final String expected = "if ((int)(X) == 1) {" + LINE_NUMBER_VARIABLE + " = 790;break;}" +
                "if ((int)(X) == 2) {" + LINE_NUMBER_VARIABLE + " = 820;break;}" +
                "if ((int)(X) == 3) {" + LINE_NUMBER_VARIABLE + " = 910;break;}";
        assertThat(translate, is(equalTo(expected)));
    }

}