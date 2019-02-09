package br.ufg.inf.pos.android.aplicacaocombd.model;

public class Livro {

    private String id;
    private String titulo;
    private String autor;
    private String editora;

    public Livro() {
    }

    public Livro(String id, String titulo, String autor, String editora) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    @Override
    public String toString(){
        return "livro: " + this.id + " "+ this.titulo + " "+ this.autor + " "+ this.editora;
    }
}
