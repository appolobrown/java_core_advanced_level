package ru.geekbrains.advancedjava.lesson3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PhoneBook {
    private HashMap<String, List<Person>> personPhoneBook;
    public static final Person[] initValues = new Person[]{
            new Person("Ivanov", "880005553535"),
            new Person("Petrov", "880005553536"),
            new Person("Sidorov", "880005553537"),
            new Person("Ivanov", "880005553538"),
            new Person("Petrov", "880005553539"),
            new Person("Stepanov", "880005553531")
    };

    public PhoneBook() {
        this.personPhoneBook = new HashMap<>();
        populatePhoneBook();
    }

    private void populatePhoneBook() {
        for (Person person : initValues) {
            putEntry(person);
        }
    }

    public void putEntry(Person person) {
        List<Person> personList;
        if (personPhoneBook.containsKey(person.getName())) {
            personList = personPhoneBook.get(person.getName());
        } else {
            personList = new ArrayList<>();
        }
        personList.add(person);
        personPhoneBook.put(person.getName(), personList);
    }

    public List<String> getPhonesByName(String name) {
        return personPhoneBook.get(name)
                .stream()
                .map(Person::getPhone)
                .collect(Collectors.toList());
    }

    public List<String> getEmailsByName(String name) {
        ArrayList<String> emails = new ArrayList<>();
        for (Person person : personPhoneBook.get(name)) {
            emails.add(person.getEmail());
        }
        return emails;
    }
}
