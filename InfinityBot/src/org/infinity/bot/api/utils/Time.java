package org.infinity.bot.api.utils;

public class Time {

	public abstract class Condition {

		private final Condition self = this;

		public abstract boolean validate();

		public boolean sleep(final int millis) {
			final long start = System.currentTimeMillis();
			while (System.currentTimeMillis() - start < millis) {
				if (validate()) {
					return true;
				}
				Time.sleep(5, 10);
			}
			return false;
		}

		public Condition inverse() {
			return new Condition() {
				public boolean validate() {
					return !self.validate();
				}
			};
		}
	}

	public static void sleep(final int millis) {
		try {
			Thread.sleep(millis);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void sleep(final int min, final int max) {
		sleep(Random.nextInt(min, max));
	}

	public static boolean sleep(final int millis, final Condition... conditions) {
		for (final Condition condition : conditions) {
			if (!condition.sleep(millis)) {
				return false;
			}
		}
		return true;
	}
}
