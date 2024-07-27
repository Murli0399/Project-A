package com.todo.todo.service;

import com.todo.todo.dto.TodoDTO;
import com.todo.todo.model.Todo;
import com.todo.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(String id, TodoDTO todo) {
        Optional<Todo> oldTodo = todoRepository.findById(id);
        if(!oldTodo.isPresent())
            throw new RuntimeException("Task Not Present");

        Todo old = oldTodo.get();
        old.setName(todo.getName());
        old.setDescription(todo.getDescription());
        return todoRepository.save(old);
    }

    public void deleteTodo(String id) {
        Optional<Todo> oldTodo = todoRepository.findById(id);
        if(!oldTodo.isPresent())
            throw new RuntimeException("Task Not Present");

        todoRepository.deleteById(id);
    }

    public List<Todo> viewAll() {
        return todoRepository.findAll();
    }

}
