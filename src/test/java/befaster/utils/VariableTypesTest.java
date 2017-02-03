package befaster.utils;

import befaster.utils.VariableTypes;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class VariableTypesTest {

    @Test
    public void recognize_string_and_numeric_types() throws Exception {
        final VariableTypes.Types stringType = VariableTypes.getType("StringType$");
        final VariableTypes.Types numericType = VariableTypes.getType("NumericType");

        assertThat(stringType, is(equalTo(VariableTypes.Types.STRING)));
        assertThat(numericType, is(equalTo(VariableTypes.Types.NUMBER)));
    }




}