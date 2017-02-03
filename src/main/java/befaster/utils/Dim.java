package befaster.utils;

/**
 * Helper class for arrays description.
 */
public class Dim {
    private String name;
    private int dimension;
    public final static String PREFIX = "dim";

    public Dim(String name, int dimension) {
        this.name = name;
        this.dimension = dimension;
    }

    public String getName() {
        return name;
    }

    public String getNameWithPrefix() {
        return PREFIX + name;
    }

    public int getDimension() {
        return dimension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dim dim = (Dim) o;

        return name != null ? name.equals(dim.name) : dim.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
