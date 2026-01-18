package com.henriquepivetti.gestao_de_projetos.dto.task;

import com.henriquepivetti.gestao_de_projetos.model.TaskStatus;

import jakarta.validation.constraints.NotNull;

public record TaskStatusUpdateDTO(
    @NotNull
    TaskStatus status
) {}