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

	private static int              inPort;
	private static int              outPort;
	private static DataInputStream  in;
	private static HouseContents    contents;
	private static Serial           serial;
	private static ServerSocket     listenSocket;
	private static Socket           inSocket;
	private static Socket           outSocket;
	private static String           recivied;

	public static void closeSerialPort() {
		if(serial != null) {
			serial.dispose();
			serial = null;
		}
	}

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

	private static void initServer() {

		contents  = new HouseContents("server");

		inPort    = Integer.parseInt(contents.getContent("inPort"));
		outPort   = Integer.parseInt(contents.getContent("outPort"));
		inSocket  = null;
		outSocket = null;

	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		initServer();

		try {
			serial       = new Serial();
			listenSocket = new ServerSocket(inPort);

			while(true) {
				inSocket  = listenSocket.accept();
				in        = new DataInputStream(inSocket.getInputStream());

				recivied = in.readUTF();
				serial.write(recivied.getBytes("US-ASCII"));

				closeSocketPort();
			}
		} catch(EOFException e) {
			HandlerLog.logger.throwing("Server", "main", e);
			HandlerLog.logger.warning("Reach End Of File or File descriptor " + serial.toString());
		} catch(IOException e) {
			HandlerLog.logger.throwing("Server", "main", e);
			HandlerLog.logger.warning("Socket error " + inSocket.toString());
		} catch(Exception e) {
			HandlerLog.logger.throwing("Server", "main", e);
			HandlerLog.logger.severe("Unknown exception");
		} finally {
			closeSerialPort();
		}
	}
}
