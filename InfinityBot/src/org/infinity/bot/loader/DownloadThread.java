package org.infinity.bot.loader;

import java.awt.BorderLayout;

import org.infinity.bot.Client;

public class DownloadThread extends Thread {

	private Client myClient;
	private GameLoader loader;

	public DownloadThread(final Client cli, final GameLoader load) {
		myClient = cli;
		loader = load;
	}

	@Override
	public void run() {
		try {
			loader = new GameLoader(myClient);
		} catch (final Exception e) {
			e.printStackTrace();
		} catch (final Error e) {
			e.printStackTrace();
		}
		myClient.remove(myClient.getSplash());
		myClient.add(loader.getApplet(), BorderLayout.CENTER);
		myClient.repaint();
		myClient.enableButtons();
		myClient.log("Client", "New Game Loaded!");
		myClient = null;
	}
}
