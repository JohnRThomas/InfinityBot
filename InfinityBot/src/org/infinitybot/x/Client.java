package org.infinitybot.x;

import icons.IconFactory;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GameCanvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.infinitybot.x.UI.InfinityLog;
import org.infinitybot.x.api.APIManager;
import org.infinitybot.x.api.Script;
import org.infinitybot.x.loader.DownloadThread;
import org.infinitybot.x.loader.GameLoader;
import org.infinitybot.x.scripts.Runner;
import org.infinitybot.x.scripts.ScriptSelectionGUI;


public class Client extends Container{
	private static final long serialVersionUID = 2014843184188029061L;
	private GameLoader loader;
	private final GameCanvas canvasAccess = new GameCanvas(-1);
	private static ArrayList<Client> clients = new ArrayList<Client>();
	private final int id;
	private Runner scriptRunner;
	private MouseManager mouse;
	private KeyboardManager keyboard;
	private boolean inputEnabled = true;

	private InfinityLog log = new InfinityLog();
	private JScrollPane logScroller = new JScrollPane(log);

	private final IconFactory ico = new IconFactory();

	JButton startB = new JButton("");
	JButton pauseB = new JButton("");
	JButton stopB = new JButton("");
	JButton rerunB = new JButton("");
	final JButton inputB = new JButton("");
	private JMenuBar menu = new JMenuBar();

	JMenuItem start = new JMenuItem("Start");
	JMenuItem pause = new JMenuItem("Pause");
	JMenuItem stop = new JMenuItem("Stop");
	JMenuItem rerun = new JMenuItem("Re-Run");

	private boolean isScriptRunning = false;
	private boolean isScriptSet = false;
	private String status = "(Gathering information)";
	JPanel splash = new JPanel(){
		private static final long serialVersionUID = 376248046061283699L;
		public void paintComponent(Graphics2D g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 10000, 10000);
			g.drawImage(ico.getLoading(),221,150,null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Avenir LT Std",Font.BOLD,16));
			g.drawString(status, 300,350);
		}
	};
	private Script myScript = null;
	private APIManager myAPIManager;

	@SuppressWarnings("static-access")
	public Client(){
		setSize(784,600);
		logScroller.setPreferredSize(new Dimension(784, 130));
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		makeMenuBar();
		setLayout(new BorderLayout());
		add(menu,BorderLayout.NORTH);
		add(splash,BorderLayout.CENTER);
		add(logScroller,BorderLayout.SOUTH);
		id = canvasAccess.count;
		clients.add(this);
	}	
	public void init(){
		mouse = new MouseManager(this);
		keyboard = new KeyboardManager(this);
		new DownloadThread(this, loader).start();
		this.repaint();
	}

	public void enableButtons() {
		if(!isScriptRunning){
			startB.setEnabled(true);
			stopB.setEnabled(false);
			pauseB.setEnabled(false);

			start.setEnabled(true);
			stop.setEnabled(false);
			pause.setEnabled(false);
			if(isScriptSet){
				rerunB.setEnabled(true);
				rerun.setEnabled(true);
			}else{
				rerunB.setEnabled(false);
				rerun.setEnabled(false);
			}
		}else{
			startB.setEnabled(false);
			stopB.setEnabled(true);
			pauseB.setEnabled(true);
			rerunB.setEnabled(false);

			start.setEnabled(false);
			stop.setEnabled(true);
			pause.setEnabled(true);
			rerun.setEnabled(false);
		}
		inputB.setEnabled(true);
	}
	public void draw(Graphics graphics){
		graphics.setColor(Color.WHITE);
		if(isScriptRunning)myScript.paint(graphics);
	}
	public Applet getApplet() {
		return loader.getApplet();
	}

	public int getId() {
		return id;
	}

	@SuppressWarnings("static-access")
	public BufferedImage getImage(){
		return canvasAccess.get(id).image;
	}
	public MouseManager getMouse() {
		return mouse;
	}

	public KeyboardManager getKeyboard() {
		return keyboard;
	}

	public void setInputEnabled(boolean inputEnabled) {
		this.inputEnabled = inputEnabled;
	}

	public boolean isInputEnabled() {
		return inputEnabled;
	}

	public void destroy(){
		if(loader != null){
			loader.getApplet().stop();
			loader.getApplet().destroy();
			loader = null;
		}
		mouse = null;
		keyboard = null;
		clients.remove(this);
		myAPIManager = null;
		scriptRunner = null;
	}
	public void setScript(Script script){
		myAPIManager = new APIManager(this);
		scriptRunner = new Runner(myScript, this);
		myScript = script.init(scriptRunner, myAPIManager);
		isScriptSet = true;
	}

