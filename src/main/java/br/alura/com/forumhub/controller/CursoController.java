package com.alura.ForumHub.controller;

import com.alura.ForumHub.dto.request.CursoDto;
import com.alura.ForumHub.dto.response.CursoIdDto;
import com.alura.ForumHub.service.CursoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    private final CursoService cursoService;  // Serviço de Curso

    @Autowired
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<String> registrarCurso(@RequestBody @Valid CursoDto cursoDto,
                                                 UriComponentsBuilder uriBuilder) {

        // Salva o curso e obtém seu ID
        Long idCurso = cursoService.saveCurso(cursoDto);

        // Constrói a URI do novo recurso
        var uri = uriBuilder.path("/cursos/{id}")
                .buildAndExpand(idCurso)
                .toUri();

        // Retorna uma resposta de criação com a URI do novo curso
        return ResponseEntity.created(uri)
                .body("Curso registrado com sucesso");
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<CursoIdDto>> listarTodosCursos(Pageable pageable) {
        // Obtém todos os cursos em uma página
        Page<CursoIdDto> cursosPagina = cursoService.getAllCursos(pageable);

        // Retorna uma resposta com a página de cursos
        return ResponseEntity.ok(cursosPagina);
    }

    @PutMapping("/atualizar/{cursoId}")
    @Transactional
    public ResponseEntity<String> atualizarCurso(
            @PathVariable Long cursoId,
            @RequestBody CursoIdDto cursoIdDto) {

        // Atualiza o curso com o ID fornecido
        cursoService.updateCurso(cursoId, cursoIdDto);

        // Retorna uma resposta de sucesso
        return ResponseEntity.ok("Curso atualizado com sucesso.");
    }
}
