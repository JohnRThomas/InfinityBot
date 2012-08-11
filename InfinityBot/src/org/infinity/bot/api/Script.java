package org.infinity.bot.api;

import org.infinity.bot.api.methods.Game;
import org.infinity.bot.api.methods.Keyboard;
import org.infinity.bot.api.methods.Mouse;
import org.infinity.bot.internals.script.Runner;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public abstract class Script {

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

	public Script init(final Runner runner, final APIManager api) {
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

	public void onStop() {
	}

	public abstract int loop();

	public void paint(final Graphics graphics) {
	}

	public void log(final Object str) {
		runner.getMyClient().log(manifest.Name(), str);
	}

	public void log(final Object str, final Color textCol) {
		runner.getMyClient().log(manifest.Name(), str, textCol);
	}

	public void log(final Object str, final Color textCol, final Color backCol) {
		runner.getMyClient().log(manifest.Name(), str, textCol, backCol);
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
