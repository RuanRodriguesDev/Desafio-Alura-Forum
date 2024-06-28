package com.alura.ForumHub.domain;

import com.alura.ForumHub.domain.Topico;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "curso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    private String nomeCurso;
    private String categoriaCurso;

    @OneToMany(mappedBy = "curso")
    private List<Topico> listaTopicos = new ArrayList<>();

}
