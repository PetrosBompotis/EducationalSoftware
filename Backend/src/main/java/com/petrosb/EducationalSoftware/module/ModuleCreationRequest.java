package com.petrosb.EducationalSoftware.module;

import java.util.List;

public record ModuleCreationRequest(
        String title,
        String textContent,
        String description,
        List<String> imageUrls,
        String videoUrl
) {
}
