package src;

public class Log {

    public static void print(String message) {
        System.out.println(message);
    }

    public static void debug(String s) {
        if (Constants.DEBUG) {
            print(s);
        }
    }
}
