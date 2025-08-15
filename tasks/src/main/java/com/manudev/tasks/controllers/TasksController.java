package com.manudev.tasks.controllers;

import com.manudev.tasks.domain.dto.TaskDto;
import com.manudev.tasks.domain.entities.Task;
import com.manudev.tasks.mappers.TaskMapper;
import com.manudev.tasks.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/task-lists/{task_list_id}/tasks")
public class TasksController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TasksController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id") UUID taskListId){

       return taskService.listTasks(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskDto taskDto){
        Task createdtask = taskService.createTask(taskListId,
                taskMapper.fromDto(taskDto)
        );
        return taskMapper.toDto(createdtask);
    }

}
