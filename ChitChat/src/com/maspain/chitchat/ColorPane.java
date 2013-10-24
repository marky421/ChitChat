package com.maspain.chitchat;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ColorPane extends JTextPane {
	
	private static final long serialVersionUID = 1L;
	
	public ColorPane() {
		super();
	}
	
	public void append(Color c, String s) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		getDisabledTextColor();
		
		setCaretPosition(getDocument().getLength());
		setCharacterAttributes(aset, false);
		
		setEditable(true);
		replaceSelection(s);	// this only works if the JTextPane is editable, so toggle its boolean before and after writing
		setEditable(false);
	}
}
