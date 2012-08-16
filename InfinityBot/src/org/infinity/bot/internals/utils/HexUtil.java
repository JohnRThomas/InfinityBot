package org.infinity.bot.internals.utils;

import javax.imageio.ImageIO;

import javax.imageio.stream.ImageInputStream;
 
import java.awt.image.BufferedImage;
 
import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
 
import java.net.URL;
 
public class HexUtil {  
	public static BufferedImage getImageFromHex(final String hex) {
        try {
            return ImageIO.read(new ByteArrayInputStream(ByteUtil.getByteArrayFromHexString(hex)));
        } catch (final Exception e) {
            return null;
        }
    }
 
    public static String getImageAsHex(final BufferedImage image) {
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
            String result = "";
            for (final byte b : output.toByteArray()) {
                result += Integer.toString((b & 0xff) + 0x100, 16).substring(1);
            }
            return result;
        } catch (final Exception e) {
            return null;
        }
    }
 
    public static String getImageAsHex(final URL url) {
        try {
            return getImageAsHex(ImageIO.read(url));
        } catch (final Exception e) {
            return null;
        }
    }
 
    public static String getImageAsHex(final String url) {
        try {
            return getImageAsHex(new URL(url));
        } catch (final Exception e) {
            return null;
        }
    }
 
    public static void main(final String[] args) throws Exception {
        final String hex = getImageAsHex("http://www.infinitybot.org/bot/splash.png");
        System.out.print(hex);
//        StringBuilder result = new StringBuilder();
//        
//        for (final byte b : hex.getBytes()) {
//            result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
//        }
//        System.out.println("Encrypted String: " + result);
    }
}
