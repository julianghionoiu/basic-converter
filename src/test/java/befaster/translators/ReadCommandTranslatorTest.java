package befaster.translators;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ReadCommandTranslatorTest {

    @Test
    public void translate_simple_read_command() throws Exception {
        final ReadCommandTranslator readCommandTranslator = new ReadCommandTranslator();
        final String translateNumber = readCommandTranslator.translate("READ K[M]");
        final String translateString = readCommandTranslator.translate("READ K$[M]");
        final String expectedNumber = "K[M] = (double) globalDataList.removeFirst();";
        final String expectedString = "K$[M] = (String) globalDataList.removeFirst();";

        assertThat(translateNumber, is(equalTo(expectedNumber)));
        assertThat(translateString, is(equalTo(expectedString)));
    }

}