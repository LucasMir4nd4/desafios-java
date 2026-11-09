package br.com.lucas.vendas.model;

import java.time.LocalDateTime;

public class Venda {

    private LocalDateTime data;
    private String nome;
    private int quantidade;
    private double valor;
    private boolean valida;

    

    public Venda(LocalDateTime data, String nome, int quantidade, double valor) {
        this.data = data;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
        this.valida = true;
    }
    
    
    public Venda(String nome) {
        this.data = null;
        this.nome = nome;
        this.quantidade = 0;
        this.valor = 0;
        this.valida = false;
    }


    public LocalDateTime getData() {
        return data;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValor() {
        return valor;
    }

}
