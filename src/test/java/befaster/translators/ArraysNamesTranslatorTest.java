package befaster.translators;

import befaster.utils.Dim;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


public class ArraysNamesTranslatorTest {

    @Test
    public void translate_simple_array_name() throws Exception {
        final Set<Dim> arrays = new HashSet<>();
        arrays.add(new Dim("A$", 1));
        final ArraysNamesTranslator arraysNamesTranslator = new ArraysNamesTranslator(arrays);
        final String translate = arraysNamesTranslator.translate("PRINT A$(INT(13*RND(1)+1))");
        final String expected = "PRINT dimA$[(int)(INT(13*RND(1)+1)-1)]";

        assertThat(translate, is(equalTo(expected)));
    }

    @Test
    public void translate_array_name_if_used_more_than_once() throws Exception {
        final Set<Dim> arrays = new HashSet<>();
        arrays.add(new Dim("A$", 1));
        final ArraysNamesTranslator arraysNamesTranslator = new ArraysNamesTranslator(arrays);
        final String translate = arraysNamesTranslator.translate("PRINT A$(1), PRINT A$(2)");
        final String expected = "PRINT dimA$[(int)(1-1)], PRINT dimA$[(int)(2-1)]";

        assertThat(translate, is(equalTo(expected)));
    }

    @Test
    public void translate_multi_dimension_arrays_names() throws Exception {
        final Set<Dim> arrays = new HashSet<>();
        arrays.add(new Dim("W", 2));
        arrays.add(new Dim("V", 2));
        final ArraysNamesTranslator arraysNamesTranslator = new ArraysNamesTranslator(arrays);
        final String translate = arraysNamesTranslator.translate("W(R,S+1)=C:C=C+1:IF V(R,S)=0 THEN 940");
        final String expected = "dimW[(int)(R-1)][(int)(S+1-1)]=C:C=C+1:IF dimV[(int)(R-1)][(int)(S-1)]=0 THEN 940";

        assertThat(translate, is(equalTo(expected)));
    }
}