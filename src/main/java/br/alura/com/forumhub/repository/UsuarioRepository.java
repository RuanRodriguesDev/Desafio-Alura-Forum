package com.alura.ForumHub.repository;

import com.alura.ForumHub.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByEmail(String usuarioEmail);

    boolean existsByEmail(String usuarioEmail);
}
