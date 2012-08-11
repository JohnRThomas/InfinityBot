package org.infinity.bot.internals.script;

import org.infinity.bot.Client;

import org.infinity.bot.api.Script;

import org.infinity.bot.api.utils.Time;

public class Runner extends Thread {

	private final Script curScript;
	private Client myClient;

	private boolean isPaused = false;
	
	public Runner(final Script curScript, final Client cli) {
		this.curScript = curScript;
		setMyClient(cli);
	}

	@Override
	public void run() {
		if (curScript.onStart()) {
			while (!isInterrupted()) {
				if (isPaused) {
					Time.sleep(5, 10);
					continue;
				}
				final int sleep = curScript.loop();
				if (sleep < 0) {
					break;
				}
				Time.sleep(sleep);
			}
			curScript.onStop();
		} else {
			getMyClient().log("ScriptLoader", "Script bloked itself from starting!");
		}
	}

	public void setPause(boolean pause) {
		isPaused = pause;
	}

	public boolean isPaused(){
		return isPaused;
	}

	public void setMyClient(Client myClient) {
		this.myClient = myClient;
	}

	public Client getMyClient() {
		return myClient;
	}
}
