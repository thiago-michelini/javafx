package com.t2m.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
