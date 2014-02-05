package br.sushi.zen.interfacesGraficas.jinternalframe;

import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Cliente;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Cesar Schutz
 */
public class CadastroPedidoClienteVisualizar extends CadastroClienteVisualizar {

    public CadastroPedidoClienteVisualizar() {
        super();
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroPedidos.png")));
        this.setTitle("Incluir Cliente em Pedido");
        jBAtualizarCliente.setVisible(true);
        jBSelecionarCliente.setVisible(true);
        jBNovoCliente.setText("Novo Cliente");

        jBNovoCliente.setPreferredSize(jBSelecionarCliente.getPreferredSize());

        //dois cliques na tabela
        jTable1.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() > 1) {
                            botaoSelecionarCliente();
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }
                }
        );
    }

    @Override
    public void clicaTabela(Cliente cliente) {
    }

    @Override
    public void doDefaultCloseAction() {
        CadastroPedido.cadastroClienteVisualizar.dispose();
        CadastroPedido.cadastroClienteVisualizar = null;
        JanelaPrincipal.internalFrameCadastroPedido.setVisible(true);
    }

    @Override
    public void botaoNovo(){
        CadastroPedido.cadastroCliente = new CadastroPedidoCliente();
        JanelaPrincipal.abrirInternalFrame(CadastroPedido.cadastroCliente);
        this.setVisible(false);
    }
    @Override
    public void botaoEditarCliente() {
        try {
            CadastroPedido.cadastroCliente = new CadastroPedidoCliente((Cliente) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
            JanelaPrincipal.abrirInternalFrame(CadastroPedido.cadastroCliente);
            this.setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Selecione um Cliente", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void botaoSelecionarCliente() {
        try {
            JanelaPrincipal.internalFrameCadastroPedido.pedido.setCliente((Cliente) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
            JanelaPrincipal.internalFrameCadastroPedido.jTFNomeCliente.setText(JanelaPrincipal.internalFrameCadastroPedido.pedido.getCliente().getNome());
            JanelaPrincipal.internalFrameCadastroPedido.jTFTelefone.setText(JanelaPrincipal.internalFrameCadastroPedido.pedido.getCliente().getTelefone());
            JanelaPrincipal.internalFrameCadastroPedido.jTFCelular.setText(JanelaPrincipal.internalFrameCadastroPedido.pedido.getCliente().getCelular());
            JanelaPrincipal.internalFrameCadastroPedido.jTFEndereco.setText(JanelaPrincipal.internalFrameCadastroPedido.pedido.getCliente().getEndereco());
            JanelaPrincipal.internalFrameCadastroPedido.jTFBairro.setText(JanelaPrincipal.internalFrameCadastroPedido.pedido.getCliente().getBairro());
            JanelaPrincipal.internalFrameCadastroPedido.jTAReferencia.setText(JanelaPrincipal.internalFrameCadastroPedido.pedido.getCliente().getReferencia());
            doDefaultCloseAction();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Selecione um Cliente", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }
}
