package com.david.api_test.controllerstest;

import com.david.api_test.controllers.TaskController;
import com.david.api_test.models.*;
import com.david.api_test.services.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    public void testGetAllTasks_SuperUser() {
         
        Long userId = 1L;
        Role role = Role.SUPER_USER;
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskService.getAllTasks()).thenReturn(tasks);
        List<Task> result = taskController.getAllTasks(userId, role);
        assertEquals(tasks, result);
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    public void testGetAllTasks_CompanyAdmin() {
         
        Long userId = 1L;
        Role role = Role.COMPANY_ADMIN;
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskService.getTasksByCompany(userId)).thenReturn(tasks);
        List<Task> result = taskController.getAllTasks(userId, role);
        assertEquals(tasks, result);
        verify(taskService, times(1)).getTasksByCompany(userId);
    }

    @Test
    public void testGetAllTasks_StandardUser() {
         
        Long userId = 1L;
        Role role = Role.STANDARD;
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskService.getTasksByUser(userId)).thenReturn(tasks);
        List<Task> result = taskController.getAllTasks(userId, role);
        assertEquals(tasks, result);
        verify(taskService, times(1)).getTasksByUser(userId);
    }

    @Test
    public void testGetTaskById_SuperUser() {
         
        Long taskId = 1L;
        Long userId = 1L;
        Role role = Role.SUPER_USER;
        Task task = new Task();
        task.setId(taskId);
        when(taskService.getTaskById(taskId)).thenReturn(task);
        ResponseEntity<Task> response = taskController.getTaskById(taskId, userId, role);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
        verify(taskService, times(1)).getTaskById(taskId);
    }

    @Test
    public void testGetTaskById_CompanyAdmin_Authorized() {
        Long taskId = 1L;
        Long userId = 1L;
        Role role = Role.COMPANY_ADMIN;
        Task task = new Task();
        task.setId(taskId);
        Users user = new Users();
        user.setCompany(new Company());
        user.getCompany().setId(userId); 
        task.setUser(user);
        when(taskService.getTaskById(taskId)).thenReturn(task);
        ResponseEntity<Task> response = taskController.getTaskById(taskId, userId, role);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
        verify(taskService, times(1)).getTaskById(taskId);
    }

    @Test
    public void testGetTaskById_StandardUser_Authorized() {
        Long taskId = 1L;
        Long userId = 1L;
        Role role = Role.STANDARD;
        Task task = new Task();
        task.setId(taskId);
        Users user = new Users();
        user.setId(userId);
        task.setUser(user);
        when(taskService.getTaskById(taskId)).thenReturn(task);
        ResponseEntity<Task> response = taskController.getTaskById(taskId, userId, role);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
        verify(taskService, times(1)).getTaskById(taskId);
    }

    @Test
    public void testCreateTask_StandardUser() {   
        Long userId = 1L;
        Role role = Role.STANDARD;
        Task task = new Task();
        Task createdTask = new Task();
        createdTask.setId(1L);
        when(taskService.getUserById(userId)).thenReturn(new Users());
        when(taskService.createTask(task)).thenReturn(createdTask);
        ResponseEntity<Task> response = taskController.createTask(task, userId, role);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdTask, response.getBody());
        verify(taskService, times(1)).getUserById(userId);
        verify(taskService, times(1)).createTask(task);
    }

    @Test
    public void testCreateTask_CompanyAdmin() {   
        Long userId = 1L;
        Role role = Role.COMPANY_ADMIN;
        Task task = new Task();
        Task createdTask = new Task();
        createdTask.setId(1L);
        when(taskService.getUserById(userId)).thenReturn(new Users());
        when(taskService.createTask(task)).thenReturn(createdTask);
        ResponseEntity<Task> response = taskController.createTask(task, userId, role);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdTask, response.getBody());
        verify(taskService, times(1)).getUserById(userId);
        verify(taskService, times(1)).createTask(task);
    }

    @Test
    public void testUpdateTask_SuperUser() {
         
        Long taskId = 1L;
        Long userId = 1L;
        Role role = Role.SUPER_USER;
        Task task = new Task();
        task.setId(taskId);
        Task updatedTask = new Task();
        updatedTask.setId(taskId);
        when(taskService.getTaskById(taskId)).thenReturn(task);
        when(taskService.updateTask(taskId, task)).thenReturn(updatedTask);
        ResponseEntity<Task> response = taskController.updateTask(taskId, task, userId, role);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTask, response.getBody());
        verify(taskService, times(1)).getTaskById(taskId);
        verify(taskService, times(1)).updateTask(taskId, task);
    }
    @Test
