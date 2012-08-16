package org.infinity.bot.internals.utils;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;

import javax.imageio.stream.ImageInputStream;

import java.awt.image.BufferedImage;

import java.io.*;

import java.net.URL;

public class NetUtil {
	public static void main(String args[]){
		System.out.println(NetUtil.getImageAsBase64("http://forums.infinitybot.org/public/style_images/brave/logo.png"));
	}
	public static String getImageAsBase64(final BufferedImage image) {
		try {
			final File f = File.createTempFile("image", "png");
			ImageIO.write(image, "png", f);
			final ImageInputStream input = ImageIO.createImageInputStream(f);
			final ByteArrayOutputStream output = new ByteArrayOutputStream();
			int read;
			while ((read = input.read()) != -1) {
				output.write(read);
			}
			input.close();
			output.close();
			return Base64.encode(output.toByteArray());
		} catch (final Exception e) {
			return null;
		}
	}

	public static String getImageAsBase64(final URL url) {
		try {
			return getImageAsBase64(ImageIO.read(url));
		} catch (final Exception e) {
			return null;
		}
	}

	public static String getImageAsBase64(final String url) {
		try {
			return getImageAsBase64(new URL(url));
		} catch (final Exception e) {
			return null;
		}
	}

	public static BufferedImage getImageFromBase64(final String base64) {
		try {
			final byte[] decrypted = Base64.decode(base64);
			return ImageIO.read(new ByteArrayInputStream(decrypted));
		} catch (final Exception e) {
			return null;
		}
	}

}
