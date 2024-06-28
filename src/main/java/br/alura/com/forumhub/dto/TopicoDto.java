package br.alura.com.forumhub.dto;

public record TopicoDto(Long id, String titulo,String mensagem, AutorDto autor, CursoDto curso,boolean ativo) {
    public TopicoDto(Long id, String titulo, String mensagem, AutorDto autor, CursoDto curso, boolean ativo) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.autor = autor;
        this.curso = curso;
        this.ativo = ativo;
    }
}
