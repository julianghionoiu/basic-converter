package befaster.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


public class LineNumberExtractorTest {

    @Test
    public void get_line_number() throws Exception {
        final LineNumberExtractor lineNumberExtractor = new LineNumberExtractor();
        final String lineNumber10 = lineNumberExtractor.getLineNumber("10 PRINT TAB(26);\"BUZZWORD GENERATOR\"");
        assertThat(lineNumber10, is(equalTo("10")));
        final String lineNumber999 = lineNumberExtractor.getLineNumber("999 PRINT \"COME BACK WHEN YOU NEED HELP WITH ANOTHER REPORT!\":END");
        assertThat(lineNumber999, is(equalTo("999")));
    }

    @Test
    public void remove_line_number() throws Exception {
        final LineNumberExtractor lineNumberExtractor = new LineNumberExtractor();
        final String line10 = lineNumberExtractor.removeLineNumber("10 PRINT TAB(26);\"BUZZWORD GENERATOR\"");
        final String expectedLine10 = "PRINT TAB(26);\"BUZZWORD GENERATOR\"";
        assertThat(line10, is(equalTo(expectedLine10)));
        final String line999 = lineNumberExtractor.removeLineNumber("999 PRINT \"COME BACK WHEN YOU NEED HELP WITH ANOTHER REPORT!\":END");
        final String expectedLine999 = "PRINT \"COME BACK WHEN YOU NEED HELP WITH ANOTHER REPORT!\":END";
        assertThat(line999, is(equalTo(expectedLine999)));
    }

}