package org.infinity.bot.api.utils;

public class Random {

	public static int nextInt(final int min, final int max) {
		if (min == max) {
			return min;
		} else {
			if (min > max) {
				throw new IllegalArgumentException("max <= min");
			}
			return min + (int) (Math.random() * ((max - min) + 1));
		}
	}
}
