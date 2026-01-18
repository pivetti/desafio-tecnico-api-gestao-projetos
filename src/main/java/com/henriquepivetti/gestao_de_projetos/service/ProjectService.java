package com.henriquepivetti.gestao_de_projetos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henriquepivetti.gestao_de_projetos.dto.project.ProjectPatchDTO;
import com.henriquepivetti.gestao_de_projetos.dto.project.ProjectRequestDTO;
import com.henriquepivetti.gestao_de_projetos.dto.project.ProjectResponseDTO;
import com.henriquepivetti.gestao_de_projetos.exception.BusinessRuleException;
import com.henriquepivetti.gestao_de_projetos.exception.ResourceNotFoundException;
import com.henriquepivetti.gestao_de_projetos.model.Project;
import com.henriquepivetti.gestao_de_projetos.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectResponseDTO create(ProjectRequestDTO dto) {

        if (projectRepository.existsByNameIgnoreCase(dto.name())) {
            throw new BusinessRuleException(
                "Project with this name already exists"
            );
        }

        Project project = new Project();
        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setStartDate(dto.startDate());
        project.setEndDate(dto.endDate());

        validateDates(project.getStartDate(), project.getEndDate());

        Project saved = projectRepository.save(project);

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> findAll() {

        return projectRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    public ProjectResponseDTO patch(Long id, ProjectPatchDTO dto) {

        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        if (dto.name() != null && !dto.name().equalsIgnoreCase(project.getName())) {

            if (projectRepository.existsByNameIgnoreCase(dto.name())) {
                throw new BusinessRuleException(
                    "Project with this name already exists"
                );
            }

            project.setName(dto.name());
        }

        if (dto.description() != null) {
            project.setDescription(dto.description());
        }

        if (dto.startDate() != null) {
            project.setStartDate(dto.startDate());
        }

        if (dto.endDate() != null) {
            project.setEndDate(dto.endDate());
        }

        validateDates(project.getStartDate(), project.getEndDate());

        Project updated = projectRepository.save(project);

        return mapToResponse(updated);
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {

        if (endDate != null && endDate.isBefore(startDate)) {
            throw new BusinessRuleException(
                "End date cannot be before start date"
            );
        }
    }

    private ProjectResponseDTO mapToResponse(Project project) {

        return new ProjectResponseDTO(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getStartDate(),
            project.getEndDate()
        );
    }
}
