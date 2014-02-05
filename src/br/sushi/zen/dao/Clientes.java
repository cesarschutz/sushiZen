package br.sushi.zen.dao;

import br.sushi.zen.interfaces.InterfaceDaoCrud;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.firebirdsql.jdbc.FirebirdPreparedStatement;

/**
 * Classe DAO da tabela Cliente do banco de dados
 *
 * @author Cesar Schutz
 */
public class Clientes implements InterfaceDaoCrud<Cliente> {

    private PreparedStatement stmtt;
    private Connection conn;

    @Override
    public boolean cadastrar(Cliente cliente){
        try {
            conn = Conexao.conectar();
            String sql = "insert into cliente (nome, nascimento, telefone, celular, email, endereco, bairro, referencia, como_chegou_ate_nos, observacao) "
                    + "values(?,?,?,?,?,?,?,?,?,?)";
            stmtt = conn.prepareStatement(sql);
            stmtt.setString(1, cliente.getNome());
            stmtt.setDate(2, cliente.getNascimento());
            stmtt.setString(3, cliente.getTelefone());
            stmtt.setString(4, cliente.getCelular());
            stmtt.setString(5, cliente.geteMail());
            stmtt.setString(6, cliente.getEndereco());
            stmtt.setString(7, cliente.getBairro());
            stmtt.setString(8, cliente.getReferencia());
            stmtt.setString(9, cliente.getComo_chegou_ate_nos());
            stmtt.setString(10, cliente.getObservacao());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Cadastrar Cliente. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                stmtt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
    }
    
    public int cadastrarComRetornoId(Cliente cliente){
        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false);
            
            String sql = "insert into cliente (nome, nascimento, telefone, celular, email, endereco, bairro, referencia, como_chegou_ate_nos, observacao) "
                    + "values(?,?,?,?,?,?,?,?,?,?) RETURNING id_cliente";
            stmtt = (FirebirdPreparedStatement) conn.prepareStatement(sql);
            stmtt.setString(1, cliente.getNome());
            stmtt.setDate(2, cliente.getNascimento());
            stmtt.setString(3, cliente.getTelefone());
            stmtt.setString(4, cliente.getCelular());
            stmtt.setString(5, cliente.geteMail());
            stmtt.setString(6, cliente.getEndereco());
            stmtt.setString(7, cliente.getBairro());
            stmtt.setString(8, cliente.getReferencia());
            stmtt.setString(9, cliente.getComo_chegou_ate_nos());
            stmtt.setString(10, cliente.getObservacao());
            ResultSet rs = stmtt.executeQuery();
            
            //pega o id retornado
            rs.next();
            int idCliente = rs.getInt("id_cliente");
            
            conn.commit();
            return idCliente;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Cadastrar Cliente. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
            try {
                conn.rollback();
            } catch (SQLException ex) {
            }
            return 0;
        } finally {
            try {
                stmtt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public boolean atualizar(Cliente cliente){
        try {
            conn = Conexao.conectar();
            String sql = "update cliente "
                    + "set nome = ?, nascimento = ?, telefone = ?, celular = ?, email = ?, endereco = ?, bairro = ?, referencia = ?, como_chegou_ate_nos = ?, observacao = ? "
                    + "where id_cliente = ?";
            stmtt = conn.prepareStatement(sql);
            stmtt.setString(1, cliente.getNome());
            stmtt.setDate(2, cliente.getNascimento());
            stmtt.setString(3, cliente.getTelefone());
            stmtt.setString(4, cliente.getCelular());
            stmtt.setString(5, cliente.geteMail());
            stmtt.setString(6, cliente.getEndereco());
            stmtt.setString(7, cliente.getBairro());
            stmtt.setString(8, cliente.getReferencia());
            stmtt.setString(9, cliente.getComo_chegou_ate_nos());
            stmtt.setString(10, cliente.getObservacao());
            stmtt.setInt(11, cliente.getId_cliente());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Atualizar Cliente. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                stmtt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public boolean deletar(Cliente cliente){
        try {
            conn = Conexao.conectar();
            String sql = "delete from cliente where id_cliente = ?";
            stmtt = conn.prepareStatement(sql);
            stmtt.setInt(1, cliente.getId_cliente());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Deletar Cliente. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                stmtt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public ArrayList<Cliente> listar(){
        ArrayList<Cliente> listaCliente = new ArrayList<>();
        try {
            conn = Conexao.conectar();
            stmtt = conn.prepareStatement("select * from cliente order by nome");
            ResultSet resultSet = stmtt.executeQuery();
            while (resultSet.next()) {                
                Cliente cliente = new Cliente();
                cliente.setId_cliente(resultSet.getInt("id_cliente"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setNascimento(resultSet.getDate("nascimento"));
                cliente.setTelefone(resultSet.getString("telefone"));
                cliente.setCelular(resultSet.getString("celular"));
                cliente.seteMail(resultSet.getString("email"));
                cliente.setEndereco(resultSet.getString("endereco"));
                cliente.setBairro(resultSet.getString("bairro"));
                cliente.setReferencia(resultSet.getString("referencia"));
                cliente.setComo_chegou_ate_nos(resultSet.getString("como_chegou_ate_nos"));
                cliente.setObservacao(resultSet.getString("observacao"));
                
                listaCliente.add(cliente);
            }
            return listaCliente;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Consultar Clientes. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                stmtt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
        return null;
    }

    @Override
    public Cliente consultar(Cliente condicao){
        return null;
    }
}
