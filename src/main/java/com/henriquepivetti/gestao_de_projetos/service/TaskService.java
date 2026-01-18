package com.henriquepivetti.gestao_de_projetos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henriquepivetti.gestao_de_projetos.dto.task.TaskRequestDTO;
import com.henriquepivetti.gestao_de_projetos.dto.task.TaskResponseDTO;
import com.henriquepivetti.gestao_de_projetos.dto.task.TaskStatusUpdateDTO;
import com.henriquepivetti.gestao_de_projetos.exception.BusinessRuleException;
import com.henriquepivetti.gestao_de_projetos.exception.ResourceNotFoundException;
import com.henriquepivetti.gestao_de_projetos.model.Project;
import com.henriquepivetti.gestao_de_projetos.model.Task;
import com.henriquepivetti.gestao_de_projetos.model.TaskPriority;
import com.henriquepivetti.gestao_de_projetos.model.TaskStatus;
import com.henriquepivetti.gestao_de_projetos.repository.ProjectRepository;
import com.henriquepivetti.gestao_de_projetos.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    // =========================
    // CREATE
    // =========================
    public TaskResponseDTO create(TaskRequestDTO dto) {

        Project project = projectRepository.findById(dto.projectId())
            .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setPriority(dto.priority()); // prioridade obrigatória
        task.setDueDate(dto.dueDate());
        task.setProject(project);

        // REGRA DE NEGÓCIO: task sempre nasce como TODO
        task.setStatus(TaskStatus.TODO);

        Task saved = taskRepository.save(task);

        return TaskResponseDTO.from(saved);
    }

    // =========================
    // FIND WITH FILTERS
    // =========================
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> findTasks(
        TaskStatus status,
        TaskPriority priority,
        Long projectId
    ) {

        // Todos os filtros
        if (status != null && priority != null && projectId != null) {
            return taskRepository
                .findByStatusAndPriorityAndProjectId(status, priority, projectId)
                .stream()
                .map(TaskResponseDTO::from)
                .toList();
        }

        // Apenas status
        if (status != null && priority == null && projectId == null) {
            return taskRepository.findByStatus(status)
                .stream()
                .map(TaskResponseDTO::from)
                .toList();
        }

        // Apenas priority
        if (priority != null && status == null && projectId == null) {
            return taskRepository.findByPriority(priority)
                .stream()
                .map(TaskResponseDTO::from)
                .toList();
        }

        // Apenas project
        if (projectId != null && status == null && priority == null) {
            return taskRepository.findByProjectId(projectId)
                .stream()
                .map(TaskResponseDTO::from)
                .toList();
        }

        // Combinações parciais (status + priority)
        if (status != null && priority != null) {
            return taskRepository.findByStatus(status)
                .stream()
                .filter(t -> t.getPriority() == priority)
                .map(TaskResponseDTO::from)
                .toList();
        }

        // Fallback: retorna tudo
        return taskRepository.findAll()
            .stream()
            .map(TaskResponseDTO::from)
            .toList();
    }

    // =========================
    // UPDATE STATUS
    // =========================
    public TaskResponseDTO updateStatus(Long taskId, TaskStatusUpdateDTO dto) {

        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        validateStatusTransition(task.getStatus(), dto.status());

        task.setStatus(dto.status());

        return TaskResponseDTO.from(task);
    }

    // =========================
    // DELETE
    // =========================
    public void delete(Long taskId) {

        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("Task not found");
        }

        taskRepository.deleteById(taskId);
    }

    // =========================
    // DOMAIN RULES
    // =========================
    private void validateStatusTransition(TaskStatus current, TaskStatus next) {

        if (current == TaskStatus.DONE) {
            throw new BusinessRuleException(
                "Cannot change status of a completed task"
            );
        }

        if (current == TaskStatus.TODO && next == TaskStatus.DONE) {
            throw new BusinessRuleException(
                "Task must be DOING before DONE"
            );
        }
    }
}
