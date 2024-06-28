package com.alura.ForumHub.dto.response;

import com.alura.ForumHub.dto.response.CursoIdDto;
import com.alura.ForumHub.dto.response.UsuarioIdDto;

public record TopicosListAtivosDto (
        Long idTopicoAtivo,
        String tituloTopicoAtivo,
        String conteudoTopicoAtivo,
        UsuarioIdDto autorTopicoAtivo,
        CursoIdDto cursoTopicoAtivo
) {
    public TopicosListAtivosDto(Long idTopicoAtivo, String tituloTopicoAtivo, String conteudoTopicoAtivo, UsuarioIdDto autorTopicoAtivo, CursoIdDto cursoTopicoAtivo) {
        this.idTopicoAtivo = idTopicoAtivo;
        this.tituloTopicoAtivo = tituloTopicoAtivo;
        this.conteudoTopicoAtivo = conteudoTopicoAtivo;
        this.autorTopicoAtivo = autorTopicoAtivo;
        this.cursoTopicoAtivo = cursoTopicoAtivo;
    }
}
