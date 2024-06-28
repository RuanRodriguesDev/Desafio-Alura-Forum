package com.alura.ForumHub.service;

import com.alura.ForumHub.domain.Curso;
import com.alura.ForumHub.domain.Topico;
import com.alura.ForumHub.domain.Usuario;
import com.alura.ForumHub.dto.request.TopicoDto;
import com.alura.ForumHub.dto.response.*;
import com.alura.ForumHub.repository.TopicoRepository;
import com.alura.ForumHub.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioService usuarioService;
    private final CursoService cursoService;

    @Autowired
    public TopicoService(TopicoRepository topicoRepository, UsuarioService usuarioService, CursoService cursoService) {
        this.topicoRepository = topicoRepository;
        this.usuarioService = usuarioService;
        this.cursoService = cursoService;
    }

    @Transactional
    public Long salvarTopico(TopicoDto topicoDto, String emailUsuarioLogado) {
        validarTopicoDto(topicoDto);
        Usuario usuarioLogado = usuarioService.findByEmail(emailUsuarioLogado);
        validarUnicidadeTopico(topicoDto);
        validarCursoId(topicoDto.cursoTopico().idCurso());

        Topico topico = criarTopicoEntidade(topicoDto, usuarioLogado);
        Topico topicoSalvo = topicoRepository.save(topico);
        return topicoSalvo.getCodigo();
    }

    public Page<TopicoListDto> listarTopicosOrdenadosPorDataCriacao(Pageable pageable, String cursoNome, Integer ano) {
        Page<Topico> topicosPage;

        if (cursoNome != null && ano != null) {
            topicosPage = topicoRepository.findByCursoNomeAndAno(cursoNome, ano, pageable);
        } else {
            topicosPage = topicoRepository.findAllByOrderByDataCriacaoAsc(pageable);
        }

        return topicosPage.map(this::mapearParaTopicoListDto);
    }

    public void atualizarTopico(Long topicoId, TopicoDetalhamentoDto topicoDetalhamentoDto) {
        Topico topicoExistente = buscarTopicoPorId(topicoId);
        atualizarEntidadeTopico(topicoExistente, topicoDetalhamentoDto);

        try {
            topicoRepository.save(topicoExistente);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Título ou mensagem não podem ser nulos", e);
        }
    }

    @Transactional
    public void inativarTopico(Long id) {
        Topico topico = buscarTopicoPorId(id);
        topico.setAtivo(false);
        topicoRepository.save(topico);
    }

    public Page<TopicosListAtivosDto> listarTopicosAtivos(Pageable pageable) {
        Page<Topico> topicosPage = topicoRepository.findByStatusTrue(pageable);
        return topicosPage.map(this::mapearParaTopicosListAtivosDto);
    }

    public Topico buscarTopicoPorId(Long id) {
        return topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));
    }

    public Optional<TopicoDetalhamentoDto> detalharTopico(Long id) {
        return topicoRepository.findByIdAndStatusTrue(id)
                .map(this::mapearParaTopicoDetalhamentoDto);
    }

    private void validarTopicoDto(TopicoDto topicoDto) {
        if (topicoDto.tituloTopico() == null || topicoDto.conteudoTopico() == null) {
            throw new IllegalArgumentException("Título e Mensagem são obrigatórios.");
        }
    }

    private void validarUnicidadeTopico(TopicoDto topicoDto) {
        if (topicoRepository.existsByTituloAndMensagemAndCursoId(topicoDto.tituloTopico(), topicoDto.conteudoTopico(), topicoDto.cursoTopico().idCurso())) {
            throw new IllegalArgumentException("Combinação de Título, Mensagem e Curso já existe.");
        }
    }

    private void validarCursoId(Long cursoId) {
        if (cursoId == null || !cursoService.existeCursoPorId(cursoId)) {
            throw new IllegalArgumentException("O ID do Curso não é válido");
        }
    }

    private Topico criarTopicoEntidade(TopicoDto topicoDto, Usuario usuarioLogado) {
        Curso curso = new Curso(topicoDto.cursoTopico().idCurso(), topicoDto.cursoTopico().nomeCurso(), topicoDto.cursoTopico().categoriaCurso().categoria());

        Topico topico = new Topico();
        topico.setTitulo(topicoDto.tituloTopico());
        topico.setConteudo(topicoDto.conteudoTopico());
        topico.setAutor(usuarioLogado);
        topico.setCurso(curso);
        topico.setDataCriacao(LocalDateTime.now());
        topico.setAtivo(true);

        return topico;
    }

    private TopicoListDto mapearParaTopicoListDto(Topico topico) {
        return new TopicoListDto(
                topico.getCodigo(),
                topico.getTitulo(),
                topico.getConteudo(),
                new UsuarioIdDto(topico.getAutor().getCodigo(), topico.getAutor().getNomeUsuario(), topico.getAutor().getEmail()),
                new CursoIdDto(topico.getCurso().getCodigo(), topico.getCurso().getNomeCurso(), topico.getCurso().getCategoriaCurso()),
                topico.isAtivo()
        );
    }

    private TopicosListAtivosDto mapearParaTopicosListAtivosDto(Topico topico) {
        return new TopicosListAtivosDto(
                topico.getCodigo(),
                topico.getTitulo(),
                topico.getConteudo(),
                new UsuarioIdDto(topico.getAutor().getCodigo(), topico.getAutor().getNomeUsuario(), topico.getAutor().getEmail()),
                new CursoIdDto(topico.getCurso().getCodigo(), topico.getCurso().getNomeCurso(), topico.getCurso().getCategoriaCurso()),
        );
    }

    private TopicoDetalhamentoDto mapearParaTopicoDetalhamentoDto(Topico topico) {
        List<RespostaIdDto> respostasDto = topico.getListaRespostas().stream()
                .map(resposta -> new RespostaIdDto(
                        resposta.getId,
                        resposta.getMensagem(),
                        resposta.getData_criacao(),
                        resposta.isSolucao(),
                        resposta.getAutor().getId(),
                        resposta.getTopico().getId()
                ))
                .collect(Collectors.toList());

        return new TopicoDetalhamentoDto(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                new UsuarioIdDto(topico.getAutor().getId(), topico.getAutor().getNome(), topico.getAutor().getEmail()),
                new CursoIdDto(topico.getCurso().getId(), topico.getCurso().getNome(), topico.getCurso().getCategoria()),
                respostasDto,
                topico.isStatus()
        );
    }

    private void atualizarEntidadeTopico(Topico topico, TopicoDetalhamentoDto topicoDetalhamentoDto) {
        topico.setTitulo(topicoDetalhamentoDto.tituloTopico());
        topico.setConteudo(topicoDetalhamentoDto.conteudoTopico());
    }
}
