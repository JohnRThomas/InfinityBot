package org.infinity.bot.api;

import org.infinity.bot.Client;
import org.infinity.bot.KeyboardManager;
import org.infinity.bot.MouseManager;

import java.awt.image.BufferedImage;

public class APIManager {

	private final Client myClient;

	public APIManager(final Client myClient) {
		this.myClient = myClient;
	}

	public MouseManager getMouse() {
		return myClient.getMouse();
	}

	public KeyboardManager getKeyboard() {
		return myClient.getKeyboard();
	}

	public BufferedImage getImage() {
		return myClient.getImage();
	}
}
