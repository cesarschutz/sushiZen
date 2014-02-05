package br.sushi.zen.dao;

import br.sushi.zen.interfaces.InterfaceDaoCrud;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Categoria_gasto;
import br.sushi.zen.model.Gasto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Cesar Schutz
 */
public class Gastos implements InterfaceDaoCrud<Gasto>{
    private PreparedStatement stmtt;
    private Connection conn;

    @Override
    public boolean cadastrar(Gasto gasto){
        try {
            conn = Conexao.conectar();
            String sql = "insert into gastos (nome, descricao, valor, data, id_categoria_gasto) "
                    + "values(?,?,?,?,?)";
            stmtt = conn.prepareStatement(sql);
            stmtt.setString(1, gasto.getNome());
            stmtt.setString(2, gasto.getDescricao());
            stmtt.setBigDecimal(3, gasto.getValor());
            stmtt.setDate(4, gasto.getData());
            stmtt.setInt(5, gasto.getCategoria_gasto().getId_categoria_gasto());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Cadastrar Gasto. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                //stmtt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public boolean atualizar(Gasto gasto){
        try {
            conn = Conexao.conectar();
            String sql = "update gastos "
                    + "set nome = ?, descricao = ?, valor = ?, data = ?, id_categoria_gasto = ? "
                    + "where id_gasto = ?";
            stmtt = conn.prepareStatement(sql);
            stmtt.setString(1, gasto.getNome());
            stmtt.setString(2, gasto.getDescricao());
            stmtt.setBigDecimal(3, gasto.getValor());
            stmtt.setDate(4, gasto.getData());
            stmtt.setInt(5, gasto.getCategoria_gasto().getId_categoria_gasto());
            stmtt.setInt(6, gasto.getId_gasto());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Atualizar Gasto. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
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
    public boolean deletar(Gasto gasto){
        try {
            conn = Conexao.conectar();
            String sql = "delete from gastos where id_gasto = ?";
            stmtt = conn.prepareStatement(sql);
            stmtt.setInt(1, gasto.getId_gasto());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Deletar Gasto. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                stmtt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public ArrayList<Gasto> listar(Date data){
        ArrayList<Gasto> listaDeGastos = new ArrayList<>();
        try {
            conn = Conexao.conectar();
            stmtt = conn.prepareStatement("select gastos.id_gasto, gastos.data, gastos.nome, gastos.descricao, gastos.valor, categorias_gasto.id_categoria_gasto, categorias_gasto.nome nome_categoria\n" +
                    "from gastos INNER JOIN categorias_gasto ON gastos.id_categoria_gasto = categorias_gasto.id_categoria_gasto\n" +
                    "where gastos.data = ?\n" +
                    "order by categorias_gasto.nome, gastos.nome");
            stmtt.setDate(1, data);
            ResultSet resultSet = stmtt.executeQuery();
            while (resultSet.next()) {   
                Categoria_gasto categoria = new Categoria_gasto();
                categoria.setId_categoria_gasto(resultSet.getInt("id_categoria_gasto"));
                categoria.setNome(resultSet.getString("nome_categoria"));
                
                Gasto gasto = new Gasto();
                gasto.setId_gasto(resultSet.getInt("id_gasto"));
                gasto.setCategoria_gasto(categoria);
                gasto.setNome(resultSet.getString("nome"));
                gasto.setDescricao(resultSet.getString("descricao"));
                gasto.setValor(new BigDecimal(resultSet.getDouble("valor")).setScale(2, RoundingMode.HALF_EVEN));
                gasto.setData(resultSet.getDate("data"));
                
                listaDeGastos.add(gasto);
            }
            return listaDeGastos;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Consultar Gastos. Procure o Administrador." + e, "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                //stmtt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
        return null;
    }

    @Override
    public Gasto consultar(Gasto condicao){
        return null;
    }
    
    @Override
    public ArrayList<Gasto> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
