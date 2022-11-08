package ivans.start.messager;

public class User {
    private String name;
    private int age;

    public User() {
        age = 21;
        name = "Anonymous";
    }

    public User(String name) {
        age = 21;
        this.name = name;
    }

    public User(int age) {
        this.age = age;
        name = "Anonymous";
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return name + ", " + age;
    }
}
