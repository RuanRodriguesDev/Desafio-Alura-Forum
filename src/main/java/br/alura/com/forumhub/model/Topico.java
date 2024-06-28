package br.alura.com.forumhub.model;

import br.alura.com.forumhub.dto.AutorDto;
import br.alura.com.forumhub.dto.CursoDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "topicos")
@Table(name = "topico" )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    private Long id ;
    private String titulo;

    private String mensagem;

    private Boolean ativo ;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private AutorDto  autor;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private CursoDto curso;
    @OneToMany(mappedBy = "topico")
    private List<Resposta> respostas = new ArrayList<>();
}
