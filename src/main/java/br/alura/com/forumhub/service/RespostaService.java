package com.alura.ForumHub.service;

import com.alura.ForumHub.domain.Curso;
import com.alura.ForumHub.dto.request.CursoDto;
import com.alura.ForumHub.dto.response.CursoIdDto;
import com.alura.ForumHub.repository.CursoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    @Autowired
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public Long salvarCurso(CursoDto cursoDto) {
        validarCursoDto(cursoDto);
        Curso curso = converterParaEntidade(cursoDto);
        Curso cursoSalvo = cursoRepository.save(curso);
        return cursoSalvo.getCodigo();
    }

    public Page<CursoIdDto> listarTodosOsCursos(Pageable paginacao) {
        Page<Curso> cursos = cursoRepository.findAll(paginacao);
        return cursos.map(this::converterParaDto);
    }

    public void atualizarCurso(Long cursoId, CursoIdDto cursoIdDto) {
        Curso cursoExistente = buscarCursoPorId(cursoId);
        atualizarEntidadeCurso(cursoExistente, cursoIdDto);
        cursoRepository.save(cursoExistente);
    }

    public boolean existeCursoPorId(Long cursoId) {
        return cursoRepository.existsById(cursoId);
    }

    private void validarCursoDto(CursoDto cursoDto) {
        if (cursoDto.nomeCurso() == null || cursoDto.categoriaCurso() == null) {
            throw new IllegalArgumentException("Nome e categoria são obrigatórios.");
        }
    }

    private Curso converterParaEntidade(CursoDto cursoDto) {
        Curso curso = new Curso();
        curso.setNomeCurso(cursoDto.nomeCurso());
        curso.setCategoriaCurso(cursoDto.categoriaCurso());
        return curso;
    }

    private CursoIdDto converterParaDto(Curso curso) {
        return new CursoIdDto(curso.getCodigo(), curso.getNomeCurso(), curso.getCategoriaCurso());
    }

    private Curso buscarCursoPorId(Long cursoId) {
        return cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalStateException("Curso não encontrado com ID: " + cursoId));
    }

    private void atualizarEntidadeCurso(Curso curso, CursoIdDto cursoIdDto) {
        curso.setNomeCurso(cursoIdDto.nomeCurso());
        curso.setCategoriaCurso(cursoIdDto.categoriaCurso());
    }
}
