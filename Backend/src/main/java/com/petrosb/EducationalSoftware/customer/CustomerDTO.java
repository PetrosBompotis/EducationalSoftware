package com.petrosb.EducationalSoftware.customer;

import com.petrosb.EducationalSoftware.Level;

import java.util.List;

public record CustomerDTO(
        Long id,
        String name,
        String email,
        Level level,
        List<String> roles,
        String username
) {
}
