package befaster.translators;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class EndCommandTranslatorTest {

    @Test
    public void translate_simple_end_command() throws Exception {
        final EndCommandTranslator endCommandTranslator = new EndCommandTranslator();
        final String translate = endCommandTranslator.translate("END");
        final String expected = "System.exit(0);";
        assertThat(translate, is(equalTo(expected)));
    }

}