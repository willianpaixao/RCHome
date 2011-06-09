/* rchome.java.server.HandlerLog.java */
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
import java.util.logging.*;

/**
 * This class implements o logging system.
 * 
 * USAGE: 
 *      import rchome.java.HandlerLog; 
 * 
 * 		// and for send a message:
 * 		HandlerLog.logger.<level>(<message>);
 * 		// e.g:
 * 		HandlerLog.logger.info("Sending data to the board.");
 * 
 * @author Willian Paixao <willian@ufpa.br>
 * @see java.util.logging
 * @since 0.01
 * @version 0.01
 */
public class HandlerLog {

	private static FileHandler handler;
	/**
	 * You can set the debugging level.
	 * USAGE:
	 * 		HandlerLog.level = Level.<level>;
	 * 		//e.g:
	 * 		HandlerLog.level = Level.warning;
	 */
	public static Level  level = Level.FINER;
	/**
	 * logger is the core of this class. You should use statically.
	 * See the Logger class API for more informations.
	 */
	public static Logger logger;
	/**
	 * You can set where the log file will be wrote.
	 */
	public static String logFile = "%t/rchome.%u.log";
	//public static String logFile = "/var/log/rchome.%u.log";

	/**
	 * Think this static block below like a constructor method.
	 * Here will be initialized the objects and variables.
	 */
	static {
		logger = Logger.getLogger("br.ufpa.rchome");
		logger.setLevel(level);
		logger.setUseParentHandlers(false);
		try {
                        /* If necessary, change these values
                         */
			handler = new FileHandler(logFile, 50000, 1);
		} catch (SecurityException e) {
			System.err.println("Error in HandlerLog class: SecurityException.");
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println("Error in HandlerLog class: IOException.");
			System.err.println(e.getMessage());
		} finally {
			logger.addHandler(handler);
			handler.setFormatter(new SimpleFormatter());

			logger.finer("HandlerLog class loaded sucefully.");
		}
	}
}
