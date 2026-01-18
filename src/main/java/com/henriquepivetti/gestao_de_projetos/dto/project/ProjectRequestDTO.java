package com.henriquepivetti.gestao_de_projetos.dto.project;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectRequestDTO(
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 150, message = "Name must be between 3 and 150 characters")
    String name,

    String description,

    @NotNull(message = "Start date is required")
    LocalDate startDate,

    LocalDate endDate
){
}