package com.petrosb.EducationalSoftware.auth;

public record TokenValidationRequest(
        String accessToken,
        String username
) {
}
