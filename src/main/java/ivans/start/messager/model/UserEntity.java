package ivans.start.messager.model;


public class UserEntity {
    private String name;
    private String password;
    private Integer age;

    public UserEntity(String name, String password, Integer age) {
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public UserEntity() {
        name = "Anonymous";
        password = name;
        age = 18;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
