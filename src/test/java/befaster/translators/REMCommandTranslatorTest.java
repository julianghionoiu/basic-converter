package befaster.translators;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class REMCommandTranslatorTest {
    @Test
    public void translate_simple_rem_command() throws Exception {
        final REMCommandTranslator remCommandTranslator = new REMCommandTranslator();
        final String translate = remCommandTranslator.translate("REM     SUBROUTINE TO PRINT QUESTIONS");
        final String expected = "//     SUBROUTINE TO PRINT QUESTIONS";
        assertThat(translate, is(equalTo(expected)));
    }

}