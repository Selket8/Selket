import java.util.Scanner;

public class Arrows {
    public static void main(String[] ags){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите последовательность из символов ('>', '<', '-'): ");
        String sequence = scanner.nextLine();

        if (sequence.length() > 1_000_000) {
            System.out.println("Длина последовтательности превышает 1 000 000 симовлолв");
            return;
        }

        int count = 0;
        int lenght = sequence.length();

        for (int i = 0; i < lenght - 4; i ++) {
            String substring = sequence.substring(i, i + 5);
            if (substring.equals(">>-->") || substring.equals("<--<<")) {
                count++;
            }
        }

        System.out.println("Количество спрятанных стрел: " + count);
    }
}
