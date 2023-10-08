/* --> Camada de visão que lida com a interação com o usuário.
 */

package Modelagem_SemScriptSQL.view;
import java.util.List;
import java.util.Scanner;

import Modelagem_SemScriptSQL.controller.LojinhaController;
import Modelagem_SemScriptSQL.model.ProdutoModel;

public class LojinhaView{
    public static void main(String[] args) {
        LojinhaController controller = new LojinhaController();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Listar Catálogo");
            System.out.println("2 - Efetuar Venda");
            System.out.println("3 - Entrada no Estoque");
            System.out.println("4 - Compras Necessárias");
            System.out.println("0 - Sair");
            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    listarCatalogo(controller);
                    break;
                case 2:
                    efetuarVenda(controller, scanner);
                    break;
                case 3:
                    entradaNoEstoque(controller, scanner);
                    break;
                case 4:
                    comprasNecessarias(controller, scanner);
                    break;
                case 0:
                    System.out.println("Saindo da aplicação. Bye Bye xoxo");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente :(");
            }
        }
    }

    private static void listarCatalogo(LojinhaController controller) {
        List<ProdutoModel> catalogo = controller.listarCatalogo();
        System.out.println(" | Catálogo de Produtos Disponíveis:");

        for (ProdutoModel produto : catalogo) {
            System.out.println("> Código: " + produto.getCodigo());
            System.out.println("> Descrição: " + produto.getDescricao());
            System.out.println("> Preço Unitário: " + produto.getPrecoUni());
            System.out.println("> Quantidade em Estoque: " + produto.getQuantidadeEstoque());
            System.out.println("---------------------------");
        }
    }

    private static void efetuarVenda(LojinhaController controller, Scanner scanner) {
        System.out.print("> Digite o código do produto: ");
        int codigoProduto = scanner.nextInt();

        System.out.print("> Digite a quantidade desejada: ");
        int quantidadeDesejada = scanner.nextInt();

        double custoVenda = controller.efetuarVenda(codigoProduto, quantidadeDesejada);
        if (custoVenda == -1) {
            System.out.println("A venda não foi possível devido à falta de estoque ou produto não encontrado :(");
        } else {
            System.out.println("> Custo da Venda: " + custoVenda);
        }
    }

    private static void entradaNoEstoque(LojinhaController controller, Scanner scanner) {
        System.out.print("> Digite o código do produto: ");
        int codigoProduto = scanner.nextInt();

        System.out.print("> Digite a quantidade a ser adicionada ao estoque: ");
        int quantidade = scanner.nextInt();

        controller.entradaNoEstoque(codigoProduto, quantidade);
        System.out.println("Entrada no estoque realizada com sucesso :D");
    }

    private static void comprasNecessarias(LojinhaController controller, Scanner scanner) {
        System.out.print("> Digite a quantidade mínima de estoque: ");
        int estoqueMinimo = scanner.nextInt();

        List<Integer> produtosComEstoqueBaixo = controller.comprasNecessarias(estoqueMinimo);
        System.out.println("> Produtos com estoque abaixo do mínimo:");

        for (int codigoProduto : produtosComEstoqueBaixo) {
            System.out.println("Código: " + codigoProduto);
        }
    }
}
