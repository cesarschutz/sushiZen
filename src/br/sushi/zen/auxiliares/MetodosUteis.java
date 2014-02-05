package br.sushi.zen.auxiliares;

import javax.swing.text.MaskFormatter;

/**
 *
 * @author Cesar Schutz
 */
public class MetodosUteis {
    /**
     * Metodo que define uma mascara para um jinternalframe.
     * @param s
     * @return 
     */
    public static MaskFormatter mascaraParaJFormattedTextField(String s) {  
        MaskFormatter formatter = null;  
            try {  
                formatter = new MaskFormatter(s);  
            } catch (java.text.ParseException e) { 
            }  
        return formatter;  
    }
}
