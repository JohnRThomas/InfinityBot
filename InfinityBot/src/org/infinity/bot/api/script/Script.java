package org.infinity.bot.api.script;

import org.infinity.bot.api.APIManager;
import org.infinity.bot.api.methods.Game;
import org.infinity.bot.api.methods.Keyboard;
import org.infinity.bot.api.methods.Mouse;
import org.infinity.bot.scriptloader.Runner;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public abstract class Script{

	private static boolean isPaused = false;
	private final ScriptManifest manifest;
	private Runner runner = null;
	private APIManager api;
	public Mouse mouse;
	public Game screen;
	public Keyboard keyboard;

	public Script() {
		manifest = getClass().getAnnotation(ScriptManifest.class);
	}

	public final Script init(final Runner runner, final APIManager api) {
		this.api = api;
		mouse = new Mouse(api);
		keyboard = new Keyboard(api);
		screen = new Game(api);
		this.runner = runner;
		return this;
	}

	public boolean onStart() {
		return true;
	}

	public void onStop(){
	}

	public abstract int loop();

	public void paint(final Graphics graphics) {
	}

	public final void log(final Object str) {
		runner.getClient().log(manifest.Name(), str);
	}

	public final void log(final Object str, final Color textCol) {
		runner.getClient().log(manifest.Name(), str, textCol);
	}

	public final void log(final Object str, final Color textCol, final Color backCol) {
		runner.getClient().log(manifest.Name(), str, textCol, backCol);
	}

	protected final boolean isPaused() {
		return isPaused;
	}

	protected final void setPaused(final boolean paused) {
		isPaused = paused;
	}

	public final void stop() {
		runner.interrupt();
	}

	public ScriptManifest getAnnotaion() {
		return manifest;
	}

	public JPanel customPop() {
		return null;
	}

	protected APIManager getAPIManager() {
		return api;
	}
}
