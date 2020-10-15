package ru.geekbrains.advancedjava.lesson3;

public class Person {
    private String name;
    private String phone;
    private String email;

    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.email = this.name + phone + "@evil.corp";
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
