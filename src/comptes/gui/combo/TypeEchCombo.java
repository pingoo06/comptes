package comptes.gui.combo;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class TypeEchCombo extends JComboBox<String> implements ActionListener {

	private static final long serialVersionUID = 1L;
		private static final String[] typeStrings = { "Prelevement","Debit", "Credit"};

		public TypeEchCombo() {
			super(typeStrings);
			setSelectedIndex(0);
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);

		}
}
