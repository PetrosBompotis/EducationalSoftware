package com.petrosb.EducationalSoftware.auth;

import com.petrosb.EducationalSoftware.customer.CustomerDTO;

public record AuthenticationResponse(
        String token,
        CustomerDTO customerDTO
) {
}
