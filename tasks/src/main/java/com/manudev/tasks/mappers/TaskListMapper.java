package com.manudev.tasks.mappers;

import com.manudev.tasks.domain.dto.TaskListDto;
import com.manudev.tasks.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
