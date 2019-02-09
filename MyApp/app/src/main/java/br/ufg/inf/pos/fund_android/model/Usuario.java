package br.ufg.inf.pos.fund_android.model;

public class Usuario {

    private String nome;
    private String token;
    private String urlFoto;

    public Usuario(String nome, String token, String urlFoto) {
        this.nome = nome;
        this.token = token;
        this.urlFoto = urlFoto;
    }

    public Usuario() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
