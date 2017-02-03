package befaster.translators;

import befaster.utils.Dim;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class DimCommandTranslatorTest {

    @Test
    public void translate_simple_dim_command() throws Exception {

        final Set<Dim> arrays = new HashSet<>();
        final DimCommandTranslator dimCommand = new DimCommandTranslator(arrays);

        assertThat(dimCommand.translate("DIM A(40)"),
                is(equalTo("dimA = new double[(int)(40)];\n")));
        assertThat(dimCommand.translate("DIM A$(40)"),
                is(equalTo("dimA$ = new String[(int)(40)];\n")));

        assertThat(arrays.size(), is(equalTo(2)));
        assertThat(arrays, hasItems(hasProperty("name", equalTo("A")),
                hasProperty("name", equalTo("A$"))));
    }

    @Test
    public void translate_double_dim_command() throws Exception {

        final Set<Dim> arrays = new HashSet<>();
        final DimCommandTranslator dimCommand = new DimCommandTranslator(arrays);

        assertThat(dimCommand.translate("DIM A(H,V),B(H,V)"),
                is(equalTo("dimA = new double[(int)(H)][(int)(V)];\ndimB = new double[(int)(H)][(int)(V)];\n")));

        assertThat(arrays.size(), is(equalTo(2)));
        assertThat(arrays, hasItems(hasProperty("name", equalTo("A")),
                hasProperty("name", equalTo("B"))));
    }
}