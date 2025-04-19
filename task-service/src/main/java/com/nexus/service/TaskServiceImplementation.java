package com.nexus.service;

import com.nexus.model.Task;
import com.nexus.model.TaskStatus;
import com.nexus.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImplementation implements TaskService{

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task, String requesterRole) throws Exception {
        if(!requesterRole.equals("ROLE_ADMIN")){
            throw new Exception("Only admin can create task");
        }
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) throws Exception {
        return taskRepository.findById(id).orElseThrow(()->new Exception("Task not found with id "+id));
    }

    @Override
    public List<Task> getAllTask(TaskStatus status) {
        List<Task> allTask = taskRepository.findAll();
        List<Task> filterTask = allTask.stream().filter(
                task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString()
        )).collect(Collectors.toList());
        return filterTask;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask, Long userId) throws Exception {
        Task existingTask = getTaskById(id);

        if(updatedTask.getTitle()!=null){
            existingTask.setTitle(updatedTask.getTitle());
        }
        if(updatedTask.getImage()!=null){
            existingTask.setImage(updatedTask.getImage());
        }
        if(updatedTask.getDescription()!=null){
            existingTask.setDescription(updatedTask.getDescription());
        }
        if(updatedTask.getStatus()!=null){
            existingTask.setStatus(updatedTask.getStatus());
        }
        if(updatedTask.getDeadLine()!=null){
            existingTask.setDeadLine(updatedTask.getDeadLine());
        }
        existingTask.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(existingTask);
    }

    @Override
    public String deleteTask(Long id) throws Exception {
        getTaskById(id);
        taskRepository.deleteById(id);
        return id+" Task is Deleted Successfully";
    }

    @Override
    public Task assignedToUser(Long userId, Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUsersTask(Long userId, TaskStatus status) {
        List<Task> allTask = taskRepository.findByAssignedUserId(userId);
        List<Task> filterTask = allTask.stream().filter(
                task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString()
                )).collect(Collectors.toList());
        return filterTask;
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}
