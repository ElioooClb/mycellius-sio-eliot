package fr.mycellius;

public class Person {
    private final String name;
    private final int age;
    public Person(String name, int age) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nom obligatoire");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Âge invalide");
        }
        this.name = name.trim();
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public void sayHello() {
        System.out.println("Bonjour, je m'appelle " + name + " et j'ai " + age + " ans.");
    }
}