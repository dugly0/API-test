package com.david.api_test.controllers;

import com.david.api_test.models.Role;
import com.david.api_test.models.Task;
import com.david.api_test.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks(@RequestHeader("User-Id") Long userId,
                                 @RequestHeader("User-Role") Role role) {
        if (role == Role.SUPER_USER) {
            return taskService.getAllTasks();
        } else if (role == Role.COMPANY_ADMIN) {
            return taskService.getTasksByCompany(userId);
        } else {
            return taskService.getTasksByUser(userId);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id,
                                           @RequestHeader("User-Id") Long userId,
                                           @RequestHeader("User-Role") Role role) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        if (role == Role.SUPER_USER || 
            (role == Role.COMPANY_ADMIN && task.getUser().getCompany().getId() == userId) ||
            (role == Role.STANDARD && task.getUser().getId() == userId)) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task,
                                         @RequestHeader("User-Id") Long userId,
                                         @RequestHeader("User-Role") Role role) {
        if (role == Role.STANDARD || role == Role.COMPANY_ADMIN) {
            task.setUser(taskService.getUserById(userId));
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, 
                                         @RequestBody Task task,
                                         @RequestHeader("User-Id") Long userId,
                                         @RequestHeader("User-Role") Role role) {
        Task existingTask = taskService.getTaskById(id);
        if (existingTask == null) {
            return ResponseEntity.notFound().build();
        }

        if (role == Role.SUPER_USER || 
            (role == Role.COMPANY_ADMIN && existingTask.getUser().getCompany().getId() == userId) ||
            (role == Role.STANDARD && existingTask.getUser().getId() == userId)) {
            task.setId(id);
            Task updatedTask = taskService.updateTask(id, task);
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id,
                                           @RequestHeader("User-Id") Long userId,
                                           @RequestHeader("User-Role") Role role) {
        Task existingTask = taskService.getTaskById(id);
        if (existingTask == null) {
            return ResponseEntity.notFound().build();
        }

        if (role == Role.SUPER_USER || 
            (role == Role.COMPANY_ADMIN && existingTask.getUser().getCompany().getId() == userId) ||
            (role == Role.STANDARD && existingTask.getUser().getId() == userId)) {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}