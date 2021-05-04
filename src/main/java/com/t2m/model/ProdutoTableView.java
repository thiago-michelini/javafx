package com.t2m.model;

public class ProdutoTableView {
    private String nome;
    private String fornecedor;
    private Integer tipoNegocio;
    private String negocio;

    public ProdutoTableView(String nome, String fornecedor, Integer tipoNegocio, String negocio) {
        this.nome = nome;
        this.fornecedor = fornecedor;
        this.tipoNegocio = tipoNegocio;
        this.negocio = negocio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Integer getTipoNegocio() {
        return tipoNegocio;
    }

    public void setTipoNegocio(Integer tipoNegocio) {
        this.tipoNegocio = tipoNegocio;
    }

    public String getNegocio() {
        return negocio;
    }

    public void setNegocio(String negocio) {
        this.negocio = negocio;
    }
}
