public class Dye {
    
    private static String dye(String _color, String _string) {
        return (char)27 + _color + _string + (char)27 + "[0m" + "";
    }

    public static String red(String _string) {
        return dye(Color.RED, _string);
    } 

    public static String green(String _string) {
        return dye(Color.GREEN, _string);
    }

    public static String yellow(String _string) {
        return dye(Color.YELLOW, _string);
    }

    public static String blue(String _string) {
        return dye(Color.BLUE, _string);
    }

    public static String magenta(String _string) {
        return dye(Color.MAGENTA, _string);
    }

    public static String cyan(String _string) {
        return dye(Color.CYAN, _string);
    }
}
