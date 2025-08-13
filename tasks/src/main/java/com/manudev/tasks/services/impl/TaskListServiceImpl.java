package com.manudev.tasks.services.impl;

import com.manudev.tasks.domain.entities.TaskList;
import com.manudev.tasks.repositories.TaskListRepository;
import com.manudev.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository tasklistRepository;

    public TaskListServiceImpl(TaskListRepository tasklistRepository) {
        this.tasklistRepository = tasklistRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return tasklistRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(null != taskList.getId()) {
            throw new IllegalArgumentException("Task list id is already has an Id!!");
        }
        if(null == taskList.getTitle()) {
            throw new IllegalArgumentException("Task list dont have title!");
        }
        LocalDateTime now = LocalDateTime.now();

        return tasklistRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now
        ));
    }
    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return tasklistRepository.findById(id);
    }

    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        if(null == taskList.getId()) {
            throw new IllegalArgumentException("Task must have an id!!");
        }
        if(!Objects.equals(taskList.getId(),taskListId) ) {
            throw new IllegalArgumentException("not permitted to edit task list!");
        }

        TaskList existingTaskList = tasklistRepository.findById(taskListId).orElseThrow(()
                -> new IllegalArgumentException("task list not found"));
        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());
        return tasklistRepository.save(existingTaskList);
    }
}
