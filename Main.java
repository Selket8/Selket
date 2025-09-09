package org.example;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PowerfulSet ps = new PowerfulSet();

        System.out.println("Выберите операцию:");
        System.out.println("1 - Пересечение (intersection)");
        System.out.println("2 - Объединение (union)");
        System.out.println("3 - Уникальность (relativeComplement)");
        System.out.print("Введите номер операции (1, 2 или 3): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // очистка буфера

        System.out.print("Введите первую строку, элементы через запятую: ");
        String input1 = scanner.nextLine();
        System.out.print("Введите вторую строку, элементы через запятую: ");
        String input2 = scanner.nextLine();

        Set<String> set1 = parseInputToSet(input1);
        Set<String> set2 = parseInputToSet(input2);

        switch (choice) {
            case 1:
                Set<String> intersection = ps.intersection(set1, set2);
                System.out.println("Результат пересечения: " + setToString(intersection));
                break;
            case 2:
                Set<String> union = ps.union(set1, set2);
                System.out.println("Результат объединения: " + setToString(union));
                break;
            case 3:
                Set<String> relative = ps.relativeComplement(set1, set2);
                System.out.println("Элементы первого набора без общих: " + setToString(relative));
                break;
            default:
                System.out.println("Некорректный выбор операции.");
        }

        scanner.close();
    }


    private static Set<String> parseInputToSet(String input) {
        // Разделяем по запятой, удаляем лишние пробелы и фильтруем пустые
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
    }


    private static String setToString(Set<String> set) {
        return String.join(", ", set);
    }
}


class PowerfulSet {


    public <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }


    public <T> Set<T> union(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.addAll(set2);
        return result;
    }


    public <T> Set<T> relativeComplement(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.removeAll(set2);
        return result;
    }
}
