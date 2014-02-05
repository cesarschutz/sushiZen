package br.sushi.zen.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Classe Gasto
 * @author Cesar Schutz
 */
public class Forma_de_pagamento {
    private int id_forma_de_pagamento;
    private String nome;
    private String descricao;

    public Forma_de_pagamento() {
    }

    public Forma_de_pagamento(int id_forma_de_pagamento, String nome, String descricao, BigDecimal valor, Date data) {
        this.id_forma_de_pagamento = id_forma_de_pagamento;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getId_forma_de_pagamento() {
        return id_forma_de_pagamento;
    }

    public void setId_forma_de_pagamento(int id_forma_de_pagamento) {
        this.id_forma_de_pagamento = id_forma_de_pagamento;
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
