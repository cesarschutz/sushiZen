package br.sushi.zen.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Classe Gasto
 * @author Cesar Schutz
 */
public class Categoria_gasto {
    private int id_categoria_gasto;
    private String nome;
    private String descricao;

    public Categoria_gasto() {
    }

    public Categoria_gasto(int id_categoria_gasto, String nome, String descricao, BigDecimal valor, Date data) {
        this.id_categoria_gasto = id_categoria_gasto;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getId_categoria_gasto() {
        return id_categoria_gasto;
    }

    public void setId_categoria_gasto(int id_categoria_gasto) {
        this.id_categoria_gasto = id_categoria_gasto;
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
}
