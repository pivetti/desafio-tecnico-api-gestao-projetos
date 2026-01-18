package com.henriquepivetti.gestao_de_projetos.dto.project;

import java.time.LocalDate;

public record ProjectResponseDTO (
    Long id,
    String name,
    String description,
    LocalDate startDate,
    LocalDate endDate
){}
