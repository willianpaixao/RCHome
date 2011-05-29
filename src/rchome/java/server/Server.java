/* rchome.java.server.Server.java */
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

package rchome.java.server;

import java.io.*;
import java.net.*;

/**
 * 
 * @author Willian Paixao <willian@ufpa.br>
 * @since 0.01
 */
public class Server {

	private static int inPort;
	private static HouseContents contents;
	private static ServerSocket listenSocket;

	/**
	 * It is like a constructor method of this static class. Nobody instantiates
	 * this class, so this method do this job.
	 */
	private static void initServer() {

		contents = new HouseContents("server");

		inPort = Integer.parseInt(contents.getContent("inPort"));
	}

	/**
	 * Main class for the server side. Resposible for instantiate the main
	 * objects and to start all program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		initServer();

		try {
			listenSocket = new ServerSocket(inPort);

			/**
			 * FIXME This loop creates the max threads as possible.
			 * How to find when a request is comming?
			 */
			// Yes, until the rest of your life :)
			while (true)
				new HandlerSocket(listenSocket.accept()).start();

		} catch (EOFException e) {
			HandlerLog.logger.throwing("Server", "main", e);
			HandlerLog.logger.warning("Reach End Of File or File descriptor "
					+ HandlerSerial.tostring());
		} catch (Exception e) {
			HandlerLog.logger.throwing("Server", "main", e);
			HandlerLog.logger.severe("Unknown exception");
		} finally {
			HandlerSerial.closeSerialPort();
		}
	}
}

class HandlerSocket extends Thread {

	private static DataInputStream in;
	private static Socket inSocket;
	private static Socket outSocket;
	private static String recivied;

	public HandlerSocket(Socket s) {

		inSocket = s;
		outSocket = null;
	}

	/**
	 * 
	 */
	public static void closeSocketPort() {
		if (inSocket != null) {
			try {
				inSocket.close();
			} catch (IOException e) {
				HandlerLog.logger.throwing("HandlerSocket", "closeSocketPort",
						e);
			} finally {
				inSocket = null;
			}
		}
		if (outSocket != null) {
			try {
				outSocket.close();
			} catch (IOException e) {
				HandlerLog.logger.throwing("HandlerSocket", "closeSocketPort",
						e);
			} finally {
				outSocket = null;
			}
		}
	}

	public void run() {

		try {
			in = new DataInputStream(inSocket.getInputStream());

			HandlerLog.logger.info("Connection accepted from "
					+ inSocket.getLocalAddress().getHostName() + "("
					+ inSocket.getLocalAddress() + ")");
			/* Here, arrivies the receivied data. */
			recivied = in.readUTF();
			/* For while, we send it direct to serial port. */
			HandlerSerial.write(recivied);

			closeSocketPort();
		} catch (EOFException e) {
			HandlerLog.logger.throwing("HandlerSocket", "run", e);
		} catch (IOException e) {
			HandlerLog.logger.throwing("HandlerSocket", "run", e);
		}

		Thread.currentThread().interrupt();
	}
}
