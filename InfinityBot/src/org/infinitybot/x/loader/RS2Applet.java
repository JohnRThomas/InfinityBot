package org.infinitybot.x.loader;

import java.applet.Applet;
import java.awt.Graphics2D;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public final class RS2Applet extends Applet {
	private static final long serialVersionUID = 1L;

	private Object mainClassInstance = null;
	private Class<?> mainClass = null;
	private HashMap<String, byte[]> clientfiles;
	public RS2Applet(HashMap<String, byte[]> clientfiles){
		this.clientfiles = clientfiles;
	}
	public final void destroy() {
		if (this.mainClassInstance != null)
			this.invokeMethod(null, null, "destroy");
		else
			System.out.print("Destroy while mainclass null");


	}

	private final void invokeMethod(Object[] arg0, Class<?>[] arg2, String arg3) {
		try {
			Method var14 = this.mainClass.getMethod(arg3, arg2);
			var14.invoke(this.mainClassInstance, arg0);
		} catch (NoSuchMethodException var6) {
			var6.printStackTrace();
		} catch (IllegalAccessException var7) {
			var7.printStackTrace();
		} catch (InvocationTargetException var8) {
			var8.printStackTrace();
		}
	}

	public final void update(Graphics2D arg0) {
		if (this.mainClassInstance != null)
			this.invokeMethod(new Object[]{arg0}, new Class[]{Graphics2D.class}, "update");
		else
			System.out.print("update while mainclass null");


	}

	private final void throwException(Throwable arg1) {
		System.out.print("Client error called");
		arg1.printStackTrace();
	}

	public final void stop() {
		if (this.mainClassInstance != null)
			this.invokeMethod(null, null, "stop");
		else
			System.out.print("stop while mainclass null");
	}

	public final void start() {
		if (this.mainClassInstance != null)
			this.invokeMethod(null, null, "start");
		else
			System.out.print("start while mainclass null");
	}

	public final void paint(Graphics2D arg0) {
		if (this.mainClassInstance != null)
			this.invokeMethod(new Object[]{arg0}, new Class[]{Graphics2D.class}, "paint");
		else
			System.out.print("paint while mainclass null");
	}

	public final void init() {
		try {
			myClassLoader loader = new myClassLoader(clientfiles);
			this.mainClass = loader.loadClass("client");
		} catch (Exception e) {
			e.printStackTrace();
		}

//		if (this.mainClass == null) {
//			this.throwException(new RuntimeException());
//			System.exit(1);
//		}

		try {
			Constructor<?> constructor = this.mainClass.getConstructor((Class[]) null);
			this.mainClassInstance = constructor.newInstance((Object[]) null);
		} catch (NoSuchMethodException var25) {
			this.throwException(var25);
		} catch (InvocationTargetException var26) {
			this.throwException(var26);
		} catch (IllegalAccessException var27) {
			this.throwException(var27);
		} catch (InstantiationException var28) {
			this.throwException(var28);
		}


		this.invokeMethod(new Object[]{this}, new Class[]{Applet.class}, "supplyApplet");
		this.invokeMethod(null, null, "init");
	}

}

