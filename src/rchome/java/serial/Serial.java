/* rchome.java.serial.Serial.java */
package rchome.java.serial;

import gnu.io.*;
import java.io.*;
import java.util.*;

/*
 * PSerial - class for serial port goodness
 * Part of the Processing project - http://processing.org
 * 
 * Copyright (c) 2004 Ben Fry & Casey Reas
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 */

public class Serial implements SerialPortEventListener {

	private byte         buffer[]    = new byte[32768];
	private int          bufferIndex;
	private int          bufferLast;
	private InputStream  input;
	private OutputStream output;
	private SerialPort   port;
	private String       portName    = "/dev/ttyUSB0";

	public Serial() throws Exception {

		try {
			port = null;
			do {
				CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(port);
				if (portId.getPortType() == 1) {
					if (portId.getName().equals(portName)) {
						port = (SerialPort) portId.open("serial madness", 2000);
						input = port.getInputStream();
						output = port.getOutputStream();
						port.setSerialPortParams(9600, 8, 1, 0);
						port.addEventListener(this);
						port.notifyOnDataAvailable(true);
					}
				}
			} while (port == null);

		} catch (PortInUseException e) {
			throw new Exception("Serial port '" + portName + 
					"' already in use.  Try quiting any programs that may be using it.");
		} catch (Exception e) {
			throw new Exception("Error opening serial port '" + portName + "'.", e);
		}

		if (port == null)
			throw new Exception("Serial port '" + portName + "' not found.");
	}

	public void dispose() {
		try {
			if (input != null) input.close();
			if (output != null) output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		input = null;
		output = null;

		try {
			if (port != null) port.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		port = null;
	}

	synchronized public void serialEvent(SerialPortEvent serialEvent) {
		if (serialEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				while (input.available() > 0) {
					synchronized (buffer) {
						if (bufferLast == buffer.length) {
							byte temp[] = new byte[bufferLast << 1];
							System.arraycopy(buffer, 0, temp, 0, bufferLast);
							buffer = temp;
						}
						System.out.print((char) input.read());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Returns the number of bytes that have been read from serial and are
	 * waiting to be dealt with by the user.
	 */
	public int available() {
		return (bufferLast - bufferIndex);
	}

	/**
	 * Ignore all the bytes read so far and empty the buffer.
	 */
	public void clear() {
		bufferLast = 0;
		bufferIndex = 0;
	}

	/**
	 * Returns a number between 0 and 255 for the next byte that's waiting in
	 * the buffer. Returns -1 if there was no byte (although the user should
	 * first check available() to see if things are ready to avoid this)
	 */
	public int read() {
		if (bufferIndex == bufferLast)
			return -1;

		synchronized (buffer) {
			int outgoing = buffer[bufferIndex++] & 0xff;
			if (bufferIndex == bufferLast) { // rewind
				bufferIndex = 0;
				bufferLast = 0;
			}
			return outgoing;
		}
	}

	/**
	 * Returns the next byte in the buffer as a char. Returns -1, or 0xffff, if
	 * nothing is there.
	 */
	public char readChar() {
		if (bufferIndex == bufferLast)
			return (char) (-1);
		return (char) read();
	}

	/**
	 * Return a byte array of anything that's in the serial buffer. Not
	 * particularly memory/speed efficient, because it creates a byte array on
	 * each read, but it's easier to use than readBytes(byte b[]) (see below).
	 */
	public byte[] readBytes() {
		if (bufferIndex == bufferLast)
			return null;

		synchronized (buffer) {
			int length = bufferLast - bufferIndex;
			byte outgoing[] = new byte[length];
			System.arraycopy(buffer, bufferIndex, outgoing, 0, length);

			bufferIndex = 0; // rewind
			bufferLast = 0;
			return outgoing;
		}
	}

	/**
	 * Grab whatever is in the serial buffer, and stuff it into a byte buffer
	 * passed in by the user. This is more memory/time efficient than
	 * readBytes() returning a byte[] array.
	 * 
	 * Returns an int for how many bytes were read. If more bytes are available
	 * than can fit into the byte array, only those that will fit are read.
	 */
	public int readBytes(byte outgoing[]) {
		if (bufferIndex == bufferLast)
			return 0;

		synchronized (buffer) {
			int length = bufferLast - bufferIndex;
			if (length > outgoing.length)
				length = outgoing.length;
			System.arraycopy(buffer, bufferIndex, outgoing, 0, length);

			bufferIndex += length;
			if (bufferIndex == bufferLast) {
				bufferIndex = 0; // rewind
				bufferLast = 0;
			}
			return length;
		}
	}

	/**
	 * Return whatever has been read from the serial port so far as a String. It
	 * assumes that the incoming characters are ASCII.
	 * 
	 * If you want to move Unicode data, you can first convert the String to a
	 * byte stream in the representation of your choice (i.e. UTF8 or two-byte
	 * Unicode data), and send it as a byte array.
	 */
	public String readString() {
		if (bufferIndex == bufferLast)
			return null;
		return new String(readBytes());
	}

	/**
	 * This will handle both ints, bytes and chars transparently.
	 */
	public void write(int what) { // will also cover char
		try {
			output.write(what & 0xff); // for good measure do the &
			output.flush(); // hmm, not sure if a good idea

		} catch (Exception e) { // null pointer or serial port dead
			e.printStackTrace();
		}
	}

	public void write(byte bytes[]) {
		try {
			output.write(bytes);
			output.flush(); // hmm, not sure if a good idea
		} catch (Exception e) { // null pointer or serial port dead
			e.printStackTrace();
		}
	}

	/**
	 * Write a String to the output. Note that this doesn't account for Unicode
	 * (two bytes per char), nor will it send UTF8 characters.. It assumes that
	 * you mean to send a byte buffer (most often the case for networking and
	 * serial i/o) and will only use the bottom 8 bits of each char in the
	 * string. (Meaning that internally it uses String.getBytes)
	 * 
	 * If you want to move Unicode data, you can first convert the String to a
	 * byte stream in the representation of your choice (i.e. UTF8 or two-byte
	 * Unicode data), and send it as a byte array.
	 */
	public void write(String what) {
		write(what.getBytes());
	}

	static public String[] list() {
		Vector<String> list = new Vector<String>();
		try {
			Enumeration<?> portList = CommPortIdentifier.getPortIdentifiers();
			while (portList.hasMoreElements()) {
				CommPortIdentifier portId = (CommPortIdentifier) portList
						.nextElement();

				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					String name = portId.getName();
					list.addElement(name);
				}
			}

		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}
		String outgoing[] = new String[list.size()];
		list.copyInto(outgoing);
		return outgoing;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String arg) {
		portName = arg;
		}
}
