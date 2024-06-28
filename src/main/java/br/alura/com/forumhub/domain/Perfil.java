package com.alura.ForumHub.domain;

import com.alura.ForumHub.domain.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "perfil")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "codigo")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    private String nomePerfil;

    @ManyToMany(mappedBy = "perfis")
    private Set<Usuario> listaUsuarios = new HashSet<>();

}
