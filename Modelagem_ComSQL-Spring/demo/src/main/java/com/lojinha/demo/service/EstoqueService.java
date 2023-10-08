package com.lojinha.demo.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lojinha.demo.exceptions.OperacaoNaoRealizadaException;
import com.lojinha.demo.model.ProdutoModel;
import com.lojinha.demo.repository.EstoqueRepository;

@Service
public class EstoqueService {

    public EstoqueRepository repository;

    @Autowired
    public EstoqueService(EstoqueRepository repository) {
        this.repository = repository;
    }

    public List<ProdutoModel> listarCatalogo() {
        List<ProdutoModel> catalogo = new ArrayList<>();

        for (ProdutoModel produto : repository.getProdutos()) {
            if (produto.getQuantidadeEstoque() > 0) {
                catalogo.add(produto);
            }
        }
        return catalogo;
    }

    public double efetuarVenda(int codigoProduto, int quantidadeDesejada) {

        if (!produtoExiste(codigoProduto))
            throw new OperacaoNaoRealizadaException("Produto não encontrado! :(");

        ProdutoModel produto = repository.getProdutoEstoque(codigoProduto);

        if (produto.getQuantidadeEstoque() < quantidadeDesejada) {
            throw new OperacaoNaoRealizadaException("Não há produtos no estoque! :(");
        }

        double custoVenda = calcularCustoVenda(produto, quantidadeDesejada);
        // --> atualizar o estoque após a venda
        int novaQuantidade = produto.getQuantidadeEstoque() - quantidadeDesejada;
        repository.atualizarProduto(codigoProduto, novaQuantidade);

        return custoVenda;
    }

    public void entradaNoEstoque(int codigo, String descricao, double preco, int quantidade) {

        if (!produtoExiste(codigo))
            repository.adicionarProduto(new ProdutoModel(codigo, descricao, preco, quantidade));

        ProdutoModel produto = repository.getProdutoEstoque(codigo);

        int novaQuantidade = produto.getQuantidadeEstoque() + quantidade;
        repository.atualizarProduto(codigo, novaQuantidade);
    }

    public List<Integer> comprasNecessarias(int estoqueMinimo) {
        List<Integer> produtosComEstoqueBaixo = new ArrayList<>();

        for (ProdutoModel produto : repository.getProdutos()) {

            if (produto.getQuantidadeEstoque() < estoqueMinimo) {
                produtosComEstoqueBaixo.add(produto.getCodigo());
            }
        }

        return produtosComEstoqueBaixo;
    }

    // --> método auxiliar
    private double calcularCustoVenda(ProdutoModel produto, int quantidadeDesejada) {
        double precoUnitario = produto.getPrecoUni();
        double custo = precoUnitario * quantidadeDesejada;

        // --> aplicar desconto de 10% para quantidades maiores que 10 unidades
        if (quantidadeDesejada > 10) {
            custo *= 0.9;
        }
        // --> aplicar 5% de imposto sobre o valor final
        custo *= 1.05;

        return custo;
    }

    // --> método auxiliar
    private boolean produtoExiste(int codigo) {
        ProdutoModel produto = repository.getProdutoEstoque(codigo);
        return produto == null;
    }
}
