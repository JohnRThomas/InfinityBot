package org.infinitybot.x.loader;

import java.awt.BorderLayout;

import org.infinitybot.x.Client;

public class DownloadThread extends Thread{
	Client myClient;
	GameLoader loader;

	public DownloadThread(Client cli, GameLoader load){
		myClient = cli;
		loader = load;
	}
	@Override
	public void run(){
		try{
			loader = new GameLoader(myClient);
		}catch(Exception e){
			e.printStackTrace();
		}catch(Error e){
			e.printStackTrace();
		}
		myClient.remove(myClient.getSplash());
		myClient.add(loader.getApplet(),BorderLayout.CENTER);
		myClient.repaint();
		myClient.enableButtons();
		myClient.log("Client", "New Game Loaded!");
		myClient = null;
	}
}
