package br.sushi.zen.interfacesGraficas.jinternalframe;

import br.sushi.zen.auxiliares.documentoSemAspasEPorcento;
import br.sushi.zen.dao.Clientes;
import br.sushi.zen.interfaces.JanelaVisualizar;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.model.Cliente;
import java.util.ArrayList;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */
public class CadastroClienteVisualizar extends javax.swing.JInternalFrame implements JanelaVisualizar<Cliente> {

    Clientes clientesDao = new Clientes();
    ArrayList<Cliente> listaCliente = new ArrayList<>();

    /**
     * DEFAULT CONSTRUCTOR
     */
    public CadastroClienteVisualizar() {
        super("Clientes", true, true, false, true);
        initComponents();
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroClientes.png")));
        jTable1.setRowHeight(30);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        preencheTabela(listaCliente = clientesDao.listar());

        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);

        jTable1.getColumnModel().getColumn(1).setMaxWidth(250);
        jTable1.getColumnModel().getColumn(1).setMinWidth(250);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(250);

        jTable1.getColumnModel().getColumn(2).setMaxWidth(110);
        jTable1.getColumnModel().getColumn(2).setMinWidth(110);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(110);

        jTable1.getColumnModel().getColumn(3).setMaxWidth(110);
        jTable1.getColumnModel().getColumn(3).setMinWidth(110);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(110);

        jBAtualizarCliente.setVisible(false);
        jBSelecionarCliente.setVisible(false);
    }

    @Override
    public void doDefaultCloseAction() {
        JanelaPrincipal.internalFrameCadastroClienteVisualizar.dispose();
        JanelaPrincipal.internalFrameCadastroClienteVisualizar = null;
    }

    @Override
    public final void preencheTabela(ArrayList<Cliente> lista) {
        //limpa tabela
        ((DefaultTableModel) jTable1.getModel()).setNumRows(0);
        jTable1.updateUI();
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

        if (lista != null) {
            for (Cliente cliente : lista) {
                modelo.addRow(new Object[]{cliente, cliente.getNome(), corrigirTelefone(cliente.getTelefone()), corrigirTelefone(cliente.getCelular()), cliente.getEndereco()});
            }
        }
    }

    @Override
    public void botaoNovo() {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroCliente = new CadastroCliente();
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroCliente);
    }

    @Override
    public void clicaTabela(Cliente cliente) {
        doDefaultCloseAction();
        JanelaPrincipal.internalFrameCadastroCliente = new CadastroCliente(cliente);
        JanelaPrincipal.abrirInternalFrame(JanelaPrincipal.internalFrameCadastroCliente);
    }

    private String corrigirTelefone(String telefone) {
        try {
            String string = telefone;
            StringBuilder stringBuilder = new StringBuilder(string);
            stringBuilder.insert(string.length() - 10, '(');
            stringBuilder.insert(string.length() - 7, ") ");
            stringBuilder.insert(string.length() - 1, " - ");

            return stringBuilder.toString();
        } catch (Exception e) {
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
        jBNovoCliente = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jBAtualizarTabela = new javax.swing.JButton();
        jBAtualizarCliente = new javax.swing.JButton();
        jBSelecionarCliente = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1041, 676));
        setMinimumSize(new java.awt.Dimension(1041, 676));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cliente", "Nome", "Telefone", "Celular", "Endereço"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        jBNovoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/crudNovo.png"))); // NOI18N
        jBNovoCliente.setText("Novo");
        jBNovoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNovoClienteActionPerformed(evt);
            }
        });

        jTextField2.setDocument(new documentoSemAspasEPorcento(64));
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jLabel1.setText("Pesquisa Cliente por Nome: ");

        jBAtualizarTabela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/atualizarTabela.png"))); // NOI18N
        jBAtualizarTabela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAtualizarTabelaActionPerformed(evt);
            }
        });

        jBAtualizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/crudAtualizar.png"))); // NOI18N
        jBAtualizarCliente.setText("Editar Cliente");
        jBAtualizarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAtualizarClienteActionPerformed(evt);
            }
        });

        jBSelecionarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/imagemBotaoOk.png"))); // NOI18N
        jBSelecionarCliente.setText("Selecionar Cliente");
        jBSelecionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSelecionarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1005, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextField2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBAtualizarTabela))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBSelecionarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBAtualizarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBNovoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBNovoCliente)
                    .addComponent(jBAtualizarCliente)
                    .addComponent(jBSelecionarCliente))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBNovoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNovoClienteActionPerformed
        botaoNovo();
    }//GEN-LAST:event_jBNovoClienteActionPerformed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        clicaTabela((Cliente) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
    }//GEN-LAST:event_jTable1MouseReleased

    private void jBAtualizarTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAtualizarTabelaActionPerformed
        jTextField2.setText("");
        preencheTabela(listaCliente = clientesDao.listar());
    }//GEN-LAST:event_jBAtualizarTabelaActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        if ("".equals(jTextField2.getText())) {
            preencheTabela(listaCliente);
        } else {
            //limpa tabela
            ((DefaultTableModel) jTable1.getModel()).setNumRows(0);
            jTable1.updateUI();
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

            if (listaCliente != null) {
                for (Cliente cliente : listaCliente) {
                    if (cliente.getNome().contains(jTextField2.getText())) {
                        modelo.addRow(new Object[]{cliente, cliente.getNome(), corrigirTelefone(cliente.getTelefone()), corrigirTelefone(cliente.getCelular()), cliente.getEndereco()});
                    }
                }
            }
        }
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jBAtualizarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAtualizarClienteActionPerformed
        botaoEditarCliente();
    }//GEN-LAST:event_jBAtualizarClienteActionPerformed

    private void jBSelecionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSelecionarClienteActionPerformed
        botaoSelecionarCliente();
    }//GEN-LAST:event_jBSelecionarClienteActionPerformed

    public void botaoSelecionarCliente() {

    }

    public void botaoEditarCliente() {

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jBAtualizarCliente;
    private javax.swing.JButton jBAtualizarTabela;
    public javax.swing.JButton jBNovoCliente;
    public javax.swing.JButton jBSelecionarCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

}
