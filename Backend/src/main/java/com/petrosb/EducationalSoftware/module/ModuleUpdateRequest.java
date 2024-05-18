package com.petrosb.EducationalSoftware.module;

public record ModuleUpdateRequest(
        String title,
        String description,
        String textContent,
        String extendedTextContent
) {
}
