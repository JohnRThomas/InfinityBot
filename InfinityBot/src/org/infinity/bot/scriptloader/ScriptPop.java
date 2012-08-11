package org.infinity.bot.scriptloader;

import java.awt.Color;
import java.awt.MouseInfo;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import org.infinity.bot.api.script.Script;

public class ScriptPop extends JDialog{
	private static final long serialVersionUID = 1L;
	public ScriptPop(String name, double version, String [] authors, String category, String descript, Script myScript){
		JTextArea info = new JTextArea();
		this.setAlwaysOnTop(true);
		int x = MouseInfo.getPointerInfo().getLocation().x+10;
		int y = MouseInfo.getPointerInfo().getLocation().y;
		this.setLocation(x,y);
		setUndecorated(true);
		setVisible(true);
		this.setFocusable(false);
		if(myScript.customPop() == null){
			add(info);
			info.setLayout(new BoxLayout(info, BoxLayout.PAGE_AXIS));
			info.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(77,77,77), 2),
					BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(64,64,64), new Color(64,64,64))));
			info.setEditable(false);
			info.setLineWrap(true);
			info.append("Name:" + name + "\n");
			info.append("Version: " + version + "\n");
			info.append("Authors: ");
			for(int i = 0; i < authors.length; i++){
				if(i ==0)info.append(authors[i]);
				else info.append(", " + authors[i]);
			}
			info.append("\n");
			info.append("Description: " + descript);
			setSize(150,105);
		}else{
			add(myScript.customPop());
			setSize(myScript.customPop().getSize());
			myScript.customPop().setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(77,77,77), 2),
					BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(64,64,64), new Color(64,64,64))));

		}
	}
}
