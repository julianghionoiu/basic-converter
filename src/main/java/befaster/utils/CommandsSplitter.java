package befaster.utils;

/**
 * Created by lyson on 08/01/2017.
 */
public class CommandsSplitter {

    private static final String PATTER = ":(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

    public static String[] split(String line) {
        return line.split(":(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
    }
}
