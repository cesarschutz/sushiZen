package br.sushi.zen.model;

import java.math.BigDecimal;

/**
 * Classe Produtos
 * @author Cesar Schutz
 */
public class Produto {
    private int id_produto;
    private String nome;
    private String descricao;
    private BigDecimal valor;

    public Produto() {}

    public Produto(int id_produto, String nome, String descricao, BigDecimal valor) {
        this.id_produto = id_produto;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    @Override
    public String toString(){
        return "Nome produto: " + nome + ", descricao produto: " + descricao + ", valor produto: " + valor;
    }
}
