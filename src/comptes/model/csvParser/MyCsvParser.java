package comptes.model.csvParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import comptes.util.log.Logger;

public class MyCsvParser {

	private String separator;

	private ArrayList<String> headers;
	private BufferedReader br;
	private String currentLine;
	
	public MyCsvParser(boolean containsHeaders, int lineToSkip, String separator, String filePath) {
		super();
		this.separator = separator;
		headers = new ArrayList<>();

		try {
			br = new BufferedReader(new FileReader(filePath));
			for (int i = 0; i < lineToSkip; i++) {
				if (!next()) {
					break;
				}
			}

			if (containsHeaders) {
				next();
				headers.addAll(Arrays.asList(currentLine.split(separator)));
			}
		} catch (FileNotFoundException e) {
			Logger.logError("csv not found : " + filePath, e);
		}
	}

	public static MyCsvParser getMoneyParser(String filePath) {
		return new MyCsvParser(true, 0, ",", filePath);
	}

	public static MyCsvParser getBnpParser(String filePath) {
		return new MyCsvParser(false, 1, ";", filePath);
	}

	public boolean next() {
		try {
			if (((currentLine = br.readLine()) != null)) {
				return true;
			} else {
				br.close();
				return false;
			}
		} catch (IOException e) {
			Logger.logError("error getting next line", e);
			return false;
		}
	}

	public String getString(String key) {
		if (headers.contains(key)) {
			return getString(headers.indexOf(key));
		} else {
			Logger.logError("No column named: " + key + ". Available columns: " + headers);
			return null;
		}
	}

	public String getString(int pos) {
		String[] values = currentLine.split(separator);
		if (pos < values.length) {
			return currentLine.split(separator)[pos];
		} else {
			Logger.logError("Dans getstring de csvparser Value given it too big: " + pos);
			Logger.logError(Arrays.toString(values));
			Logger.logError("Dans getstring de csvparser current line" + currentLine );
			Logger.logError("Dans getstring de csvparser current line" + Arrays.toString(values) );
		
		}
		return null;
	}

	public Integer getInt(String key) {
		if (headers.contains(key)) {
			return getInt(headers.indexOf(key));
		} else {
			Logger.logError("No column named: " + key + ". Available columns: " + headers);
			return null;
		}
	}

	public Integer getInt(int pos) {
		String[] values = currentLine.split(separator);
		if (pos < values.length) {
			String value = values[pos];
			if (value != null && !value.isEmpty()) {
				return Integer.parseInt(value);
			}
		} else {
			Logger.logError("Value given it too big: " + pos);
		}
		return null;
	}

	public Double getDouble(String key) {
		if (headers.contains(key)) {
			return getDouble(headers.indexOf(key));
		} else {
			Logger.logError("No column named: " + key + ". Available columns: " + headers);
			return null;
		}
	}

	
	public Double getDouble(int pos) {
		String[] values = currentLine.split(separator);
		if (pos < values.length) {
			String value = values[pos];
			double zero=0;
			if (value != null && !value.isEmpty()) {
				return Double.parseDouble(value.replace(",", ".").replace(" ", ""));
			}
			//ici
			else { return zero;}
			//fin ici
		} else {
			System.err.println("Value given it too big: " + pos);
		}
		return null;
	}
	
	public Float getFloat(int pos) {
		String[] values = currentLine.split(separator);
		if (pos < values.length) {
			String value = values[pos];
			if (value != null && !value.isEmpty()) {
				return Float.parseFloat(value.replace(",", ".").replace(" ", ""));
			}
		} else {
			Logger.logError("Value given it too big: " + pos);
		}
		return null;
	}
}
