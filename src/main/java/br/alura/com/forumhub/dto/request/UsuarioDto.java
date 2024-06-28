package com.alura.ForumHub.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDto(
        @NotBlank String nomeUsuario,
        @NotBlank @Email String emailUsuario,
        @NotBlank String senhaUsuario
) {
}
