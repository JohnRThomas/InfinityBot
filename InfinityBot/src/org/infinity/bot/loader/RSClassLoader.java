package org.infinity.bot.loader;

import java.util.HashMap;

public class RSClassLoader extends ClassLoader {

	private HashMap<String, byte[]> classfiles;

	public RSClassLoader(final HashMap<String, byte[]> classfiles) {
		this.classfiles = classfiles;
	}

	public Class<?> loadClass(final String name) {
		Class<?> ret;
		try {
			ret = super.findSystemClass(name);
		} catch (ClassNotFoundException e) {
			if (classfiles.containsKey(name)) {
				final byte[] buff = classfiles.get(name);
				ret = defineClass(name, buff, 0, buff.length);
			} else {
				return null;
			}
		}
		return ret;
	}
}
