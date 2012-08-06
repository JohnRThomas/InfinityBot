package org.infinitybot.x.api;

import java.awt.image.BufferedImage;


public class Screen {
	APIManager manager;
	public Screen(APIManager manager){
		this.manager = manager;
	}
	public BufferedImage getImage(){
		return manager.getImage();
	}
}
