package br.sushi.zen.dao;

import br.sushi.zen.interfaces.InterfaceDaoCrud;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Cliente;
import br.sushi.zen.model.Pedido;
import br.sushi.zen.model.Pedido_Produto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.firebirdsql.jdbc.FirebirdPreparedStatement;

/**
 *
 * @author Cesar Schutz
 */
public class Pedidos implements InterfaceDaoCrud<Pedido> {

    private PreparedStatement stmtt;
    private Connection conn;

    @Override
    public boolean cadastrar(Pedido pedido) {
        try {
            conn = Conexao.conectar();
            
            conn.setAutoCommit(false);
            
            //cadastra o pedido
            String sql = "insert into pedidos (id_cliente, data, valor_total, observacao, ID_FORMA_DE_PAGAMENTO) "
                    + "values(?,?,?,?,?) RETURNING id_pedido";
            stmtt = (FirebirdPreparedStatement) conn.prepareStatement(sql);
            stmtt.setInt(1, pedido.getCliente().getId_cliente());
            stmtt.setDate(2, pedido.getData());
            stmtt.setBigDecimal(3, pedido.getValor_total());
            stmtt.setString(4, pedido.getObservacao());
            stmtt.setInt(5, pedido.getID_FORMA_DE_PAGAMENTO());
            ResultSet rs = stmtt.executeQuery();
            
            //pega o id retornado
            rs.next();
            pedido.setId_pedido(rs.getInt("id_pedido"));
            
            //salvando os produtos_pedidos
            for (Object pedidoProduto : pedido.getListaProdutos()) {
                Pedido_Produto pedido_produto = (Pedido_Produto) pedidoProduto;
                String sqlProduto = "insert into pedido_produtos (id_pedido, id_produto, quantidade, valor_unitario, valor_total_cobrado, valor_total) "
                    + "values(?,?,?,?,?,?)";
                stmtt =  conn.prepareStatement(sqlProduto);
                stmtt.setInt(1, pedido.getId_pedido());
                stmtt.setInt(2, pedido_produto.getId_produto());
                stmtt.setInt(3, pedido_produto.getQuantidade());
                stmtt.setBigDecimal(4, pedido_produto.getValor_unitario());
                stmtt.setBigDecimal(5, pedido_produto.getValor_total_cobrado());
                stmtt.setBigDecimal(6, pedido_produto.getValor_total());
                
                stmtt.executeUpdate();
            }
            
            conn.commit();

            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Cadastrar Pedido. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
            try {
                conn.rollback();
            } catch (SQLException ex) {
            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public boolean atualizar(Pedido pedido) {
        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false);
            //cadastrando o pedido
            String sql = "update pedidos "
                    + "set id_cliente = ?, data = ?, valor_total = ?, observacao = ?, ID_FORMA_DE_PAGAMENTO = ? "
                    + "where id_pedido = ?";
            stmtt = conn.prepareStatement(sql);
            stmtt.setInt(1, pedido.getCliente().getId_cliente());
            stmtt.setDate(2, pedido.getData());
            stmtt.setBigDecimal(3, pedido.getValor_total());
            stmtt.setString(4, pedido.getObservacao());
            stmtt.setInt(5, pedido.getID_FORMA_DE_PAGAMENTO());
            stmtt.setInt(6, pedido.getId_pedido());
            stmtt.executeUpdate();
            
            //apagando os produtos
            String sql2 = "delete from pedido_produtos where id_pedido = ?";
            stmtt = conn.prepareStatement(sql2);
            stmtt.setInt(1, pedido.getId_pedido());
            stmtt.executeUpdate();
            
            //salvando os produtos_pedidos
            for (Object pedidoProduto : pedido.getListaProdutos()) {
                Pedido_Produto pedido_produto = (Pedido_Produto) pedidoProduto;
                String sqlProduto = "insert into pedido_produtos (id_pedido, id_produto, quantidade, valor_unitario, valor_total_cobrado, valor_total) "
                    + "values(?,?,?,?,?,?)";
                stmtt =  conn.prepareStatement(sqlProduto);
                stmtt.setInt(1, pedido.getId_pedido());
                stmtt.setInt(2, pedido_produto.getId_produto());
                stmtt.setInt(3, pedido_produto.getQuantidade());
                stmtt.setBigDecimal(4, pedido_produto.getValor_unitario());
                stmtt.setBigDecimal(5, pedido_produto.getValor_total_cobrado());
                stmtt.setBigDecimal(6, pedido_produto.getValor_total());
                
                stmtt.executeUpdate();
            }
            
            
            conn.commit();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
            }
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Atualizar Pedido. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                stmtt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }    }

