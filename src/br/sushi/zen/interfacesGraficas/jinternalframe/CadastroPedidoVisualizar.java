package br.sushi.zen.interfacesGraficas.jinternalframe;

import br.sushi.zen.auxiliares.documentoSemAspasEPorcento;
import br.sushi.zen.auxiliares.jTextFieldDinheiroReais;
import br.sushi.zen.dao.Pedidos;
import br.sushi.zen.interfaces.JanelaVisualizar;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Pedido;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */
public class CadastroPedidoVisualizar extends javax.swing.JInternalFrame implements JanelaVisualizar<Pedido> {

    Pedidos pedidosDao = new Pedidos();
    ArrayList<Pedido> listaPedidos = new ArrayList<>();

    /**
     * DEFAULT CONSTRUCTOR
     */
    public CadastroPedidoVisualizar() {
        super("Pedidos", true, true, false, true);
        initComponents();
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroPedidos.png")));
        jTable1.setRowHeight(30);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        preencheTabela(listaPedidos = pedidosDao.listar(pegandoDataDoDataPicker()));

        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);

        jTable1.getColumnModel().getColumn(2).setMaxWidth(110);
        jTable1.getColumnModel().getColumn(2).setMinWidth(110);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(110);

        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);

        jTable1.getColumnModel().getColumn(2).setCellRenderer(direita);

        //reescrevendo metodo de ação quando mudar o jdatepicker
        jXDatePicker1.setFormats(new String[]{"E dd/MM/yyyy"});
        jXDatePicker1.setLinkDate(System.currentTimeMillis(), "Hoje");
        jXDatePicker1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                preencheTabela(listaPedidos = pedidosDao.listar(pegandoDataDoDataPicker()));
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        jTextField2.requestFocusInWindow();
                    }
                });
            }
        });
    }

    @Override
    public void doDefaultCloseAction() {
        JanelaPrincipal.internalFrameCadastroPedidoVisualizar.dispose();
        JanelaPrincipal.internalFrameCadastroPedidoVisualizar = null;
    }

    @Override
    public final void preencheTabela(ArrayList<Pedido> lista) {
        jTextField2.setText("");
        //limpa tabela
        ((DefaultTableModel) jTable1.getModel()).setNumRows(0);
        jTable1.updateUI();
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

        double valorTotalDaLista = 0.0;
        if (lista != null) {
            for (Pedido pedido : lista) {
                valorTotalDaLista += pedido.getValor_total().doubleValue();
                modelo.addRow(new Object[]{pedido, pedido.getCliente().getNome(), new java.text.DecimalFormat("#,###,##0.00").format(pedido.getValor_total())});
            }
            jTFValorTotalDaTabela.setText(new BigDecimal(valorTotalDaLista).setScale(2, RoundingMode.HALF_EVEN).toString().replaceAll("\\.", ","));
        }
    }

    @Override
    public void botaoNovo() {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroPedido = new CadastroPedido();
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroPedido);
    }

    @Override
    public void clicaTabela(Pedido pedido) {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroPedido = new CadastroPedido(pedido);
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroPedido);
    }

    private Date pegandoDataDoDataPicker() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date data = null;
        java.util.Date dataSelecionada = jXDatePicker1.getDate();
        //criando um formato de data
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
        //colocando data selecionado no formato criado acima
        String data2 = dataFormatada.format(dataSelecionada);

        try {
            data = new java.sql.Date(format.parse(data2).getTime());
            return data;
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jBAtualizarTabela = new javax.swing.JButton();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jTFValorTotalDaTabela = new jTextFieldDinheiroReais(){
            {
                setLimit(8);
            }
        };
        jLabel2 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(601, 666));
        setMinimumSize(new java.awt.Dimension(601, 666));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Pedido", "Nome Cliente", "Valor Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

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
        jScrollPane1.setViewportView(jTable1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/crudNovo.png"))); // NOI18N
        jButton1.setText("Novo Pedido");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField2.setDocument(new documentoSemAspasEPorcento(64));
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jLabel1.setText("Pesquisa Pedido por Cliente: ");

        jBAtualizarTabela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/atualizarTabela.png"))); // NOI18N
        jBAtualizarTabela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAtualizarTabelaActionPerformed(evt);
            }
        });

        jTFValorTotalDaTabela.setEditable(false);
        jTFValorTotalDaTabela.setBackground(new java.awt.Color(230, 230, 230));
        jTFValorTotalDaTabela.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel2.setText("Total dos Pedidos: R$");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFValorTotalDaTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBAtualizarTabela)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jBAtualizarTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTFValorTotalDaTabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        botaoNovo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        clicaTabela((Pedido) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
    }//GEN-LAST:event_jTable1MouseReleased

    private void jBAtualizarTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAtualizarTabelaActionPerformed
        preencheTabela(listaPedidos = pedidosDao.listar(pegandoDataDoDataPicker()));
    }//GEN-LAST:event_jBAtualizarTabelaActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        if ("".equals(jTextField2.getText())) {
            preencheTabela(listaPedidos);
        } else {
            //limpa tabela
            ((DefaultTableModel) jTable1.getModel()).setNumRows(0);
            jTable1.updateUI();
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

            double valorTotalDaLista = 0.0;
            if (listaPedidos != null) {
                for (Pedido pedido : listaPedidos) {
                    if (pedido.getCliente().getNome().contains(jTextField2.getText())) {
                        valorTotalDaLista += pedido.getValor_total().doubleValue();
                        modelo.addRow(new Object[]{pedido, pedido.getCliente().getNome(), new java.text.DecimalFormat("#,###,##0.00").format(pedido.getValor_total())});
                    }
                }
                jTFValorTotalDaTabela.setText(new BigDecimal(valorTotalDaLista).setScale(2, RoundingMode.HALF_EVEN).toString().replaceAll("\\.", ","));
            }
        }
    }//GEN-LAST:event_jTextField2KeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAtualizarTabela;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFValorTotalDaTabela;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    // End of variables declaration//GEN-END:variables

}
