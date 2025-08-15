package com.manudev.tasks.services;

import com.manudev.tasks.domain.entities.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    List<Task> listTasks(UUID taskListId);
    Task createTask(UUID taskListId, Task task);
}
