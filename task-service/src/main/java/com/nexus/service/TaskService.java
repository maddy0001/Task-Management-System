package com.nexus.service;

import com.nexus.model.Task;
import com.nexus.model.TaskStatus;

import java.util.List;

public interface TaskService {
    Task createTask(Task task, String requesterRole)throws Exception;

    Task getTaskById(Long id) throws Exception;

    List<Task> getAllTask(TaskStatus status);

    Task updateTask(Long id, Task updatedTask, Long userId)throws Exception;

    String deleteTask(Long id) throws Exception;

    Task assignedToUser(Long userId, Long taskId)throws Exception;

    List<Task> assignedUsersTask(Long userId, TaskStatus status);

    Task completeTask(Long taskId) throws Exception;
}
