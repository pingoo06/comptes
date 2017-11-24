package comptes.gui.onglets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import comptes.gui.combo.CategorieCombo;
import comptes.gui.combo.TiersCombo;
import comptes.gui.combo.TypeEchCombo;
import comptes.gui.component.MyJTextField;
import comptes.gui.dto.EcheancierDTO;
import comptes.gui.listener.DateDocumentListener;
import comptes.gui.tableaux.EcheancierTableau;
import comptes.model.bo.EcheancierBO;
import comptes.model.db.entity.Tiers;
import comptes.model.facade.TiersFacade;
import comptes.model.services.EchToOpe;
import comptes.model.services.EcheancierUtil;
import comptes.util.MyDate;
import comptes.util.log.LogEcheancier;
import comptes.util.log.LogOperation;

public class OngletEcheancier extends JSplitPane {

	private static final long serialVersionUID = 1L;

	private JPanel saisieEchPan;

	// Pour echeancier
	private MyJTextField jtfDateEch;
	private MyJTextField jtfNbEch;
	private MyJTextField jtfMontantEch;

	private JLabel labelTypeEch;
	private JLabel labelTiers;
	private JLabel labelDateEch;
	private JLabel labelMontantEch;
	private JLabel labelCategOpe;
	private JLabel labelNbEch;

	private JButton boutonOKEch;
	private JButton boutonAnnulEch;
	private JButton boutonSupprEch;

	private JPanel vTopE;
	private JPanel vBottomE;
	private JPanel boutonPanE;

	private JTable tableEcheancier;

	// combo echeancier
	private TypeEchCombo comboTypeEch;
	private TiersCombo comboTiersE;
	private CategorieCombo comboCategorieE;

	public OngletEcheancier() {
		saisieEchPan = new JPanel();
		jtfDateEch = new MyJTextField(new MyDate().toString());
		jtfNbEch = new MyJTextField("");
		jtfMontantEch = new MyJTextField("");

		labelTypeEch = new JLabel("TypeEch");
		labelTiers = new JLabel("Tiers");
		labelDateEch = new JLabel("Date Ech");
		labelMontantEch = new JLabel("Montant Ech");
		labelCategOpe = new JLabel("Categorie");
		labelNbEch = new JLabel("Nb Ech");

		// combo echeancier
		comboTypeEch = new TypeEchCombo();
		comboTiersE = new TiersCombo();
		comboCategorieE = new CategorieCombo();

		boutonOKEch = new JButton("OK");
		boutonAnnulEch = new JButton("Annuler");
		boutonSupprEch = new JButton("Supprimer");

		// Tableau echeancier
		vTopE = new JPanel();
		setTopComponent(vTopE);
		vTopE.setLayout(new BorderLayout());
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		tableEcheancier = new JTable(new EcheancierTableau());
		vTopE.add(new JScrollPane(tableEcheancier), BorderLayout.CENTER);
		tableEcheancier.setAutoCreateRowSorter(true);

		// boutons
		vBottomE = new JPanel();
		setBottomComponent(vBottomE);
		vBottomE.setLayout(new BorderLayout());
		boutonPanE = new JPanel();
		boutonPanE.add(boutonAnnulEch);
		boutonPanE.add(boutonOKEch);
		boutonPanE.add(boutonSupprEch);
		vBottomE.add(boutonPanE, BorderLayout.EAST);
		

		//Bouton ok
		boutonOKEch.addActionListener(new BoutonOKEchListener());

		// Bouton Supprimer
		boutonSupprEch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tableIdx = tableEcheancier.getSelectedRow();
				int modelIdx = tableEcheancier.convertRowIndexToModel(tableIdx);
				EcheancierTableau model = ((EcheancierTableau) tableEcheancier.getModel());
				model.deleteRow(modelIdx);
			}
		});

		//bouton Annuler
		boutonAnnulEch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearSaisieEch();
			}
		});
		
		
		comboTiersE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TiersFacade myTiersFacade = new TiersFacade();
				int idTiers = myTiersFacade.findLib(comboTiersE.getSelectedItem().toString());
				if (idTiers != 0) {
					Tiers myTiers = new Tiers();
					myTiers = myTiersFacade.find(idTiers);
					String derCategDeTiers = myTiers.getDerCategDeTiers();
					comboCategorieE.setSelectedItem(derCategDeTiers);
//					jtfDateEch.requestFocus();
				}else {
					comboCategorieE.setSelectedIndex(0);
				}
			}
		}
			)
		;
//		comboTypeEch.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//			}
//		});

