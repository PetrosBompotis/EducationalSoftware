package com.petrosb.EducationalSoftware.customer;

import com.petrosb.EducationalSoftware.Level;

public record CustomerRegistrationRequest(
        String name,
        String email,
        String password,
        Level level
) {
}

