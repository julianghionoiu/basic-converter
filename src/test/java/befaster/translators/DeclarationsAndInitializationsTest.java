package befaster.translators;

import befaster.utils.Dim;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class DeclarationsAndInitializationsTest {

    @Test
    public void translate_arrays_declarations() throws Exception {

        final Set<Dim> arrays = new HashSet<>();
        arrays.add(new Dim("A", 1));
        arrays.add(new Dim("B$", 1));
        final DeclarationsAndInitializations declarationsAndInitializations =
                new DeclarationsAndInitializationsBuilder().withArrays(arrays).build();

        final String arraysDeclarations = declarationsAndInitializations.getArraysDeclarations();
        final String expected = "double dimA[] = null;String dimB$[] = null;";

        assertThat(arraysDeclarations, is(equalTo(expected)));
    }

    @Test
    public void translate_two_dimension_array_declarations() throws Exception {

        final Set<Dim> arrays = new HashSet<>();
        arrays.add(new Dim("A", 2));
        final DeclarationsAndInitializations declarationsAndInitializations =
                new DeclarationsAndInitializationsBuilder().withArrays(arrays).build();

        final String arraysDeclarations = declarationsAndInitializations.getArraysDeclarations();
        final String expected = "double dimA[][] = null;";

        assertThat(arraysDeclarations, is(equalTo(expected)));
    }

    @Test
    public void translate_variables_declarations() throws Exception {

        final Set<String> variablesNames = new HashSet<>();
        variablesNames.add("A");
        variablesNames.add("B$");

        final DeclarationsAndInitializations declarationsAndInitializations =
                new DeclarationsAndInitializationsBuilder().withVariableNames(variablesNames).build();

        final String variablesDeclarations = declarationsAndInitializations.getVariablesDeclarations();
        final String expected = "double A = 0;String B$ = \"\";";

        assertThat(variablesDeclarations, is(equalTo(expected)));
    }

    @Test
    public void translate_global_data_initialisation() throws Exception {

        final LinkedList<Object> globalDataList = new LinkedList<>();
        globalDataList.add("HELLO");
        globalDataList.add(5);

        final DeclarationsAndInitializations declarationsAndInitializations =
                new DeclarationsAndInitializationsBuilder().withGlobalDataList(globalDataList).build();

        final String globalData = declarationsAndInitializations.getGlobalData();
        final String expected = "final LinkedList<Object> globalDataList = new LinkedList<>();" +
                "globalDataList.add(\"HELLO\");globalDataList.add(5);";

        assertThat(globalData, is(equalTo(expected)));
    }

}