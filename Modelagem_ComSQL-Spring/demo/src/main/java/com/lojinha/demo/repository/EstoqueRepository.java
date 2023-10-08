package com.lojinha.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lojinha.demo.model.ProdutoModel;

@Repository
public class EstoqueRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EstoqueRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProdutoModel> getProdutos() {
        String sql = "SELECT * FROM produtos";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ProdutoModel(
            rs.getInt("codigo"), 
            rs.getString("descricao"),
            rs.getDouble("preco_unitario"), 
            rs.getInt("quantidade_estoque")
            ));
    }

    public ProdutoModel getProdutoEstoque(int codigo) {
        String sql = "SELECT * FROM produtos WHERE codigo = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ProdutoModel(
                rs.getInt("codigo"),
                rs.getString("descricao"),
                rs.getDouble("preco_unitario"),
                rs.getInt("quantidade_estoque")
        ), codigo);
    }

    public void adicionarProduto(ProdutoModel produto) {
        String sql = "INSERT INTO produtos(codigo, descricao, preco_unitario, quantidade_estoque) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, produto.getCodigo(), produto.getDescricao(), produto.getPrecoUni(),
                produto.getQuantidadeEstoque());
    }

    public void atualizarProduto(int codigo, int novaQuantidade) {
        String sql = "UPDATE produtos SET quantidade_estoque = ? WHERE codigo = ?";
        jdbcTemplate.update(sql, novaQuantidade, codigo);
    }

}
