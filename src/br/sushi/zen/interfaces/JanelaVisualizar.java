package br.sushi.zen.interfaces;

import java.util.ArrayList;

/**
 * Interface que todas as janelas de visualizar cadastros devem implementar.
 * @author Cesar Schutz
 */
public interface JanelaVisualizar<E> {
    /**
     * Metodo que preenche a tabela com os objetos consultados.
     * @param lista 
     */
    void preencheTabela(ArrayList<E> lista);
    /**
     * Metodo que abre a janela para um novo cadastro no banco de dados.
     */
    void botaoNovo();
    /**
     * Metodo que abre a janela para editar um objeto selecionado.
     * @param objeto 
     */
    void clicaTabela(E objeto);
}
