package ivans.start.messager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty(value = "name", defaultValue = "Anonymous")
    private String name;

    @JsonProperty(value = "age", defaultValue = "18")
    private int age;

    public User() {
        age = 21;
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
