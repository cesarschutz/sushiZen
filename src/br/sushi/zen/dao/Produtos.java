package br.sushi.zen.dao;

import br.sushi.zen.interfaces.InterfaceDaoCrud;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Produto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Cesar Schutz
 */
public class Produtos implements InterfaceDaoCrud<Produto>{
    private PreparedStatement stmtt;
    private Connection conn;

    @Override
    public boolean cadastrar(Produto produto){
        try {
            conn = Conexao.conectar();
            String sql = "insert into produtos (nome, descricao, valor) "
                    + "values(?,?,?)";
            stmtt = conn.prepareStatement(sql);
            stmtt.setString(1, produto.getNome());
            stmtt.setString(2, produto.getDescricao());
            stmtt.setBigDecimal(3, produto.getValor());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Cadastrar Produto. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
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
    public boolean atualizar(Produto produto){
        try {
            conn = Conexao.conectar();
            String sql = "update produtos "
                    + "set nome = ?, descricao = ?, valor = ? "
                    + "where id_produto = ?";
            stmtt = conn.prepareStatement(sql);
            stmtt.setString(1, produto.getNome());
            stmtt.setString(2, produto.getDescricao());
            stmtt.setBigDecimal(3, produto.getValor());
            stmtt.setInt(4, produto.getId_produto());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Atualizar Produto. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
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
    public boolean deletar(Produto produto){
        try {
            conn = Conexao.conectar();
            String sql = "delete from produtos where id_produto = ?";
            stmtt = conn.prepareStatement(sql);
            stmtt.setInt(1, produto.getId_produto());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Deletar Produto. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
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
    public ArrayList<Produto> listar(){
        ArrayList<Produto> listaProdutos = new ArrayList<>();
        try {
            conn = Conexao.conectar();
            stmtt = conn.prepareStatement("select * from produtos order by nome");
            ResultSet resultSet = stmtt.executeQuery();
            while (resultSet.next()) {                
                Produto produto = new Produto();
                produto.setId_produto(resultSet.getInt("id_produto"));
                produto.setNome(resultSet.getString("nome"));
                produto.setDescricao(resultSet.getString("descricao"));
                produto.setValor(new BigDecimal(resultSet.getDouble("valor")).setScale(2, RoundingMode.HALF_EVEN));
                
                listaProdutos.add(produto);
            }
            return listaProdutos;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Consultar Produtos. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
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
    public Produto consultar(Produto condicao){
        return null;
    }
}
