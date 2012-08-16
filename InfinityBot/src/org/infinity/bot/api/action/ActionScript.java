package org.infinity.bot.api.script;

import org.infinity.bot.api.action.Action;
import org.infinity.bot.api.action.ActionTree;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public abstract class ActionScript extends Script {

	private final List<Action> actions = new ArrayList<Action>();

	public void submit(final Action... actions) {
		Collections.addAll(this.actions, actions);
	}

	public abstract boolean setup();

	@Override
	public final int loop() {
		for (final Action action : actions) {
			if (action instanceof ActionTree) {
				((ActionTree) action).run();
			} else {
				if (action.validate()) {
					action.execute();
				}
			}
		}
		return 0;
	}
}
