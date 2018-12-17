package App;

import java.io.*;

import FileIO.FileIO;

import RSA.RSA;

public class App {
	public static void main(String[] args) throws IOException {
		String path = "D:/temp/test.txt";
		{
			// create file with text
			// erstelle Datei mit Text
			new FileIO(path, "Das ist mein Text!".getBytes());
		}
		{
			RSA rsa = new RSA();

			// read file
			// lese Datei
			FileIO myFile = new FileIO(path, rsa);
			System.out.println(myFile.getText());
			for (int i = 0; i < myFile.getBuf().length; i++) {
				System.out.print(myFile.getBuf()[i] + "\t");
			}
			System.out.print("\n");

			// encrypt file
			// Datei verschlüsseln
			myFile.writeFile(path, myFile.encrypt());
			myFile.refresh(path);
			System.out.println(myFile.getText());

			// decrypt file
			// Datei entschlüsseln
			myFile.writeFile(path, myFile.decrypt());
			myFile.refresh(path);
			System.out.println(myFile.getText());
		}
	}
}