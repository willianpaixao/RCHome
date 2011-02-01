/* rchome.java.HandlerLog */
/*
 * RCHome - For more moderns homes
 * 
 * Copyright (C) 2011 Mônica Nelly   <monica.araujo@itec.ufpa.br>
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

import java.io.IOException;
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
 * @author Willian Paixao
 * @exception SecurityException Attempts for permissions of log file.
 * @exception IOException       
 * @see java.util.logging
 */
public class HandlerLog {

	private static FileHandler handler;
	public static Level        level = Level.FINE;
	public static Logger       logger;
	public static String       logFile = "%t/rchome%u.log";
	
	static {
		logger = Logger.getLogger("br.ufpa.rchome");
		logger.setLevel(level);
		logger.setUseParentHandlers(false);

		try {
			handler = new FileHandler(logFile, 50000, 1);
		} catch (SecurityException e) {
			System.err.println("Error in HandlerLog class: SecurityException.");
		} catch (IOException e) {
			System.err.println("Error in HandlerLog class: IOException.");
		}

		logger.addHandler(handler);
		handler.setFormatter(new SimpleFormatter());
	}

	public String getLogFile() {
		return logFile.toString();
	}

	public void setLogFile(String arg) {
		logFile = arg;
	}
}
