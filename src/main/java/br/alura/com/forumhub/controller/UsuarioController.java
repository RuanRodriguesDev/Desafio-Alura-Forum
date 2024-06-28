package com.alura.ForumHub.controller;

import com.alura.ForumHub.dto.request.UsuarioDto;
import com.alura.ForumHub.dto.response.UsuarioDetalhamentoDto;
import com.alura.ForumHub.dto.response.UsuarioIdDto;
import com.alura.ForumHub.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<String> cadastrarUsuario(@RequestBody @Valid UsuarioDto usuarioDto,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        Long idUsuario = usuarioService.saveUser(usuarioDto);
        var uri = uriComponentsBuilder.path("/usuarios/{id}")
                .buildAndExpand(idUsuario).toUri();
        return ResponseEntity.created(uri)
                .body("Usuário registrado com sucesso. Id: " + idUsuario);
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<UsuarioIdDto>> listarUsuarios(Pageable pageable) {
        Page<UsuarioIdDto> usuariosPage = usuarioService.getAllUsers(pageable);
        return ResponseEntity.ok(usuariosPage);
    }

    @PutMapping("/{userId}")
    @Transactional
    public ResponseEntity<String> atualizarUsuario(
            @PathVariable Long userId,
            @RequestBody UsuarioIdDto usuarioIdDto) {
        usuarioService.updateUser(userId, usuarioIdDto);
        return ResponseEntity.ok("Usuário atualizado com sucesso.");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long userId) {
        usuarioService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDetalhamentoDto> detalharUsuario(@PathVariable Long id) {
        Optional<UsuarioDetalhamentoDto> detalheOptional = usuarioService.detalharUsuario(id);
        return detalheOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
