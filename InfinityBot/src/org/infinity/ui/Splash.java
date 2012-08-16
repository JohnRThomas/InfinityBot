package org.infinity.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class Splash extends JDialog{
	private static final long serialVersionUID = -8067240547376106464L;
	BufferedImage logo = null;
	JPanel panel = new JPanel() {
		private static final long serialVersionUID = 1L;
		@Override
		protected void paintComponent(Graphics g) {
			g.drawImage(logo,0,0,null);
		}
	};
	public Splash(final BufferedImage logo){
		this.logo = logo;
		setUndecorated(true);
		setBackground(new Color(0,0,0,0));
		add(panel);
		panel.update(panel.getGraphics());
		repaint();
		setLocationRelativeTo(getOwner());
		setSize(342,200);
		setAlwaysOnTop(true);
		setVisible(true);
	}
	public void changeImage(final BufferedImage logo){
		this.logo = logo;
		panel.update(panel.getGraphics());
		repaint();
	}
}
