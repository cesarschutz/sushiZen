package br.sushi.zen.model;

import java.math.BigDecimal;



/**
 * o valor_total é o valor da quantidade * valor_unitario
 * o valor_total_cobrado é o que o usuario digitou e que foi cobrado
 * @author Cesar Schutz
 */
public class Pedido_Produto extends Produto{

    private int id_pedido_produto;
    private int id_pedido;
    private int quantidade;
    private BigDecimal valor_unitario;
    private BigDecimal valor_total_cobrado;
    private BigDecimal valor_total;

    public Pedido_Produto() {
    }

    public Pedido_Produto(int id_pedido_produto, int id_pedido, int quantidade, BigDecimal valor_unitario, BigDecimal valor_total_cobrado, int id_produto, String nome, String descricao, BigDecimal valor) {
        super(id_produto, nome, descricao, valor);
        this.id_pedido_produto = id_pedido_produto;
        this.id_pedido = id_pedido;
        this.quantidade = quantidade;
        this.valor_unitario = valor_unitario;
        this.valor_total_cobrado = valor_total_cobrado;
    }

    public int getId_pedido_produto() {
        return id_pedido_produto;
    }

    public void setId_pedido_produto(int id_pedido_produto) {
        this.id_pedido_produto = id_pedido_produto;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(BigDecimal valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    public BigDecimal getValor_total_cobrado() {
        return valor_total_cobrado;
    }

    public void setValor_total_cobrado(BigDecimal valor_total_cobrado) {
        this.valor_total_cobrado = valor_total_cobrado;
    }

    public BigDecimal getValor_total() {
        return valor_total;
    }

    public void setValor_total(BigDecimal valor_total) {
        this.valor_total = valor_total;
    }
}
