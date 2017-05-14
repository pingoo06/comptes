package comptes.util;

public class DoubleFormater {

	public static double truncateDouble(double d) {
		return (double)((int)(d*100))/100;
		}
}
