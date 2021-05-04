package com.t2m.model;

public class Consulta {

    private int tipo;
    private String nome;

    public Consulta(int tipo, String nome) {
        this.tipo = tipo;
        this.nome = nome;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
