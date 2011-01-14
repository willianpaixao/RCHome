package rchome.java.socket;

import java.net.*;
import java.io.*;

public class Client {

	public static void main (String args[]) throws IOException, ClassNotFoundException {

		Socket s = null;
		try{
			int serverPort = 1123;
			s = new Socket("127.0.0.1", serverPort);
			//DataInputStream in= new DataInputStream(s.getInputStream());
			DataOutputStream out= new DataOutputStream(s.getOutputStream());

			out.writeUTF("l1l2l3");
			out.flush();

		}catch (UnknownHostException e){
			System.out.println("Sock:"+e.getMessage());
		}catch (EOFException e){
			System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){
			System.out.println("IO Client:"+e.getMessage());
		} finally {
			if(s!=null)
				try {
					s.close();
				}
			catch (IOException e){
				System.out.println("close:"+e.getMessage());
			}
		}
	}
}
