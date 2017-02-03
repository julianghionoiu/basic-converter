package befaster.translators;

import org.junit.Test;

import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;


public class DataCommandTranslatorTest {

    @Test
    public void translate_data_command() throws Exception {
        final LinkedList<Object> data = new LinkedList<>();
        final DataCommandTranslator dataCommandTranslator = new DataCommandTranslator(data);
        String intData = "DATA 3,102,103";
        String stringData = "DATA \"ABILITY\",\"BASAL\",\"BEHAVIORAL\"";

        dataCommandTranslator.translate(intData);
        assertThat(data, hasItems(3.0, 102.0, 103.0));
        data.clear();
        dataCommandTranslator.translate(stringData);
        assertThat(data, hasItems("ABILITY", "BASAL", "BEHAVIORAL"));
    }
}