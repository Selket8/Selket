package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);
System.out.println("Введите имя: ");
String name = scanner.nextLine();
System.out.println("Привет, " + name);
scanner.close();
    }
}