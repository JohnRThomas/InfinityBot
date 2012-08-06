package org.infinitybot.x.UI;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


public class InfinityButton extends JButton implements MouseListener { 

	//instance variables
	private static final long serialVersionUID = -1067770645969409240L;
	private Color defaultColor;
	private Color mouseOverColor;

	//class constructor
	public InfinityButton(String text, Color defaultColor, Color mouseOverColor) { 
		super(text);
		setBackground(defaultColor);
		this.defaultColor = defaultColor;
		this.mouseOverColor = mouseOverColor;
		addMouseListener(this);
	}

	//override the methods of implemented MouseListener
	public void mouseClicked(MouseEvent e) { }
	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }

	public void mouseEntered(MouseEvent e) { 
		if(e.getSource()==this) {  this.setBackground(this.mouseOverColor); }
	}

	public void mouseExited(MouseEvent e) { 
		if(e.getSource()==this) { this.setBackground(this.defaultColor); }
	}
}
