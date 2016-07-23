package presentation.common;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class MyTextPanel extends JTextPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5775710955624757055L;

	public void setDocs(String str, Color col, boolean bold, int fontSize, boolean isNewLine) {
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attrSet, col);
		if (bold == true) {
			StyleConstants.setBold(attrSet, true);
		}
		StyleConstants.setFontSize(attrSet, fontSize);
		Document doc = getDocument();
		if (isNewLine == true) {
			str = "\n" + str;
		}
		try {
			doc.insertString(doc.getLength(), str, attrSet);
		} catch (BadLocationException e) {
			System.out.println("BadLocationException:   " + e);
		}
	}
}
