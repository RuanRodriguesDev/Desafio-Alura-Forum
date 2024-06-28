package com.alura.ForumHub.controller;

import com.alura.ForumHub.service.RespostaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respostas")
@SecurityRequirement(name = "bearer-key")
public class RespostaController {

    private final RespostaService respostaService;  // Serviço de Respostas

    @Autowired
    public RespostaController(RespostaService respostaService) {
        this.respostaService = respostaService;
    }

    // Métodos da controller podem ser adicionados aqui conforme necessário
}
