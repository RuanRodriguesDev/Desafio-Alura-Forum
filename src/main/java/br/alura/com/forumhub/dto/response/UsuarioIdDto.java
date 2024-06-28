package com.alura.ForumHub.dto.response;

public record UsuarioIdDto(
        Long idUsuario,
        String nomeUsuario,
        String emailUsuario,
        boolean statusUsuario
) {
    public UsuarioIdDto(Long idUsuario, String nomeUsuario, String emailUsuario) {
        this(idUsuario, nomeUsuario, emailUsuario, false);
    }
}
