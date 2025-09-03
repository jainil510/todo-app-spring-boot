
package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This is the Controller for our To-Do API.
 * @RestController tells Spring that this class will handle incoming web requests.
 * @RequestMapping("/api/todos") sets the base path for all methods in this class.
 */
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    /**
     * Spring needs to give us an instance of the TodoRepository we created.
     * This is called "Dependency Injection". @Autowired tells Spring to do this.
     */
    @Autowired
    private TodoRepository todoRepository;

    /**
     * Handles GET requests to /api/todos
     * It fetches all Todo items from the database and returns them.
     */
    @GetMapping
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    /**
     * Handles POST requests to /api/todos
     * It takes the request body (which should be a JSON object representing a Todo),
     * saves it to the database, and returns the newly created Todo.
     * @RequestBody tells Spring to convert the incoming JSON into a Todo object.
     */
    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    /**
     * Handles PUT requests to /api/todos/{id}
     * It finds the existing Todo, updates its properties, saves it, and returns the updated object.
     * @PathVariable tells Spring to take the {id} from the URL and pass it to the method.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo existingTodo = optionalTodo.get();
            existingTodo.setTitle(todoDetails.getTitle());
            existingTodo.setCompleted(todoDetails.isCompleted());
            final Todo updatedTodo = todoRepository.save(existingTodo);
            return ResponseEntity.ok(updatedTodo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Handles DELETE requests to /api/todos/{id}
     * It finds the Todo by its ID and deletes it from the database.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            todoRepository.delete(optionalTodo.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
