package com.alura.ForumHub.dto.request;

import com.alura.ForumHub.dto.response.CursoIdDto;
import com.alura.ForumHub.dto.response.UsuarioIdDto;
import jakarta.validation.constraints.NotBlank;

public record TopicoDto(
        @NotBlank String tituloTopico,
        @NotBlank String conteudoTopico,
        UsuarioIdDto autorTopico,
        CursoIdDto cursoTopico
) {
}
