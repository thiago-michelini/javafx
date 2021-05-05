package com.t2m.model;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class TransacaoTableView {

    private String produto;
    private String fornecedor;
    private String valor;
    private String status;
    private String idTransacao;
    private Long nsuFornecedor;
    private String dataHora;

    public TransacaoTableView() {
        super();
    }
    public TransacaoTableView(Transacao transacao) {
        super();
        produto = transacao.getProduto().getNome();
        fornecedor = transacao.getProduto().getFornecedor().getNome();
        valor = new DecimalFormat("##,##0.00").format( Double.valueOf(transacao.getValor())/100);
        status = transacao.getStatus();
        idTransacao = transacao.getIdTransacao();
        nsuFornecedor = transacao.getNsuFornecedor();
        int idxPonto = transacao.getDataHora().indexOf(".");
        TemporalAccessor ta = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").parse(transacao.getDataHora().substring(0, idxPonto));
        dataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy' 'HH:mm:ss").format(ta);
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(String idTransacao) {
        this.idTransacao = idTransacao;
    }

    public Long getNsuFornecedor() {
        return nsuFornecedor;
    }

    public void setNsuFornecedor(Long nsuFornecedor) {
        this.nsuFornecedor = nsuFornecedor;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
}
