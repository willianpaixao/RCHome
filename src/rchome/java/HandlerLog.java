/* rchome.java.HandlerLog */
package rchome.java;

import java.io.IOException;
import java.util.logging.*;

/**
 * This class implements o logging system for debug softmodem.
 * 
 * USAGE: 
 *              rchome.java.HandlerLog; 
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

	public static FileHandler handler;
	public static Logger      logger;
	
	static {
		logger = Logger.getLogger("br.ufpa.rchome");
		logger.setLevel(Level.FINE);
		logger.setUseParentHandlers(false);

		try {
			//handler = new FileHandler("/var/log/rchome.log", 50000, 1);
			handler = new FileHandler("%t/rchome%u.log", 50000, 1);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.throwing("HandlerLog", "HandlerLog", e);
		}

		logger.addHandler(handler);
		handler.setFormatter(new SimpleFormatter());
	}
}
