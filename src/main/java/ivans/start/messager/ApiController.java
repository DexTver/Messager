package ivans.start.messager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RestController
public class ApiController {
    private final java.util.List<String> messages = new ArrayList<>();

    // curl localhost:8080/messages
    // curl localhost:8080/messages?prefix=aba
    @GetMapping("messages")
    public ResponseEntity<java.util.List<String>> getMessages(
            @RequestParam(value = "prefix", required = false) String prefix) {
        if (prefix == null) {
            return ResponseEntity.ok(messages);
        }
        java.util.List<String> prefixMessages = new ArrayList<>();
        for (String message : messages) {
            if (message.startsWith(prefix)) {
                prefixMessages.add(message);
            }
        }
        return ResponseEntity.ok(prefixMessages);
    }

    // curl -X POST localhost:8080/messages -H "Content-Type: text/plain" -d "ababa"
    // curl -X POST localhost:8080/messages -H "Content-Type: text/plain" -d "abrba"
    // curl -X POST localhost:8080/messages -H "Content-Type: text/plain" -d "abaca"
    @PostMapping("messages")
    public ResponseEntity<Void> addMessage(
            @RequestBody String text) {
        messages.add(text);
        return ResponseEntity.accepted().build();
    }

    // curl localhost:8080/messages/0
    @GetMapping("messages/{index}")
    public ResponseEntity<String> getMessage(
            @PathVariable("index") Integer index) {
        return ResponseEntity.ok(messages.get(index));
    }

    // curl -X DELETE localhost:8080/messages/0
    @DeleteMapping("messages/{index}")
    public ResponseEntity<Void> deleteText(
            @PathVariable("index") Integer index) {
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

    // curl localhost:8080/messages/search/aba
    @GetMapping("messages/search/{text}")
    public ResponseEntity<Integer> getMessageIndex(
            @PathVariable("text") String text) {
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).startsWith(text)) {
                return ResponseEntity.ok(i);
            }
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // curl localhost:8080/messages/count
    @GetMapping("messages/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(messages.size());
    }

    // curl -X POST localhost:8080/messages/2/create -H "Content-Type: text/plain" -d "dbdbd"
    @PostMapping("messages/{index}/create")
    public ResponseEntity<Void> createMessage(
            @PathVariable("index") Integer index,
            @RequestBody String text) {
        if (index < messages.size()) {
            messages.add(index, text);
        } else {
            messages.add(text);
        }
        return ResponseEntity.accepted().build();
    }

    // curl -X DELETE localhost:8080/messages/search/cbc
    @DeleteMapping("messages/search/{text}")
    public ResponseEntity<Void> deleteMessages(
            @PathVariable("text") String text) {
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).startsWith(text)) {
                messages.remove(i);
            }
        }
        return ResponseEntity.noContent().build();
    }

}