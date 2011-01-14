package rchome.java.serial;

public class SerialMonitor {

	public static void main(String[] args) throws SerialException {
		Serial serial = new Serial();

		serial.write("h1h2");
	}
}
