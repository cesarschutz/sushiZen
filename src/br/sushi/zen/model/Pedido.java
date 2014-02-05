package br.sushi.zen.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cesar Schutz
 */
public class Pedido {
    
    private int id_pedido;
    private Cliente cliente;
    private Date data;
    private List listaProdutos = new ArrayList();
    private BigDecimal valor_total;
    private BigDecimal valor_total_cobrado;
    private int porcentagem_desconto;
    private String observacao;
    private int ID_FORMA_DE_PAGAMENTO;

    public Pedido() {
    }

    public Pedido(int id_pedido, Cliente cliente, Date data, BigDecimal valor_total, BigDecimal valor_total_cobrado, int porcentagem_desconto, String observacao, int ID_FORMA_DE_PAGAMENTO) {
        this.id_pedido = id_pedido;
        this.cliente = cliente;
        this.data = data;
        this.valor_total = valor_total;
        this.valor_total_cobrado = valor_total_cobrado;
        this.porcentagem_desconto = porcentagem_desconto;
        this.observacao = observacao;
        this.ID_FORMA_DE_PAGAMENTO = ID_FORMA_DE_PAGAMENTO;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public BigDecimal getValor_total() {
        return valor_total;
    }

    public void setValor_total(BigDecimal valor_total) {
        this.valor_total = valor_total;
    }

    public BigDecimal getValor_total_cobrado() {
        return valor_total_cobrado;
    }

    public void setValor_total_cobrado(BigDecimal valor_total_cobrado) {
        this.valor_total_cobrado = valor_total_cobrado;
    }

    public int getPorcentagem_desconto() {
        return porcentagem_desconto;
    }

    public void setPorcentagem_desconto(int porcentagem_desconto) {
        this.porcentagem_desconto = porcentagem_desconto;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getID_FORMA_DE_PAGAMENTO() {
        return ID_FORMA_DE_PAGAMENTO;
    }

    public void setID_FORMA_DE_PAGAMENTO(int ID_FORMA_DE_PAGAMENTO) {
        this.ID_FORMA_DE_PAGAMENTO = ID_FORMA_DE_PAGAMENTO;
    }
}
