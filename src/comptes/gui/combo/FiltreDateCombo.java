package comptes.gui.combo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class FiltreDateCombo extends JComboBox<String> implements ActionListener {

	private static final String[] typeStrings = { "Tout", "1 mois", "3 mois", "1 an" };

	public FiltreDateCombo() {
		super(typeStrings);
		setSelectedIndex(0);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);

	}

}
