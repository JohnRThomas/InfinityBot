package org.infinity.ui;

import javax.swing.JButton;

import java.awt.Color;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InfinityButton extends JButton implements MouseListener {

	private static final long serialVersionUID = -1067770645969409240L;

	private final Color defaultColor;
	private final Color mouseOverColor;

	public InfinityButton(final String text, final Color defaultColor, final Color mouseOverColor) {
		super(text);
		setBackground(defaultColor);
		this.defaultColor = defaultColor;
		this.mouseOverColor = mouseOverColor;
		addMouseListener(this);
	}

	public void mouseClicked(final MouseEvent e) {
	}

	public void mousePressed(final MouseEvent e) {
	}

	public void mouseReleased(final MouseEvent e) {
	}

	public void mouseEntered(final MouseEvent e) {
		if (e.getSource() == this) {
			this.setBackground(this.mouseOverColor);
		}
	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource() == this) {
			this.setBackground(this.defaultColor);
		}
	}
}
