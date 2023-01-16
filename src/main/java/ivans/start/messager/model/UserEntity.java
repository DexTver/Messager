package ivans.start.messager.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty(value = "name", defaultValue = "Anonymous")
    @Column(name = "name")
    private String name;
    @JsonProperty(value = "password")
    @Column(name = "password")
    private String password;
    @JsonProperty(value = "repeatPassword")
    private String repeatPassword;
    @JsonProperty(value = "age", defaultValue = "18")
    @Column(name = "age")
    private Integer age;

    public UserEntity(String name, Integer age, String password, String repeatPassword) {
        this.name = name;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.age = age;
    }

    public UserEntity(Long id, String name, Integer age, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public UserEntity() {
    }

    public Long getId() {
        return id;
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

    @Override
    public String toString() {
        return name + ", " + age;
    }
}
