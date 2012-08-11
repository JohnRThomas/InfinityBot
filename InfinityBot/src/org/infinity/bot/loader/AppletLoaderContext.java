package org.infinity.bot.loader;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * User: Oliver
 * Date: 15/11/11
 * Time: 21:17
 */
public class AppletLoaderContext implements AppletStub, AppletContext, Enumeration<Applet> {

    public static boolean ALLOW_SHOW_DOCUMENT = false;

    private int nextElementCalled;

    private HashMap<String, InputStream> appletStreams;

    private final HashMap<String, String> parameters;

    private URL codeBase;

    private URL documentBase;

    public AppletLoaderContext() {
        nextElementCalled = 0;
        appletStreams = new HashMap<String, InputStream>();
        this.parameters = new HashMap<String, String>();
    }

    public void appletResize(int width, int height) {
    }

    public AppletContext getAppletContext() {
        return this;
    }

    public URL getCodeBase() {
        return codeBase;
    }

    public void setCodeBase(URL codeBase) {
        this.codeBase = codeBase;
    }

    public URL getDocumentBase() {
        return documentBase;
    }

    public void setDocumentBase(URL documentBase) {
        this.documentBase = documentBase;
    }

    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    public void putParameter(String key, String value) {
        this.parameters.put(key, value);
    }

    public boolean isActive() {
        return true;
    }

    public java.applet.AudioClip getAudioClip(URL url) {
        return new AudioClip(url);
    }

    public Image getImage(URL url) {
        return Toolkit.getDefaultToolkit().createImage(url);
    }

    public Applet getApplet(String name) {
        return null;
    }

    public Enumeration<Applet> getApplets() {
        return this;
    }

    public void showDocument(URL url) {
        if (ALLOW_SHOW_DOCUMENT && Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(url.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void showDocument(URL url, String target) {
        showDocument(url);
    }

    public void showStatus(String status) {

    }

    public void setStream(String key, InputStream stream) throws IOException {
        appletStreams.put(key, stream);
    }

    public InputStream getStream(String key) {
        return appletStreams.get(key);
    }

    public Iterator<String> getStreamKeys() {
        return appletStreams.keySet().iterator();
    }

    public boolean hasMoreElements() {
        return (nextElementCalled == 0);
    }

    public Applet nextElement() throws NoSuchElementException {
        nextElementCalled++;
        if (nextElementCalled != 1)
            throw new NoSuchElementException();
        return null;
    }

	public HashMap<String, String> getParameters() {
		return parameters;
	}
}

class AudioClip implements java.applet.AudioClip {

    public static final short STATE_STOPPED = 0;

    public static final short STATE_PLAYING = 1;

    public static final short STATE_LOOPING = 2;

    private URL sourceURL;

    private short audioClipState;

    public AudioClip(URL sourceURL) {
        this.sourceURL = sourceURL;
        audioClipState = STATE_STOPPED;
    }

    public short getAudioClipState() {
        return audioClipState;
    }

    public URL getURL() {
        return sourceURL;
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof AudioClip))
            return false;
        AudioClip ac = (AudioClip) obj;
        return ac.getAudioClipState() == audioClipState
                && ac.getURL().equals(sourceURL);
    }

    public void play() {
        audioClipState = STATE_PLAYING;
    }

    public void loop() {
        audioClipState = STATE_LOOPING;
    }

    public void stop() {
        audioClipState = STATE_STOPPED;
    }

}