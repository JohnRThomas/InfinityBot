package org.infinity.bot.internals.utils;

public class ByteUtil {
	 
    public static byte[] getByteArrayFromHexString(final String hex) {
        final byte[] b = new byte[hex.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(hex.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }
}
