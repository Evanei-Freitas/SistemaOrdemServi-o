/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Evanei Freitas - Programador.
 */
public class SoNumeros extends PlainDocument{
    /***
     * Método que restringe que o usuário digite strings(Textos), onde deve-se
     * digitar somente Números.
     * @param offs
     * @param str
     * @param a
     * @throws BadLocationException 
     */
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offs, str.replaceAll("[^0-9]", ""), a);
    }
    
}
