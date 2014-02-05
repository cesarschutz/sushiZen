package br.sushi.zen.interfacesGraficas.jinternalframe;

import br.sushi.zen.auxiliares.jTextFieldDinheiroReais;
import br.sushi.zen.auxiliares.documentoSomenteNumeros;
import br.sushi.zen.auxiliares.MetodosUteis;
import br.sushi.zen.auxiliares.documentoSemAspasEPorcento;
import br.sushi.zen.dao.Formas_de_pagamento;
import br.sushi.zen.dao.Pedidos;
import br.sushi.zen.dao.Produtos;
import br.sushi.zen.interfaces.JanelaCadastrarCrud;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Forma_de_pagamento;
import br.sushi.zen.model.Pedido;
import br.sushi.zen.model.Pedido_Produto;
import br.sushi.zen.model.Produto;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */
public final class CadastroPedido extends javax.swing.JInternalFrame implements JanelaCadastrarCrud<Pedido> {

    Pedidos pedidosDao = new Pedidos();
    Pedido pedido = new Pedido();

    ArrayList<Produto> listaProdutos = new ArrayList<>();
    Produtos produtosDao = new Produtos();
    ArrayList<Forma_de_pagamento> listaFormas_de_pagamento = new ArrayList<>();
    Formas_de_pagamento formas_de_pagamentoDao = new Formas_de_pagamento();

    static CadastroPedidoClienteVisualizar cadastroClienteVisualizar;
    static CadastroPedidoCliente cadastroCliente;

    public CadastroPedido() {
        super("Cadastrar Pedido", true, true, false, true);
        initComponents();
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroPedidos.png")));
        ativandoSelecaoDeLinhaComBotaoDireitoDoMouse();
        pedido.setValor_total(BigDecimal.ZERO);
        jBAtualizar.setVisible(false);
        jBDeletar.setVisible(false);
        jTFData.setText(pegandoDataDoSistema());
        preencheComboBoxFormas_de_pagamento();
        preencheComboBoxProdutos();
        arrumaTabela();
    }

    public CadastroPedido(Pedido pedido) {
        super("Atualizar Pedido", true, true, false, true);
        initComponents();
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroPedidos.png")));
        ativandoSelecaoDeLinhaComBotaoDireitoDoMouse();
        preencheComboBoxFormas_de_pagamento();
        preencheComboBoxProdutos();
        preencher(pedido);
        jBSalvar.setVisible(false);
        this.pedido = pedido;

        arrumaTabela();
    }

    @Override
    public void preencher(Pedido pedido) {
        jTFNomeCliente.setText(pedido.getCliente().getNome());
        jTFTelefone.setText(pedido.getCliente().getTelefone());
        jTFCelular.setText(pedido.getCliente().getCelular());
        jTFEndereco.setText(pedido.getCliente().getEndereco());
        jTFBairro.setText(pedido.getCliente().getBairro());
        jTAReferencia.setText(pedido.getCliente().getReferencia());

        SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
        jTFData.setText(dataFormat.format(pedido.getData()));
        jTAObservacao.setText(pedido.getObservacao());
        jTFValorTotalPedido.setText(new java.text.DecimalFormat("#,###,##0.00").format(pedido.getValor_total()));

        for (int i = 0; i < pedido.getListaProdutos().size(); i++) {
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            Pedido_Produto pedidoProduto = (Pedido_Produto) pedido.getListaProdutos().get(i);

            modelo.addRow(new Object[]{pedidoProduto, pedidoProduto.getNome(), new java.text.DecimalFormat("#,###,##0.00").format(pedidoProduto.getValor_unitario()), corrigiQutantidade(String.valueOf(pedidoProduto.getQuantidade())), new java.text.DecimalFormat("#,###,##0.00").format(pedidoProduto.getValor_total_cobrado())});
        }

        //preenchendo a forma de pagamento
        //varre a lista procurando o id
        for (Forma_de_pagamento formaDePagamento : listaFormas_de_pagamento) {
            if (formaDePagamento.getId_forma_de_pagamento() == pedido.getID_FORMA_DE_PAGAMENTO()) {
                //se id_forme_de_pagamento da lista for igual a do pedido, ele seleciona
                int indice = listaFormas_de_pagamento.indexOf(formaDePagamento);
                jCBFormaDePagamento.setSelectedIndex(indice);
            }
        }
    }

