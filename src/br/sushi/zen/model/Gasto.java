package br.sushi.zen.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Classe Gasto
 * @author Cesar Schutz
 */
public class Gasto {
    private int id_gasto;
    private Categoria_gasto categoria_gasto;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private Date data;

    public Gasto() {
    }

    public Gasto(int id_gasto, Categoria_gasto categoria_gasto, String nome, String descricao, BigDecimal valor, Date data) {
        this.id_gasto = id_gasto;
        this.categoria_gasto = categoria_gasto;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public int getId_gasto() {
        return id_gasto;
    }

    public void setId_gasto(int id_gasto) {
        this.id_gasto = id_gasto;
    }

    public Categoria_gasto getCategoria_gasto() {
        return categoria_gasto;
    }

    public void setCategoria_gasto(Categoria_gasto categoria_gasto) {
        this.categoria_gasto = categoria_gasto;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
