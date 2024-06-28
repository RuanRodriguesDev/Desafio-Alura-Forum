package com.alura.ForumHub.dto.request;

import com.alura.ForumHub.domain.Resposta;
import com.alura.ForumHub.domain.Topico;
import com.alura.ForumHub.domain.Usuario;

import java.time.LocalDateTime;

public record RespostaDto(
        String conteudo,
        boolean resolvido,
        Long idAutor,
        Long idTopico
) {
    public Resposta toEntity(Usuario autor, Topico topico, LocalDateTime dataCriacao) {
        Resposta resposta = new Resposta();
        resposta.setConteudo(this.conteudo());
        resposta.setTopico(this.resolvido());
        resposta.setAutor(autor);
        resposta.setTopico(topico);
        resposta.setDataCriacao(dataCriacao);
        return resposta;
    }
}
