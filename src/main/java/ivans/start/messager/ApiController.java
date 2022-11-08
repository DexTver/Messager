package ivans.start.messager;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RestController
public class ApiController {
    private final java.util.List<String> messages = new ArrayList<>();

    // curl localhost:8080/messages
    @GetMapping("messages")
    public ResponseEntity<java.util.List<String>> getMessages() {
        return ResponseEntity.ok(messages);
    }

    // curl -X POST localhost:8080/messages -H "Content-Type: text/plain" -d "ababa"
    @PostMapping("messages")
    public ResponseEntity<Void> addMessage(@RequestBody String text) {
        messages.add(text);
        return ResponseEntity.accepted().build();
    }

    // curl localhost:8080/messages/0
    @GetMapping("messages/{index}")
    public ResponseEntity<String> getMessage(@PathVariable("index") Integer
                                                     index) {
        return ResponseEntity.ok(messages.get(index));
    }

    // curl -X DELETE localhost:8080/messages/0
    @DeleteMapping("messages/{index}")
    public ResponseEntity<Void> deleteText(@PathVariable("index") Integer
                                                   index) {
        messages.remove((int) index);
        return ResponseEntity.noContent().build();
    }

    // curl -X PUT localhost:8080/messages/0 -H "Content-Type: text/plain" -d "cbcbc"
    @PutMapping("messages/{index}")
    public ResponseEntity<Void> updateMessage(
            @PathVariable("index") Integer i,
            @RequestBody String message) {
        messages.remove((int) i);
        messages.add(i, message);
        return ResponseEntity.accepted().build();
    }
}