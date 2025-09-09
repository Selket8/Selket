package dz7_collections;

import java.util.*;

public class UniqueWordsFinder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите список слов через запятую:");
        String input = scanner.nextLine();

        ArrayList<String> list = parseInputToList(input);

        Set<String> uniqueWords = getUniqueElements(list);

        System.out.println("Уникальные слова:");
        for (String word : uniqueWords) {
            System.out.println(word);
        }

        scanner.close();
    }

    private static ArrayList<String> parseInputToList(String input) {
        String[] parts = input.split(",");
        ArrayList<String> list = new ArrayList<>();
        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                list.add(trimmed);
            }
        }
        return list;
    }

    public static <T> Set<T> getUniqueElements(ArrayList<T> list) {
        return new HashSet<>(list);
    }
}