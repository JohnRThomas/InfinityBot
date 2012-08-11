package org.infinity.bot.internals.script;

import java.awt.Color;
import java.awt.MouseInfo;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

public class CategoryPop extends JDialog{
	private static final long serialVersionUID = 1L;
	JMenuBar bar = new JMenuBar();
	public CategoryPop(String name, int num){
		JTextArea info = new JTextArea();
		this.setAlwaysOnTop(true);
		this.setFocusable(false);
		int x = MouseInfo.getPointerInfo().getLocation().x+10;
		int y = MouseInfo.getPointerInfo().getLocation().y;
		this.setLocation(x,y);
		setUndecorated(true);
		setSize(150,25);
		setVisible(true);
		add(info);
		info.setLayout(new BoxLayout(info, BoxLayout.PAGE_AXIS));
		info.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(77,77,77), 2),
				BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(64,64,64), new Color(64,64,64))));
		info.setEditable(false);
		info.append("Contains " + num + " scripts");
	}
}