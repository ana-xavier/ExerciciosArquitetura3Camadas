package com.lojinha.demo.exceptions;

public class OperacaoNaoRealizadaException extends RuntimeException{
    
    public OperacaoNaoRealizadaException(String mensagem){
        super("Operação Não Realizada - " + mensagem);
    }
}
