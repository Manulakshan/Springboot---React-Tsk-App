package com.manudev.tasks.mappers;

import com.manudev.tasks.domain.dto.TaskDto;
import com.manudev.tasks.domain.entities.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
