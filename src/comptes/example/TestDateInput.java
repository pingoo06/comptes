package comptes.example;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TestDateInput {

	public static void main(String[] args) {

		JFrame f = new JFrame();
		JPanel p = new JPanel();
		
		final JTextField tf = new JTextField(12);
		
		p.add(tf);
		f.getContentPane().add(p);
		JTextField tf2 = new JTextField("");
		
		f.setVisible(true);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tf.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				System.out.println("lala");
				
			}
		});
		
		tf.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				String dateStr = tf.getText();
				System.out.println(dateStr);
				
				final String newText;
				if(dateStr.matches("[0-9]{2}/[0-9]{2}")) {
					int year = LocalDate.now().getYear();
					newText = dateStr+"/"+year;
				}else if(dateStr.endsWith("+")){
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate date = LocalDate.parse(dateStr.substring(0,dateStr.length()-1), formatter);
					date = date.plusDays(1);
					newText = date.format(formatter);
					System.out.println("plus: "+dateStr);
				}else if(dateStr.endsWith("-")){
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate date = LocalDate.parse(dateStr.substring(0,dateStr.length()-1), formatter);
					date = date.minusDays(1);
					newText = date.format(formatter);
					System.out.println("moins: "+dateStr);
				}else{
					return;
				}
				
				new Thread(new Runnable() {
					public void run() {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								tf.setText(newText);
							}
						});
					}
				}).start();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				
			}
		});
		
	}

}
