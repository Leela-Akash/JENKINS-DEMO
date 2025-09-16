package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")  // allow frontend access
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    // root route (/v)
    @GetMapping("/v")
    public String version() {
        return "Backend running v1.0";
    }

    @GetMapping
    public List<Task> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        return repository.save(task);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task) {
        Task existing = repository.findById(id).orElseThrow();
        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