//		comboCategorieE.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				jtfCategEch.setText(comboCategorieE.getSelectedItem().toString());
//				// System.out.println("dans combo categE add action table
//				// selected item : "
//				// + comboCategorie.getSelectedItem().toString());
//			}
//		});

		// AJOUT TESTS SUR DATECH
		jtfDateEch.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});

		jtfDateEch.getDocument().addDocumentListener(new DateDocumentListener(jtfDateEch));

		Font police = new Font("Arial", Font.BOLD, 12);

		jtfMontantEch.setFont(police);
		jtfMontantEch.setPreferredSize(new Dimension(100, 20));
		jtfMontantEch.setForeground(Color.BLUE);
		jtfDateEch.setFont(police);
		jtfDateEch.setPreferredSize(new Dimension(100, 20));
		jtfDateEch.setForeground(Color.BLUE);
		jtfDateEch.setFont(police);
		jtfNbEch.setPreferredSize(new Dimension(100, 20));
		jtfNbEch.setFont(police);
		jtfNbEch.setForeground(Color.RED);

		saisieEchPan.add(labelTypeEch);
		saisieEchPan.add(comboTypeEch);
		saisieEchPan.add(labelTiers);
		saisieEchPan.add(comboTiersE);
		saisieEchPan.add(labelCategOpe);
		saisieEchPan.add(comboCategorieE);
		saisieEchPan.add(labelDateEch);
		saisieEchPan.add(jtfDateEch);
		saisieEchPan.add(labelMontantEch);
		saisieEchPan.add(jtfMontantEch);
		saisieEchPan.add(labelNbEch);
		saisieEchPan.add(jtfNbEch);
		vBottomE.add(saisieEchPan, BorderLayout.CENTER);
		comboTiersE.requestFocus();

	}

	class BoutonOKEchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			final JOptionPane frame;
			String res = validateSaisieEch();
			if (res != "") {
				frame = new JOptionPane();
				JOptionPane.showMessageDialog(frame, res, "Saisie erronée", JOptionPane.WARNING_MESSAGE);
			} else {
				EcheancierDTO myEcheancierDTO = new EcheancierDTO();
				myEcheancierDTO.setId(0);
				myEcheancierDTO.setTypeEch(comboTypeEch.getMySelectedItem());
				myEcheancierDTO.setCategEch(comboCategorieE.getSelectedItem().toString());
				myEcheancierDTO.setTiersEch(comboTiersE.getSelectedItem().toString());
				myEcheancierDTO.setMontantEch(Double.parseDouble(jtfMontantEch.getText())*-1);
				myEcheancierDTO.setDateEch(jtfDateEch.getText());
				myEcheancierDTO.setNbEch(Integer.parseUnsignedInt(jtfNbEch.getText()));
				EcheancierUtil myGestionEcheancier = new EcheancierUtil();
				myGestionEcheancier.create(myEcheancierDTO);
				clearSaisieEch();
				EcheancierBO myEcheancierBO = myGestionEcheancier.buildEcheancierBo(myEcheancierDTO);
				LogEcheancier.logDebug("dans BoutonOKEchListener tableEcheancier : " + tableEcheancier);
				EcheancierTableau model = ((EcheancierTableau) tableEcheancier.getModel());
				model.getListEcheancierBO().add(myEcheancierBO);
				model.fireTableDataChanged();
			}
		}
	}

	public String validateSaisieEch() {
		String res = "";
		LogEcheancier.logDebug("Debut validateSaisieEch");

		// Date présente et correcte
		if (jtfDateEch.getText().length() == 0) {
			res = "Saisir une date";
		} else {
			String dateSaisie = jtfDateEch.getText();
			if (!dateSaisie.matches("[0123][0-9]/[01][0-9]/[0-9]{4}")) {
				res = "Saisir une date au format jj/mm/aaaa";
			}
		}

		if (comboTypeEch.getSelectedItem().toString().length() == 0) {
			res = "Saisir un type d'échancier";
		}

		// Tiers choisi
		if (comboTiersE.getSelectedItem().toString().length() == 0
				|| comboTiersE.getSelectedItem().toString() == "Tout") {
			res = "Saisir un tiers";
		}

		// categorie choisie
		if (comboCategorieE.getSelectedItem().toString().length() == 0) {
			res = "Saisir une categorie";
		}

		// montant saisi
		if (jtfMontantEch.getText().length() == 0) {
			res = "saisir un montant";
		} else if ("0".equals(jtfMontantEch.getText())) {
			res = "saisir un montant";
		}

		// nb echéances saisi
		if (jtfNbEch.getText().length() == 0) {
			res = "saisir un nombre d'echeances";
		} else if ("0".equals(jtfNbEch.getText())) {
			res = "saisir un nombre d'echeances";
		}

		return res;
	}

	public void clearSaisieEch() {
		comboTiersE.setSelectedIndex(0);
		comboCategorieE.setSelectedIndex(0);
		comboTypeEch.setSelectedIndex(0);
		comboTiersE.requestFocus();
		jtfDateEch.setText("");
		jtfNbEch.setText("");
		jtfMontantEch.setText("");
	}
}
