package br.sushi.zen.dao;

import br.sushi.zen.interfaces.InterfaceDaoCrud;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Categoria_gasto;
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
public class Categorias_gasto implements InterfaceDaoCrud<Categoria_gasto>{
    private PreparedStatement stmtt;
    private Connection conn;

    @Override
    public boolean cadastrar(Categoria_gasto Categoria_gasto){
        try {
            conn = Conexao.conectar();
            String sql = "insert into Categorias_gasto (nome, descricao) "
                    + "values(?,?)";
            stmtt = conn.prepareStatement(sql);
            stmtt.setString(1, Categoria_gasto.getNome());
            stmtt.setString(2, Categoria_gasto.getDescricao());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Cadastrar Categoria_gasto. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
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
    public boolean atualizar(Categoria_gasto Categoria_gasto){
        try {
            conn = Conexao.conectar();
            String sql = "update Categorias_gasto "
                    + "set nome = ?, descricao = ? "
                    + "where id_Categoria_gasto = ?";
            stmtt = conn.prepareStatement(sql);
            stmtt.setString(1, Categoria_gasto.getNome());
            stmtt.setString(2, Categoria_gasto.getDescricao());
            stmtt.setInt(3, Categoria_gasto.getId_categoria_gasto());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Atualizar Categoria_gasto. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
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
    public boolean deletar(Categoria_gasto Categoria_gasto){
        try {
            conn = Conexao.conectar();
            String sql = "delete from Categorias_gasto where id_Categoria_gasto = ?";
            stmtt = conn.prepareStatement(sql);
            stmtt.setInt(1, Categoria_gasto.getId_categoria_gasto());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Deletar Categoria_gasto. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
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
    public ArrayList<Categoria_gasto> listar(){
        ArrayList<Categoria_gasto> listaCategorias_gasto = new ArrayList<>();
        try {
            conn = Conexao.conectar();
            stmtt = conn.prepareStatement("select * from Categorias_gasto order by nome");
            ResultSet resultSet = stmtt.executeQuery();
            while (resultSet.next()) {                
                Categoria_gasto Categoria_gasto = new Categoria_gasto();
                Categoria_gasto.setId_categoria_gasto(resultSet.getInt("id_Categoria_gasto"));
                Categoria_gasto.setNome(resultSet.getString("nome"));
                Categoria_gasto.setDescricao(resultSet.getString("descricao"));
                
                listaCategorias_gasto.add(Categoria_gasto);
            }
            return listaCategorias_gasto;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Consultar Categorias_gasto. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
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
    public Categoria_gasto consultar(Categoria_gasto condicao){
        return null;
    }
}
