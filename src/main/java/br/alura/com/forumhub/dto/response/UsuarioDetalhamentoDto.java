package com.alura.ForumHub.dto.response;

public record UsuarioDetalhamentoDto(
        Long idUsuario,
        String nomeUsuario,
        String emailUsuario,
        boolean statusUsuario
) {}