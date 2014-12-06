import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import dbmanager.DbManager;

public class Bootstrap{

	public static void main (String[]args){
		try{
			URL[] jars = getJars();
			URLClassLoader ucl = URLClassLoader.newInstance(jars);
			Thread.currentThread().setContextClassLoader(ucl);

			Class startupClass = ucl.loadClass("dbmanager.DbManager");
	    	Object dbmanager =  startupClass.newInstance();
	    	startupClass.getMethod("start", null).invoke(dbmanager, (Object[])null);
		}catch(Throwable t){
			t.printStackTrace();
		}
	}

	private static URL[] getJars() throws MalformedURLException{
		File libDir = new File("lib");

		File[]jars = libDir.listFiles( new JarFileFilter() );

		URL[] ret = new URL[jars.length];
		try{
			for(int i = 0; i < jars.length; i++){
				ret[i] = jars[i].getCanonicalFile().toURL();
				System.out.println("Aggiunto al classpath " + ret[i]);
			}
		}catch(IOException e){}
		return ret;
	}

	private static class JarFileFilter implements FileFilter{

		/** Accetta solo i file che finiscono con estensione jar*/
		public boolean accept(File file){
			return (file.isFile() &&
					file.getName().toLowerCase().endsWith(".jar"));
		}
	}


}