package org.infinity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Boot {
	public static void main(final String args[]) throws Exception {	
		try {
			/*final BufferedImage logo = ImageIO.read(new URL("http://forums.infinitybot.org/public/style_images/brave/logo.png"));
			System.out.println("Done loading picture");
			
			new Splash(logo);*/
			
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
					final File curFile = jarLoc[i];
					new File(curFile.getParent()).mkdir();
					curFile.createNewFile();
					new Thread(){
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

					}.start();
				}
			}
			builder.append("\" org.infinity.ui.InfinityGUI");
			Runtime.getRuntime().exec(builder.toString());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
