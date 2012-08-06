package org.infinitybot.x.scripts;

import org.infinitybot.x.Client;
import org.infinitybot.x.api.Script;

public class Runner extends Thread{
	Script curScript;
	private Client myClient;
	private boolean isPaused = false;
	
	public Runner(Script curScript, Client cli){
		this.curScript = curScript;
		setMyClient(cli);
	}
	public void run() {
		if(curScript.doStart()){
			while(!isInterrupted()&& (isPaused  || curScript.loop())){}
			curScript.doStop();
		}else{
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
