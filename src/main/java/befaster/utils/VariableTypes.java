package befaster.utils;

public class VariableTypes {

    public static Types getType(String name) {
        return name.charAt(name.length() - 1) == '$' ? Types.STRING : Types.NUMBER;
    }

    public enum Types {
        STRING("String", "\"\""),
        NUMBER("double", "0");

        private String name;
        private String defaultValue;

        Types(String name, String defaultValue) {
            this.name = name;
            this.defaultValue = defaultValue;
        }

        public String getName() {
            return name;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }
}
