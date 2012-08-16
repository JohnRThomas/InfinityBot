package org.infinity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.infinity.bot.internals.utils.HexImageConstants;
import org.infinity.bot.internals.utils.HexUtil;
import org.infinity.ui.Splash;

public class Boot {
	public static void main(final String args[]) throws Exception {	
		try {
			final BufferedImage logo = HexUtil.getImageFromHex(HexImageConstants.logo);
			Splash splash = new Splash(logo);
			
			String location = Boot.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			location = location.replace('\\', '/');
			location = location.replace("%20", " ");
			String canvas = new File("CanvasHack.jar").getCanonicalPath();
			canvas = canvas.replace('\\', '/');
			canvas = canvas.replace("%20", " ");
			final StringBuilder builder = new StringBuilder(64);
			builder.append("javaw -cp \"");
			builder.append(location.substring(1)).append("\"");
			builder.append(" -Xmx1024m");
			builder.append(" -Xbootclasspath/p:\"");
			final File baseFolder = new File(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "InfinityBot" + File.separator + "Lib");
			final File [] jarLoc = {new File(baseFolder + File.separator + "InfinityX.jar"),new File(baseFolder + File.separator + "substance.jar"),new File(baseFolder + File.separator + "trident.jar"),new File(baseFolder + File.separator + "Icons.jar")};
			for(int i = 0; i < jarLoc.length; i++){
				builder.append(jarLoc[i]+";");
				if(!jarLoc[i].exists()){
					splash.changeImage(HexUtil.getImageFromHex(HexImageConstants.loadingImages));
					final File curFile = jarLoc[i];
					new File(curFile.getParent()).mkdir();
					curFile.createNewFile();
					Thread run = new Thread(){
						@Override
						public void run() {
							try {
								URL website = new URL("http://www.infinitybot.org/bot/" + curFile.getName());
								final ReadableByteChannel rbc = Channels.newChannel(website.openStream());
								final FileOutputStream fos = new FileOutputStream(curFile);
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
			builder.append("\" org.infinity.ui.InfinityGUI");
			Runtime.getRuntime().exec(builder.toString());
			Thread.sleep(1500);
			splash.dispose();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
