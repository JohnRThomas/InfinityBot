package org.infinitybot.UI;


import icons.IconFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

import org.infinitybot.x.Client;
import org.infinitybot.x.Console;
import org.infinitybot.x.UI.InfinityButton;
import org.pushingpixels.substance.api.skin.SubstanceOfficeBlack2007LookAndFeel;


public class InfinityGUI extends JFrame{
	private static final long serialVersionUID = -525789742766444583L;

	private static ArrayList<Client> clients = new ArrayList<Client>();
	static JTabbedPane tabs = new JTabbedPane();
	private int visCount = 1;

	private Container superPane = new Container();
	private Container mainPane = new Container();
	private static Point point = new Point();
	private IconFactory ico = new IconFactory();

	public static void main(String args[]){
		new Console();
		SwingUtilities.invokeLater(new Runnable() {  
			public void run() {  
				try {  
					UIManager.setLookAndFeel(new SubstanceOfficeBlack2007LookAndFeel());
				} catch (Exception e) {}  
				//new ScriptSelectionGUI(null).setVisible(true);
				new InfinityGUI().setVisible(true);
			}  
		}); 

	}
	public InfinityGUI(){
		super("Infinity Bot vB1.0");
		makeTitleBar("Infinity Bot vB1.0");
        //TODO make all size icons
		BufferedImage image = new BufferedImage(ico.getFavi().getIconWidth(),ico.getFavi().getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        ico.getFavi().paintIcon(null, image.getGraphics(), 0, 0);
		setIconImage(image);
		setSize(788, 767);
		superPane.setLayout(new BorderLayout());
		makeGUI();
		SwingUtilities.updateComponentTreeUI(this);
		init();
		setVisible(true);
		setLocationRelativeTo(getOwner());
		addNewTab(tabs.getTabCount());
	}

	private void makeGUI() {
		tabs.setPreferredSize(new Dimension(784, 655));
		tabs.addTab("+", new JLabel());		
		superPane.add(tabs,BorderLayout.CENTER);
		mainPane.add(superPane);
	}
	
	private void init() {
		tabs.setFocusable(false);

		tabs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tabs.getSelectedComponent() instanceof JLabel) {
					int count = tabs.getTabCount();
					if(count < 7){
						addNewTab(count);
					}
				}
			}
		});	
	}
	public void makeTitleBar(String name){
		JMenuBar topBar = new JMenuBar();
		InfinityButton closeB = new InfinityButton("", Color.BLACK, new Color(255,50,50));
		InfinityButton minB = new InfinityButton("", Color.BLACK, new Color(0,175,223));
		JLabel title = new JLabel(name);
		title.setIcon(ico.getFavi());
		title.setFont(new Font("Segoe UI",Font.BOLD,13));
		setUndecorated(true);
		JPanel window = new JPanel();
		window.setLayout(new BorderLayout());
		window.add(topBar, BorderLayout.NORTH);
		topBar.add(title);
		topBar.add(Box.createHorizontalGlue());
		minB.setFocusable(false);
		closeB.setFocusable(false);
		minB.setIcon(ico.getMini());
		closeB.setIcon(ico.getClose());
		topBar.add(minB);
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
				System.exit(1);
			}
		});
		minB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setState(Frame.ICONIFIED);
			}
		});
	}


	private void addNewTab(final int count) {
		final Client newTab = new Client();
		CloseableTabOverlay newTabB = new CloseableTabOverlay(tabs);
		tabs.insertTab("Client " + visCount++, null, newTab, null, count -1);
		tabs.setTabComponentAt(count - 1, newTabB);
		tabs.setSelectedIndex(count - 1);
		this.repaint();
		clients.add(newTab);
		this.repaint();
		newTab.init();
	}
	public static void removeClient(int i) {
		clients.get(i).destroy();
		clients.remove(i);
	}
}
