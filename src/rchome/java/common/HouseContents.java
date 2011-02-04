/* rchome.java.common.HouseContents.java */
/*
 * RCHome - For more moderns homes
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

package rchome.java.common;

import java.io.*;
import java.util.*;

import rchome.java.server.HandlerLog;

/**
 * Handling the property file.
 * Reads by default rchome.properties in ~/.rchome directory.
 * Try to handling exceptions, like create a new file if it doesn't 
 * exist. Yours get and set methods, are used by other classes
 * for handling configuration.
 * 
 * @author Willian Paixao <willian@ufpa.br>
 * @since 0.01
 */
public class HouseContents {

	private File             file;
	private FileInputStream  fis      = null;
	private Properties       contents;
	private String           filePath = System.getProperty("user.home")
										+ "/.rchome/" + "rchome.properties";

	/**
	 * Constructor method.
	 * Just initialize the objects and variables.
	 * 
	 * @exception FileNotFoundException If the property file doesn't exist.
	 * @exception IOException           Troubles with read/write permission.
	 */
	public HouseContents() {
		try {
			file     = new File(filePath);
			fis      = new FileInputStream(file);
			contents = new Properties();
			contents.load(fis);
		} catch (FileNotFoundException e) {
			try {
				if ((new File(System.getProperty("user.home") + "/.rchome")).mkdir())
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
	 * @param filePath Full path and file name of property file.
	 * @exception FileNotFoundException If the property file doesn't exist.
	 * @exception IOException           Troubles with read/write permission.
	 */
	public HouseContents(String filePath) {
		try {
			file     = new File(filePath);
			fis      = new FileInputStream(file);
			contents = new Properties();
			contents.load(fis);
		} catch (FileNotFoundException e) {
			try {
				file.createNewFile();
			} catch (IOException f) {
				HandlerLog.logger.throwing("HouseContents", "constructor", e);
				HandlerLog.logger.severe("Can't creat " + filePath);
			} finally {
				HandlerLog.logger.warning("Creating a new file: " + filePath);
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
	 * @param key What you want to change.
	 * @param value New value.
	 */
	public void setContent(String key, String value) {
		contents.setProperty(key, value);
	}
}
