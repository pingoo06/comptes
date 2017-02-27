package comptes.example;

import java.io.File;
import java.io.IOException;

import comptes.util.MyDate;

public class TestFiles {

	public static void main(String[] args) {

		File f = new File("res/pouet.txt");
		
		try {
			f.createNewFile();
			f.renameTo(new File("res/toto.txt"));
			f.renameTo(new File("lib/tata.txt"+(new MyDate()).toDbFormat()));
			f.delete();
		} catch (IOException e) {
		}
	}

}
