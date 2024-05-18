package com.petrosb.EducationalSoftware.module;

import java.util.List;

public record ModuleCreationRequest(
        String title,
        String textContent,
        String extendedTextContent,
        String description,
        List<String> imageUrls,
        String videoUrl
) {
}
