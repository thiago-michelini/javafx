package com.t2m.model;

import jakarta.xml.bind.annotation.XmlElement;

public class Negocio {
    @XmlElement(name = "tipoNegocio")
    private Integer tipo;

    private String nome;

    public Negocio() {
        super();
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