    @Override
    public boolean deletar(Pedido pedido) {
        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false);
            
            //deletando os produtos
            String sql = "delete from pedido_produtos where id_pedido = ?";
            stmtt = conn.prepareStatement(sql);
            stmtt.setInt(1, pedido.getId_pedido());
            stmtt.executeUpdate();
            
            //deletando o pedido
            String sql2 = "delete from pedidos where id_pedido = ?";
            stmtt = conn.prepareStatement(sql2);
            stmtt.setInt(1, pedido.getId_pedido());
            stmtt.executeUpdate();
            
            //dando o commit
            conn.commit();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
            }
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Deletar Pedido. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                stmtt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }    }

    public ArrayList<Pedido> listar(Date data) {
        ArrayList<Pedido> listaPedidos = new ArrayList<>();
        try {
            conn = Conexao.conectar();
            stmtt = conn.prepareStatement("SELECT\n"
                    + "     PEDIDOS.\"ID_PEDIDO\" AS PEDIDOS_ID_PEDIDO,\n"
                    + "     PEDIDOS.\"ID_CLIENTE\" AS PEDIDOS_ID_CLIENTE,\n"
                    + "     PEDIDOS.\"DATA\" AS PEDIDOS_DATA,\n"
                    + "     PEDIDOS.\"VALOR_TOTAL\" AS PEDIDOS_VALOR_TOTAL,\n"
                    + "     PEDIDOS.\"OBSERVACAO\" AS PEDIDOS_OBSERVACAO,\n"
                    + "     PEDIDOS.\"ID_FORMA_DE_PAGAMENTO\" AS PEDIDOS_ID_FORMA_DE_PAGAMENTO,\n"
                    + "     CLIENTE.\"ID_CLIENTE\" AS CLIENTE_ID_CLIENTE,\n"
                    + "     CLIENTE.\"NOME\" AS CLIENTE_NOME,\n"
                    + "     CLIENTE.\"NASCIMENTO\" AS CLIENTE_NASCIMENTO,\n"
                    + "     CLIENTE.\"TELEFONE\" AS CLIENTE_TELEFONE,\n"
                    + "     CLIENTE.\"CELULAR\" AS CLIENTE_CELULAR,\n"
                    + "     CLIENTE.\"EMAIL\" AS CLIENTE_EMAIL,\n"
                    + "     CLIENTE.\"ENDERECO\" AS CLIENTE_ENDERECO,\n"
                    + "     CLIENTE.\"BAIRRO\" AS CLIENTE_BAIRRO,\n"
                    + "     CLIENTE.\"REFERENCIA\" AS CLIENTE_REFERENCIA,\n"
                    + "     CLIENTE.\"COMO_CHEGOU_ATE_NOS\" AS CLIENTE_COMO_CHEGOU_ATE_NOS,\n"
                    + "     CLIENTE.\"OBSERVACAO\" AS CLIENTE_OBSERVACAO\n"
                    + "FROM\n"
                    + "     \"PEDIDOS\" PEDIDOS INNER JOIN \"CLIENTE\" CLIENTE ON PEDIDOS.\"ID_CLIENTE\" = CLIENTE.\"ID_CLIENTE\"\n"
                    + "WHERE\n"
                    + "     PEDIDOS.\"DATA\" = ?");
            stmtt.setDate(1, data);
            ResultSet resultSet = stmtt.executeQuery();
            while (resultSet.next()) {
                //pega o cliente apra colocar no pedido
                Cliente cliente = new Cliente();
                cliente.setId_cliente(resultSet.getInt("cliente_id_cliente"));
                cliente.setNome(resultSet.getString("cliente_nome"));
                cliente.setNascimento(resultSet.getDate("cliente_nascimento"));
                cliente.setTelefone(resultSet.getString("cliente_telefone"));
                cliente.setCelular(resultSet.getString("cliente_celular"));
                cliente.seteMail(resultSet.getString("cliente_email"));
                cliente.setEndereco(resultSet.getString("cliente_endereco"));
                cliente.setBairro(resultSet.getString("cliente_bairro"));
                cliente.setReferencia(resultSet.getString("cliente_referencia"));
                cliente.setComo_chegou_ate_nos(resultSet.getString("cliente_como_chegou_ate_nos"));
                cliente.setObservacao(resultSet.getString("cliente_observacao"));

                //monta o pedido
                Pedido pedido = new Pedido();
                pedido.setId_pedido(resultSet.getInt("pedidos_id_pedido"));
                pedido.setCliente(cliente);
                pedido.setData(resultSet.getDate("pedidos_data"));
                pedido.setValor_total(new BigDecimal(resultSet.getDouble("pedidos_valor_total")).setScale(2, RoundingMode.HALF_EVEN));
                pedido.setObservacao(resultSet.getString("observacao"));
                pedido.setID_FORMA_DE_PAGAMENTO(resultSet.getInt("PEDIDOS_ID_FORMA_DE_PAGAMENTO"));

                listaPedidos.add(pedido);
            }

            //agora vamos pegar os produtos dos pedidos
            for (Pedido pedido : listaPedidos) {
                stmtt.close();
                conn.close();
                conn = Conexao.conectar();
                stmtt = conn.prepareStatement("SELECT\n"
                        + "     PEDIDO_PRODUTOS.\"ID_PEDIDO_PRODUTO\" AS PEDIDO_PRODUTOS_ID_PEDIDO_PRODU,\n"
                        + "     PEDIDO_PRODUTOS.\"ID_PEDIDO\" AS PEDIDO_PRODUTOS_ID_PEDIDO,\n"
                        + "     PEDIDO_PRODUTOS.\"ID_PRODUTO\" AS PEDIDO_PRODUTOS_ID_PRODUTO,\n"
                        + "     PEDIDO_PRODUTOS.\"QUANTIDADE\" AS PEDIDO_PRODUTOS_QUANTIDADE,\n"
                        + "     PEDIDO_PRODUTOS.\"VALOR_UNITARIO\" AS PEDIDO_PRODUTOS_VALOR_UNITARIO,\n"
                        + "     PEDIDO_PRODUTOS.\"VALOR_TOTAL\" AS PEDIDO_PRODUTOS_VALOR_TOTAL,\n"
                        + "     PEDIDO_PRODUTOS.\"VALOR_TOTAL_COBRADO\" AS PEDIDO_PRODUTOS_VALOR_TOTAL_COBRADO,\n"
                        + "     PRODUTOS.\"NOME\" AS PRODUTOS_NOME\n"
                        + "FROM\n"
                        + "     \"PRODUTOS\" PRODUTOS INNER JOIN \"PEDIDO_PRODUTOS\" PEDIDO_PRODUTOS ON PRODUTOS.\"ID_PRODUTO\" = PEDIDO_PRODUTOS.\"ID_PRODUTO\""
                        + "WHERE\n"
                        + "     PEDIDO_PRODUTOS.\"ID_PEDIDO\" = ?");
                stmtt.setInt(1, pedido.getId_pedido());
                ResultSet resultSet2 = stmtt.executeQuery();
                while (resultSet2.next()) {
                    Pedido_Produto produtoPedido = new Pedido_Produto();
                    produtoPedido.setId_pedido_produto(resultSet2.getInt("PEDIDO_PRODUTOS_ID_PEDIDO_PRODU"));
                    produtoPedido.setId_pedido(pedido.getId_pedido());
                    produtoPedido.setNome(resultSet2.getString("PRODUTOS_NOME"));
                    produtoPedido.setId_produto(resultSet2.getInt("pedido_produtos_id_produto"));
                    produtoPedido.setQuantidade(resultSet2.getInt("pedido_produtos_quantidade"));
                    produtoPedido.setValor_total_cobrado(new BigDecimal(resultSet2.getDouble("pedido_produtos_valor_total_cobrado")).setScale(2, RoundingMode.HALF_EVEN));
                    produtoPedido.setValor_total(new BigDecimal(resultSet2.getDouble("pedido_produtos_valor_total")).setScale(2, RoundingMode.HALF_EVEN));
                    produtoPedido.setValor_unitario(new BigDecimal(resultSet2.getDouble("pedido_produtos_valor_unitario")).setScale(2, RoundingMode.HALF_EVEN));
                    pedido.getListaProdutos().add(produtoPedido);
                }
            }
            //retona a lista completa
            return listaPedidos;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao Consultar Pedidos. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
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
    public Pedido consultar(Pedido condicao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Pedido> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
