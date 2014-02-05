package br.sushi.zen.interfacesGraficas.jinternalframe;

import br.sushi.zen.auxiliares.documentoSemAspasEPorcento;
import br.sushi.zen.dao.Formas_de_pagamento;
import br.sushi.zen.interfaces.JanelaCadastrarCrud;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Forma_de_pagamento;
import javax.swing.JOptionPane;

/**
 *
 * @author Cesar Schutz
 */
public final class CadastroForma_de_pagamento extends javax.swing.JInternalFrame implements JanelaCadastrarCrud<Forma_de_pagamento> {

    Formas_de_pagamento forma_de_pagamentosDao = new Formas_de_pagamento();
    Forma_de_pagamento forma_de_pagamentoEditar = new Forma_de_pagamento();

    public CadastroForma_de_pagamento() {
        super("Cadastrar Forma de Pagamento", true, true, false, true);
        initComponents();
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroFormas_de_pagamento.png")));
        jBAtualizar.setVisible(false);
        jBDeletar.setVisible(false);
    }

    public CadastroForma_de_pagamento(Forma_de_pagamento forma_de_pagamento) {
        super("Atualizar Forma de Pagamento", true, true, false, true);
        initComponents();
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroFormas_de_pagamento.png")));
        preencher(forma_de_pagamento);
        jBSalvar.setVisible(false);
        this.forma_de_pagamentoEditar = forma_de_pagamento;
    }

    @Override
    public void preencher(Forma_de_pagamento forma_de_pagamento) {
        jTFNome.setText(forma_de_pagamento.getNome());
        jTADescricao.setText(forma_de_pagamento.getDescricao());
    }

    @Override
    public void botaoCancelar() {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroForma_de_pagamentoVisualizar = new CadastroForma_de_pagamentoVisualizar();
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroForma_de_pagamentoVisualizar);
    }

    @Override
    public void doDefaultCloseAction() {
        JanelaPrincipal.internalFrameCadastroForma_de_pagamento.dispose();
        JanelaPrincipal.internalFrameCadastroForma_de_pagamento = null;
    }

    @Override
    public void botaoSalvar(Forma_de_pagamento forma_de_pagamento) {
        if (forma_de_pagamento.getNome().length() > 0) {
            forma_de_pagamentosDao.cadastrar(forma_de_pagamento);
            voltarParaJanelaDeVisualizacao();
        } else {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Preencha o Nome.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void botaoAtualizar(Forma_de_pagamento forma_de_pagamento) {
        if (forma_de_pagamento.getNome().length() > 0) {
            forma_de_pagamentosDao.atualizar(forma_de_pagamento);
            voltarParaJanelaDeVisualizacao();
        } else {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Preencha o Nome.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void botaoDeletar(Forma_de_pagamento forma_de_pagamento) {
        forma_de_pagamentosDao.deletar(forma_de_pagamento);
        voltarParaJanelaDeVisualizacao();
    }

    private Forma_de_pagamento criaForma_de_pagamento(Forma_de_pagamento forma_de_pagamento) {
        forma_de_pagamento.setNome(jTFNome.getText());
        forma_de_pagamento.setDescricao(jTADescricao.getText());
        return forma_de_pagamento;
    }

    private void voltarParaJanelaDeVisualizacao() {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroForma_de_pagamentoVisualizar = new CadastroForma_de_pagamentoVisualizar();
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroForma_de_pagamentoVisualizar);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBSalvar = new javax.swing.JButton();
        jBAtualizar = new javax.swing.JButton();
        jBDeletar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jTFNome = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTADescricao = new javax.swing.JTextArea();

        setMaximumSize(new java.awt.Dimension(513, 269));
        setMinimumSize(new java.awt.Dimension(513, 269));

        jBSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/crudSalvar.png"))); // NOI18N
        jBSalvar.setText("Salvar");
        jBSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBSalvar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalvarActionPerformed(evt);
            }
        });

        jBAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/crudAtualizar.png"))); // NOI18N
        jBAtualizar.setText("Atualizar");
        jBAtualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBAtualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAtualizarActionPerformed(evt);
            }
        });

        jBDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/crudDeletar.png"))); // NOI18N
        jBDeletar.setText("Deletar");
        jBDeletar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBDeletar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDeletarActionPerformed(evt);
            }
        });

        jBCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/crudCancelar.png"))); // NOI18N
        jBCancelar.setText("Cancelar");
        jBCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });

        jTFNome.setDocument(new documentoSemAspasEPorcento(64));

        jLabel1.setText("Nome:");

        jLabel3.setText("Descrição:");

        jTADescricao.setLineWrap(true);
        jTADescricao.setWrapStyleWord (true);
        jTADescricao.setDocument(new documentoSemAspasEPorcento(500));
        jTADescricao.setColumns(20);
        jTADescricao.setRows(5);
        jScrollPane1.setViewportView(jTADescricao);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFNome))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBAtualizar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBDeletar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBCancelar))
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTFNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBAtualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBDeletar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvarActionPerformed
        Forma_de_pagamento forma_de_pagamento = new Forma_de_pagamento();
        forma_de_pagamento = criaForma_de_pagamento(forma_de_pagamento);
        botaoSalvar(forma_de_pagamento);
    }//GEN-LAST:event_jBSalvarActionPerformed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        botaoCancelar();
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jBAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAtualizarActionPerformed
        forma_de_pagamentoEditar = criaForma_de_pagamento(forma_de_pagamentoEditar);
        botaoAtualizar(forma_de_pagamentoEditar);
    }//GEN-LAST:event_jBAtualizarActionPerformed

    private void jBDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDeletarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(JanelaPrincipal.janelaPrincipal, "Deseja realmente deletar esse Forma de Pagamento?", "ATENÇÃO", 0);
        if (resposta == JOptionPane.YES_OPTION) {
            botaoDeletar(forma_de_pagamentoEditar);
        }
    }//GEN-LAST:event_jBDeletarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAtualizar;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBDeletar;
    private javax.swing.JButton jBSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTADescricao;
    private javax.swing.JTextField jTFNome;
    // End of variables declaration//GEN-END:variables
}