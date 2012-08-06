package org.infinitybot.x.api;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import org.infinitybot.x.scripts.Runner;

public abstract class Script{
	private static boolean isPaused = false;
	private final ScriptManifest manifest;
	private Runner runner = null;
	private APIManager api;
	public Mouse mouse;
	public Screen screen;
	public Keyboard keyboard;

	public Script(){
		manifest = getClass().getAnnotation(ScriptManifest.class);
	}

	public Script init (Runner runner, APIManager api) {
		this.api = api;
		mouse = new Mouse(api);
		keyboard = new Keyboard(api);
		screen = new Screen(api);
		this.runner = runner;
		return this;
	}
	public boolean doStart(){
		return true;
	}
	public void doStop(){}
	public abstract boolean loop();
	public void paint(Graphics graphics){}

	public void log(Object str){
		runner.getMyClient().log(manifest.Name(), str);
	}
	public void log(Object str, Color textCol){
		runner.getMyClient().log(manifest.Name(), str, textCol);
	}
	public void log(Object str, Color textCol, Color backCol){
		runner.getMyClient().log(manifest.Name(), str, textCol, backCol);
	}
	protected static final boolean isPaused() {
		return isPaused ;
	}
	protected static void setPaused(boolean bool){
		isPaused = bool;
	}

	public final void stop() {
		runner.interrupt();
	}
	public ScriptManifest getAnnotaion(){
		return manifest;
	}
	public JPanel customPop(){
		return null;
	}

	protected APIManager getAPIManager() {
		return api;
	}
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
		}catch(final InterruptedException e){}
	}

}
