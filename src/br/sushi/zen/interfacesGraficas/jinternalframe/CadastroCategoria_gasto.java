package br.sushi.zen.interfacesGraficas.jinternalframe;

import br.sushi.zen.auxiliares.documentoSemAspasEPorcento;
import br.sushi.zen.dao.Categorias_gasto;
import br.sushi.zen.interfaces.JanelaCadastrarCrud;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Categoria_gasto;
import javax.swing.JOptionPane;

/**
 *
 * @author Cesar Schutz
 */
public final class CadastroCategoria_gasto extends javax.swing.JInternalFrame implements JanelaCadastrarCrud<Categoria_gasto> {

    Categorias_gasto tegorias_gastoDao = new Categorias_gasto();
    Categoria_gasto categoria_gastoEditar = new Categoria_gasto();

    public CadastroCategoria_gasto() {
        super("Cadastrar Categoria de Gasto", true, true, false, true);
        initComponents();
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroCategorias_gasto.png")));
        jBAtualizar.setVisible(false);
        jBDeletar.setVisible(false);
    }

    public CadastroCategoria_gasto(Categoria_gasto categoria_gasto) {
        super("Atualizar Categoria de Gasto", true, true, false, true);
        initComponents();
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroCategorias_gasto.png")));
        preencher(categoria_gasto);
        jBSalvar.setVisible(false);
        this.categoria_gastoEditar = categoria_gasto;
    }

    @Override
    public void preencher(Categoria_gasto categoria_gasto) {
        jTFNome.setText(categoria_gasto.getNome());
        jTADescricao.setText(categoria_gasto.getDescricao());
    }

    @Override
    public void botaoCancelar() {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroCategoria_gastoVisualizar = new CadastroCategoria_gastoVisualizar();
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroCategoria_gastoVisualizar);
    }

    @Override
    public void doDefaultCloseAction() {
        JanelaPrincipal.internalFrameCadastroCategoria_gasto.dispose();
        JanelaPrincipal.internalFrameCadastroCategoria_gasto = null;
    }

    @Override
    public void botaoSalvar(Categoria_gasto categoria_gasto) {
        if (categoria_gasto.getNome().length() > 0) {
            tegorias_gastoDao.cadastrar(categoria_gasto);
            voltarParaJanelaDeVisualizacao();
        } else {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Preencha o Nome.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void botaoAtualizar(Categoria_gasto categoria_gasto) {
        if (categoria_gasto.getNome().length() > 0) {
            tegorias_gastoDao.atualizar(categoria_gasto);
            voltarParaJanelaDeVisualizacao();
        } else {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Preencha o Nome.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void botaoDeletar(Categoria_gasto categoria_gasto) {
        tegorias_gastoDao.deletar(categoria_gasto);
        voltarParaJanelaDeVisualizacao();
    }

    private Categoria_gasto criaCategoria_gasto(Categoria_gasto categoria_gasto) {
        categoria_gasto.setNome(jTFNome.getText());
        categoria_gasto.setDescricao(jTADescricao.getText());
        return categoria_gasto;
    }

    private void voltarParaJanelaDeVisualizacao() {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroCategoria_gastoVisualizar = new CadastroCategoria_gastoVisualizar();
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroCategoria_gastoVisualizar);
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
        Categoria_gasto categoria_gasto = new Categoria_gasto();
        categoria_gasto = criaCategoria_gasto(categoria_gasto);
        botaoSalvar(categoria_gasto);
    }//GEN-LAST:event_jBSalvarActionPerformed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        botaoCancelar();
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jBAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAtualizarActionPerformed
        categoria_gastoEditar = criaCategoria_gasto(categoria_gastoEditar);
        botaoAtualizar(categoria_gastoEditar);
    }//GEN-LAST:event_jBAtualizarActionPerformed

    private void jBDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDeletarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(JanelaPrincipal.janelaPrincipal, "Deseja realmente deletar esse Categoria de Gasto?", "ATENÇÃO", 0);
        if (resposta == JOptionPane.YES_OPTION) {
            botaoDeletar(categoria_gastoEditar);
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
