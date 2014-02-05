package br.sushi.zen.interfacesGraficas.jinternalframe;

import br.sushi.zen.auxiliares.documentoSemAspasEPorcento;
import br.sushi.zen.auxiliares.jTextFieldDinheiroReais;
import br.sushi.zen.dao.Gastos;
import br.sushi.zen.interfaces.JanelaVisualizar;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Gasto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;
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
public class CadastroGastosVisualizar extends javax.swing.JInternalFrame implements JanelaVisualizar<Gasto> {

    Gastos gastosDao = new Gastos();
    ArrayList<Gasto> listaGastos = new ArrayList<>();

    /**
     * DEFAULT CONSTRUCTOR
     */
    public CadastroGastosVisualizar() {
        super("Gastos", true, true, false, true);
        initComponents();
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroGastos.png")));
        jTable1.setRowHeight(30);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        preencheTabela(listaGastos = gastosDao.listar(pegandoDataDoDataPicker()));

        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);

        jTable1.getColumnModel().getColumn(3).setMaxWidth(110);
        jTable1.getColumnModel().getColumn(3).setMinWidth(110);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(110);

        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);

        jTable1.getColumnModel().getColumn(3).setCellRenderer(direita);

        //reescrevendo metodo de ação quando mudar o jdatepicker
        jXDatePicker1.setFormats(new String[]{"E dd/MM/yyyy"});
        jXDatePicker1.setLinkDate(System.currentTimeMillis(), "Hoje");
        jXDatePicker1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                preencheTabela(listaGastos = gastosDao.listar(pegandoDataDoDataPicker()));
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        jTextField2.requestFocusInWindow();
                    }
                });
            }
        });
    }

    @Override
    public void doDefaultCloseAction() {
        JanelaPrincipal.internalFrameCadastroGastoVisualizar.dispose();
        JanelaPrincipal.internalFrameCadastroGastoVisualizar = null;
    }

    @Override
    public final void preencheTabela(ArrayList<Gasto> lista) {
        jTextField2.setText("");
        //limpa tabela
        ((DefaultTableModel) jTable1.getModel()).setNumRows(0);
        jTable1.updateUI();
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

        double valorTotalDaLista = 0.0;
        if (lista != null) {
            DecimalFormat decimalFormat = new java.text.DecimalFormat("#,###,##0.00");
            for (Gasto gasto : lista) {
                valorTotalDaLista += gasto.getValor().doubleValue();
                modelo.addRow(new Object[]{gasto, gasto.getNome(), gasto.getCategoria_gasto().getNome(), decimalFormat.format(gasto.getValor())});
            }
            
            String valorTotalTabela = decimalFormat.format(new BigDecimal(valorTotalDaLista).setScale(2, RoundingMode.HALF_EVEN));
            jTFValorTotalDaTabela.setText(valorTotalTabela);
        }
    }

    @Override
    public void botaoNovo() {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroGasto = new CadastroGasto();
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroGasto);
    }

    @Override
    public void clicaTabela(Gasto gasto) {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroGasto = new CadastroGasto(gasto);
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroGasto);
    }

    private Date pegandoDataDoDataPicker() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date data;
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
        jTFValorTotalDaTabela = new jTextFieldDinheiroReais() {
            {// limita a 8
                // caracteres
                setLimit(8);
            }
        };
        jLabel2 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(704, 685));
        setMinimumSize(new java.awt.Dimension(704, 685));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Gasto", "Nome", "Categoria", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        jButton1.setText("Novo Gasto");
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

        jLabel1.setText("Pesquisa Gasto por Nome:");

        jBAtualizarTabela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/atualizarTabela.png"))); // NOI18N
        jBAtualizarTabela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAtualizarTabelaActionPerformed(evt);
            }
        });

        jTFValorTotalDaTabela.setEditable(false);
        jTFValorTotalDaTabela.setBackground(new java.awt.Color(230, 230, 230));
        jTFValorTotalDaTabela.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel2.setText("Total dos Gastos: R$");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBAtualizarTabela))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFValorTotalDaTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jTFValorTotalDaTabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        botaoNovo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        clicaTabela((Gasto) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
    }//GEN-LAST:event_jTable1MouseReleased

    private void jBAtualizarTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAtualizarTabelaActionPerformed
        preencheTabela(listaGastos = gastosDao.listar(pegandoDataDoDataPicker()));
    }//GEN-LAST:event_jBAtualizarTabelaActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        if ("".equals(jTextField2.getText())) {
            preencheTabela(listaGastos);
        } else {
            //limpa tabela
            ((DefaultTableModel) jTable1.getModel()).setNumRows(0);
            jTable1.updateUI();
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

            double valorTotalDaLista = 0.0;
            if (listaGastos != null) {
                DecimalFormat decimalFormat = new java.text.DecimalFormat("#,###,##0.00");
                for (Gasto gasto : listaGastos) {
                    if (gasto.getNome().contains(jTextField2.getText())) {
                        valorTotalDaLista += gasto.getValor().doubleValue();
                        modelo.addRow(new Object[]{gasto, gasto.getNome(), gasto.getCategoria_gasto().getNome(), decimalFormat.format(gasto.getValor())});
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
