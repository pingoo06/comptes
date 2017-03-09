package comptes.util;

public class StringFormater {

	public static String format(String s) {
		String res = "";
		if (!s.isEmpty()) {
			res = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		}

		return res;
	}

	public static boolean estUnDouble(String chaine) {
		try {
			Double.parseDouble(chaine);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
