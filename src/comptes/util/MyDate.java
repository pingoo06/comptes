package comptes.util;

import java.time.LocalDate;

import comptes.util.log.Logger;

public class MyDate implements Comparable<MyDate> {

	public static final String DB_FORMAT = "yyyy-MM-dd";
	public static final String FRENCH_FORMAT = "dd/MM/yyyy";
	public static final String SHORT_FRENCH_FORMAT = "dd/MM/yy";
	public static final String BNP_FORMAT = "ddMMyy";

	private LocalDate date;

	public MyDate() {
		date = LocalDate.now();
	}
	
	public MyDate(String date) {
		update(date);
	}

	public MyDate(String date, String pattern) {
		update(date, pattern);
	}

	public MyDate(long date) {
		update(date);
	}

	@Override
	public int compareTo(MyDate o) {
		return date.compareTo(o.date);
	}

	public String toString() {
		return DateUtil.format(date, "dd/MM/yyyy");
	}

	public void update(String date) {
		if (date.matches("[0123][0-9]/[01][0-9]/[0-9]{4}")) {
			this.date = DateUtil.parse(date, FRENCH_FORMAT);
		} else if (date.matches("[0123][0-9]/[01][0-9]/[0-9]{2}")) {
			this.date = DateUtil.parse(date, SHORT_FRENCH_FORMAT);
		} else if (date.matches("[0-9]{4}-[01][0-9]-[0123][0-9]")) {
			this.date = DateUtil.parse(date, DB_FORMAT);
		} else if (date.matches("[0123][0-9][01][0-9][0-9]{2}")) {
			this.date = DateUtil.parse(date, BNP_FORMAT);
		} else {
			Logger.logError("DateFormat not detected for date: " + date);
		}
	}
	
	public void update(String date, String pattern) {
		this.date = DateUtil.parse(date, pattern);
	}

	public void update(long date) {
		this.date = LocalDate.ofEpochDay(date);
	}
	
	public String toDbFormat(){
		return DateUtil.format(date, DB_FORMAT);
	}
	
	public String format(String pattern) {
		return DateUtil.format(date, pattern);
	}
	
	public LocalDate getDate() {
		return date;
	}

	public long toLongValue() {
		return date.toEpochDay();
	}
	
	public void plusMonth(long n){
		this.date=date.plusMonths(n);
	}

	public void minusMonth(long n){
		this.date=date.minusMonths(n);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyDate other = (MyDate) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}

	public static void main(String... strings) {
		System.out.println(new MyDate("24/05/1990"));
		System.out.println(new MyDate("01/11/20"));
		System.out.println(new MyDate("2006-12-31"));
		System.out.println(new MyDate("010417"));
	}
}
