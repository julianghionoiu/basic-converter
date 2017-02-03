package befaster.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;


public class CommandsSplitterTest {

    @Test
    public void split_commands() throws Exception {
        String line1 = "PRINT \":--\";";
        String line2 = "PRINT \"--\"; : PRINT \"--\";";
        final String[] commands1 = CommandsSplitter.split(line1);
        final String[] commands2 = CommandsSplitter.split(line2);

        assertThat(commands1.length, is(equalTo(1)));
        assertThat(commands2.length, is(equalTo(2)));
    }

}