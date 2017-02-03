package befaster.translators;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionsTranslatorTest {

    private DeclarationsAndInitializationsBuilder declarationsAndInitializationsBuilder;
    private FunctionsTranslator functionsTranslator;

    @Before
    public void setUp() throws Exception {
        declarationsAndInitializationsBuilder = new DeclarationsAndInitializationsBuilder();
        this.functionsTranslator = new FunctionsTranslator(declarationsAndInitializationsBuilder);
    }

    @Test
    public void translate_RND_function() throws Exception {
        assertThat(functionsTranslator.translate("RND(1)"),
                is(equalTo("Math.random()")));
    }

    @Test
    public void translate_INT_function() throws Exception {
        assertThat(functionsTranslator.translate("INT(5/2)"), is(equalTo("(int)(5/2)")));
    }

    @Test
    public void translate_CHR_function() throws Exception {
        assertThat(functionsTranslator.translate("CHR$(2)"), is(equalTo("(char)(2)")));
    }

    @Test
    public void translate_ASC_function() throws Exception {
        assertThat(functionsTranslator.translate("ASC(A$)"), is(equalTo("ASC(A$)")));
        String ASCFunction = "public static int ASC(String s) {char c = s.charAt(0);return (int)c;}";
        final DeclarationsAndInitializations build = declarationsAndInitializationsBuilder.build();
        assertThat(build.getFunctions(), is(equalTo(ASCFunction)));
    }

    @Test
    public void translate_LEFT_function() throws Exception {
        assertThat(functionsTranslator.translate("LEFT$(A$,1)"), is(equalTo("LEFT$(A$,1)")));
        String leftFunction = "public static String LEFT$(String text, double length) " +
                "{return text.substring(0, (int)length);}";
        final DeclarationsAndInitializations build = declarationsAndInitializationsBuilder.build();
        assertThat(build.getFunctions(), is(equalTo(leftFunction)));
    }

    @Test
    public void translate_RIGHT_function() throws Exception {
        assertThat(functionsTranslator.translate("RIGHT$(A$,1)"), is(equalTo("RIGHT$(A$,1)")));
        String rightFunction = "public static String RIGHT$(String text, double length) " +
                "{return text.substring(text.length() - (int)length, text.length());}";
        final DeclarationsAndInitializations build = declarationsAndInitializationsBuilder.build();
        assertThat(build.getFunctions(), is(equalTo(rightFunction)));
    }

    @Test
    public void translate_LEN_function() throws Exception {
        assertThat(functionsTranslator.translate("LEN(A$)"), is(equalTo("LEN(A$)")));
        String lenFunction = "public static double LEN(String s) {return s.length();}";
        final DeclarationsAndInitializations build = declarationsAndInitializationsBuilder.build();
        assertThat(build.getFunctions(), is(equalTo(lenFunction)));
    }

    @Test
    public void translate_MID_function() throws Exception {

        assertThat(functionsTranslator.translate("MID$(A$,8,8)"), is(equalTo("MID$(A$,8,8)")));
        String midFunction = "public static String MID$ (String text, double start, double end) {return text.substring((int)start, (int)start + (int)end);}";
        final DeclarationsAndInitializations build = declarationsAndInitializationsBuilder.build();
        assertThat(build.getFunctions(), is(equalTo(midFunction)));
    }

    @Test
    public void translate_TAB_function() throws Exception {

        assertThat(functionsTranslator.translate("TAB(5)"), is(equalTo("TAB(5)")));
        final DeclarationsAndInitializations build = declarationsAndInitializationsBuilder.build();
        assertThat(build.getFunctions(), is(equalTo(DeclarationsAndInitializationsBuilder.Function.TAB.getJava())));
    }

    @Test
    public void translate_VAL_function() throws Exception {
        assertThat(functionsTranslator.translate("VAL(A$)"), is(equalTo("Double.parseDouble(A$)")));
    }

    @Test
    public void translate_STR_function() throws Exception {
        assertThat(functionsTranslator.translate("STR$(5)"), is(equalTo("String.valueOf(5)")));
    }

    @Test
    public void translate_SQR_function() throws Exception {
        assertThat(functionsTranslator.translate("SQR(5)"), is(equalTo("Math.sqrt(5)")));
    }

    @Test
    public void translate_ABS_function() throws Exception {
        assertThat(functionsTranslator.translate("ABS(5)"), is(equalTo("Math.abs(5)")));
    }

}