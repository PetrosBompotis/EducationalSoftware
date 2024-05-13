package com.petrosb.EducationalSoftware.module;

import com.petrosb.EducationalSoftware.quiz.Quiz;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ModuleDataAccessService {
    private final ModuleRepository moduleRepository;

    public ModuleDataAccessService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> selectAllModules() {
        return moduleRepository.findAll();
    }

    public Optional<Module> selectModuleByID(Long id) {
        return moduleRepository.findById(id);
    }

    public void insertModule(Module module) {
        moduleRepository.save(module);
    }

    public void deleteModuleById(Long moduleId) {
        moduleRepository.deleteById(moduleId);
    }

    public boolean existsModuleWithId(Long id) {
        return moduleRepository.existsModuleById(id);
    }

    public void updateModuleById(Module module) {
        moduleRepository.save(module);
    }
}
