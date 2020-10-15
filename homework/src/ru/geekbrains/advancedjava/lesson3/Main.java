package ru.geekbrains.advancedjava.lesson3;

import java.util.*;

public class Main {

    public static final String text = "Hash table based implementation of the Map interface. This " +
            "implementation provides all of the optional map operations, and permits " +
            "null values and the null key. (The HashMap " +
            "class is roughly equivalent to Hashtable, except that it is " +
            "unsynchronized and permits nulls.) This class makes no guarantees as to " +
            "the order of the map; in particular, it does not guarantee that the order " +
            "will remain constant over time.";

    public static void main(String[] args) {

        Set<String> words = new HashSet<>(textAsWordList(text));
        System.out.println(words);

        HashMap<String, Integer> counted = countWords(text);
        counted.forEach((key, value) -> System.out.printf("word %s : %d times \n", key, value));

        PhoneBook phoneBook = new PhoneBook();
        phoneBook.putEntry(new Person("Ivanov", "8902020202"));
        phoneBook.putEntry(new Person("Petrov", "8902030303"));

        phoneBook.getEmailsByName("Ivanov").forEach(email -> System.out.printf("%s \n", email));
        phoneBook.getPhonesByName("Petrov").forEach(phone -> System.out.printf("%s \n", phone));
    }

    private static HashMap<String, Integer> countWords(String text) {
        HashMap<String, Integer> result = new HashMap<>();
        if (text == null || text.isBlank()) return result;
        textAsWordList(text).forEach(s -> {
            if (result.containsKey(s)) {
                result.put(s, result.get(s) + 1);
            } else result.put(s, 1);
        });
        return result;
    }

    private static List<String> textAsWordList(String text) {
        return Arrays.asList(text
                .toLowerCase()
                .replaceAll("[.,();!?\\-]", "")
                .split(" "));
    }

}
