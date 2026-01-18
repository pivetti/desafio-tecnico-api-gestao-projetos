package com.henriquepivetti.gestao_de_projetos.dto.task;

import java.time.LocalDate;

import com.henriquepivetti.gestao_de_projetos.model.Task;
import com.henriquepivetti.gestao_de_projetos.model.TaskPriority;
import com.henriquepivetti.gestao_de_projetos.model.TaskStatus;

public record TaskResponseDTO(
    Long id,
    String title,
    String description,
    TaskStatus status,
    TaskPriority priority,
    LocalDate dueDate,
    Long projectId
){
    public static TaskResponseDTO from(Task task) {
    return new TaskResponseDTO(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getStatus(),
        task.getPriority(),
        task.getDueDate(),
        task.getProject().getId()
    );
}
}
