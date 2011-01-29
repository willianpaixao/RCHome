/* rchome.java.socket.Server.java */
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

package rchome.java.socket;

import rchome.java.serial.*;

import java.io.*;
import java.net.*;

/**
 * 
 * @author Willian Paixao <willian@ufpa.br>
 * @version 0.001
 */
public class Server {

	private static int              port         = 2189;
	private static DataInputStream  in;
	private static Serial           serial;
	private static ServerSocket     listenSocket; 
	private static Socket           socket       = null;

	public static void closeSerialPort() {
		if (serial != null) {
			serial.dispose();
			serial = null;
		}
	}

	public static void closeSocketPort() {
		if (socket != null) {
			try {
				socket.close();
				socket = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getSerialPortName() {
		return serial.getPortName();
	}

	public static int getSocketPort() {
		return Server.port;
	}

	public static void setSocketPort(int port) {
		Server.port = port;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			serial       = new Serial();
			listenSocket = new ServerSocket(port);

			while (true) {
				socket = listenSocket.accept();
				in     = new DataInputStream(socket.getInputStream());

				serial.write(in.readUTF().getBytes("US-ASCII"));

				closeSocketPort();
			}
		} catch (EOFException e) {
			System.out.println("EOF Server:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO Server:" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSerialPort();
		}
	}
}
