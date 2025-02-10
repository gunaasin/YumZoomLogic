package com.guna.yumzoom.user;

import lombok.Builder;

@Builder
public record UserSignInResponseDTO(
        String username
) {
}
