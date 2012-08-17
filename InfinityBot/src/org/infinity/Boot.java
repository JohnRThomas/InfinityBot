package org.infinity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.infinity.bot.internals.utils.Configuration;
import org.infinity.bot.internals.utils.HexImageConstants;
import org.infinity.bot.internals.utils.HexUtil;
import org.infinity.ui.Splash;

public class Boot {
	private static enum OS {
		WINDOWS, MAC, UNIX
	}

	private static OS getOS() {
		final String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("windows")) {
			return OS.WINDOWS;
		} else if (os.contains("mac")) {
			return OS.MAC;
		}
		return OS.UNIX;
	}

	public static void main(final String args[]) throws Exception {
		try {
			final BufferedImage logo = HexUtil.getImageFromHex(HexImageConstants.logo);
			Splash splash = new Splash(logo);

			String location = Boot.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            location = URLDecoder.decode(location, "UTF-8").replaceAll("\\\\", "/");
            final StringBuilder builder = new StringBuilder(64);
            final String flags = "-Xmx512m -Dsun.java2d.d3d=false";
            final StringBuilder jarbuilder = new StringBuilder();
            final OS os = getOS();
            boolean sh = true;
            switch (os) {
                case WINDOWS:
                    sh = false;
                    builder.append("javaw ");
                    builder.append(flags).append(" ");
                break;
                case MAC:
                    builder.append("java ");
                    builder.append(flags).append(" ");
                    builder.append("-Xdock:name=").append("\"");
                    builder.append(Configuration.NAME).append("\" ");
                    builder.append("-Xdock:icon=").append("\"");
                    builder.append(Configuration.Paths.Resources.Images.ICON).append("\" ");
                    builder.append("-Dapple.laf.useScreenMenuBar=true ");
                break;
                case UNIX:
                    builder.append("java ");
                    builder.append(flags).append(" ");
                break;
 
            }
            builder.append("-classpath \"");
            builder.append(location.substring(1)).append("\" ");
            final File home = new File(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "InfinityBot" + File.separator + "Lib");
            final File [] jars = {new File(home + File.separator + "InfinityX.jar"),new File(home + File.separator + "substance.jar"),new File(home + File.separator + "trident.jar"),new File(home + File.separator + "Icons.jar")};
            for (final File jar : jars) {
            	String path = jar.getPath();
            	path = URLDecoder.decode(path, "UTF-8").replaceAll("\\\\", "/");
            	jarbuilder.append(path).append(";");
            	if(!jar.exists()){
            		splash.changeImage(HexUtil.getImageFromHex(HexImageConstants.loadingImages));
            		new File(new File(jar.getParent()).getParent()).mkdir();
            		new File(jar.getParent()).mkdir();
            		jar.createNewFile();
            		Thread run = new Thread(){
            			@Override
            			public void run() {
            				try {
            					URL website = new URL("http://www.infinitybot.org/bot/" + jar.getName());
            					final ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            					final FileOutputStream fos = new FileOutputStream(jar);
            					fos.getChannel().transferFrom(rbc, 0, 1 << 24);
            					rbc.close();
            					fos.close();
            				} catch (MalformedURLException e) {
            					e.printStackTrace();
            				} catch (FileNotFoundException e) {
            					e.printStackTrace();
            				} catch (IOException e) {
            					e.printStackTrace();
            				}			
            			}

            		};
            		run.start();
            		run.join();
            	}
            }
            splash.changeImage(HexUtil.getImageFromHex(HexImageConstants.logo));
            builder.append("-Xbootclasspath/p:\"");
            builder.append(jarbuilder.toString()).append("\" ");
            builder.append("org.infinity.ui.InfinityGUI");
            System.out.println(builder);
            final Runtime runtime = Runtime.getRuntime();
            if (sh) {
                System.out.println("Using unix.");
                runtime.exec(new String[] { "/bin/sh", "-c", builder.toString()});
            } else {
                runtime.exec(builder.toString());
            }
			Thread.sleep(600);
			splash.dispose();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}