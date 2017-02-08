package comptes.gui.combo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
//nico faut il enlever l'action listener dans fenetre ?
public class TypeOpeCombo extends JComboBox<String> implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String[] typeStrings = {"CB", "CHQ", "PRLV", "RETRAIT", "VIR_EMIS", "VIR_RECU"};
	public TypeOpeCombo() {
		super(typeStrings);
		setSelectedIndex(0);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);

	}

}

