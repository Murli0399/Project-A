package com.todo.todo.controller;
import com.todo.todo.dto.TodoDTO;
import com.todo.todo.model.Todo;
import com.todo.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/rest")
@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/helth")
    public ResponseEntity<String> helth(){
        return new ResponseEntity<>("welcome to new web", HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Todo> create(@RequestBody Todo todo){
        Todo saveTodo = todoService.createTodo(todo);
        return new ResponseEntity<>(saveTodo, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Todo> update(@PathVariable String id, @RequestBody TodoDTO todoDTO){
        Todo saveTodo = todoService.updateTodo(id,todoDTO);
        return new ResponseEntity<>(saveTodo, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        todoService.deleteTodo(id);
        return new ResponseEntity<>("Task Deleted Successful",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/")
    public ResponseEntity<List<Todo>> viewAll(){
        List<Todo> todos = todoService.viewAll();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }
}
