package org.infinity.bot;

import java.awt.GameCanvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyboardManager implements KeyListener{
	protected GameCanvas cancan;
	private final Client myClient;
	public KeyboardManager(Client cli) {
		myClient = cli;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		try{
			if(myClient.isInputEnabled())cancan.getSlaveKeyListener().keyPressed(e);
		}catch(Exception ex){}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		try{
			if(myClient.isInputEnabled())cancan.getSlaveKeyListener().keyReleased(e);
		}catch(Exception ex){}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		try{
			if(myClient.isInputEnabled())cancan.getSlaveKeyListener().keyTyped(e);
		}catch(Exception ex){}
	}
}
