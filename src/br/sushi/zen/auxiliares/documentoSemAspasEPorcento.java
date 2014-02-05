/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sushi.zen.auxiliares;

import javax.swing.text.AttributeSet; 
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
/**
 * Classe para definir o maximo de caracteres em um JTextField!
 * Mode de usar:
 * JTextField.setDocument(new DefinirMaxCaracteresDeUmJtextField(32));
 * com parmetro(32) sendo o numero de caracteres maximos do JTextField.
 * @author Cesar Schutz
 */
public class documentoSemAspasEPorcento extends PlainDocument{
    private int iMaxLength;  
   
    public documentoSemAspasEPorcento(int maxCaracteres) {  
        super();  
        iMaxLength = maxCaracteres;  
    }  
   
    @Override
    public void insertString(int offset, String str, AttributeSet attr)  
                    throws BadLocationException {  
          
        str = str.toUpperCase();
  
        str = str.replaceAll("[%'\"]", "");
        
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
