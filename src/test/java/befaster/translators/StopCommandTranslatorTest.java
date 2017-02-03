package befaster.translators;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class StopCommandTranslatorTest {

    @Test
    public void translate_simple_stop_command() throws Exception {
        final StopCommandTranslator stopCommandTranslator = new StopCommandTranslator();
        final String translate = stopCommandTranslator.translate("STOP");
        final String expected = "break;";
        assertThat(translate, is(equalTo(expected)));
    }
}