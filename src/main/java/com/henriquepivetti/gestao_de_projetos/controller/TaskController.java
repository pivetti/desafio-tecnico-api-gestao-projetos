package com.henriquepivetti.gestao_de_projetos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.henriquepivetti.gestao_de_projetos.dto.task.TaskRequestDTO;
import com.henriquepivetti.gestao_de_projetos.dto.task.TaskResponseDTO;
import com.henriquepivetti.gestao_de_projetos.dto.task.TaskStatusUpdateDTO;
import com.henriquepivetti.gestao_de_projetos.model.TaskPriority;
import com.henriquepivetti.gestao_de_projetos.model.TaskStatus;
import com.henriquepivetti.gestao_de_projetos.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // =========================
    // CREATE
    // =========================
    @PostMapping
    public ResponseEntity<TaskResponseDTO> create(
        @Valid @RequestBody TaskRequestDTO dto
    ) {
        TaskResponseDTO response = taskService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // =========================
    // FIND WITH FILTERS
    // =========================
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> findTasks(
        @RequestParam(required = false) TaskStatus status,
        @RequestParam(required = false) TaskPriority priority,
        @RequestParam(required = false) Long projectId
    ) {
        return ResponseEntity.ok(
            taskService.findTasks(status, priority, projectId)
        );
    }

    // =========================
    // UPDATE STATUS
    // =========================
    @PutMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> updateStatus(
        @PathVariable Long id,
        @Valid @RequestBody TaskStatusUpdateDTO dto
    ) {
        return ResponseEntity.ok(
            taskService.updateStatus(id, dto)
        );
    }

    // =========================
    // DELETE
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
