public class Logger {
    public static boolean state = false;

    public static void log(String text) {
        if(state) {
            System.out.println(text);
        }
    }
}
