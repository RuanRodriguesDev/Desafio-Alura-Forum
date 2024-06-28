package com.alura.ForumHub.controller;

import com.alura.ForumHub.domain.Usuario;
import com.alura.ForumHub.dto.request.RespostaDto;
import com.alura.ForumHub.dto.request.TopicoDto;
import com.alura.ForumHub.dto.response.TopicoDetalhamentoDto;
import com.alura.ForumHub.dto.response.TopicoListDto;
import com.alura.ForumHub.dto.response.TopicosListAtivosDto;
import com.alura.ForumHub.service.RespostaService;
import com.alura.ForumHub.service.TopicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    private final TopicoService topicoService;
    private final RespostaService respostaService;

    @Autowired
    public TopicoController(TopicoService topicoService, RespostaService respostaService) {
        this.topicoService = topicoService;
        this.respostaService = respostaService;
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<String> cadastrarTopico(
            @RequestBody @Valid TopicoDto topicoDto,
            UriComponentsBuilder uriComponentsBuilder,
            Authentication authentication) {

        String emailUsuarioLogado = authentication.getName();

        Long idTopico = topicoService.saveTopico(topicoDto, emailUsuarioLogado);

        var uri = uriComponentsBuilder.path("/topicos/{id}")
                .buildAndExpand(idTopico).toUri();

        return ResponseEntity.created(uri)
                .body("Tópico registrado com sucesso. Id: " + idTopico);
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<TopicosListAtivosDto>> listarTopicosAtivos(
            @RequestParam(required = false) String cursoNome,
            @RequestParam(required = false) Integer ano,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TopicosListAtivosDto> topicosPage = topicoService.getAllTopicosAtivos(pageable, cursoNome, ano);
        return ResponseEntity.ok(topicosPage);
    }

    @GetMapping("/admin")
    public ResponseEntity<Page<TopicoListDto>> listarTodosTopicos(
            @RequestParam(required = false) String cursoNome,
            @RequestParam(required = false) Integer ano,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TopicoListDto> topicosPage = topicoService.getAllTopicosOrderByDataCriacao(pageable, cursoNome, ano);
        return ResponseEntity.ok(topicosPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharTopico(@PathVariable Long id) {
        Optional<TopicoDetalhamentoDto> detalheOptional = topicoService.detalharTopico(id);

        return detalheOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{topicoId}")
    @Transactional
    public ResponseEntity<String> atualizarTopico(
            @PathVariable Long topicoId,
            @RequestBody TopicoDetalhamentoDto topicoDetalhamentoDto) {

        topicoService.updateTopico(topicoId, topicoDetalhamentoDto);
        return ResponseEntity.ok("Tópico atualizado com sucesso.");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluirTopico(@PathVariable Long id) {
        topicoService.inativarTopico(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/respostas/{topicoId}")
    @Transactional
    public ResponseEntity<Void> salvarResposta(
            @PathVariable Long topicoId,
            @RequestBody @Valid RespostaDto respostaDto,
            Principal principal) {

        Usuario autor = respostaService.findByEmail(principal.getName());
        LocalDateTime dataCriacao = LocalDateTime.now();
        respostaService.saveResposta(topicoId, respostaDto, autor, dataCriacao);
        return ResponseEntity.ok().build();
    }
}
