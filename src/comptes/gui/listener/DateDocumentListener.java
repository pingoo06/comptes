package comptes.gui.listener;

import java.time.LocalDate;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import comptes.util.DateUtil;

public class DateDocumentListener implements DocumentListener {

	private JTextField jtf;
	
	
	public DateDocumentListener(JTextField jtf) {
		super();
		this.jtf = jtf;
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		String dateStr = jtf.getText();
		System.out.println(dateStr);

		final String newText;
		if (dateStr.matches("[0-9]{2}/[0-9]{2}")) {
			int year = LocalDate.now().getYear();
			newText = dateStr + "/" + year;
			// } else if (dateStr.matches("[0-9]{1}/[0-9]{1}")) {
			// int year = LocalDate.now().getYear();
			// newText = "0" + dateStr.substring(0, 1) + "/" +
			// dateStr.substring(2, 1) + "/" + year;
			// } else if (dateStr.matches("[0-9]{2}/[0-9]{1}")) {
			// int year = LocalDate.now().getYear();
			// newText = dateStr.substring(0, 1) + "/" +
			// dateStr.substring(2, 1) + "/" + year;
		} else if (dateStr.matches("[0-9]{1}/[0-9]{2}")) {
			int year = LocalDate.now().getYear();
			newText = "0" + dateStr + "/" + year;
			// } else if(dateStr.matches("[0-9]{1}")) {
			// int year = LocalDate.now().getYear();
			// int month = LocalDate.now().getMonthValue();
			// newText = "0" + dateStr + "/" + month + "/" + year;
			// } else if (dateStr.matches("[0-9]{2}")) {
			// int year = LocalDate.now().getYear();
			// int month = LocalDate.now().getMonthValue();
			// newText = dateStr + "/" + month + "/" + year;
		} else if (dateStr.endsWith("+") || dateStr.startsWith("+")) {
			LocalDate date = DateUtil.parse(dateStr.substring(0, dateStr.length() - 1));
			date = date.plusDays(1);
			newText = DateUtil.format(date, "dd/MM/yyyy");
			System.out.println("plus: " + dateStr);
		} else if (dateStr.endsWith("-") || dateStr.startsWith("-")) {
			LocalDate date = DateUtil.parse(dateStr.substring(0, dateStr.length() - 1));
			date = date.minusDays(1);
			newText = DateUtil.format(date, "dd/MM/yyyy");
			System.out.println("moins: " + dateStr);
		} else {
			return;
		}

		new Thread(new Runnable() {
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						jtf.setText(newText);
					}
				});
			}
		}).start();

	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub

	}

}
