package com.manudev.tasks.services.impl;

import com.manudev.tasks.domain.entities.Task;
import com.manudev.tasks.domain.entities.TaskList;
import com.manudev.tasks.domain.entities.TaskPriority;
import com.manudev.tasks.domain.entities.TaskStatus;
import com.manudev.tasks.mappers.TaskMapper;
import com.manudev.tasks.repositories.TaskListRepository;
import com.manudev.tasks.repositories.TaskRepository;
import com.manudev.tasks.services.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(null != task.getId()) {
            throw new IllegalArgumentException("Task already has an Id!!");
        }
        if(null == task.getTitle() || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task list dont have title!");
        }
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Task list does not exist!"));
        LocalDateTime now = LocalDateTime.now();
        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );
        return taskRepository.save(taskToSave);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
       return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if(null == task.getId()) {
            throw new IllegalArgumentException("Task must have an id!!");
        }
        if(!Objects.equals(task.getId(),taskId) ) {
            throw new IllegalArgumentException("not permitted to edit task list!");
        }

        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId,taskId).orElseThrow(()
                -> new IllegalArgumentException("Task does not exist!"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setUpdated(LocalDateTime.now());
        return taskRepository.save(existingTask);

    }
    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }


}
