import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        for (String s : Arrays.asList(
                null, "", "a", "ab", "abc", "abcd", "abcde")) {
            System.out.println("half of " + s + " == " + halfOrDie(s));
        }
    }

    private static String halfOrDie(String s) {
        if (s == null || s.length() == 0) {
            return "I need a string";
        }
        int i = s.length() % 2;
        return i == 0 ? s.substring(0, (i / 2) + 1) : "String length is odd";
    }
}
