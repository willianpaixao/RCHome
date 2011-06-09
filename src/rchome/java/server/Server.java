/* rchome.java.server.Server.java */
/*
 * RCHome - For more modern homes
 * 
 * Copyright (C) 2011 Monica Nelly   <monica.araujo@itec.ufpa.br>
 * Copyright (C) 2011 Willian Paixao <willian@ufpa.br>
 *
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
import java.net.*;

/**
 * The server side of RCHome listen on <code>inPort</code>.
 * This class is the main class of server side because it handle with other
 * server side classes.
 * 
 * @author Willian Paixao <willian@ufpa.br>
 * @since 0.01
 * @version 0.01
 */
public class Server {

	private static int              inPort;
	private static DataInputStream  in;
	private static HouseContents    contents;
        /* The scheduler feature isn't supported by client side, yet.
         * So, the instance here is unnecessary.
         */
        //private static Scheduler        scheduler;
        private static ServerSocket     listenSocket;
	private static Socket           inSocket;
	private static Socket           outSocket;
	private static String           recivied;

	/**
	 * Close the sockets properly.
	 * 
	 * @exception IOException Handle with errors in I/O
	 */
	public static void closeSocketPort() {
		if(inSocket != null) {
			try {
				inSocket.close();
			} catch(IOException e) {
				HandlerLog.logger.throwing("Server", "closeSocketPort", e);
				HandlerLog.logger.severe("Can't close " + inSocket.toString());
			} finally {
				inSocket = null;
			}
		} if(outSocket != null) {
			try {
				outSocket.close();
			} catch(IOException e) {
				HandlerLog.logger.throwing("Server", "closeSocketPort", e);
				HandlerLog.logger.severe("Can't close " + outSocket.toString());
			} finally {
				outSocket = null;
			}
		}
	}

	/**
	 * It is like a constructor method of this static class.
	 * Nobody instantiates this class, so this method do this job.
	 */
	private static void initServer() {

		contents  = new HouseContents("server");
		//scheduler = new Scheduler();

		inPort    = Integer.parseInt(contents.getContent("inPort"));
		inSocket  = null;
		outSocket = null;
	}

	/**
	 * Main class for the server side.
	 * Responsible for instantiate the main objects and to start all program.
	 * 
	 * @param args Nothing is implemented yet
	 */
	public static void main(String[] args) {

		initServer();

		try {
			listenSocket = new ServerSocket(inPort);

			//Yes, until the rest of your life :)
			while(true) {
				inSocket = listenSocket.accept();
				in       = new DataInputStream(inSocket.getInputStream());

				//Here, arrives the received data.
				recivied = in.readUTF();
				//For while, we send it direct to serial port.
				HandlerSerial.write(recivied);

				closeSocketPort();
			}
		} catch(EOFException e) {
			HandlerLog.logger.throwing("Server", "main", e);
			HandlerLog.logger.warning("Reach End Of File or File descriptor " + HandlerSerial.tostring());
		} catch(IOException e) {
			HandlerLog.logger.throwing("Server", "main", e);
			HandlerLog.logger.warning("Socket error " + inSocket.toString());
		} catch(Exception e) {
			HandlerLog.logger.throwing("Server", "main", e);
			HandlerLog.logger.severe("Unknown exception");
		} finally {
			HandlerSerial.closeSerialPort();
		}
	}
}

