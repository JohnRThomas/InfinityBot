package org.infinitybot.x.api;

import org.infinitybot.x.KeyboardManager;

public class Keyboard {
	private KeyboardManager keys;
	public Keyboard(APIManager manager){
		keys = manager.getKeyboard();
	}
	 
}
