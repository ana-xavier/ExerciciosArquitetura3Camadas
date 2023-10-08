package com.lojinha.demo.model;

public class ProdutoModel {
    
    private int codigo;
    private String descricao;
    private double precoUni;
    private int quantidadeEstoque;
    
    public ProdutoModel(int codigo, String descricao, double precoUnitario, int quantidadeEstoque) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.precoUni = precoUnitario;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getCodigo() {
        return codigo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public double getPrecoUni() {
        return precoUni;
    }
    public void setPrecoUni(double precoUni) {
        this.precoUni = precoUni;
    }
    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
}
