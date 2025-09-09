package dz7_collections;
import java.util.Arrays;
import java.util.Scanner;
public class AnagramChecker {
    public static boolean isAnagram(String s, String t) {
        String sClean = s.replaceAll("\\s+", "").toLowerCase();
        String tClean = t.replaceAll("\\s+", "").toLowerCase();
        if (sClean.length() != tClean.length()) {
            return false;
        }
        char[] sChars = sClean.toCharArray();
        char[] tChars = tClean.toCharArray();
        Arrays.sort(sChars);
        Arrays.sort(tChars);
        return Arrays.equals(sChars, tChars);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите первую строку: ");
        String s = scanner.nextLine();
        System.out.println("Введите вторую строку: ");
        String t = scanner.nextLine();
        boolean result = isAnagram(s, t);
        System.out.println(result);
        scanner.close();
    }
}
