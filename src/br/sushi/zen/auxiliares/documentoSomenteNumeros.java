package br.sushi.zen.auxiliares;


import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
/**
 *
 * @author BCN
 */
public class documentoSomenteNumeros extends PlainDocument {
    
    private int iMaxLength;  
   
    public documentoSomenteNumeros(int maxCaracteres) {  
        super();  
        iMaxLength = maxCaracteres;  
    }  
   
    @Override
    public void insertString(int offset, String str, AttributeSet attr)  
                    throws BadLocationException {  
          
  
        str = str.toUpperCase();

        str = str.replaceAll("[^0-9 ]", "");
        str = str.replaceAll("  ", " ");
        
        if (iMaxLength <= 0)        // aceitara qualquer no. de caracteres  
        {  
            super.insertString(offset, str, attr);  
            return;  
        }  
  
        int ilen = (getLength() + str.length());  
        if (ilen <= iMaxLength)    // se o comprimento final for menor...  
            super.insertString(offset, str, attr);   // ...aceita str  
        }
}
