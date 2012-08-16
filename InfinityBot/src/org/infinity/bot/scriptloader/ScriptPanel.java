package org.infinity.bot.scriptloader;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.infinity.bot.api.script.Script;
import org.infinity.bot.internals.Client;

public class ScriptPanel extends JPanel implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = -8735648526505335657L;
	private String name;
	private double version;
	private String [] authors;
	private String category;
	private String descript;
	private Script myScript;
	private ScriptPop myPop;


	public ScriptPanel(final Client myClient, Script script, boolean light, final ScriptSelectionGUI myGUI){
		super();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		myScript = script;
		name = script.getAnnotaion().Name();
		version = script.getAnnotaion().Version();
		authors = script.getAnnotaion().Authors();
		category = script.getAnnotaion().Category().getCategory();
		descript = script.getAnnotaion().Description();
		this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		JLabel nameLab = new JLabel(name);
		JButton launchB = new JButton("Launch");
		if(!light)this.setBackground(Color.BLACK);
		add(nameLab);
		add(Box.createHorizontalGlue());
		add(launchB);
		launchB.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				myClient.setScript(myScript);
				myClient.reStartScript();
				myGUI.dispose();
			}
		});
	}
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) { 
		if(e.getSource()==this) {
			final ScriptPanel This = this;
			java.awt.EventQueue.invokeLater (new Thread() {
				public void run() {
					try {
						sleep(100);
					} catch (InterruptedException e) {}
					Rectangle rect = new Rectangle(This.getLocationOnScreen().x,This.getLocationOnScreen().y,This.getWidth(),This.getHeight());
					if(rect.contains(MouseInfo.getPointerInfo().getLocation())){
						myPop = new ScriptPop(name, version, authors, category, descript, myScript);
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
