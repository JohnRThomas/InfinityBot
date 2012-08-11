package org.infinity.bot.api.methods;

import org.infinity.bot.api.APIManager;

import java.awt.image.BufferedImage;

public class Game {

	private final APIManager manager;

	public Game(final APIManager manager){
		this.manager = manager;
	}

	public BufferedImage getImage(){
		return manager.getImage();
	}
}
