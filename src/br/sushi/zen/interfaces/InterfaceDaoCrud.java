package br.sushi.zen.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Interface que todas as classes DAO devem ter
 * @author Cesar Schutz
 */
public interface InterfaceDaoCrud<E> {
    /**
     * Variavel de conexão com o banco de dados.
     */
    Connection con = null;
    /**
     * Variavel de statmente para realizar ações no banco de dados.
     */
    PreparedStatement stmt = null;
    /**
     * Cadastra o objeto no banco de dados;.
     * @param objetoParaCadastrar
     * @return true se cadastrou e false se não cadastrou 
     */
    boolean cadastrar(E objetoParaCadastrar);
    /**
     * Atualiza o objeto no banco de dados.
     * @param objetoParaAtualizar
     * @return true se atualizou e false se não atualizou 
     */
    boolean atualizar(E objetoParaAtualizar);
    /**
     * Deleta o objeto do banco de dados.
     * @param objetoParaDeletar
     * @return true se deletou e false se não deletou 
     */
    boolean deletar(E objetoParaDeletar);
    /**
     * Busca todos os registros do banco de dados.
     * @return lista com todos os registros encontrados 
     */
    ArrayList<E> listar();
    /**
     * Consulta um unico registro com alguma condição (id, nome, etc)
     * @return 
     */
    E consultar(E condicao);
}
