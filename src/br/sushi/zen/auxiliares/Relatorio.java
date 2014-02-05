/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sushi.zen.auxiliares;

import br.sushi.zen.dao.Conexao;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import br.sushi.zen.interfacesGraficas.frames.jFCarregando;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * Para esta classe funcionar, o arquivo.jasper e a imagem utilizada deve estar na mesma pasta do .jar
 * Pois é de la que ele pega o jasper e a imagem para criar o pdf
 * 
 * args 0 = aruqivo.pdf
 * args 1 = aruqivo.jasper
 * args 2 = dataInicial
 * args 3 = dataFinal
 * args 4 = texto da janela carregando
 * @author Cesar Schutz
 */
public class Relatorio {

    public static void main(final String[] args) {
        SwingWorker worker = new SwingWorker() {
            jFCarregando carregando = new jFCarregando();
            @Override
            protected Object doInBackground() throws Exception {
                JanelaPrincipal.janelaPrincipal.setVisible(false);
                carregando = new jFCarregando();
                carregando.setVisible(true);
                carregando.jLabel1.setText(args[4]);
                
                gerarRelatorio(args[0], args[1], args[2], args[3]);
                return null;
            }

            @Override
            protected void done() {
                carregando.dispose();
                JanelaPrincipal.janelaPrincipal.setVisible(true);
            }
        };
        worker.execute();
        
    }

    public static void gerarRelatorio(String arquivo, String relatorioJasper, String dataInicial, String dataFinal) {
        try (Connection conn = Conexao.conectar()) {
            String caminho;
            caminho = Relatorio.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            caminho = caminho.substring(1, caminho.lastIndexOf('/') + 1);

            //criando arquivo
            Map<String, Object> params = new HashMap<>();
            //definindo os parametros
            params.put("dataInicial", dataInicial);
            params.put("dataFinal", dataFinal);
            params.put("caminhoPlaca", caminho + "Placa.jpg");
            System.out.println(caminho);
            JasperPrint print = JasperFillManager.fillReport(caminho + relatorioJasper, params, conn);

            //criando o arquivo PDF e salvando o mesmo
            JRExporter export = new JRPdfExporter();
            export.setParameter(JRExporterParameter.JASPER_PRINT, print);
            try {
                FileOutputStream fos = new FileOutputStream(arquivo);
                export.setParameter(JRExporterParameter.OUTPUT_STREAM, fos);
                export.exportReport();
                fos.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Selecione uma pasta Válida.", "Atenção!", JOptionPane.WARNING_MESSAGE);
            }
            
            
            
            //pdf criado, agora vamos abrilo
            File pdf = new File(arquivo);
            Desktop.getDesktop().open(pdf);
        } catch(JRException | ClassNotFoundException | IOException | URISyntaxException | SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao gerar Relatório. Procure o Administrador.", "Atenção!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
