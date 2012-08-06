package org.infinitybot.x.api;

public class Tools {
	public static int randomInt(int min, int max){
		if (min == max)return min;
		if (min > max) throw new IllegalArgumentException("max <= min");
		return min + (int)(Math.random() * ((max - min) + 1));
	}
}
