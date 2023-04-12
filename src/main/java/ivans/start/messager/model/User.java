package ivans.start.messager.model;

public class User {
    private final String name;

    private final int age;


    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User(UserEntity user) {
        this.name = user.getName();
        this.age = user.getAge();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return name + ", " + age;
    }
}
