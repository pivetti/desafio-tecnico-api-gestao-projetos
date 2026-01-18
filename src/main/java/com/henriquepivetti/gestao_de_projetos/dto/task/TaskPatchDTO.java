package com.henriquepivetti.gestao_de_projetos.dto.task;

import java.time.LocalDate;

import com.henriquepivetti.gestao_de_projetos.model.TaskPriority;
import com.henriquepivetti.gestao_de_projetos.model.TaskStatus;

public record TaskPatchDTO (
    String title,
    String description,
    TaskStatus status,
    TaskPriority priority,
    LocalDate dueDate,
    Long projectId
){}
