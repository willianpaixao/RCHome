/*
 * 
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

	//private static DataOutputStream out;
	private static DataInputStream  in;
	private static Serial           serial;
	private static ServerSocket     listenSocket; 
	private static Socket           socket;
	private static int              port;

	/**
	 * 
	 * @param incomming
	 */
	public static void writeSerial (String incomming) {
		serial.write(incomming);
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			port   = 1123;
			serial = new Serial();
			socket = null;
			listenSocket = new ServerSocket(port);

			while(true) {
				socket = listenSocket.accept();
				in = new DataInputStream(socket.getInputStream());
				//out = new DataOutputStream(clientSocket.getOutputStream());

				writeSerial(in.readUTF());
			}
		} catch(EOFException e) {
			System.out.println("EOF Server:"+e.getMessage());
		} catch(IOException e) {
			System.out.println("IO Server:"+e.getMessage());
		} catch (SerialException e) {
			e.printStackTrace();
		}
	}
}
