package org.infinitybot.x.loader;

import java.applet.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.*;

import org.infinitybot.x.Client;

/**
 * @author Infinity
 */
public class GameLoader {
	private static final HashMap<String, String> params = new HashMap<String, String>();
	final String baseLink = "http://www.runescape.com/game.ws?j=1";
	String HTML = null;
	String URL = null;
	Applet loader;
	private String link = "";
	private HashMap<String, byte[]> clientfiles;
	AppletLoaderContext context = new AppletLoaderContext();
	private static Pattern session_pattern = Pattern.compile("src=\"(.*)\" frame");
	private static Pattern archive_pattern = Pattern.compile("archive=(.*) '\\);");
	private static Pattern param_pattern = Pattern.compile("<param name=\"([^\\s]+)\"\\s+value=\"([^>]*)\">");

	public GameLoader(Client myClient) {
		try {
			parseParams();
			try{
				myClient.setStatus("Setting Game Paramaters");
				final String jarName = context.getParameter("jarName");
				final HashMap<String, Object> files = getJarFiles(context
						.getCodeBase() + "/" + jarName);
				clientfiles = AES.decryptPack(context.getParameter("0"), context.getParameter("-1"),
						(InputStream) files.get("inner.pack.gz"));
				myClient.setStatus("Starting Game");
				loader = new RS2Applet(clientfiles);
				loader.setStub(context);
				loader.init();
				loader.start();
			}catch(Exception e){
				myClient.setStatus("Failed to use exsiting files!)\n(Downloading GamePack");
				downloadFile(URL);
				myClient.setStatus("Setting Game Paramaters");
				final String jarName = context.getParameter("jarName");
				final HashMap<String, Object> files = getJarFiles(context
						.getCodeBase() + "/" + jarName);
				clientfiles = AES.decryptPack(context.getParameter("0"), context.getParameter("-1"),
						(InputStream) files.get("inner.pack.gz"));
				myClient.setStatus("Starting Game");
				loader = new RS2Applet(clientfiles);
				loader.setStub(context);
				loader.init();
				loader.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	public void restart(){
		loader = new RS2Applet(clientfiles);
		loader.setStub(context);
		loader.init();
		loader.start();
	}
	public void appletResize(int width, int height) {
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

	public final String getParameter(String name) {
		return params.get(name);
	}

	public final AppletContext getAppletContext() {
		return null;
	}

	String getContent(String lnk) {
		try {
			URL url = new URL(lnk);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String myparams = null;
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				myparams += inputLine;
			}
			in.close();
			return myparams;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	String getUrl(String link) throws Exception {
		return link + ext("archive=", " ", HTML);
	}

	void downloadFile(final String url) {
		try {
			BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
			FileOutputStream fos = new FileOutputStream("./lib/runescape.jar");
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			byte[] data = new byte[1024];
			int x = 0;
			while((x=in.read(data, 0, 1024))>=0) {
				bout.write(data, 0, x);
			}
			bout.close();
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	String ext(String from,String to,String str1) {
		int p = 0;
		p = str1.indexOf(from,p)+from.length();
		return str1.substring(p,str1.indexOf(to,p));
	}

	void parseParams() {
		String gamePage = null;
		try {
			gamePage = getContent(baseLink);
			Matcher session = session_pattern.matcher(gamePage);
			if (session.find()) {
				String gameFrameURL = session.group(1).trim();
				context.setDocumentBase(new URL(gameFrameURL.substring(0, gameFrameURL.indexOf("/,"))));
				context.setCodeBase(new URL(gameFrameURL.substring(0, gameFrameURL.indexOf("/,"))));
				String gameFrame = getContent(gameFrameURL);
				Matcher archive = archive_pattern.matcher(gameFrame);
				if (archive.find()) {
					String jar = archive.group(1);
					context.putParameter("jarName", jar);
					Matcher param = param_pattern.matcher(gameFrame);
					while (param.find())
						if (context.getParameter(param.group(1)) == null) {
							context.putParameter(param.group(1), param.group(2));
						}
				}
			}
			link  = context.getCodeBase() + "/";
			HTML = getContent(link);
			Pattern regex = Pattern.compile("<param name=\"?([^\\s]+)\"?\\s+value=\"?([^>]*)\"?>", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
			Matcher regexMatcher = regex.matcher(HTML);
			while (regexMatcher.find())
				if (!params.containsKey(regexMatcher.group(1))) {
					params.put(remove(regexMatcher.group(1)), remove(regexMatcher.group(2)));
				}
			URL = getUrl(link);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static HashMap<String, Object> getJarFiles(String file) {
		HashMap<String, Object> files = new HashMap<String, Object>();
		HashMap<String, byte[]> classfiles = new HashMap<String, byte[]>();
		try {
			URL url = new URL("jar:" + file + "!/");
			JarURLConnection jarConnection = (JarURLConnection) url.openConnection();

			JarFile fil = jarConnection.getJarFile();
			Enumeration<JarEntry> entries = fil.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();

				InputStream in =
					new BufferedInputStream(fil.getInputStream(entry));
				if(entry.getName().endsWith(".gz")){
					files.put(entry.getName(),in);
					continue;
				}
				byte[] buffer = new byte[in.available()];
				if(entry.getName().endsWith(".class"))
					classfiles.put(entry.getName().replaceAll("\\.class", ""), buffer);
				else
					files.put(entry.getName(), buffer);


			}
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		files.put("classfiles",classfiles);
		return files;
	}
	String remove(String str) {
		return str.replaceAll("\"", "");
	}

	public Applet getApplet(){
		return loader;
	}
}