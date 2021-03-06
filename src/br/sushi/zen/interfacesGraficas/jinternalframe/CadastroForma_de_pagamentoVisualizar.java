package br.sushi.zen.interfacesGraficas.jinternalframe;

import br.sushi.zen.auxiliares.documentoSemAspasEPorcento;
import br.sushi.zen.dao.Formas_de_pagamento;
import br.sushi.zen.interfaces.JanelaVisualizar;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Forma_de_pagamento;
import java.util.ArrayList;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Cesar Schutz
 */
public class CadastroForma_de_pagamentoVisualizar extends javax.swing.JInternalFrame implements JanelaVisualizar<Forma_de_pagamento> {

    Formas_de_pagamento forma_de_pagamentosDao = new Formas_de_pagamento();
    ArrayList<Forma_de_pagamento> listaFormas_de_pagamento = new ArrayList<>();

    /**
     * DEFAULT CONSTRUCTOR
     */
    public CadastroForma_de_pagamentoVisualizar() {
        super("Formas de Pagamento", true, true, false, true);
        initComponents();
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroFormas_de_pagamento.png")));
        jTable1.setRowHeight(30);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        preencheTabela(listaFormas_de_pagamento = forma_de_pagamentosDao.listar());

        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    @Override
    public void doDefaultCloseAction() {
        JanelaPrincipal.internalFrameCadastroForma_de_pagamentoVisualizar.dispose();
        JanelaPrincipal.internalFrameCadastroForma_de_pagamentoVisualizar = null;
    }

    @Override
    public final void preencheTabela(ArrayList<Forma_de_pagamento> lista) {
        //limpa tabela
        ((DefaultTableModel) jTable1.getModel()).setNumRows(0);
        jTable1.updateUI();
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

        if (lista != null) {
            for (Forma_de_pagamento forma_de_pagamento : lista) {
                modelo.addRow(new Object[]{forma_de_pagamento, forma_de_pagamento.getNome()});
            }
        }
    }

    @Override
    public void botaoNovo() {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroForma_de_pagamento = new CadastroForma_de_pagamento();
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroForma_de_pagamento);
    }

    @Override
    public void clicaTabela(Forma_de_pagamento forma_de_pagamento) {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroForma_de_pagamento = new CadastroForma_de_pagamento(forma_de_pagamento);
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroForma_de_pagamento);
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

        setMaximumSize(new java.awt.Dimension(575, 672));
        setMinimumSize(new java.awt.Dimension(575, 672));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Forma_de_pagamento", "Nome"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
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
        jButton1.setText("Nova");
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

        jLabel1.setText("Pesquisa Forma de Pagamento por Nome: ");

        jBAtualizarTabela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/atualizarTabela.png"))); // NOI18N
        jBAtualizarTabela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAtualizarTabelaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextField2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBAtualizarTabela))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jBAtualizarTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        botaoNovo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        clicaTabela((Forma_de_pagamento) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
    }//GEN-LAST:event_jTable1MouseReleased

    private void jBAtualizarTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAtualizarTabelaActionPerformed
        jTextField2.setText("");
        preencheTabela(listaFormas_de_pagamento = forma_de_pagamentosDao.listar());
    }//GEN-LAST:event_jBAtualizarTabelaActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        if ("".equals(jTextField2.getText())) {
            preencheTabela(listaFormas_de_pagamento);
        } else {
            //limpa tabela
            ((DefaultTableModel) jTable1.getModel()).setNumRows(0);
            jTable1.updateUI();
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

            if (listaFormas_de_pagamento != null) {
                for (Forma_de_pagamento forma_de_pagamento : listaFormas_de_pagamento) {
                    if (forma_de_pagamento.getNome().contains(jTextField2.getText())) {
                        modelo.addRow(new Object[]{forma_de_pagamento, forma_de_pagamento.getNome()});
                    }
                }
            }
        }
    }//GEN-LAST:event_jTextField2KeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAtualizarTabela;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

}
