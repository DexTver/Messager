// https://github.com/DexTver/Messager/tree/master/src/main/java/ivans/start/messager

package ivans.start.messager.controller;

import ivans.start.messager.model.Name;
import ivans.start.messager.model.User;
import ivans.start.messager.model.UserEntity;
import ivans.start.messager.repositories.NameRepository;
import ivans.start.messager.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverse;

@Controller
@RestController
public class UserApiController {

    private final UserRepository userRepository;
    private final NameRepository nameRepository;

    public UserApiController(UserRepository userRepository, NameRepository nameRepository) {
        this.userRepository = userRepository;
        this.nameRepository = nameRepository;
    }

    // curl -X POST localhost:8080/users -H "Content-Type: application/json" -d '{"name": "Ivan", "age": 17, "password": "IR01vS", "repeatPassword": "IR01vS"}'
    @PostMapping("users")
    public ResponseEntity addUser(
            @RequestBody UserEntity user) {
        String _name = user.getName();
        Optional<Name> nameData = nameRepository.findByName(_name);
        if (user.getPassword().equals(user.getRepeatPassword()) && (nameData.isPresent() ? !nameData.get().isIs_existed() : true)) {
            userRepository.save(user);
            nameRepository.save(new Name(_name, true));
            return ResponseEntity.ok(new User(user));
        }
        if (!user.getPassword().equals(user.getRepeatPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords don't equals!");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Name is existed!");
    }

    // curl localhost:8080/users/1
    @GetMapping("users/{index}")
    public ResponseEntity<String> getUser(
            @PathVariable("index") Integer index) {
        Optional<UserEntity> userData = userRepository.findById(Long.valueOf(index));
        if (userData.isPresent()) {
            return ResponseEntity.ok(userData.toString());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
    }

    // curl -X DELETE localhost:8080/users/0 123123
    @DeleteMapping("users/{index}")
    public ResponseEntity<String> deleteTheUser(
            @PathVariable("index") Integer index,
            @RequestBody String password) {
        Long _id = Long.valueOf(index);
        if (userRepository.findById(_id).isPresent()) {
            String _name = userRepository.findById(_id).get().getName();
            if (!Objects.equals(userRepository.findById(_id).get().getPassword(), password)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password!");
            }
            userRepository.deleteById(_id);
            nameRepository.deleteById(_name);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
    }

    @DeleteMapping("users")
    public ResponseEntity<String> deleteUser(
            @RequestBody UserEntity user) {
        String _name = user.getName();
        Optional<UserEntity> userData = userRepository.findByName(_name);
        if (userData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found!");
        }
        String myPassword = userData.get().getPassword();
        String theirPassword = user.getPassword(), theirRepeatPassword = user.getRepeatPassword();
        if (theirRepeatPassword.equals("") || theirRepeatPassword.equals(theirPassword)) {
            if (myPassword.equals(theirPassword)) {
                userRepository.deleteById(userRepository.findByName(_name).get().getId());
                nameRepository.deleteById(_name);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords don't equals!");
    }

    // curl -X PUT localhost:8080/users/0 -H "Content-Type: application/json" -d '{"name": "Ivan", "age": 17, "password": "krA0bk", "repeatPassword": "krA0bk"}'
    @PutMapping("users/{index}")
    public ResponseEntity<String> updateAge(
            @PathVariable("index") Integer index,
            @RequestBody UserEntity user) {
        Optional<UserEntity> userData = userRepository.findById(Long.valueOf(index));
        String _name = user.getName();
        Optional<Name> nameData = nameRepository.findByName(_name);
        if (userData.isPresent() && user.getPassword().equals(user.getRepeatPassword()) &&
                (userData.get().getName().equals(user.getName()) || (nameData.isPresent() ? !nameData.get().isIs_existed() : true))) {
            userRepository.save(new UserEntity(Long.valueOf(index), user.getName(), user.getAge(), user.getPassword()));
            return ResponseEntity.accepted().build();
        }
        if (userData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
        if (!user.getPassword().equals(user.getRepeatPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords don't equals!");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Name is existed!");
    }

    // curl localhost:8080/users
    // curl localhost:8080/users?sortBy=name
    // curl localhost:8080/users?age=18&sortBy=age
    // curl localhost:8080/users?number=1&limit=2
    @GetMapping("users")
    public ResponseEntity<List<User>> getUsers(
            @RequestParam(value = "age", required = false) Integer age,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "direction", required = false) String direction,
            @RequestParam(value = "number", required = false) Integer number,
            @RequestParam(value = "limit", required = false) Integer limit) {
        List<UserEntity> _users = userRepository.findAll();
        // халява! стоит исправить
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : _users) {
            users.add(new User(userEntity.getName(), userEntity.getAge()));
        }
        if (sortBy != null) {
            if (sortBy.equals("name")) {
                users.sort(Comparator.comparing(User::getName));
            } else if (sortBy.equals("age")) {
                users.sort(Comparator.comparing(User::getAge));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        if (direction != null && direction.equals("down")) {
            reverse(users);
        }
        if (age != null) {
            users.removeIf(user -> !(user.getAge() >= age - 5) || (user.getAge() <= age + 5));
        }
        if (number == null || limit == null) {
            return ResponseEntity.ok(users);
        }
        if (number >= 0 && limit > 0) {
            return ResponseEntity.ok(users.stream().skip((long) limit * number).toList().stream().limit(limit).collect(Collectors.toList()));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
