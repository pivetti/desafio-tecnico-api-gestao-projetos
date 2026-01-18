package com.henriquepivetti.gestao_de_projetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.henriquepivetti.gestao_de_projetos.model.Task;
import com.henriquepivetti.gestao_de_projetos.model.TaskPriority;
import com.henriquepivetti.gestao_de_projetos.model.TaskStatus;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByPriority(TaskPriority priority);
    List<Task> findByProjectId(Long projectId);

    List<Task> findByStatusAndPriorityAndProjectId(
        TaskStatus status,
        TaskPriority priority,
        Long projectId
    );
}
