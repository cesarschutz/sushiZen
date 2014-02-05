package br.sushi.zen.model;

import java.sql.Date;

/**
 * Classe modelo da tabela Cliente do banco de dados.
 * @author Cesar Schutz
 */
public class Cliente {
    int id_cliente;
    String nome;
    Date nascimento;
    String telefone;
    String celular;
    String eMail;
    String endereco;
    String bairro;
    String referencia;
    String observacao;
    String como_chegou_ate_nos;

    /**
     * DEFAULT CONSTRUCTOR
     */
    public Cliente() {
    }
    
    /**
     * FULL CONSTRUCTOR
     */
    public Cliente(String nome, Date nascimento, String telefone, String celular, String eMail, String endereco, String bairro, String referencia, String observacao, String como_chegou_ate_nos) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.celular = celular;
        this.eMail = eMail;
        this.endereco = endereco;
        this.bairro = bairro;
        this.referencia = referencia;
        this.observacao = observacao;
        this.como_chegou_ate_nos = como_chegou_ate_nos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getComo_chegou_ate_nos() {
        return como_chegou_ate_nos;
    }

    public void setComo_chegou_ate_nos(String como_chegou_ate_nos) {
        this.como_chegou_ate_nos = como_chegou_ate_nos;
    }
    
    @Override
    public String toString(){
        return "nome: " + nome + " - email: " + eMail + " - celular: " + celular + " - endereco: " + endereco + " - bairro: " + bairro + " - referencia: " + referencia + " - aniversario: " + nascimento+ "\n";
    }
}
