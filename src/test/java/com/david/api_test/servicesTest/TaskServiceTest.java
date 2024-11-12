package com.david.api_test.servicesTest;

import com.david.api_test.models.Task;
import com.david.api_test.models.Users;
import com.david.api_test.repository.TaskRepository;
import com.david.api_test.repository.UsersRepository;
import com.david.api_test.services.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testGetAllTasks() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> result = taskService.getAllTasks();

        assertEquals(tasks, result);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testGetTaskById() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(taskId);

        assertEquals(task, result);
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    public void testCreateTask() {
        Task task = new Task();
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);

        assertEquals(task, result);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testUpdateTask() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        Task updatedTask = new Task();
        updatedTask.setId(taskId);
        updatedTask.setDescription("Updated description");
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(updatedTask);

        Task result = taskService.updateTask(taskId, updatedTask);

        assertEquals(updatedTask, result);
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testDeleteTask() {
        Long taskId = 1L;

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    public void testGetTasksByUser() {
        Long userId = 1L;
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.findByUserId(userId)).thenReturn(tasks);

        List<Task> result = taskService.getTasksByUser(userId);

        assertEquals(tasks, result);
        verify(taskRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testGetTasksByCompany() {
        Long companyId = 1L;
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.findByUserCompanyId(companyId)).thenReturn(tasks);

        List<Task> result = taskService.getTasksByCompany(companyId);

        assertEquals(tasks, result);
        verify(taskRepository, times(1)).findByUserCompanyId(companyId);
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        Users user = new Users();
        user.setId(userId);
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        Users result = taskService.getUserById(userId);

        assertEquals(user, result);
        verify(usersRepository, times(1)).findById(userId);
    }
}
