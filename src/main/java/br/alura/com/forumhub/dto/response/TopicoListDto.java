package com.alura.ForumHub.dto.response;

import com.alura.ForumHub.dto.response.CursoIdDto;
import com.alura.ForumHub.dto.response.UsuarioIdDto;

public record TopicoListDto(
        Long idTopico,
        String tituloTopico,
        String conteudoTopico,
        UsuarioIdDto autorTopico,
        CursoIdDto cursoTopico,
        boolean statusTopico
) {
    public TopicoListDto(Long idTopico, String tituloTopico, String conteudoTopico, UsuarioIdDto autorTopico, CursoIdDto cursoTopico, boolean statusTopico) {
        this.idTopico = idTopico;
        this.tituloTopico = tituloTopico;
        this.conteudoTopico = conteudoTopico;
        this.autorTopico = autorTopico;
        this.cursoTopico = cursoTopico;
        this.statusTopico = statusTopico;
    }
}
