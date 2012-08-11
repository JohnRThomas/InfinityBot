package org.infinity.bot.scriptloader;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.infinity.bot.Client;
import org.infinity.bot.api.script.Script;
import org.infinity.bot.api.script.ScriptManifest.Categories;

public class CategoryPanel extends JPanel implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	Client myClient;
	private CategoryPop myPop;
	Categories category;
	ArrayList<Script> list;
	private JPanel holder = new JPanel();
	private int scroll;
	private boolean open = false;
	JLabel lab;
	private ScriptSelectionGUI myGUI;

	public CategoryPanel(Client myClient, Categories category, ArrayList<Script> list, ScriptSelectionGUI myGUI) {
		this.myGUI = myGUI;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.myClient = myClient;
		this.category = category;
		this.list = list;
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		holder.setLayout(new BoxLayout(holder,BoxLayout.PAGE_AXIS));
		lab = new JLabel(this.category.getCategory());
		lab.setAlignmentY(TOP_ALIGNMENT);
		lab.setAlignmentX(LEFT_ALIGNMENT);
		add(lab);
		add(holder);
	}
	public void open(){
		this.setMaximumSize(new Dimension(400,20 + 21*list.size()));
		holder.setMaximumSize(new Dimension(400,2+21*list.size()));
		open = true;
		for(int i = 0; i < list.size(); i++){
			addScriptLine(list.get(i), i%2!=0);
		}
	}
	private void addScriptLine(final Script script, boolean light){
		ScriptPanel line = new ScriptPanel(myClient, script, light, myGUI);
		line.setAlignmentY(TOP_ALIGNMENT);
		line.setAlignmentX(LEFT_ALIGNMENT);
		line.setMaximumSize(new Dimension(400,21));
		holder.add(Box.createHorizontalGlue());
		holder.add(line);
		holder.setBorder(new LineBorder(Color.BLACK));
		scroll  = (int)line.getPreferredSize().getHeight();
		Rectangle rect = new Rectangle(0,scroll,10,10);
		line.scrollRectToVisible(rect);
	}
	public void close(){
		holder.setMaximumSize(new Dimension(400,0));
		holder.removeAll();
		this.removeAll();
		add(lab);
		add(holder);
		this.setMaximumSize(new Dimension(400,20));
		this.repaint();
		open = false;
		scroll  = (int)this.getPreferredSize().getHeight();
		Rectangle rect = new Rectangle(0,scroll,10,10);
		this.scrollRectToVisible(rect);
	}
	public void mouseClicked(MouseEvent e) {
		if(open){
			close();
			mouseEntered(e);
		}else{
			open();
			mouseExited(e);
		}
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) { 
		if(e.getSource()==this && !open ) {
			final CategoryPanel This = this;
			java.awt.EventQueue.invokeLater (new Thread() {
				public void run() {
					try {
						sleep(100);
					} catch (InterruptedException e) {}
					Rectangle rect = new Rectangle(This.getLocationOnScreen().x,This.getLocationOnScreen().y,This.getWidth(),This.getHeight());
					if(rect.contains(MouseInfo.getPointerInfo().getLocation())){
						myPop = new CategoryPop(category.getCategory(), list.size());
					}
				}
			});
		}
	}

	public void mouseExited(MouseEvent e) { 
		if(e.getSource()==this) {
			if(myPop != null)myPop.dispose();
		}
	}

	public void mouseMoved(MouseEvent e) {
		if(myPop != null)myPop.setLocation(MouseInfo.getPointerInfo().getLocation().x+10,MouseInfo.getPointerInfo().getLocation().y);
	}
}
