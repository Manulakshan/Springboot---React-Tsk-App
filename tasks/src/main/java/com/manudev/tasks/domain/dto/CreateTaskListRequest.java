package com.manudev.tasks.domain.dto;

public record CreateTaskListRequest(
        String title,
        String description
) {}
