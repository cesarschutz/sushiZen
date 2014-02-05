package br.sushi.zen.interfaces;

/**
 * Interface que todas as janelas de cadastros devem implementar.
 * @author Cesar Schutz
 * @param <E>
 */
public interface JanelaCadastrarCrud<E> {    
    /**
     * Metodo que preenche as informações de um registro. 
     * @param e
     */
    void preencher(E e);
    /**
     * Metodo que volta para a janela visualizar deste cadastro. 
     */
    void botaoCancelar();
    /**
     * Metodo que salva um objeto no banco de dados.
     * @param e 
     */
    void botaoSalvar(E e);
    /**
     * Atualiza um objeto no banco de dados.
     * @param e 
     */
    void botaoAtualizar(E e);
    /**
     * Metodo que deleta um objeto do banco de dados.
     * @param e 
     */
    void botaoDeletar(E e);
}
