package br.sushi.zen.dao;

import br.sushi.zen.interfaces.InterfaceDaoCrud;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Forma_de_pagamento;
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
public class Formas_de_pagamento implements InterfaceDaoCrud<Forma_de_pagamento>{
    private PreparedStatement stmtt;
    private Connection conn;

    @Override
    public boolean cadastrar(Forma_de_pagamento Forma_de_pagamento){
        try {
            conn = Conexao.conectar();
            String sql = "insert into Formas_de_pagamento (nome, descricao) "
                    + "values(?,?)";
            stmtt = conn.prepareStatement(sql);
            stmtt.setString(1, Forma_de_pagamento.getNome());
            stmtt.setString(2, Forma_de_pagamento.getDescricao());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Cadastrar Forma_de_pagamento. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
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
    public boolean atualizar(Forma_de_pagamento Forma_de_pagamento){
        try {
            conn = Conexao.conectar();
            String sql = "update Formas_de_pagamento "
                    + "set nome = ?, descricao = ? "
                    + "where id_Forma_de_pagamento = ?";
            stmtt = conn.prepareStatement(sql);
            stmtt.setString(1, Forma_de_pagamento.getNome());
            stmtt.setString(2, Forma_de_pagamento.getDescricao());
            stmtt.setInt(3, Forma_de_pagamento.getId_forma_de_pagamento());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Atualizar Forma_de_pagamento. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
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
    public boolean deletar(Forma_de_pagamento Forma_de_pagamento){
        try {
            conn = Conexao.conectar();
            String sql = "delete from Formas_de_pagamento where id_Forma_de_pagamento = ?";
            stmtt = conn.prepareStatement(sql);
            stmtt.setInt(1, Forma_de_pagamento.getId_forma_de_pagamento());
            stmtt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Deletar Forma_de_pagamento. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
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
    public ArrayList<Forma_de_pagamento> listar(){
        ArrayList<Forma_de_pagamento> listaFormas_de_pagamento = new ArrayList<>();
        try {
            conn = Conexao.conectar();
            stmtt = conn.prepareStatement("select * from Formas_de_pagamento order by nome");
            ResultSet resultSet = stmtt.executeQuery();
            while (resultSet.next()) {                
                Forma_de_pagamento Forma_de_pagamento = new Forma_de_pagamento();
                Forma_de_pagamento.setId_forma_de_pagamento(resultSet.getInt("id_Forma_de_pagamento"));
                Forma_de_pagamento.setNome(resultSet.getString("nome"));
                Forma_de_pagamento.setDescricao(resultSet.getString("descricao"));
                
                listaFormas_de_pagamento.add(Forma_de_pagamento);
            }
            return listaFormas_de_pagamento;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Consultar Formas_de_pagamento. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
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
    public Forma_de_pagamento consultar(Forma_de_pagamento condicao){
        return null;
    }
}
