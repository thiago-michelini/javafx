package com.t2m.model;

import java.util.List;

public class ProdutoResponse {
    private List<ProdutosFornecedores> produtosFornecedores;

    public ProdutoResponse() {
        super();
    }

    public List<ProdutosFornecedores> getProdutosFornecedores() {
        return produtosFornecedores;
    }

    public void setProdutosFornecedores(List<ProdutosFornecedores> produtosFornecedores) {
        this.produtosFornecedores = produtosFornecedores;
    }
}
