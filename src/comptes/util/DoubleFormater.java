package comptes.util;

import java.text.DecimalFormat;

public class DoubleFormater {

	static final DecimalFormat formatter= new DecimalFormat("#0.00");
	public static double truncateDouble(double d) {
		return (double)((int)(d*100))/100;
		}
	
	public static String formatDouble(double d) {
		return formatter.format(d);
	}
}
