package com.petrosb.EducationalSoftware.module;

import com.petrosb.EducationalSoftware.exception.RequestValidationException;
import com.petrosb.EducationalSoftware.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {
    private final ModuleDataAccessService moduleDataAccessService;

    public ModuleService(ModuleDataAccessService moduleDataAccessService) {
        this.moduleDataAccessService = moduleDataAccessService;
    }

    public List<Module> getAllModules(){
        return moduleDataAccessService.selectAllModules();
    }

    public Module getModule(Long id){
        return moduleDataAccessService.selectModuleByID(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Module with id [%s] not found".formatted(id)
                ));
    }

    public void addModule(ModuleCreationRequest moduleCreationRequest){

        Module module = new Module(
                moduleCreationRequest.title(),
                moduleCreationRequest.textContent(),
                moduleCreationRequest.extendedTextContent(),
                moduleCreationRequest.description(),
                moduleCreationRequest.imageUrls(),
                moduleCreationRequest.videoUrl()
        );
        moduleDataAccessService.insertModule(module);

    }

    public void deleteModuleById(Long moduleId){

        //check if id exists
        if(!moduleDataAccessService.existsModuleWithId(moduleId)){
            throw new ResourceNotFoundException("Module with id [%s] not found".formatted(moduleId));
        }

        //otherwise remove
        moduleDataAccessService.deleteModuleById(moduleId);

    }

    public void updateModule(ModuleUpdateRequest updateRequest, Long moduleId){

        Module module = moduleDataAccessService.selectModuleByID(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Module with id [%s] not found".formatted(moduleId)
                ));
        boolean changes = false;
        //check if attributes need change exists
        if (updateRequest.title() != null && !updateRequest.title().equals(module.getTitle())){
            module.setTitle(updateRequest.title());
            changes = true;
        }

        if (updateRequest.description() != null && !updateRequest.description().equals(module.getDescription())){
            module.setDescription(updateRequest.description());
            changes = true;
        }

        if (updateRequest.textContent() != null && !updateRequest.textContent().equals(module.getTextContent())){
            module.setTextContent(updateRequest.textContent());
            changes = true;
        }

        if (updateRequest.extendedTextContent() != null && !updateRequest.extendedTextContent().equals(module.getExtendedTextContent())){
            module.setExtendedTextContent(updateRequest.extendedTextContent());
            changes = true;
        }

        if (updateRequest.videoUrl() != null && !updateRequest.videoUrl().equals(module.getVideoUrl())){
            module.setVideoUrl(updateRequest.videoUrl());
            changes = true;
        }
        //otherwise update

        if (!changes){
            throw new RequestValidationException("no data changes found");
        }

        moduleDataAccessService.updateModuleById(module);
    }
}
