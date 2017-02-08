package comptes.gui.combo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class FiltreRapproCombo extends JComboBox<String> implements ActionListener {

	private static final String[] typeStrings = { "Tout", "Non rapproches", "Rapproches" };

	public FiltreRapproCombo() {
			super(typeStrings);
			setSelectedIndex(0);
			addActionListener(this);
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);

	}

}
