package befaster.acceptance;

import befaster.BasicToJavaConverter;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Test if generated files are grammatically correct.
 */
public class FormattingTest {

    @Test
    public void test_is_formatting() {
        convert("afile");
    }

    private void convert(String fileName) {
        BasicToJavaConverter basicToJavaConverter = new BasicToJavaConverter();
        final URL url = getClass().getClassLoader().getResource("basic/" + fileName + ".bas");

        try (Stream<String> stream = Files.lines(Paths.get(url.toURI()))) {
            basicToJavaConverter.translate(StringUtils.capitalize(fileName), stream.collect(Collectors.toList()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


}
