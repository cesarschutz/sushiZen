package br.sushi.zen.interfacesGraficas.jinternalframe;

import br.sushi.zen.auxiliares.jTextFieldDinheiroReais;
import br.sushi.zen.auxiliares.documentoSemAspasEPorcento;
import br.sushi.zen.dao.Categorias_gasto;
import br.sushi.zen.dao.Gastos;
import br.sushi.zen.interfaces.JanelaCadastrarCrud;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Categoria_gasto;
import br.sushi.zen.model.Gasto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Cesar Schutz
 */
public final class CadastroGasto extends javax.swing.JInternalFrame implements JanelaCadastrarCrud<Gasto> {

    Gastos gastosDao = new Gastos();
    Gasto gastoEditar = new Gasto();
    ArrayList<Categoria_gasto> listaCategoriasGasto = new ArrayList<>();
    Categorias_gasto categoriasGastoDao = new Categorias_gasto();

    public CadastroGasto() {
        super("Cadastrar Gasto", true, true, false, true);
        initComponents();
        jXDatePicker1.setFormats(new String[]{"E dd/MM/yyyy"});
        jXDatePicker1.setLinkDate(System.currentTimeMillis(), "Hoje");
        preencheComboBoxCategorias();
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroGastos.png")));
        jBAtualizar.setVisible(false);
        jBDeletar.setVisible(false);
    }

    public CadastroGasto(Gasto gasto) {
        super("Atualizar Gasto", true, true, false, true);
        initComponents();
        jXDatePicker1.setFormats(new String[]{"E dd/MM/yyyy"});
        jXDatePicker1.setLinkDate(System.currentTimeMillis(), "Hoje");
        preencheComboBoxCategorias();
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroGastos.png")));
        preencher(gasto);
        jBSalvar.setVisible(false);
        this.gastoEditar = gasto;
    }

    @Override
    public void preencher(Gasto gasto) {
        jTFNome.setText(gasto.getNome());
        jTFValor.setText(String.valueOf(gasto.getValor()));
        jTADescricao.setText(gasto.getDescricao());
        jXDatePicker1.setDate(gasto.getData());
        
        //selecionando a modalidade
        for (Categoria_gasto categoriaGasto : listaCategoriasGasto) {
            if (gasto.getCategoria_gasto().getId_categoria_gasto() 
                    == categoriaGasto.getId_categoria_gasto()) {
                //se id_forme_de_pagamento da lista for igual a do pedido, ele seleciona
                int indice = listaCategoriasGasto.indexOf(categoriaGasto);
                jComboBox1.setSelectedIndex(indice);
            }
        }
    }

    @Override
    public void botaoCancelar() {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroGastoVisualizar = new CadastroGastosVisualizar();
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroGastoVisualizar);
    }

    @Override
    public void doDefaultCloseAction() {
        JanelaPrincipal.internalFrameCadastroGasto.dispose();
        JanelaPrincipal.internalFrameCadastroGasto = null;
    }

    @Override
    public void botaoSalvar(Gasto gasto) {
        if (gasto.getNome().length() > 0) {
            gastosDao.cadastrar(gasto);
            voltarParaJanelaDeVisualizacao();
        } else {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Preencha o Nome.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void botaoAtualizar(Gasto gasto) {
        if (gasto.getNome().length() > 0) {
            gastosDao.atualizar(gasto);
            voltarParaJanelaDeVisualizacao();
        } else {
            JOptionPane.showMessageDialog(JanelaPrincipal.janelaPrincipal, "Preencha o Nome.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void botaoDeletar(Gasto gasto) {
        gastosDao.deletar(gasto);
        voltarParaJanelaDeVisualizacao();
    }

    private Gasto criaGasto(Gasto gasto) {
        gasto.setNome(jTFNome.getText());
        gasto.setDescricao(jTADescricao.getText());
        gasto.setValor(new BigDecimal(Double.valueOf(jTFValor.getText().replaceAll("\\.", "").replaceAll(",", "."))).setScale(2, RoundingMode.HALF_EVEN));
        
        gasto.setCategoria_gasto(listaCategoriasGasto.get(jComboBox1.getSelectedIndex()));
        gasto.setData(pegandoDataDoDataPicker());
        
        
        return gasto;
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

    private void voltarParaJanelaDeVisualizacao() {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroGastoVisualizar = new CadastroGastosVisualizar();
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroGastoVisualizar);
    }
    
    private void preencheComboBoxCategorias() {
        jComboBox1.removeAllItems();
        listaCategoriasGasto = categoriasGastoDao.listar();
        for (Categoria_gasto categoria_gasto : listaCategoriasGasto) {
            jComboBox1.addItem(categoria_gasto.getNome());
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

        jBSalvar = new javax.swing.JButton();
        jBAtualizar = new javax.swing.JButton();
        jBDeletar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jTFNome = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTFValor = new jTextFieldDinheiroReais() {
            {// limita a 8
                // caracteres
                setLimit(8);
            }
        };
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTADescricao = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();

        setMaximumSize(new java.awt.Dimension(547, 291));
        setMinimumSize(new java.awt.Dimension(547, 291));

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

        jLabel2.setText("Valor:");

        jLabel3.setText("Descrição:");

        jTADescricao.setLineWrap(true);
        jTADescricao.setWrapStyleWord (true);
        jTADescricao.setDocument(new documentoSemAspasEPorcento(500));
        jTADescricao.setColumns(20);
        jTADescricao.setRows(5);
        jScrollPane1.setViewportView(jTADescricao);

        jLabel4.setText("Categoria:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Data:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
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
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTFValor, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 90, Short.MAX_VALUE))
                            .addComponent(jTFNome))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTFValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTFNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvarActionPerformed
        Gasto gasto = new Gasto();
        gasto = criaGasto(gasto);
        botaoSalvar(gasto);
    }//GEN-LAST:event_jBSalvarActionPerformed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        botaoCancelar();
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jBAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAtualizarActionPerformed
        gastoEditar = criaGasto(gastoEditar);
        botaoAtualizar(gastoEditar);
    }//GEN-LAST:event_jBAtualizarActionPerformed

    private void jBDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDeletarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(JanelaPrincipal.janelaPrincipal, "Deseja realmente deletar esse Gasto?", "ATENÇÃO", 0);
        if (resposta == JOptionPane.YES_OPTION) {
            botaoDeletar(gastoEditar);
        }
    }//GEN-LAST:event_jBDeletarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAtualizar;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBDeletar;
    private javax.swing.JButton jBSalvar;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTADescricao;
    private javax.swing.JTextField jTFNome;
    private javax.swing.JTextField jTFValor;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    // End of variables declaration//GEN-END:variables
}
