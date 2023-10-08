package com.lojinha.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lojinha.demo.service.EstoqueService;
import com.lojinha.demo.exceptions.OperacaoNaoRealizadaException;
import com.lojinha.demo.model.ProdutoModel;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/estoque")
public class EstoqueController {

    private EstoqueService service;

    @Autowired
    public EstoqueController(EstoqueService service) {
        this.service = service;
    }

    // Lista Catalogo
    @GetMapping("")
    public List<ProdutoModel> getCatalogo() {
        return service.listarCatalogo();
    }

    // Efetua uma venda
    @PostMapping("/venda/{codigo}/quantidade/{quantidade}")
    public double efetuarVenda(@PathVariable(value = "codigo") int codigo,
            @PathVariable(value = "quantidade") int quantidade) {
        return service.efetuarVenda(codigo, quantidade);
    }

    // Entrada no estoque
    @PostMapping("entrada/{codigo}/quantidade/{quantidade}")
    public ResponseEntity<String> efetuarEntrada(@PathVariable(value = "codigo") int codigo,
            @PathVariable(value = "quantidade") int quantidade,
            @RequestParam(value = "descricao") String descricao,
            @RequestParam(value = "preco") double preco) {
        service.entradaNoEstoque(codigo, descricao, preco, quantidade);
        return ResponseEntity.ok("Estoque do Produto atualizado! :D");
    }

    // Verificar compras necessárias
    @GetMapping("verificacao")
    public List<Integer> verificaCompras(@RequestParam(value = "quantidade") int quantidadeMinima){
        return service.comprasNecessarias(quantidadeMinima);
    }

    // EXCEPTION
    @ExceptionHandler({ OperacaoNaoRealizadaException.class })
    public ResponseEntity<String> handleOperacaoNaoRealizada(RuntimeException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
    }
}
