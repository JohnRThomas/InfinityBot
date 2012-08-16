package org.infinity.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class Splash extends JDialog{
	private static final long serialVersionUID = -8067240547376106464L;

	public Splash(final BufferedImage logo){
		setLocationRelativeTo(getOwner());
		setSize(342,200);
		setAlwaysOnTop(true);
		setUndecorated(true);
		setVisible(true);
		//splashScreen.setBackground(new Color(0,0,0,0));
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(logo,0,0,null);
			}
		};
		add(panel);
	}
}
