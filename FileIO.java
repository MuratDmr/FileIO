package FileIO;

import java.io.*;

import RSA.RSA;

public class FileIO {
	private byte[] buf;
	private RSA rsa;

	// new file by creating
	// neue Datei durch Erstellen
	public FileIO(String fileName, byte[] buf) {
		this.buf = buf;
		writeFile(fileName, buf);
	}

	// new file by reading
	// neue Datei durch lesen
	public FileIO(String fileName) throws IOException {
		buf = readFile(fileName);
	}

	// new file by writing with encryption
	// neue Datei durch Schreiben mit Verschlüsselung
	public FileIO(String fileName, byte[] buf, RSA rsa) {
		this.buf = buf;
		writeFile(fileName, buf);
		this.rsa = rsa;
	}

	// new file by reading with encryption
	// neue Datei durch Lesen mit Verschlüsselung
	public FileIO(String fileName, RSA rsa) throws IOException {
		buf = readFile(fileName);
		this.rsa = rsa;
	}

	public void writeFile(String fileName, byte[] buf) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
			fos.write(buf);
		} catch (IOException ex) {
			System.out.println(ex);
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (Exception ex) {
				}
		}
	}

	// read from disk to update buf, should be done if file is changed outside of
	// the environment
	// Von der Festplatte zum Aktualisierungsupdate lesen, sollte durchgeführt
	// werden, wenn die Datei außerhalb der Umgebung geändert wird
	public boolean refresh(String fileName) throws IOException {
		try {
			buf = readFile(fileName);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public byte[] encrypt() {

		// doubles buf size
		// verdoppelt die Buf-Größe
		byte[] buf = new byte[this.buf.length * 2];

		// limited by old buf shorter lenght
		// begrenzt durch alten buf kürzerer länge
		for (int i = 0; i < this.buf.length; i++) {
			buf[i] = (byte) rsa.encrypt(this.buf[i] % 20);
			buf[i + this.buf.length] = (byte) rsa.encrypt(this.buf[i] / 20);
		}
		return buf;
	}

	public byte[] decrypt() {

		// halves buf size
		// halbiert die Buf-Größe
		byte[] buf = new byte[this.buf.length / 2];

		// limited by new buf shorter lenght
		// begrenzt durch neue buf kürzere länge
		for (int i = 0; i < buf.length; i++) {
			// decrypt, multiply with 20 to retrive second enrypt and add remainder of
			// devision to retrieve original
			buf[i] = (byte) (rsa.decrypt(this.buf[i + buf.length]) * 20 + rsa.decrypt(this.buf[i]));
		}
		return buf;
	}

	public byte[] getBuf() {
		return buf;
	}

	public String getText() {
		return new String(buf);
	}

	public byte[] readFile(String fileName) throws IOException {
		File file = new File(fileName);
		long length = file.length();
		if (length > Integer.MAX_VALUE) {

			// File is too large
			// Datei ist zu groß
			throw new IOException("File is too large!");
		}
		// Create the byte array to hold the data
		// Erstellen Sie das Bytearray, um die Daten aufzunehmen
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		// Lesen sie die Bytes
		int offset = 0;
		int numRead = 0;
		InputStream is = new FileInputStream(file);
		try {
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
		} finally {
			is.close();
		}
		// Ensure all the bytes have been read in
		// Stellt sicher, dass alle Bytes eingelesen wurden
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}
		return bytes;
	}
}