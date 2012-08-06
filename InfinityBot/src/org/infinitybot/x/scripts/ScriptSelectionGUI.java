package org.infinitybot.x.scripts;

import icons.IconFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import org.infinitybot.x.Client;
import org.infinitybot.x.UI.InfinityButton;
import org.infinitybot.x.api.Script;
import org.infinitybot.x.api.ScriptManifest;
import org.infinitybot.x.api.ScriptManifest.Categories;


public class ScriptSelectionGUI extends JDialog{
	private static final long serialVersionUID = -7259390432624886828L;
	JPanel mainPane = new JPanel();
	private static Point point = new Point();
	private IconFactory ico = new IconFactory();
	private JPanel holder = new JPanel();
	private JScrollPane listScroller = new JScrollPane(holder);
	private ArrayList<Script> scripts = new ArrayList<Script>();
	private HashMap<Categories, ArrayList<Script>> sortedScripts = new HashMap<Categories, ArrayList<Script>>();
	ScriptLoader loader;
	Client myClient;
	private int scroll;

	public ScriptSelectionGUI(Client cli){
		this.setFocusable(false);
		this.setAlwaysOnTop(true);
		myClient = cli;
		loadClasses();
		for(int i = 0; i < scripts.size(); i++){
			try{
				sortedScripts.get(scripts.get(i).getAnnotaion().Category()).add(scripts.get(i));
			}catch(NullPointerException e){
				sortedScripts.put(scripts.get(i).getAnnotaion().Category(), new ArrayList<Script>());
				sortedScripts.get(scripts.get(i).getAnnotaion().Category()).add(scripts.get(i));
			}
		}
		
		setupFrame("Script Selector");
		this.setLayout(new GridLayout());
		this.setSize(400, 300);
		setLocationRelativeTo(getParent());
		mainPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		holder.setLayout(new BoxLayout(holder, BoxLayout.PAGE_AXIS));
		holder.setBorder(new LineBorder(Color.BLACK));
		mainPane.add(listScroller);
		for(Categories key: sortedScripts.keySet()){
			addScriptCategory(key, sortedScripts.get(key));
		}
	}
	private void addScriptCategory(final Categories category, final ArrayList<Script> list){
		final ScriptSelectionGUI This = this;
		java.awt.EventQueue.invokeLater (new Runnable() {
			public void run() {
				CategoryPanel cat = new CategoryPanel(myClient, category, list, This);
				cat.setAlignmentY(TOP_ALIGNMENT);
				holder.add(cat);
				cat.setMaximumSize(new Dimension(400,20));
				scroll  = (int)cat.getPreferredSize().getHeight();
				Rectangle rect = new Rectangle(0,scroll,10,10);
				cat.scrollRectToVisible(rect);
				
			}
		});
	}


	private void setupFrame(String name) {
		JMenuBar topBar = new JMenuBar();
		InfinityButton closeB = new InfinityButton("", Color.BLACK, new Color(255,50,50));
		JLabel title = new JLabel(name);
		title.setIcon(ico.getFavi());
		title.setFont(new Font("Segoe UI",Font.BOLD,13));
		setUndecorated(true);
		JPanel window = new JPanel();
		window.setLayout(new BorderLayout());
		window.add(topBar, BorderLayout.NORTH);
		topBar.add(title);
		topBar.add(Box.createHorizontalGlue());
		closeB.setFocusable(false);
		closeB.setIcon(ico.getClose());
		topBar.add(closeB);

		mainPane.setLayout(new GridLayout());
		window.add(mainPane,BorderLayout.CENTER);
		window.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(77,77,77), 2),
				BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(64,64,64), new Color(64,64,64))));

		this.add(window);
		topBar.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				point.x = e.getX();
				point.y = e.getY();
			}
		});
		topBar.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
			}
		});
		closeB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	@SuppressWarnings("unchecked")
	private void loadClasses(){
		loader = new ScriptLoader("./Scripts",myClient); 
		ArrayList<Class<? extends Script>> Cscripts = new ArrayList<Class<? extends Script>>();
		ArrayList<Class<?>> classes = loader.getScripts();
		for(int i = 0; i < classes.size(); i++){
			if(classes.get(i).getAnnotation(ScriptManifest.class) != null)Cscripts.add((Class<? extends Script>)classes.get(i));
		}
		for(int i = 0; i < Cscripts.size(); i++){
			try {
				scripts.add(Cscripts.get(i).newInstance());
			} catch (InstantiationException e1) {
			} catch (IllegalAccessException e1) {
			}catch (ClassCastException e1){}
		}
	}
}
