/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sushi.zen.interfacesGraficas.jinternalframe;

import br.sushi.zen.auxiliares.Relatorio;
import br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;

/**
 *
 * @author Cesar Schutz
 */
public class RelatorioGastos extends RelatoriosPedidos {
    
    public RelatorioGastos() {
        super();
        this.setTitle("Relatório de Gastos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/sushi/zen/imagens/menuCadastroGastos.png")));
    }
    
    @Override
    public void botaoGerarRelatorio() {
        //pega as datas
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date dataInicial = jDDataInicial.getDate();
        String dataInicialString = df.format(dataInicial);

        Date dataFinal = jDDataFinal.getDate();
        String dataFinalString = df.format(dataFinal);

        //pega o caminho 
        JFileChooser fc = new JFileChooser();
        fc.setApproveButtonText("Salvar");
        fc.setDialogTitle("Selecione a Pasta");
        fc.setSelectedFile(new File("C:\\Relatorio de Gastos"));

        int res = fc.showOpenDialog(null);
        String arquivoPDF;

        if (res == JFileChooser.APPROVE_OPTION) {
            File diretorio = fc.getSelectedFile();
            //return diretorio.getName();
            arquivoPDF = diretorio.getAbsolutePath() + ".pdf";
            
            //gera o relatorio
            Relatorio.main(new String[]{arquivoPDF, "sushiZenGastos.jasper", dataInicialString, dataFinalString, "Gerando Relatório de Gastos. Aguarde!"});
        }
    }
    
    @Override
    public void doDefaultCloseAction() {
        JanelaPrincipal.internalFrameRelatorioGastos.dispose();
        JanelaPrincipal.internalFrameRelatorioGastos = null;
    }
}
