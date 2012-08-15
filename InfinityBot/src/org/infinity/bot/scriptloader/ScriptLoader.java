package org.infinity.bot.scriptloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.infinity.bot.Client;

public class ScriptLoader{

	private static Runner curScript;
	private ArrayList<Class<?>> list = new ArrayList<Class<?>>();
	private Client myClient;

	public ScriptLoader(String path, Client cli){
		myClient = cli;
		File [] classes = new File(path).listFiles();
		ArrayList<File> classList = new ArrayList<File>();
		goDeeper(classes, classList);

		for(int i = 0; i < classList.size(); i++){
			if(classList.get(i).getName().endsWith(".jar")||classList.get(i).getName().endsWith(".zip")){
				try {
					ZipFile zip;
					zip = new ZipFile(classList.get(i));
					Enumeration<? extends ZipEntry> counter = zip.entries();
					while(counter.hasMoreElements()){
						ZipEntry entry = counter.nextElement();
						if(entry.getName().endsWith(".class")){
							String name = entry.toString().replace(".class", "").split("/")[entry.toString().replace(".class", "").split("/").length-1];
							try{
								list.add(loadClass(name,zip.getInputStream((entry)),entry.getSize()));
							}catch(NegativeArraySizeException e){
								myClient.log("ScriptLoader", "Script: " + entry.getName() + "is too large!");
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(classList.get(i).getName().endsWith(".class")){
				String name = classList.get(i).getName().replace(".class", "");
				try{
					FileInputStream in = new FileInputStream(classList.get(i));
					list.add(loadClass(name,in,classList.get(i).length()));
					in.close();
				}catch(NegativeArraySizeException e){
					myClient.log("ScriptLoader", "Script: " + classList.get(i).getName() + "is too large!");
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}	

	private Class<?> loadClass(String name, InputStream inp, long length) throws NegativeArraySizeException{
		try {
			byte [] bytes = new byte[(int) length];
			inp.read(bytes);
			FileLoader loady = new FileLoader(new URL[0], ScriptLoader.class.getClassLoader());
			inp.close();
			return loady.makeClass(name, bytes, 0, bytes.length);
		} catch (IOException e) {

		}
		return null;
	}

	private void goDeeper(File[] classes, ArrayList<File> classList) {
		try{
			for(int i = 0; i < classes.length; i++){
				if(classes[i].listFiles() == null){
					if(classes[i].getName().endsWith(".class")|| classes[i].getName().endsWith(".jar")||classes[i].getName().endsWith(".zip")){
						classList.add(classes[i]);
					}
				}else{
					goDeeper(classes[i].listFiles(),classList);
				}
			}	
		}catch(NullPointerException e){
			System.out.println("No Scrtips found!");
		}
	}

	synchronized public void addFileToClasspath(String name, FileLoader load)throws MalformedURLException, ClassNotFoundException {
		File filePath = new File(name);
		URI uriPath = filePath.toURI();
		URL urlPath = uriPath.toURL();
		load.addURL(urlPath);
	}


	public static void stopScript() {
		if(curScript.isAlive())curScript.interrupt();
	}

	public ArrayList<Class<?>> getScripts(){
		return list;
	}

	public void setCurrScript(Runner run){
		curScript = run;
	}
	public Runner gerCurrScript(){
		return curScript;
	}
}
