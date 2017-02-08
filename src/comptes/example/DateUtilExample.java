package comptes.example;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtilExample {

	/**
	 * 
	 * @param dateStr
	 *            on format dd/MM/yyyy
	 * @return dat at format yyyy-MM-dd 
	 */
	public static String convertDateStr(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date parsedDate;
		try {
			parsedDate = sdf.parse(dateStr);
			long myTime = parsedDate.getTime();
			return new Date(myTime).toString();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static LocalDate parse(String dateText) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(dateText, formatter);
		return date;
	}
/*
	public static LocalDate parse(String dateText) {
		// Parser une date depuis un String :
		
		// // Format par defaut : yyyy-MM-dd
		// System.out.println("Exemple de parse de date");
//		LocalDate date = LocalDate.parse("2017-05-24");
		// System.out.println("defaut : " + date);
		//
		// // choix 1 : tu utilise un format existant parmis les differentes
		// // valeurs des champs static de la classe DateTimeFormatter
		// date = LocalDate.parse("20170524", DateTimeFormatter.BASIC_ISO_DATE);
		// System.out.println("format predefini : " + date);
		//// res : format predefini : 2017-05-24
		// choix 2 : tu utilise un pattern custom :
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(dateText, formatter);
//		System.out.println("format specifie" + date);
		// res : format specifie2017-05-24
//		System.out.println();
		return date;
	} */

	public static String format(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}
	
	public static void format() {
		// Pour afficher une date selon un partten, idem 2 choix :
		LocalDate date = LocalDate.now();
		// res :
		// Schema par defaut :
		System.out.println("Exemple de formattage de date");
		System.out.println("defaut : " + date);
		// res :Date du jour: 2017-01-13
		// choix 1 : pattern existant :
		System.out.println("format predefini : " + date.format(DateTimeFormatter.BASIC_ISO_DATE));
		// res : defaut : 2017-01-13
		// format predefinit : 20170113
		// Choix 2 :
		System.out.println("format specifie : " + date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		// format specifie : 13-01-2017
	}
	
	public static LocalDate parse(String dateText, String pattern) {
		LocalDate date;;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		date = LocalDate.parse(dateText, formatter);
		return date;
	}

	public static long dateStringToLong(LocalDate date) {
		long longTime = date.toEpochDay();
		return longTime;
	}

	public static void dateAndLong() {
		System.out.println("Switcher LocalDate <=> long");

		System.out.println("LocalDate => long : ");
		LocalDate date = LocalDate.now();
		// convert date en long : toEpochDay(), a appliquer sur un ojbjet de
		// type LocalDate
		long longTime = date.toEpochDay();
		System.out.println(date + " => " + longTime);
		System.out.println();

		System.out.println("long => LocalDate : ");

		// convert long en date : ofEpochDay(long), a appeler depuis la class,
		// car methode statique
		date = LocalDate.ofEpochDay(longTime);
		System.out.println(longTime + " => " + date);

		// res : Switcher LocalDate <=> long
		// LocalDate => long :
		// 2017-01-13 => 17179
		//
		// long => LocalDate :
		// 17179 => 2017-01-13
	}

}
