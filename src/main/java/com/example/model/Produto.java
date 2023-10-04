package com.example.model;

import java.math.BigDecimal;

public class Produto {
    
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private Pedido pedido;
    
    public Produto(Long id, String descricao, BigDecimal valor, Pedido pedido) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Produto valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
    public Produto descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

}
