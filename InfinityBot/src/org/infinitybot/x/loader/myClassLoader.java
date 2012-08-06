package org.infinitybot.x.loader;

import java.util.HashMap;

public class myClassLoader extends ClassLoader {
	HashMap<String, byte[]> classfiles;

	public  myClassLoader(HashMap<String, byte[]> classfiles) {
		this.classfiles = classfiles;
	}
	public Class<?> loadClass(final String name) {
		Class<?> ret = null;
		try {
			ret = super.findSystemClass(name);
		} catch (ClassNotFoundException e) {
			if (classfiles.containsKey(name)) {
				final byte[] buff = classfiles.get(name);
				ret = defineClass(name, buff, 0, buff.length);
			} else {
				return null;
			}		}
		return ret;
	}
}
