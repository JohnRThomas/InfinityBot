package org.infinitybot.x.scripts;

import java.net.URL;
import java.net.URLClassLoader;

public class FileLoader extends URLClassLoader{
	public FileLoader(URL[] urls, ClassLoader classLoader){
		super(urls,classLoader);
	}

	public Class<?> loadClass(String name, boolean resolve)throws ClassNotFoundException {
		String className = name;
		return super.loadClass(className, resolve);
	}
	public void addURL(URL url){
		super.addURL(url);
	}
	public Class<?> makeClass(String name, byte[] bytes, int off,int len){
		return super.defineClass(name, bytes, off, len);
	}
}
