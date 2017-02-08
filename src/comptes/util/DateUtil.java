package comptes.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

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
	
	public static String convertDate(String date, String patternSrc, String patternDst) {
		return format(parse(date,patternSrc), patternDst);
	}

	/**
	 * 
	 * @param dateText format dd/MM/yyyy
	 * @return LocalDate
	 */
	public static LocalDate parse(String dateText) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(dateText, formatter);
		return date;
	}

	/**
	 * 
	 * @param dateText
	 * @param pattern
	 * @return LocalDate
	 */
	public static LocalDate parse(String dateText, String pattern) {
		LocalDate date;;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		date = LocalDate.parse(dateText, formatter);
		return date;
	}

	/**
	 * 
	 * @param date
	 * @return formatted date dd-MM-yyyy
	 */
	public static String format(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return formatted date as String
	 */
	public static String format(LocalDate date, String pattern) {
		return date.format(DateTimeFormatter.ofPattern(pattern));
	}
	

	public static long dateToLong(LocalDate date) {
		long longTime = date.toEpochDay();
		return longTime;
	}

}
