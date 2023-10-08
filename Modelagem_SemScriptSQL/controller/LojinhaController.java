/* --> Camada onde implementamos as classes que controlam a lógica de negócios
 *     do sistema e fornece os endpoints necessários.
*/

package Modelagem_SemScriptSQL.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Modelagem_SemScriptSQL.model.EstoqueModel;
import Modelagem_SemScriptSQL.model.ProdutoModel;

public class LojinhaController {
    private EstoqueModel estoque;

    public LojinhaController() {
        estoque = new EstoqueModel();
    }

    public List<ProdutoModel> listarCatalogo() {
        List<ProdutoModel> catalogo = new ArrayList<>();
        
        for (ProdutoModel produto : estoque.getProdutos().values()) {
            if (produto.getQuantidadeEstoque() > 0) {
                catalogo.add(produto);
            }
        }
        return catalogo;
    }

    public double efetuarVenda(int codigoProduto, int quantidadeDesejada) {
        if (!estoque.existeProduto(codigoProduto)) {
            return -1; // produto não encontrado
        }

        ProdutoModel produto = estoque.getProdutoEstoque(codigoProduto);
        if (produto.getQuantidadeEstoque() < quantidadeDesejada) {
            return -1; // venda não é possível devido à falta de estoque
        }

        double custoVenda = calcularCustoVenda(produto, quantidadeDesejada);
        // --> atualizar o estoque após a venda
        int novaQuantidade = produto.getQuantidadeEstoque() - quantidadeDesejada;
        estoque.atualizarProduto(codigoProduto, novaQuantidade);

        return custoVenda;
    }

    public void entradaNoEstoque(int codigoProduto, int quantidade) {
        Scanner entrada = new Scanner(System.in);
        if (!estoque.existeProduto(codigoProduto)) {

            // --> se o produto não existe, criá-lo no estoque
            System.out.println("Informe os dados do produto: ");
            System.out.print("Descrição: ");
            String descricao = entrada.nextLine();
            System.out.print("Preço: ");
            double preco = entrada.nextDouble();
            
            ProdutoModel novoProduto = new ProdutoModel(codigoProduto, descricao, preco, quantidade);
            estoque.adicionarProduto(novoProduto);
        } else {
            ProdutoModel produto = estoque.getProdutoEstoque(codigoProduto);
            int novaQuantidade = produto.getQuantidadeEstoque() + quantidade;
            estoque.atualizarProduto(codigoProduto, novaQuantidade);
        }
    }

    public List<Integer> comprasNecessarias(int estoqueMinimo) {
        List<Integer> produtosComEstoqueBaixo = new ArrayList<>();

        for (Map.Entry<Integer, ProdutoModel> entry : estoque.getProdutos().entrySet()) {
            int codigoProduto = entry.getKey();
            ProdutoModel produto = entry.getValue();

            if (produto.getQuantidadeEstoque() < estoqueMinimo) {
                produtosComEstoqueBaixo.add(codigoProduto);
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
}
