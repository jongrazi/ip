package friday.parser;

public class Parser {
    public static String[] splitOnce(String s, String regex) {
        return s.split(regex, 2);
    }

    public static int indexOf(String s, String sub) {
        return s.indexOf(sub);
    }
}
