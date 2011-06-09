/* rchome.java.common.HouseContents.java */
/*
 * RCHome - For more modern homes
 * 
 * Copyright (C) 2011 Monica Nelly   <monica.araujo@itec.ufpa.br>
 * Copyright (C) 2011 Willian Paixao <willian@ufpa.br>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package rchome.java.server;

import java.io.*;
import java.util.*;

/**
 * Handling the property file.
 * Reads by default rchome.properties in <code>~/.rchome</code> directory.
 * Try to handling exceptions, like create a new file if it doesn't 
 * exist. Yours get and set methods, are used by other classes
 * for handling configuration.
 * 
 * @author Willian Paixao <willian@ufpa.br>
 * @see java.util.logging
 * @since 0.01
 * @version 0.01
 */
public class HouseContents {

	private File             file;
	private FileInputStream  fis      = null;
	private Properties       contents;
	private String           filePath = "../lib/properties/";
	//private String           filePath = System.getProperty("user.home") + "/.rchome/";

	/**
	 * Constructor method.
	 * Just initialize the objects and variables.
	 * 
	 * @exception FileNotFoundException If the property file doesn't exist.
	 * @exception IOException           Troubles with read/write permission.
	 */
	public HouseContents() {

		try {
			file     = new File(filePath + "rchome.properties");
			fis      = new FileInputStream(file);
			contents = new Properties();

			contents.load(fis);
		} catch (FileNotFoundException e) {
			try {
				if ((new File(filePath)).mkdir())
					file.createNewFile();
			} catch (IOException f) {
				HandlerLog.logger.throwing("HouseContents", "constructor", e);
				HandlerLog.logger.severe("Can't creat " + filePath);
			} finally {
				HandlerLog.logger.info("Creating a new file: " + filePath);
			}
		} catch (IOException e) {
			HandlerLog.logger.throwing("HouseContents", "constructor", e);
			HandlerLog.logger.severe("Can't read/write in " + filePath);
		}
	}

	/**
	 * Constructor method.
	 * Just initialize the objects and variables.
	 * 
	 * @exception FileNotFoundException If the property file doesn't exist.
	 * @exception IOException           Troubles with read/write permission.
	 */
	public HouseContents(String fileName) {

		try {
			fileName = filePath + fileName + ".properties";

			file     = new File(fileName);
			fis      = new FileInputStream(file);
			contents = new Properties();

			contents.load(fis);
		} catch (FileNotFoundException e) {
			try {
				if ((new File(filePath)).mkdir())
					file.createNewFile();
			} catch (IOException f) {
				HandlerLog.logger.throwing("HouseContents", "constructor", e);
				HandlerLog.logger.severe("Can't creat " + filePath);
			} finally {
				HandlerLog.logger.info("Creating a new file: " + filePath);
			}
		} catch (IOException e) {
			HandlerLog.logger.throwing("HouseContents", "constructor", e);
			HandlerLog.logger.severe("Can't read/write in " + filePath);
		}
	}

	/**
	 * Returns value of a key.
	 * 
	 * @param key What you want.
	 * @return Value of such key
	 */
	public String getContent(String key) {
		return contents.getProperty(key);
	}

	/**
	 * Set a new value for the key.
	 * 
	 * @return true if changed, false otherwise.
	 * @param key What you want to change.
	 * @param value New value.
	 */
	public boolean setContent(String key, String value) {

		if(contents.setProperty(key, value) == null)
			return (false);
		else
			return (true);
	}
}

