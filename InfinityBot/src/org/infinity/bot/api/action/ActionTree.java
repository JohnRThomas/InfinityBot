package org.infinity.bot.api.action;

public abstract class ActionTree extends Action implements Runnable {

	public abstract Action getNextAction();

	@Override
	public final void run() {
		if (validate()) {
			if (execute()) {
				final Action next = getNextAction();
				if (next != null && next.validate()) {
					if (next instanceof ActionTree) {
						((ActionTree) next).run();
					} else {
						next.execute();
					}
				}
			}
		}
	}
}