    @Override
    public void botaoCancelar() {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroPedidoVisualizar = new CadastroPedidoVisualizar();
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroPedidoVisualizar);
    }

    @Override
    public void doDefaultCloseAction() {
        JanelaPrincipal.internalFrameCadastroPedido.dispose();
        JanelaPrincipal.internalFrameCadastroPedido = null;
    }

    @Override
    public void botaoSalvar(Pedido pedido) {
        pedidosDao.cadastrar(pedido);
        botaoCancelar();
    }

    @Override
    public void botaoAtualizar(Pedido pedido) {
        pedidosDao.atualizar(pedido);
        botaoCancelar();
    }

    @Override
    public void botaoDeletar(Pedido pedido) {
        pedidosDao.deletar(pedido);
        voltarParaJanelaDeVisualizacao();
    }

    private Pedido criaPedido(Pedido pedido) {
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            pedido.setData(new java.sql.Date(dataFormat.parse(jTFData.getText()).getTime()));
        } catch (ParseException ex) {
        }

        pedido.setObservacao(jTAObservacao.getText());

        //tirando todos os pedidos
        pedido.getListaProdutos().removeAll(pedido.getListaProdutos());
        //colocando os pedidos
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            pedido.getListaProdutos().add((Pedido_Produto) jTable1.getValueAt(i, 0));
        }

        //pegando a forma de pagamento
        pedido.setID_FORMA_DE_PAGAMENTO(listaFormas_de_pagamento.get(jCBFormaDePagamento.getSelectedIndex()).getId_forma_de_pagamento());

        //retorna o pedido com tudo ok
        return pedido;
    }

    private String pegandoDataDoSistema() {
        //pegando data do sistema
        Calendar hoje = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(hoje.getTime());

    }

    private void voltarParaJanelaDeVisualizacao() {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroPedidoVisualizar = new CadastroPedidoVisualizar();
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroPedidoVisualizar);
    }

    private void botaoIncluirCliente() {
        this.setVisible(false);
        cadastroClienteVisualizar = new CadastroPedidoClienteVisualizar();
        JanelaPrincipal.abrirInternalFrame(cadastroClienteVisualizar);
    }

    private void preencheComboBoxProdutos() {
        jCBProdutos.removeAllItems();
        listaProdutos = produtosDao.listar();
        for (Produto produto : listaProdutos) {
            jCBProdutos.addItem(produto.getNome() + "  -  R$: " + new java.text.DecimalFormat("#,###,##0.00").format(produto.getValor()));
        }
    }

    private void preencheComboBoxFormas_de_pagamento() {
        jCBFormaDePagamento.removeAllItems();
        listaFormas_de_pagamento = formas_de_pagamentoDao.listar();
        for (Forma_de_pagamento forma_de_pagamento : listaFormas_de_pagamento) {
            jCBFormaDePagamento.addItem(forma_de_pagamento.getNome());
        }
    }

    private void arrumaTabela() {
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);

        jTable1.getColumnModel().getColumn(2).setMaxWidth(90);
        jTable1.getColumnModel().getColumn(2).setMinWidth(90);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);

        jTable1.getColumnModel().getColumn(3).setMaxWidth(80);
        jTable1.getColumnModel().getColumn(3).setMinWidth(80);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);

        jTable1.getColumnModel().getColumn(4).setMaxWidth(80);
        jTable1.getColumnModel().getColumn(4).setMinWidth(80);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);

        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.getColumnModel().getColumn(4).setCellRenderer(direita);
        jTable1.getColumnModel().getColumn(3).setCellRenderer(direita);
        jTable1.getColumnModel().getColumn(2).setCellRenderer(direita);

        jTable1.setRowHeight(30);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void botaoIncluirProduto() {
        try {
            if (!"".equals(jTFQuantidade.getText())
                    && Integer.valueOf(jTFQuantidade.getText()) > 0) {
                //pegando o produto selecionado
                Produto produtoSelecionado = listaProdutos.get(jCBProdutos.getSelectedIndex());

                //varre a tabela para verificar se o produto ja existe
                //se existir nao o coloca e seleciona a linha do produto
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    if (((Produto) jTable1.getValueAt(i, 0)).getId_produto() == produtoSelecionado.getId_produto()) {
                        jTable1.setColumnSelectionInterval(0, 0);
                        jTable1.setRowSelectionInterval(i, i);
                        mudarQuantidadeDeUmProduto();
                        return;
                    }
                }

                //pegando dados do produto
                BigDecimal valorProduto = new BigDecimal(produtoSelecionado.getValor().doubleValue()).setScale(2, RoundingMode.HALF_EVEN);
                String valorProdutoVezesQuantidade = String.valueOf(produtoSelecionado.getValor().doubleValue() * Integer.valueOf(jTFQuantidade.getText()));
                BigDecimal valorTotalProduto = new BigDecimal(valorProdutoVezesQuantidade).setScale(2, RoundingMode.HALF_EVEN);

                //criando pedido_produto
                Pedido_Produto pedidoProduto = new Pedido_Produto();
                pedidoProduto.setNome(produtoSelecionado.getNome());
                pedidoProduto.setId_produto(produtoSelecionado.getId_produto());
                pedidoProduto.setValor_unitario(valorProduto);
                pedidoProduto.setQuantidade(Integer.valueOf(jTFQuantidade.getText()));
                pedidoProduto.setValor_total_cobrado(valorTotalProduto);
                pedidoProduto.setValor_total(valorTotalProduto);

                //colocando o pedido_produto na tabela
                DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
                modelo.addRow(new Object[]{pedidoProduto, pedidoProduto.getNome(), new java.text.DecimalFormat("#,###,##0.00").format(pedidoProduto.getValor_unitario()), corrigiQutantidade(String.valueOf(pedidoProduto.getQuantidade())), new java.text.DecimalFormat("#,###,##0.00").format(pedidoProduto.getValor_total_cobrado())});

                //somando valor total do pedido
                BigDecimal valor_total_pedido_atualizado = new BigDecimal(pedido.getValor_total().doubleValue() + valorTotalProduto.doubleValue()).setScale(2, RoundingMode.HALF_EVEN);
                pedido.setValor_total(valor_total_pedido_atualizado);
                jTFValorTotalPedido.setText(valor_total_pedido_atualizado.toString().replaceAll("\\.", ","));
                preencherValoresDasQuantidadesDeProdutos();
            } else {
                JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Preencha a quantidade corretamente.", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Preencha a quantidade corretamente.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void mudarQuantidadeDeUmProduto(){
        if (Integer.valueOf(jTFQuantidade.getText()) != Integer.valueOf(jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString())) {
            int linhaSelecionada = jTable1.getSelectedRow();
            try {
                excluirProduto();
                Produto produtoSelecionado = listaProdutos.get(jCBProdutos.getSelectedIndex());
                //pegando dados do produto
                BigDecimal valorProduto = new BigDecimal(produtoSelecionado.getValor().doubleValue()).setScale(2, RoundingMode.HALF_EVEN);
                String valorProdutoVezesQuantidade = String.valueOf(produtoSelecionado.getValor().doubleValue() * Integer.valueOf(jTFQuantidade.getText()));
                BigDecimal valorTotalProduto = new BigDecimal(valorProdutoVezesQuantidade).setScale(2, RoundingMode.HALF_EVEN);

                //criando pedido_produto
                Pedido_Produto pedidoProduto = new Pedido_Produto();
                pedidoProduto.setNome(produtoSelecionado.getNome());
                pedidoProduto.setId_produto(produtoSelecionado.getId_produto());
                pedidoProduto.setValor_unitario(valorProduto);
                pedidoProduto.setQuantidade(Integer.valueOf(jTFQuantidade.getText()));
                pedidoProduto.setValor_total_cobrado(valorTotalProduto);
                pedidoProduto.setValor_total(valorTotalProduto);

                //colocando o pedido_produto na tabela
                DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
                modelo.insertRow(linhaSelecionada, new Object[]{pedidoProduto, pedidoProduto.getNome(), new java.text.DecimalFormat("#,###,##0.00").format(pedidoProduto.getValor_unitario()), corrigiQutantidade(String.valueOf(pedidoProduto.getQuantidade())), new java.text.DecimalFormat("#,###,##0.00").format(pedidoProduto.getValor_total_cobrado())});

                jTable1.setColumnSelectionInterval(0, 0);
                jTable1.setRowSelectionInterval(linhaSelecionada, linhaSelecionada);
                
                //somando valor total do pedido
                BigDecimal valor_total_pedido_atualizado = new BigDecimal(pedido.getValor_total().doubleValue() + valorTotalProduto.doubleValue()).setScale(2, RoundingMode.HALF_EVEN);
                pedido.setValor_total(valor_total_pedido_atualizado);
                jTFValorTotalPedido.setText(valor_total_pedido_atualizado.toString().replaceAll("\\.", ","));
                preencherValoresDasQuantidadesDeProdutos();
            } catch (Exception ex) {
            }
        }
    }
    
    private void preencherValoresDasQuantidadesDeProdutos(){
        //define quantidade de produtos
        jTFQuantidadeProdutos.setText(corrigiQutantidade(String.valueOf(jTable1.getRowCount())));
        
        //define quantidade total de produtos
        int valorTotalDosProdutos = 0;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            int valorDaLinha;
            System.out.println(jTable1.getValueAt(i, 3));
            valorDaLinha = Integer.valueOf(jTable1.getValueAt(i, 3).toString());
            System.out.println(valorDaLinha);
            valorTotalDosProdutos += valorDaLinha;
        }
        jTFQuantidadeTotalProdutos.setText(corrigiQutantidade(String.valueOf(valorTotalDosProdutos)));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTFNomeCliente = new javax.swing.JTextField();
        jTFTelefone = new javax.swing.JFormattedTextField(MetodosUteis.mascaraParaJFormattedTextField("(##) #### - ####"));
        jLabel7 = new javax.swing.JLabel();
        jTFCelular =  new javax.swing.JFormattedTextField(MetodosUteis.mascaraParaJFormattedTextField("(##) #### - ####"));
        jLabel8 = new javax.swing.JLabel();
        jTFEndereco = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTFBairro = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTAReferencia = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jCBProdutos = new javax.swing.JComboBox();
        jTFQuantidade = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jBIncluirProduto = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jTFQuantidadeProdutos = new javax.swing.JTextField();
        jTFQuantidadeTotalProdutos = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTAObservacao = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jTFData = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jCBFormaDePagamento = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jTFValorTotalPedido = new jTextFieldDinheiroReais() {
            {// limita a 8
                // caracteres
                setLimit(8);
            }
        };
        jLabel5 = new javax.swing.JLabel();
        jBCancelar = new javax.swing.JButton();
        jBDeletar = new javax.swing.JButton();
        jBAtualizar = new javax.swing.JButton();
        jBSalvar = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1300, 615));
        setMinimumSize(new java.awt.Dimension(1300, 615));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel1.setText("Nome:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroClientes.png"))); // NOI18N
        jButton1.setText("Incluir Cliente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButton1KeyReleased(evt);
            }
        });

        jTFNomeCliente.setEditable(false);
        jTFNomeCliente.setBackground(new java.awt.Color(230, 230, 230));
        jTFNomeCliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jTFTelefone.setEditable(false);
        jTFTelefone.setBackground(new java.awt.Color(230, 230, 230));

        jLabel7.setText("Telefone:");

        jTFCelular.setEditable(false);
        jTFCelular.setBackground(new java.awt.Color(230, 230, 230));

        jLabel8.setText("Celular:");

        jTFEndereco.setEditable(false);
        jTFEndereco.setBackground(new java.awt.Color(230, 230, 230));

        jLabel9.setText("Endereço:");

        jLabel10.setText("Bairro:");

        jTFBairro.setEditable(false);
        jTFBairro.setBackground(new java.awt.Color(230, 230, 230));

        jLabel11.setText("Referencia:");

        jTAReferencia.setLineWrap(true);
        jTAReferencia.setWrapStyleWord (true);
        jTAReferencia.setEditable(false);
        jTAReferencia.setBackground(new java.awt.Color(230, 230, 230));
        jTAReferencia.setColumns(20);
        jTAReferencia.setRows(5);
        jScrollPane3.setViewportView(jTAReferencia);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFNomeCliente)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTFTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTFCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTFBairro, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTFEndereco)
                            .addComponent(jScrollPane3))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTFNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTFTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTFCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTFEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTFBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Produtos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel3.setText("Produtos:");

        jCBProdutos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTFQuantidade.setDocument( new documentoSomenteNumeros(5));
        jTFQuantidade.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFQuantidade.setText("001");
        jTFQuantidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFQuantidadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFQuantidadeFocusLost(evt);
            }
        });

        jLabel4.setText("Quantidade:");

        jBIncluirProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/imagemSetaParaBaixo.png"))); // NOI18N
        jBIncluirProduto.setText("Incluir Produto");
        jBIncluirProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBIncluirProdutoActionPerformed(evt);
            }
        });
        jBIncluirProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jBIncluirProdutoKeyReleased(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produto", "Nome Produto", "Valor Unitário", "Quantidade", "Valor Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTable1FocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jLabel13.setText("Quantidade de Produtos");

        jTFQuantidadeProdutos.setEditable(false);
        jTFQuantidadeProdutos.setBackground(new java.awt.Color(230, 230, 230));
        jTFQuantidadeProdutos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFQuantidadeProdutos.setText("000");
        jTFQuantidadeProdutos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFQuantidadeProdutosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFQuantidadeProdutosFocusLost(evt);
            }
        });

        jTFQuantidadeTotalProdutos.setEditable(false);
        jTFQuantidadeTotalProdutos.setBackground(new java.awt.Color(230, 230, 230));
        jTFQuantidadeTotalProdutos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFQuantidadeTotalProdutos.setText("000");
        jTFQuantidadeTotalProdutos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFQuantidadeTotalProdutosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFQuantidadeTotalProdutosFocusLost(evt);
            }
        });

        jLabel14.setText("Quantidade Total de Produtos");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBIncluirProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jCBProdutos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFQuantidade, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFQuantidadeProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFQuantidadeTotalProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBIncluirProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTFQuantidadeProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jTFQuantidadeTotalProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pedido", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jTAObservacao.setLineWrap(true);
        jTAObservacao.setWrapStyleWord (true);
        jTAObservacao.setDocument(new documentoSemAspasEPorcento(500));
        jTAObservacao.setColumns(20);
        jTAObservacao.setRows(5);
        jScrollPane1.setViewportView(jTAObservacao);

        jLabel2.setText("Observação:");

        jTFData.setEditable(false);
        jTFData.setBackground(new java.awt.Color(230, 230, 230));

        jLabel6.setText("Data:");

        jLabel12.setText("Forma de Pagamento:");

        jTFValorTotalPedido.setEditable(false);
        jTFValorTotalPedido.setBackground(new java.awt.Color(230, 230, 230));
        jTFValorTotalPedido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel5.setText("Valor Total do Pedido:");

        jBCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/crudCancelar.png"))); // NOI18N
        jBCancelar.setText("Cancelar");
        jBCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });

        jBDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/crudDeletar.png"))); // NOI18N
        jBDeletar.setText("Deletar");
        jBDeletar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jBDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDeletarActionPerformed(evt);
            }
        });

        jBAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/crudAtualizar.png"))); // NOI18N
        jBAtualizar.setText("Atualizar");
        jBAtualizar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jBAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAtualizarActionPerformed(evt);
            }
        });

        jBSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/crudSalvar.png"))); // NOI18N
        jBSalvar.setText("Salvar");
        jBSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jBSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jBSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBAtualizar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBDeletar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBCancelar))
                            .addComponent(jLabel2))
                        .addGap(0, 178, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFData, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jCBFormaDePagamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTFValorTotalPedido)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBFormaDePagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFValorTotalPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBAtualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBDeletar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        botaoIncluirCliente();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jBIncluirProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBIncluirProdutoActionPerformed
        botaoIncluirProduto();
    }//GEN-LAST:event_jBIncluirProdutoActionPerformed

    private void jButton1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            botaoIncluirCliente();
        }
    }//GEN-LAST:event_jButton1KeyReleased

    private void jBIncluirProdutoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBIncluirProdutoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            botaoIncluirProduto();
        }
    }//GEN-LAST:event_jBIncluirProdutoKeyReleased

    private void jTFQuantidadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFQuantidadeFocusGained
        jTFQuantidade.setSelectionStart(0);
        // setar a posição final na string, neste caso até o tamanho do texto  
        jTFQuantidade.setSelectionEnd(jTFQuantidade.getText().length());
    }//GEN-LAST:event_jTFQuantidadeFocusGained

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        //int colunaClicada = jTable1.columnAtPoint(evt.getPoint());
        int linhaClicada = jTable1.rowAtPoint(evt.getPoint());
        int linhaSelecionada = jTable1.getSelectedRow();

        if (MouseEvent.BUTTON3 == evt.getButton() && linhaClicada == linhaSelecionada) {
            abrirPopUpEntregue(evt);
        }
    }//GEN-LAST:event_jTable1MouseReleased

    private void jTFQuantidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFQuantidadeFocusLost
        String qtd = jTFQuantidade.getText();
        jTFQuantidade.setText(corrigiQutantidade(qtd));
    }//GEN-LAST:event_jTFQuantidadeFocusLost

    private void jTable1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost

    }//GEN-LAST:event_jTable1FocusLost

    private void jBSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvarActionPerformed
        if (jTFNomeCliente.getText() != null && jTFNomeCliente.getText().replaceAll(" ", "").length() > 0) {
            if (jTable1.getRowCount() > 0) {
                pedido = criaPedido(pedido);
                botaoSalvar(pedido);
                //mostraPedido();
            } else {
                JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Inclua algum Produto", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Inclua o Cliente", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jBSalvarActionPerformed

    private void jBAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAtualizarActionPerformed
        if (jTFNomeCliente.getText() != null && jTFNomeCliente.getText().replaceAll(" ", "").length() > 0) {
            if (jTable1.getRowCount() > 0) {
                pedido = criaPedido(pedido);
                botaoAtualizar(pedido);
            } else {
                JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Inclua algum Produto", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Inclua o Cliente", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jBAtualizarActionPerformed

    private void jBDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDeletarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(JanelaPrincipal.janelaPrincipal, "Deseja realmente deletar esse Pedido?", "ATENÇÃO", 0);
        if (resposta == JOptionPane.YES_OPTION) {
            botaoDeletar(pedido);
        }
    }//GEN-LAST:event_jBDeletarActionPerformed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        botaoCancelar();
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jTFQuantidadeProdutosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFQuantidadeProdutosFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFQuantidadeProdutosFocusGained

    private void jTFQuantidadeProdutosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFQuantidadeProdutosFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFQuantidadeProdutosFocusLost

    private void jTFQuantidadeTotalProdutosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFQuantidadeTotalProdutosFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFQuantidadeTotalProdutosFocusGained

    private void jTFQuantidadeTotalProdutosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFQuantidadeTotalProdutosFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFQuantidadeTotalProdutosFocusLost
      
    public String corrigiQutantidade(String qtd) {
        if (qtd.length() == 1) {
            qtd = "00" + qtd;
        }
        if (qtd.length() == 2) {
            qtd = "0" + qtd;
        }
        return qtd;
    }

    //abaixo metodos para o pop up na tabela de rodutos
    public void ativandoSelecaoDeLinhaComBotaoDireitoDoMouse() {
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON3) {
                    int col = jTable1.columnAtPoint(e.getPoint());
                    int row = jTable1.rowAtPoint(e.getPoint());
                    if (col != -1 && row != -1) {
                        jTable1.setColumnSelectionInterval(col, col);
                        jTable1.setRowSelectionInterval(row, row);
                    }
                }

                //colocando a seleção na celula clicada
                int linhaSelecionada = jTable1.getSelectedRow();
                int colunaSelecionada = jTable1.getSelectedColumn();

                jTable1.editCellAt(linhaSelecionada, colunaSelecionada);
            }
        });
    }

    ImageIcon iconeAlterarValor = new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/popUpProdutosCadastroDePedidosAplicarDesconto.png"));
    ImageIcon iconeExcluirProduto = new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/popUpProdutosCadastroDePedidosExcluirProduto.png"));
    private void abrirPopUpEntregue(MouseEvent evt) {

        //Paciente recebeu o exame
        JMenuItem AlterarValor = new JMenuItem("Alterar Valor", iconeAlterarValor);
        AlterarValor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarJoptionPaneParaDefinirNovoValorDoProduto();
            }
        });

        //Paciente recebeu o laudo
        JMenuItem excluirProduto = new JMenuItem("Excluir Produto", iconeExcluirProduto);
        excluirProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    excluirProduto();
                } catch (Exception ex) {
                    Logger.getLogger(CadastroPedido.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //cria o menu popup e adiciona os itens
        JPopupMenu popup = new JPopupMenu();
        popup.add(AlterarValor);
        popup.addSeparator();
        popup.add(excluirProduto);

        //mostra na tela
        int x = evt.getX();
        int y = evt.getY();
        popup.show(jTable1, x, y);
    }

    private void alterarValor(double valorDigitado) {
        try {
            //pega linha selecionada
            int linhaSelecionada = jTable1.getSelectedRow();

            //altera o valor no pedido_produto selecionado
            Pedido_Produto produtoSelecionado = ((Pedido_Produto) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
            produtoSelecionado.setValor_total_cobrado(new BigDecimal(valorDigitado).setScale(2, RoundingMode.HALF_EVEN));

            //exclui o produto
            excluirProduto();

            //colocando o pedido_produto na tabela
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            modelo.insertRow(linhaSelecionada, new Object[]{produtoSelecionado, produtoSelecionado.getNome(), new java.text.DecimalFormat("#,###,##0.00").format(produtoSelecionado.getValor_unitario()), corrigiQutantidade(String.valueOf(produtoSelecionado.getQuantidade())), new java.text.DecimalFormat("#,###,##0.00").format(produtoSelecionado.getValor_total_cobrado())});
            jTable1.setRowSelectionInterval(linhaSelecionada, linhaSelecionada);

            //somando valor total do pedido
            BigDecimal valor_total_pedido_atualizado = new BigDecimal(pedido.getValor_total().doubleValue() + valorDigitado).setScale(2, RoundingMode.HALF_EVEN);
            pedido.setValor_total(valor_total_pedido_atualizado);
            jTFValorTotalPedido.setText(new java.text.DecimalFormat("#,###,##0.00").format(valor_total_pedido_atualizado));
            
            preencherValoresDasQuantidadesDeProdutos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao alterar valor. Procure o Administrador", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Erro ao alterar valor. Procure o Administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void criarJoptionPaneParaDefinirNovoValorDoProduto() {
        // Cria campo onde o usuario entra com a senha 
        final JTextField textField = new jTextFieldDinheiroReais(new DecimalFormat("0.00")) {
            {// limita a 8
                // caracteres
                setLimit(8);
            }
        };
        Dimension tamanhoDoTextField = new Dimension(60, 30);
        textField.setPreferredSize(tamanhoDoTextField);
        textField.setMinimumSize(tamanhoDoTextField);
        textField.setMaximumSize(tamanhoDoTextField);

        textField.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent ce) {
                textField.requestFocusInWindow();
            }
        });

        // Cria um rótulo para o campo  
        JLabel rotulo = new JLabel("Digite o novo Valor Total deste(s) produto(s): R$");

        // Coloca o rótulo e a caixa de entrada numa JPanel:  
        JPanel entUsuario = new JPanel();
        entUsuario.add(rotulo);
        entUsuario.add(textField);

        // Mostra o rótulo e a caixa de entrada de password para o usuario fornecer a senha:  
        int intRetornado = JOptionPane.showConfirmDialog(JanelaPrincipal.janelaPrincipal, entUsuario, "Editar Valor de um Produto", JOptionPane.PLAIN_MESSAGE);
        textField.requestFocusInWindow();
        if (intRetornado == 0) {
            alterarValor(Double.parseDouble(textField.getText().replaceAll(",", ".")));
        }
    }

    private void excluirProduto() throws Exception {
        //diminuindo valor total do pedido
        double valorTotalDoProdutoRemovido = Double.valueOf(((String) jTable1.getValueAt(jTable1.getSelectedRow(), 4)).replace(",", "."));
        BigDecimal valor_total_pedido_atualizado = new BigDecimal(pedido.getValor_total().doubleValue() - valorTotalDoProdutoRemovido).setScale(2, RoundingMode.HALF_EVEN);
        pedido.setValor_total(valor_total_pedido_atualizado);
        jTFValorTotalPedido.setText(valor_total_pedido_atualizado.toString().replaceAll("\\.", ","));

        //removendo da tabela
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.removeRow(jTable1.getSelectedRow());
        jTable1.updateUI();

        jCBProdutos.requestFocusInWindow();
        
        preencherValoresDasQuantidadesDeProdutos();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAtualizar;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBDeletar;
    private javax.swing.JButton jBIncluirProduto;
    private javax.swing.JButton jBSalvar;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jCBFormaDePagamento;
    private javax.swing.JComboBox jCBProdutos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTAObservacao;
    public javax.swing.JTextArea jTAReferencia;
    public javax.swing.JTextField jTFBairro;
    public javax.swing.JTextField jTFCelular;
    private javax.swing.JTextField jTFData;
    public javax.swing.JTextField jTFEndereco;
    public javax.swing.JTextField jTFNomeCliente;
    private javax.swing.JTextField jTFQuantidade;
    private javax.swing.JTextField jTFQuantidadeProdutos;
    private javax.swing.JTextField jTFQuantidadeTotalProdutos;
    public javax.swing.JTextField jTFTelefone;
    private javax.swing.JTextField jTFValorTotalPedido;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
