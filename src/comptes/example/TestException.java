package comptes.example;

import javax.naming.directory.InvalidAttributeValueException;

public class TestException {

	public static void pouet() throws InvalidAttributeValueException {
		/**
		 * MET DU CODE INTELLIGENT
		 */
		throw new InvalidAttributeValueException("lalalo");

	}

	// ****************** Main method
	public static void main(String[] args) {

		try {
			pouet();
		} catch (InvalidAttributeValueException e) {
			System.out.println(e.getMessage());
		}
	}

}
