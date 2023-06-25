package br.edu.ifsp.dmos5.app_projetofinal.model;

public class Pet {

    private String nome;
    private String animal;
    private String idade;
    private String peso;
    private String raca;
    private String cor;

    public Pet(){

    }

    public Pet(String nome, String animal, String idade, String peso, String raca, String cor) {
        this.nome = nome;
        this.animal = animal;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
        this.cor = cor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
