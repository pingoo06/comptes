package comptes.gui.combo;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class TypeEchCombo extends JComboBox<String> implements ActionListener {

	private static final long serialVersionUID = 1L;
		private static final String[] typeStrings = {"Prelevement","Virement"};

		public TypeEchCombo() {
			super(typeStrings);
			setSelectedIndex(0);
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);

		}
		
		//Change la fonction parente pour retourner PRLV et VIR_EMIS
		public String getMySelectedItem(){
			String selected = super.getSelectedItem().toString();
			String res = selected;
			if(selected.equals(typeStrings[0])){
				res = "PRLV";
			}else if(selected.equals(typeStrings[1])){
				res = "VIR_EMIS";
			}
			
			return res;
		}
}
