package com.henriquepivetti.gestao_de_projetos.dto.task;

import java.time.LocalDate;

import com.henriquepivetti.gestao_de_projetos.model.TaskPriority;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskRequestDTO(
    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 150, message = "Title must be between 5 and 150 characters")
    String title,

    String description,

    @NotNull(message = "Priority is required")
    TaskPriority priority,

    @FutureOrPresent(message = "Due date must be today or in the future")
    LocalDate dueDate,

    @NotNull(message = "Project ID is required")
    Long projectId
) {
}