package org.example;

import java.util.Scanner;

public class Keyboard {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите одну маленькую букву английского алфавита: ");
        String input = scanner.nextLine();

        if (input.length() != 1 || !Character.isLowerCase(input.charAt(0))){
            System.out.println("Пожалуйста, введите маленькую букву");
            return;
        }

        char letter = input.charAt(0);
        String keyboard = "qwertyuiopasdfghjklzxcvbnm";
        int index = keyboard.indexOf(letter);

        if (index == -1) {
            System.out.println("Буква не найдена");
            return;
        }

        int LeftIndex = (index - 1 + keyboard.length()) % keyboard.length();

        char leftLetter = keyboard.charAt(LeftIndex);
        System.out.println("Буква слева: " + leftLetter);
    }
}