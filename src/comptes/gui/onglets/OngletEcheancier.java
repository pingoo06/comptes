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
	private MyJTextField jtfTypeEch;
	private MyJTextField jtfCategEch;
	private MyJTextField jtfTiersEch;
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
		jtfTypeEch = new MyJTextField("Prelevement");
		jtfCategEch = new MyJTextField("");
		jtfTiersEch = new MyJTextField("");
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
				jtfTiersEch.setText(comboTiersE.getSelectedItem().toString());
				TiersFacade myTiersFacade = new TiersFacade();
				int idTiers = myTiersFacade.findLib(comboTiersE.getSelectedItem().toString());
				Tiers myTiers = myTiersFacade.find(idTiers);
				jtfCategEch.setText(myTiers.getDerCategDeTiers());
				jtfDateEch.requestFocus();
			}
		});
		comboTypeEch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jtfTypeEch.setText(comboTypeEch.getSelectedItem().toString());
			}
		});

		comboCategorieE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jtfCategEch.setText(comboCategorieE.getSelectedItem().toString());
				// System.out.println("dans combo categE add action table
				// selected item : "
				// + comboCategorie.getSelectedItem().toString());
			}
		});

		// AJOUT TESTS SUR DATECH
		jtfDateEch.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});

		jtfDateEch.getDocument().addDocumentListener(new DateDocumentListener(jtfDateEch));

		Font police = new Font("Arial", Font.BOLD, 12);

		jtfTypeEch.setFont(police);
		jtfTypeEch.setPreferredSize(new Dimension(100, 20));
		jtfTypeEch.setForeground(Color.BLUE);
		jtfTiersEch.setFont(police);
		jtfTiersEch.setPreferredSize(new Dimension(100, 20));
		jtfTiersEch.setForeground(Color.BLUE);
		jtfCategEch.setFont(police);
		jtfCategEch.setPreferredSize(new Dimension(100, 20));
		jtfCategEch.setForeground(Color.BLUE);
		jtfMontantEch.setFont(police);
		jtfMontantEch.setPreferredSize(new Dimension(100, 20));
		jtfMontantEch.setForeground(Color.BLUE);
		jtfDateEch.setFont(police);
		jtfDateEch.setPreferredSize(new Dimension(100, 20));
		jtfDateEch.setForeground(Color.GREEN);
		jtfDateEch.setFont(police);
		jtfNbEch.setPreferredSize(new Dimension(100, 20));
		jtfNbEch.setFont(police);
		jtfNbEch.setForeground(Color.RED);

		saisieEchPan.add(labelTypeEch);
		saisieEchPan.add(comboTypeEch);
		saisieEchPan.add(jtfTypeEch);
		saisieEchPan.add(labelTiers);
		saisieEchPan.add(comboTiersE);
		saisieEchPan.add(jtfTiersEch);
		saisieEchPan.add(labelCategOpe);
		saisieEchPan.add(comboCategorieE);
		saisieEchPan.add(jtfCategEch);
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
				myEcheancierDTO.setTypeEch(jtfTypeEch.getText());
				myEcheancierDTO.setCategEch(jtfCategEch.getText());
				myEcheancierDTO.setTiersEch(jtfTiersEch.getText());
				myEcheancierDTO.setMontantEch(Double.parseDouble(jtfMontantEch.getText()));
				myEcheancierDTO.setDateEch(jtfDateEch.getText());
				myEcheancierDTO.setNbEch(Integer.parseUnsignedInt(jtfNbEch.getText()));
				EcheancierUtil myGestionEcheancier = new EcheancierUtil();
				myGestionEcheancier.create(myEcheancierDTO);
				clearSaisieEch();
				EcheancierBO myEcheancierBO = myGestionEcheancier.buildEcheancierBo(myEcheancierDTO);
				System.out.println("dans BoutonOKEchListener tableEcheancier : " + tableEcheancier);
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

		if (jtfTypeEch.getText().length() == 0) {
			res = "Saisir un type d'opération";
		}

		// Tiers choisi
		if (jtfTiersEch.getText().length() == 0) {
			res = "Saisir un tiers";
		}

		// categorie choisie
		String test = jtfCategEch.toString();
		LogEcheancier.logInfo("test : " + test);
		if (jtfCategEch.getText().length() == 0) {
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
		jtfTiersEch.setText("");
		jtfNbEch.setText("");
		jtfTypeEch.setText("Prelevement");
		jtfCategEch.setText("");
		String essai;
		essai = jtfCategEch.getText();
		LogEcheancier.logInfo("essai : " + essai);
		jtfMontantEch.setText("");
	}
}
