package com.lira.msavaliadorcredito.application.ex;

public class DadosClientesNotFoundException extends Exception{
    public DadosClientesNotFoundException() {
        super("Dados do cliente não encontrado para o CPF informado.");
    }
}
