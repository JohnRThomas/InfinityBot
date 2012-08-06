package org.infinitybot.x.api;

import java.awt.image.BufferedImage;

import org.infinitybot.x.Client;
import org.infinitybot.x.KeyboardManager;
import org.infinitybot.x.MouseManager;

public class APIManager {
	private Client myClient;
	
	public APIManager(Client myClient){
		this.myClient = myClient;
	}
	public MouseManager getMouse(){
		return myClient.getMouse();
	}
	public KeyboardManager getKeyboard(){
		return myClient.getKeyboard();
	}
	public BufferedImage getImage(){
		return myClient.getImage();
		
	}
}
