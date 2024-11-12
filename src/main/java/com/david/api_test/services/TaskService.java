package com.david.api_test.services;

import com.david.api_test.models.Task;
import com.david.api_test.models.Users;
import com.david.api_test.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UsersRepository usersRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        Task existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask != null) {
            existingTask.setDescription(task.getDescription());
            existingTask.setUser(task.getUser());

            return taskRepository.save(existingTask);
        } else {
            return null;
        }
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public List<Task> getTasksByCompany(Long companyId) {
        return taskRepository.findByUserCompanyId(companyId);
    }

    public Users getUserById(Long userId) {
        return usersRepository.findById(userId).orElse(null);
    }
}