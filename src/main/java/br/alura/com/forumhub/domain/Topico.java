package com.alura.ForumHub.domain;

import com.alura.ForumHub.domain.Curso;
import com.alura.ForumHub.domain.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "codigo")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    private String titulo;
    private String conteudo;
    private LocalDateTime dataCriacao;
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico")
    private List<com.alura.forumhub.domain.Resposta> listaRespostas = new ArrayList<>();

}
