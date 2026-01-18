package com.henriquepivetti.gestao_de_projetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.henriquepivetti.gestao_de_projetos.model.Project;
import java.util.List;
import java.time.LocalDate;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByNameIgnoreCase(String name);
    List<Project> findByStartDate(LocalDate startDate);
    List<Project> findByEndDate(LocalDate endDate);

}
