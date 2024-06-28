package com.alura.ForumHub.dto.response;

import com.alura.ForumHub.dto.request.RespostaDto;

import java.util.List;

public record TopicoDetalhamentoDto (
        Long idTopico,
        String tituloTopico,
        String conteudoTopico,
        UsuarioIdDto autorTopico,
        CursoIdDto cursoTopico,
        List<RespostaDto> respostasTopico,
        boolean statusTopico
) {
    public TopicoDetalhamentoDto(Long idTopico, String tituloTopico, String conteudoTopico, UsuarioIdDto autorTopico, CursoIdDto cursoTopico, List<RespostaDto> respostasTopico, boolean statusTopico) {
        this.idTopico = idTopico;
        this.tituloTopico = tituloTopico;
        this.conteudoTopico = conteudoTopico;
        this.autorTopico = autorTopico;
        this.cursoTopico = cursoTopico;
        this.respostasTopico = respostasTopico;
        this.statusTopico = statusTopico;
    }
}
