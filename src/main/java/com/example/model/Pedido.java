package com.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    
    private int id;
    private List<Produto> produtos = new ArrayList<Produto>();
    private LocalDate dataCriacao;
    
    public Pedido(int id, List<Produto> produtos) {
        this.id = id;
        this.produtos = produtos;
        this.dataCriacao = LocalDate.now();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public List<Produto> getProdutos() {
        return produtos;
    }
    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    } 

    
}
