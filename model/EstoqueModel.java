/*
 * --> Classe model para estoque.
 */
package model;
import java.util.HashMap;
import java.util.Map;

public class EstoqueModel {
    private Map<Integer, ProdutoModel> produtos;

    public EstoqueModel() {
        produtos = new HashMap<>();

        adicionarProduto(new ProdutoModel(1, "Chaveiro", 10.0, 20));
        adicionarProduto(new ProdutoModel(2, "Cardeneta", 15.0, 15));
        adicionarProduto(new ProdutoModel(3, "Par de Meias", 8.5, 30));
        adicionarProduto(new ProdutoModel(4, "Copo Decorado", 12.0, 25));
        adicionarProduto(new ProdutoModel(5, "Lápis Cinza", 7.0, 40));
        adicionarProduto(new ProdutoModel(6, "Caneta Brilho", 9.0, 10));
        adicionarProduto(new ProdutoModel(7, "Estojo Azul", 20.0, 5));
        adicionarProduto(new ProdutoModel(8, "Caderno 98 Folhas", 25.0, 18));
        adicionarProduto(new ProdutoModel(9, "Kit de Borrachas", 14.0, 22));
        adicionarProduto(new ProdutoModel(10, "Canetinhas", 18.0, 12));
    }

    public void adicionarProduto(ProdutoModel produto) {
        produtos.put(produto.getCodigo(), produto);
    }

    public void atualizarProduto(int codigo, int novaQuantidade) {
        if (produtos.containsKey(codigo)) {
            ProdutoModel produto = produtos.get(codigo);
            produto.setQuantidadeEstoque(novaQuantidade);
        }
    }

    public ProdutoModel getProdutoEstoque(int codigo) {
        return produtos.get(codigo);
    }

    public boolean existeProduto(int codigo) {
        return produtos.containsKey(codigo);
    }

    public Map<Integer, ProdutoModel> getProdutos() {
        return produtos;
    }
}