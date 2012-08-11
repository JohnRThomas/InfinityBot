package org.infinity.bot.internals.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JTextArea;

public class InfinityLog extends Container {

	private static final long serialVersionUID = -7395615991062077160L;

	private final Container holder;
	private int scroll;

	public InfinityLog(){
		holder = new Container();
		setLayout(new GridLayout());
		holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));
		add(holder);
	}
	
	public void append(final String str){
		append(str, null, null);
	}

	public void append(final String str, final Color frgrd){
		append(str, frgrd, null);
	}

	public void append(final String str, final Color frgrd, final Color bkgrd){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JTextArea line = new JTextArea(str.trim());
				line.setEditable(false);
				if (frgrd != null) {
					line.setForeground(frgrd);
				}
				if (bkgrd != null) {
					line.setBackground(bkgrd);
				}
				line.setOpaque(true);
				line.setAlignmentY(TOP_ALIGNMENT);
				line.setMaximumSize(new Dimension(800, 16));
				if (holder.getComponentCount() >= 40) {
					holder.remove(0);
				}
				holder.add(line);
				scroll = (int) line.getPreferredSize().getHeight();
				Rectangle rect = new Rectangle(0, scroll, 10, 10);
				line.scrollRectToVisible(rect);
			}
		});
	}
}