public void testUpdateTask_CompanyAdmin_Authorized() {
    // Arrange
    Long taskId = 1L;
    Long userId = 1L;
    Role role = Role.COMPANY_ADMIN;
    Task task = new Task();
    task.setId(taskId);
    Users user = new Users();
    user.setCompany(new Company());
    user.getCompany().setId(userId);
    task.setUser(user);
    Task updatedTask = new Task();
    updatedTask.setId(taskId);
    when(taskService.getTaskById(taskId)).thenReturn(task);
    when(taskService.updateTask(taskId, task)).thenReturn(updatedTask);

    // Act
    ResponseEntity<Task> response = taskController.updateTask(taskId, task, userId, role);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(updatedTask, response.getBody());
    verify(taskService, times(1)).getTaskById(taskId);
    verify(taskService, times(1)).updateTask(taskId, task);
}

@Test
public void testUpdateTask_StandardUser_Authorized() {
    // Arrange
    Long taskId = 1L;
    Long userId = 1L;
    Role role = Role.STANDARD;
    Task task = new Task();
    task.setId(taskId);
    Users user = new Users();
    user.setId(userId);
    task.setUser(user);
    Task updatedTask = new Task();
    updatedTask.setId(taskId);
    when(taskService.getTaskById(taskId)).thenReturn(task);
    when(taskService.updateTask(taskId, task)).thenReturn(updatedTask);

    // Act
    ResponseEntity<Task> response = taskController.updateTask(taskId, task, userId, role);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(updatedTask, response.getBody());
    verify(taskService, times(1)).getTaskById(taskId);
    verify(taskService, times(1)).updateTask(taskId, task);
}

@Test
public void testDeleteTask_SuperUser() {
    // Arrange
    Long taskId = 1L;
    Long userId = 1L;
    Role role = Role.SUPER_USER;
    Task task = new Task();
    task.setId(taskId);
    when(taskService.getTaskById(taskId)).thenReturn(task);

    // Act
    ResponseEntity<Void> response = taskController.deleteTask(taskId, userId, role);

    // Assert
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(taskService, times(1)).getTaskById(taskId);
    verify(taskService, times(1)).deleteTask(taskId);
}

@Test
public void testDeleteTask_CompanyAdmin_Authorized() {
    // Arrange
    Long taskId = 1L;
    Long userId = 1L;
    Role role = Role.COMPANY_ADMIN;
    Task task = new Task();
    task.setId(taskId);
    Users user = new Users();
    user.setCompany(new Company());
    user.getCompany().setId(userId);
    task.setUser(user);
    when(taskService.getTaskById(taskId)).thenReturn(task);

    // Act
    ResponseEntity<Void> response = taskController.deleteTask(taskId, userId, role);

    // Assert
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(taskService, times(1)).getTaskById(taskId);
    verify(taskService, times(1)).deleteTask(taskId);
}

@Test
public void testDeleteTask_StandardUser_Authorized() {
    // Arrange
    Long taskId = 1L;
    Long userId = 1L;
    Role role = Role.STANDARD;
    Task task = new Task();
    task.setId(taskId);
    Users user = new Users();
    user.setId(userId);
    task.setUser(user);
    when(taskService.getTaskById(taskId)).thenReturn(task);

    // Act
    ResponseEntity<Void> response = taskController.deleteTask(taskId, userId, role);

    // Assert
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(taskService, times(1)).getTaskById(taskId);
    verify(taskService, times(1)).deleteTask(taskId);
}
}