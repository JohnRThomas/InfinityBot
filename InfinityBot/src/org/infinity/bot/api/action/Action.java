package org.infinity.bot.api.action;

public abstract class Action {

	public boolean validate() {
		return true;
	}

	public abstract boolean execute();
}
