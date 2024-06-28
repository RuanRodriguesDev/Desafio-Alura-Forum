package com.alura.ForumHub.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaRepository extends JpaRepository<com.alura.forumhub.domain.Resposta, Long> {
}
