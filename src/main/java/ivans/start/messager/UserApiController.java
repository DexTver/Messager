package ivans.start.messager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RestController
public class UserApiController {
    private final java.util.List<User> users = new ArrayList<>();

    // curl localhost:8080/users
    @GetMapping("users")
    public ResponseEntity<java.util.List<User>> getUsers() {
        return ResponseEntity.ok(users);
    }

    // curl -X POST localhost:8080/users -H "Content-Type: application/json" -d {"name": "Ivan", "age": "17"}
    @PostMapping("users")
    public ResponseEntity<Void> addUser(
            @RequestBody User user) {
        users.add(user);
        return ResponseEntity.accepted().build();
    }

    // curl localhost:8080/users/1
    @GetMapping("users/{index}")
    public ResponseEntity<String> getUser(
            @PathVariable("index") Integer index) {
        if (index < users.size()) {
            return ResponseEntity.ok(users.get(index).toString());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Index out of range");
    }

    // curl -X DELETE localhost:8080/users/0
    @DeleteMapping("users/{index}")
    public ResponseEntity<String> deleteUser(
            @PathVariable("index") Integer index) {
        if (index < users.size()) {
            users.remove((int) index);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Index out of range");
    }

    // curl -X POST localhost:8080/users/2/create -H "Content-Type: text/plain" -d "18"
    @PostMapping("users/{index}/update_age")
    public ResponseEntity<String> updateAge(
            @PathVariable("index") Integer index,
            @RequestBody String age) {
        if (index < users.size()) {
            User main = users.get(index);
            main.setAge(Integer.parseInt(age));
            users.set(index, main);
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Index out of range");
    }
}
