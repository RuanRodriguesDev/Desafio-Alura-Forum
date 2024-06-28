package com.alura.ForumHub.dto.response;

import java.time.LocalDateTime;

public record RespostaIdDto(
        Long idResposta,
        String conteudoResposta,
        LocalDateTime dataCriacaoResposta,
        boolean respostaResolvida,
        Long idAutorResposta,
        Long idTopicoResposta
) {
}
