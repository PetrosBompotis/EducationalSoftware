package com.petrosb.EducationalSoftware.module;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    boolean existsModuleById(Long id);
}
