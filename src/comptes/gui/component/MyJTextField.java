package comptes.gui.component;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class MyJTextField extends JTextField {

	private static final long serialVersionUID = 7834295077765346095L;

	{
		init();
	}

	public MyJTextField() {
	}

	public MyJTextField(String text) {
		super(text);
	}

	public MyJTextField(int columns) {
		super(columns);
	}

	public MyJTextField(String text, int columns) {
		super(text, columns);
	}

	public MyJTextField(Document doc, String text, int columns) {
		super(doc, text, columns);
	}

	public void init() {
		addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void focusGained(FocusEvent e) {
				selectAll();
			}
		});
	}

}
