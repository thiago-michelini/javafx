package com.t2m.model;

import java.util.List;

public class Produtos {
    private List<Produto> produto;

    public Produtos() {
        super();
    }

    public List<Produto> getProduto() {
        return produto;
    }

    public void setProduto(List<Produto> produto) {
        this.produto = produto;
    }
}
