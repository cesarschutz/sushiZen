package br.sushi.zen.interfacesGraficas.jinternalframe;

import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Cliente;
import javax.swing.JOptionPane;

/**
 *
 * @author Cesar Schutz
 */
public class CadastroPedidoCliente extends CadastroCliente {

    public CadastroPedidoCliente(Cliente cliente) {
        super(cliente);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroPedidos.png")));
        this.setTitle("Incluir Cliente no Pedido");

        jBDeletar.setVisible(false);
        jBAtualizar.setText("Atualizar e Selecionar");
    }

    CadastroPedidoCliente() {
        super();
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroPedidos.png")));
        this.setTitle("Incluir Cliente no Pedido");

        jBDeletar.setVisible(false);
        jBAtualizar.setVisible(false);
        jBSalvar.setText("Salvar e Selecionar");
    }

    @Override
    public void doDefaultCloseAction() {
        CadastroPedido.cadastroClienteVisualizar.dispose();
        CadastroPedido.cadastroClienteVisualizar = null;
        CadastroPedido.cadastroCliente.dispose();
        CadastroPedido.cadastroCliente = null;
        JanelaPrincipal.internalFrameCadastroPedido.setVisible(true);
    }

    @Override
    public void botaoCancelar() {
        CadastroPedido.cadastroClienteVisualizar.setVisible(true);
        CadastroPedido.cadastroCliente.dispose();
        CadastroPedido.cadastroCliente = null;
    }

    @Override
    public void botaoSalvar(Cliente cliente) {
        if (cliente.getNome().length() > 0) {
            int idCliente = clienteDao.cadastrarComRetornoId(cliente);
            if (idCliente != 0) {
                cliente.setId_cliente(idCliente);
                JanelaPrincipal.internalFrameCadastroPedido.pedido.setCliente(cliente);
                JanelaPrincipal.internalFrameCadastroPedido.jTFNomeCliente.setText(cliente.getNome());
                JanelaPrincipal.internalFrameCadastroPedido.jTFTelefone.setText(cliente.getTelefone());
                JanelaPrincipal.internalFrameCadastroPedido.jTFCelular.setText(cliente.getCelular());
                JanelaPrincipal.internalFrameCadastroPedido.jTFEndereco.setText(cliente.getEndereco());
                JanelaPrincipal.internalFrameCadastroPedido.jTFBairro.setText(cliente.getBairro());
                JanelaPrincipal.internalFrameCadastroPedido.jTAReferencia.setText(cliente.getReferencia());
                doDefaultCloseAction();
            }
        } else {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Preencha o Nome.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void botaoAtualizar(Cliente cliente) {
        if (cliente.getNome().length() > 0) {
            clienteDao.atualizar(cliente);
            JanelaPrincipal.internalFrameCadastroPedido.pedido.setCliente(cliente);
            JanelaPrincipal.internalFrameCadastroPedido.jTFNomeCliente.setText(cliente.getNome());
            JanelaPrincipal.internalFrameCadastroPedido.jTFTelefone.setText(cliente.getTelefone());
            JanelaPrincipal.internalFrameCadastroPedido.jTFCelular.setText(cliente.getCelular());
            JanelaPrincipal.internalFrameCadastroPedido.jTFEndereco.setText(cliente.getEndereco());
            JanelaPrincipal.internalFrameCadastroPedido.jTFBairro.setText(cliente.getBairro());
            JanelaPrincipal.internalFrameCadastroPedido.jTAReferencia.setText(cliente.getReferencia());
            doDefaultCloseAction();
        } else {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Preencha o Nome.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

}
