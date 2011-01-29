/* rchome.java.socket.Client.java */
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

import java.net.*;
import java.io.*;

public class Client {

	private static int              serverPort = 2189;
	private static DataOutputStream out;
	private static Socket           s          = null;
	private static String           serverIP   = "127.0.0.1";

	public static void close() {

		if (s != null)
			try {
				s.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public static void send(String args) {

		try {
			s   = new Socket(serverIP, serverPort);
			out = new DataOutputStream(s.getOutputStream());

			out.writeUTF(args);
			out.flush();

			close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
