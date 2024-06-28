package com.alura.ForumHub.repository;

import com.alura.ForumHub.domain.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTitulo(String titulo);

    boolean existsByMensagem(String mensagem);

    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso AND FUNCTION('YEAR', t.data_criacao) = :anoCriacao")
    Page<Topico> findByNomeCursoAndAnoCriacao(String nomeCurso, int anoCriacao, Pageable pageable);

    @Query("SELECT t FROM Topico t ORDER BY t.data_criacao ASC")
    Page<Topico> findAllByOrderByDataCriacaoAsc(Pageable pageable);

    boolean existsByTituloAndMensagemAndCursoId(String titulo, String mensagem, Long cursoId);

    Page<Topico> findByStatusTrue(Pageable pageable);

    Optional<Topico> findByIdAndStatusTrue(Long topicoId);
}
