package org.infinity;

import java.io.File;

public class Boot {

	public static void main(final String args[]) throws Exception {
		try {
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
			if (new File("./lib/substance.jar").exists()){
				// download lib
			} else {
				System.out.println("failed to find substance");
			}
			builder.append("./lib/substance.jar;./lib/trident.jar;./lib/Icons.jar;./lib/InfinityX.jar");
			builder.append("\" org.infinity.ui.InfinityGUI");
			Runtime.getRuntime().exec(builder.toString());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}