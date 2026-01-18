package com.henriquepivetti.gestao_de_projetos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.henriquepivetti.gestao_de_projetos.dto.project.ProjectPatchDTO;
import com.henriquepivetti.gestao_de_projetos.dto.project.ProjectRequestDTO;
import com.henriquepivetti.gestao_de_projetos.dto.project.ProjectResponseDTO;
import com.henriquepivetti.gestao_de_projetos.service.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Validated
public class ProjectController {

    private final ProjectService projectService;

    // =========================
    // CREATE
    // =========================
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> create(
        @Valid @RequestBody ProjectRequestDTO dto
    ) {
        ProjectResponseDTO response = projectService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // =========================
    // FIND ALL
    // =========================
    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> findAll() {
        return ResponseEntity.ok(projectService.findAll());
    }

    // =========================
    // PATCH
    // =========================
    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> patch(
        @PathVariable Long id,
        @RequestBody ProjectPatchDTO dto
    ) {
        return ResponseEntity.ok(projectService.patch(id, dto));
    }
}
