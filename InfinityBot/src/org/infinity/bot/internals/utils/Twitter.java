package org.infinity.bot.internals.utils;
 
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
 
import java.net.URL;
 
import java.util.List;
import java.util.ArrayList;
 
public class Twitter {
 
    private final static String LINK = "https://twitter.com/statuses/user_timeline/infinitybot1.json";
 
    private static String[] messages = null;
 
    private static boolean initialized = false;
 
    public static void init() {
        try {
            final List<String> messages = new ArrayList<String>();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(LINK).openStream()));
            final String line = reader.readLine();
            if (line != null) {
                final String[] feeds = line.split("\"text\":\"");
                for (final String feed : feeds) {
                    if (!feed.contains("\"}")) {
                        continue;
                    }
                    messages.add(feed.split("\"}")[0]);
                }
            }
            Twitter.messages = messages.toArray(new String[messages.size()]);
            initialized = true;
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
 
    public static boolean isInitialized() {
        return initialized;
    }
     
    public static String[] getMessages() {
        if (!initialized) {
            init();
        }
        return messages;
    }
}
