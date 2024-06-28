package com.alura.ForumHub.controller;

import com.alura.ForumHub.domain.Usuario;
import com.alura.ForumHub.dto.request.AutenticacaoDto;
import com.alura.ForumHub.infra.security.DadosTokenJwt;
import com.alura.ForumHub.infra.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;  // Gerenciador de Autenticação

    @Autowired
    private TokenService tokenService;  // Serviço de Token

    @PostMapping("")
    @Transactional
    public ResponseEntity autenticarUsuario(@RequestBody @Valid AutenticacaoDto dadosAutenticacao) {
        try {
            // Cria um token de autenticação com as credenciais fornecidas
            var usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.login(), dadosAutenticacao.senha());

            // Autentica o token
            var autenticacao = authenticationManager.authenticate(usernamePasswordAuthToken);

            // Gera um token JWT para o usuário autenticado
            var tokenJwt = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());

            // Retorna uma resposta de sucesso com o token JWT
            return ResponseEntity.ok(new DadosTokenJwt(tokenJwt));
        } catch (Exception e) {
            e.printStackTrace();

            // Retorna uma resposta de erro com a mensagem de exceção
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
