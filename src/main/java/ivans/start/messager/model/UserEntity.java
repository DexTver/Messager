package ivans.start.messager.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class UserEntity {
    @JsonProperty(value = "name", defaultValue = "Anonymous")
    private String name;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "repeatPassword")
    private String repeatPassword;
    @JsonProperty(value = "age", defaultValue = "18")
    private Integer age;

    public UserEntity(String name, Integer age, String password, String repeatPassword) {
        this.name = name;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public Integer getAge() {
        return age;
    }
}
