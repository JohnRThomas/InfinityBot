package org.infinity.bot.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ScriptManifest {

	String Name();

	double Version();

	String[] Authors();

	Categories Category();

	String Description();

	public enum Categories {
		AGILITY("Agility"),
		COMBAT("Combat"),
		CONSTRUCTION("Construction"),
		COOKING("Cooking"),
		CRAFTING("Crafting"),
		DUNGEONEERING("Dungeoneering"),
		FARMING("Farming"),
		FIREMAKING("Firemaking"),
		FISHING("Fishing"),
		FLETCHING("Fletching"),
		HERBLORE("Herblore"),
		HUNTER("Hunter"),
		MAGIC("Magic"),
		MINIGAME("Minigame"),
		MINING("Mining"),
		MONEYMAKING("Money Making"),
		OTHER("Other"),
		PRAYER("Prayer"),
		RANGED("Ranged"),
		RUNECRAFTING("Runecrafting"),
		SLAYER("Slayer"),
		SMITHING("Smithing"),
		SUMMONING("Summoning"),
		THIEVING("Thieving"),
		WOODCUTTING("Woodcutting");

		private final String Category;

		private Categories(final String Category) {
			this.Category = Category;
		}

		public String getCategory() {
			return Category;
		}
	}
}
