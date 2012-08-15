package org.infinity.bot.loader;

import java.applet.Applet;
import java.applet.AppletContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.net.URL;
import java.net.JarURLConnection;

import java.util.HashMap;
import java.util.Enumeration;

import java.util.jar.JarFile;
import java.util.jar.JarEntry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.infinity.bot.Client;

/**
 * @author Infinity
 */
public class GameLoader {

	private static final HashMap<String, String> params = new HashMap<String, String>();
	final String baseLink = "http://www.runescape.com/game.ws?j=1";
	private String HTML = null;
	private String URL = null;
	private Applet applet;
	private String link = "";
	private HashMap<String, byte[]> clientfiles;
	private AppletLoaderContext context = new AppletLoaderContext();
	private static Pattern session_pattern = Pattern.compile("src=\"(.*)\" frame");
	private static Pattern archive_pattern = Pattern.compile("archive=(.*) '\\);");
	private static Pattern param_pattern = Pattern.compile("<param name=\"([^\\s]+)\"\\s+value=\"([^>]*)\">");

	public GameLoader(final Client myClient) {
		try {
			parseParams();
			try {
				myClient.setStatus("Setting Game Paramaters");
				final String jarName = context.getParameter("jarName");
				final HashMap<String, Object> files = getJarFiles(context.getCodeBase() + "/" + jarName);
				clientfiles = AES.decryptPack(context.getParameter("0"), context.getParameter("-1"), (InputStream) files.get("inner.pack.gz"));
				myClient.setStatus("Starting Game");
				applet = new RS2Applet(clientfiles);
				applet.setStub(context);
				applet.init();
				applet.start();
			} catch (Exception e) {
				myClient.setStatus("Failed to use exsiting files!)\n(Downloading GamePack");
				downloadFile(URL);
				myClient.setStatus("Setting Game Paramaters");
				final String jarName = context.getParameter("jarName");
				final HashMap<String, Object> files = getJarFiles(context.getCodeBase() + "/" + jarName);
				clientfiles = AES.decryptPack(context.getParameter("0"), context.getParameter("-1"), (InputStream) files.get("inner.pack.gz"));
				myClient.setStatus("Starting Game");
				applet = new RS2Applet(clientfiles);
				applet.setStub(context);
				applet.init();
				applet.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
	}

	public void restart() {
		applet = new RS2Applet(clientfiles);
		applet.setStub(context);
		applet.init();
		applet.start();
	}

	public void appletResize(final int width, final int height) {
	}

	public final URL getCodeBase() {
		try {
			return new URL(link);
		} catch (Exception e) {
			return null;
		}
	}

	public final URL getDocumentBase() {
		try {
			return new URL(link);
		} catch (Exception e) {
			return null;
		}
	}

	public final String getParameter(final String name) {
		return params.get(name);
	}

	public final AppletContext getAppletContext() {
		return null;
	}

	private String getContent(final String lnk) {
		try {
			final URL url = new URL(lnk);
			final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String myparams = null;
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				myparams += inputLine;
			}
			in.close();
			return myparams;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getUrl(final String link) throws Exception {
		return link + ext("archive=", " ", HTML);
	}

	private void downloadFile(final String url) {
		try {
			final BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
			final FileOutputStream fos = new FileOutputStream("./lib/runescape.jar");
			final BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			byte[] data = new byte[1024];
			int x;
			while ((x = in.read(data, 0, 1024)) >= 0) {
				bout.write(data, 0, x);
			}
			bout.close();
			in.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private String ext(final String from, final String to, final String str1) {
		int p = 0;
		p = str1.indexOf(from, p) + from.length();
		return str1.substring(p, str1.indexOf(to, p));
	}

	private void parseParams() {
		String gamePage;
		try {
			gamePage = getContent(baseLink);
			final Matcher session = session_pattern.matcher(gamePage);
			if (session.find()) {
				final String gameFrameURL = session.group(1).trim();
				context.setDocumentBase(new URL(gameFrameURL.substring(0, gameFrameURL.indexOf("/,"))));
				context.setCodeBase(new URL(gameFrameURL.substring(0, gameFrameURL.indexOf("/,"))));
				final String gameFrame = getContent(gameFrameURL);
				final Matcher archive = archive_pattern.matcher(gameFrame);
				if (archive.find()) {
					final String jar = archive.group(1);
					context.putParameter("jarName", jar);
					final Matcher param = param_pattern.matcher(gameFrame);
					while (param.find()) {
						if (context.getParameter(param.group(1)) == null) {
							context.putParameter(param.group(1), param.group(2));
						}
					}
				}
			}
			link = context.getCodeBase() + "/";
			HTML = getContent(link);
			final Pattern regex = Pattern.compile("<param name=\"?([^\\s]+)\"?\\s+value=\"?([^>]*)\"?>", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
			final Matcher regexMatcher = regex.matcher(HTML);
			while (regexMatcher.find())
				if (!params.containsKey(regexMatcher.group(1))) {
					params.put(remove(regexMatcher.group(1)), remove(regexMatcher.group(2)));
				}
			URL = getUrl(link);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static HashMap<String, Object> getJarFiles(final String file) {
		final HashMap<String, Object> files = new HashMap<String, Object>();
		final HashMap<String, byte[]> classfiles = new HashMap<String, byte[]>();
		try {
			final URL url = new URL("jar:" + file + "!/");
			final JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
			final JarFile fil = jarConnection.getJarFile();
			final Enumeration<JarEntry> entries = fil.entries();
			while (entries.hasMoreElements()) {
				final JarEntry entry = entries.nextElement();
				final InputStream in = new BufferedInputStream(fil.getInputStream(entry));
				if (entry.getName().endsWith(".gz")) {
					files.put(entry.getName(), in);
					continue;
				}
				final byte[] buffer = new byte[in.available()];
				in.close();
				if (entry.getName().endsWith(".class")) {
					classfiles.put(entry.getName().replaceAll("\\.class", ""), buffer);
				} else {
					files.put(entry.getName(), buffer);
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		files.put("classfiles", classfiles);
		return files;
	}

	private String remove(final String str) {
		return str.replaceAll("\"", "");
	}

	public Applet getApplet() {
		return applet;
	}
	public void destroy(){
		applet.stop();
		applet.destroy();
	}
}