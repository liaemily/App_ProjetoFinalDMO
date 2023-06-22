package br.edu.ifsp.dmos5.app_projetofinal.model;

public class Task {

    private String nome;
    private String data;
    private String tipo;
    private String usuario;
    private String observacao;

    public Task() {
    }

    public Task(String nome, String data, String tipo, String observacao, String usuario) {
        this.nome = nome;
        this.data = data;
        this.tipo = tipo;
        this.observacao = observacao;
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
