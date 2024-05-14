package com.petrosb.EducationalSoftware.module;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/modules")
public class ModuleController {
    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public List<Module> getModules() {
        return moduleService.getAllModules();
    }

    @GetMapping("{moduleId}")
    public Module getModule(@PathVariable("moduleId") Long moduleId) {
        return moduleService.getModule(moduleId);
    }

    @PostMapping
    public void createModule(@RequestBody ModuleCreationRequest request){
        moduleService.addModule(request);
    }

    @DeleteMapping("{moduleId}")
    public void deleteModule(@PathVariable("moduleId") Long moduleId){
        moduleService.deleteModuleById(moduleId);
    }

    @PutMapping("{moduleId}")
    public void updateModule(@RequestBody ModuleUpdateRequest updateRequest, @PathVariable("moduleId") Long moduleId) {
        moduleService.updateModule(updateRequest, moduleId);
    }
}