	public void reStartScript(){
		if(myScript != null){
			scriptRunner = new Runner(myScript, this);
			scriptRunner.start();
			log("Script Loader", myScript.getAnnotaion().Name() + " started!");
			isScriptRunning = true;
		}
		enableButtons();
	}
	public void stopScript(){
		scriptRunner.interrupt();
		isScriptRunning = false;
		log("Script Loader", myScript.getAnnotaion().Name() + " stopped!");
		enableButtons();
	}
	public void pauseScript(){
		scriptRunner.setPause(true);
		enableButtons();
	}	
	public void resumeScript(){
		scriptRunner.setPause(false);
		enableButtons();
	}
	private void makeMenuBar() {
		JMenu script = new JMenu("Script");
		script.add(start);
		script.add(pause);
		script.add(stop);
		script.add(rerun);
		menu.add(script);
		start.setEnabled(false);
		pause.setEnabled(false);
		stop.setEnabled(false);
		rerun.setEnabled(false);

		startB.setToolTipText("Start");
		pauseB.setToolTipText("Pause");
		stopB.setToolTipText("Stop");
		rerunB.setToolTipText("Re-Run");
		inputB.setToolTipText("Disable Input");

		startB.setFocusable(false);
		pauseB.setFocusable(false);
		stopB.setFocusable(false);
		rerunB.setFocusable(false);
		inputB.setFocusable(false);

		startB.setEnabled(false);
		pauseB.setEnabled(false);
		stopB.setEnabled(false);
		rerunB.setEnabled(false);
		inputB.setEnabled(false);

		startB.setIcon(ico.getStart());
		pauseB.setIcon(ico.getPause());
		stopB.setIcon(ico.getStop());
		rerunB.setIcon(ico.getRerun());
		inputB.setIcon(ico.getKey());

		startB.setHorizontalAlignment(SwingConstants.RIGHT);
		pauseB.setFocusable(false);
		stopB.setFocusable(false);
		rerunB.setFocusable(false);

		menu.add(Box.createHorizontalGlue());
		menu.add(startB);
		menu.add(pauseB);
		menu.add(stopB);
		menu.add(rerunB);
		menu.add(inputB);
		final Client This = this;

		//Script menu listeners
		start.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(scriptRunner != null && scriptRunner.isPaused()){
					resumeScript();
				}else{
					SwingUtilities.invokeLater(new Runnable() {  
						public void run() {  
							new ScriptSelectionGUI(This).setVisible(true);
						}  
					}); 
				}
			}
		});
		pause.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				pauseScript();
			}
		});
		stop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				stopScript();
			}
		});
		rerun.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				reStartScript();
			}
		});

		//Button Listeners
		startB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(scriptRunner != null && scriptRunner.isPaused()){
					resumeScript();
				}else{
					SwingUtilities.invokeLater(new Runnable() {  
						public void run() {  
							new ScriptSelectionGUI(This).setVisible(true);
						}  
					}); 
				}
			}
		});
		pauseB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				pauseScript();
			}
		});
		rerunB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				reStartScript();
			}
		});
		stopB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				stopScript();
			}
		});
		inputB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isInputEnabled()){
					setInputEnabled(false);
					inputB.setIcon(ico.getDisKey());
				}else{
					setInputEnabled(true);
					inputB.setIcon(ico.getKey());
				}
			}
		});
	}
	//Log methods
	public void log(final String source, final Object ob){
		log(source, ob, null, null);
	}
	public void log(final String source, final Object ob, Color frgrd){
		log(source, ob, frgrd, null);
	}
	public void log(final String source, final Object ob, final Color frgrd, final Color bkgrd){
		java.awt.EventQueue.invokeLater (new Runnable() {
			public void run() {
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date();
				log.append("[" + dateFormat.format(date) + "] " + source + ": " + ob.toString(), frgrd, bkgrd);
				try{
					log.update(log.getGraphics());
				}catch(Exception e){}
				logScroller.getVerticalScrollBar().setValue(100);
			}
		});
	}
	public void setStatus(String stat){
		status = "(" + stat + ")";
		this.repaint();
	}

	public Component getSplash() {
		return splash;
	}
	public APIManager getAPIManger() {
		return myAPIManager;
	}
	public void updateCanvas(GameCanvas gameCanvas) {
		getKeyboard().cancan = gameCanvas;
		getMouse().cancan = gameCanvas;
	}
	public static Client get(int id) {
		for(int i = 0; i < clients.size(); i++){
			if(clients.get(i).id == id){
				return clients.get(i);
			}
		}
		return null;
	}
}
