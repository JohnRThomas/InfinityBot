package org.infinity.bot.api;

import org.infinity.bot.api.action.Action;
import org.infinity.bot.api.action.ActionTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActionScript extends Script {

	private final List<Action> actions = new ArrayList<Action>();

	public void submit(final Action... actions) {
		Collections.addAll(this.actions, actions);
	}

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
