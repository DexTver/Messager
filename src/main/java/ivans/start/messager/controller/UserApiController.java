// https://github.com/DexTver/Messager/tree/master/src/main/java/ivans/start/messager

package ivans.start.messager.controller;

import ivans.start.messager.model.User;
import ivans.start.messager.model.UserEntity;
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

    int id = 0;
    private final Map<Integer, User> viewUsers = new HashMap<>();
    private final Map<Integer, UserEntity> privateUsers = new HashMap<>();
    private final HashSet<String> uniqueNames = new HashSet<>();

    // curl -X POST localhost:8080/users -H "Content-Type: application/json" -d '{"name": "Ivan", "age": 17, "password": "IR01vS", "repeatPassword": "IR01vS"}'
    @PostMapping("users")
    public ResponseEntity addUser(
            @RequestBody UserEntity user) {
        if (user.getPassword().equals(user.getRepeatPassword()) && !uniqueNames.contains(user.getName())) {
            privateUsers.put(id, user);
            viewUsers.put(id, new User(user.getName(), user.getAge()));
            id++;
            uniqueNames.add(user.getName());
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
        if (viewUsers.containsKey(index)) {
            return ResponseEntity.ok(viewUsers.get(index).toString());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
    }

    // curl -X DELETE localhost:8080/users/0
    @DeleteMapping("users/{index}")
    public ResponseEntity<String> deleteUser(
            @PathVariable("index") Integer index) {
        if (viewUsers.containsKey(index)) {
            uniqueNames.remove(viewUsers.get(index).getName());
            viewUsers.remove(index);
            privateUsers.remove(index);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
    }

    // curl -X POST localhost:8080/users/0 -H "Content-Type: application/json" -d '{"name": "Ivan", "age": 17, "password": "krA0bk", "repeatPassword": "krA0bk"}'
    @PostMapping("users/{index}")
    public ResponseEntity<String> updateAge(
            @PathVariable("index") Integer index,
            @RequestBody UserEntity user) {
        if (viewUsers.containsKey(index) && (!uniqueNames.contains(user.getName()) || viewUsers.get(index).getName().equals(user.getName())) && user.getPassword().equals(user.getRepeatPassword())) {
            uniqueNames.add(user.getName());
            privateUsers.put(index, user);
            viewUsers.put(index, new User(user.getName(), user.getAge()));
            return ResponseEntity.accepted().build();
        }
        if (!user.getPassword().equals(user.getRepeatPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords don't equals!");
        }
        if (!viewUsers.containsKey(index)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
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
        List<User> users = new ArrayList<>(viewUsers.values());
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
