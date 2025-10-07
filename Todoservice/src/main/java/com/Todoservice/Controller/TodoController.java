package com.Todoservice.Controller;

import com.Todoservice.Repository.TodoRepository;
import com.Todoservice.UserClient.UserClient;
import com.Todoservice.model.Todo;
import com.Todoservice.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;
    private final UserClient userClient;

    // Create a Todo
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo,
                                           @RequestHeader("Authorization") String token) {
        // Always send Bearer prefix
        String authHeader = token.startsWith("Bearer ") ? token : "Bearer " + token;
        UserDto user = userClient.getUserById(todo.getUserId(), authHeader);

        if(user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Todo saved = todoRepository.save(todo);
        return ResponseEntity.ok(saved);
    }

    // Get Todos for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Todo>> getTodosByUser(@PathVariable Long userId,
                                                     @RequestHeader("Authorization") String token) {
        String authHeader = token.startsWith("Bearer ") ? token : "Bearer " + token;
        UserDto user = userClient.getUserById(userId, authHeader);

        if(user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        List<Todo> todos = todoRepository.findByUserId(userId);
        return ResponseEntity.ok(todos);
    }

    // Mark Todo as complete
    @PutMapping("/{id}/complete")
    public ResponseEntity<Todo> completeTodo(@PathVariable Long id) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setCompleted(true);
                    return ResponseEntity.ok(todoRepository.save(todo));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/hello")
    public String hello() {
        return "Welcome to TodoService on port 8082";
    }
}
