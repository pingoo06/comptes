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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

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
import comptes.model.services.EcheancierUtil;
import comptes.util.MyDate;

public class OngletEcheancier extends JSplitPane {

	private static final long serialVersionUID = 1L;


	JPanel saisieEchPan = new JPanel();


	// Pour echeancier
	private MyJTextField jtfDateEch = new MyJTextField(new MyDate().toString());
	private MyJTextField jtfNbEch = new MyJTextField();
	private MyJTextField jtfTypeEch = new MyJTextField("Prelevement");
	private MyJTextField jtfCategEch = new MyJTextField();
	private MyJTextField jtfTiersEch = new MyJTextField();
	private MyJTextField jtfMontantEch = new MyJTextField();

	private JLabel labelTypeEch = new JLabel("TypeEch");
	private JLabel labelTiers = new JLabel("Tiers");
	private JLabel labelDateEch = new JLabel("Date Ech");
	private JLabel labelMontantEch = new JLabel("Montant Ech");
	private JLabel labelCategOpe = new JLabel("Categorie");
	private JLabel labelNbEch = new JLabel("Nb Ech");

	private JButton boutonOKEch = new JButton("OK");
	private JButton boutonAnnulEch = new JButton("Annuler");

	String whereClause = "";
	JPanel vTopE = new JPanel();
	JPanel vBottomE = new JPanel();

	private JTable tableEcheancier;

	// combo echeancier
	TypeEchCombo comboTypeEch = new TypeEchCombo();
	TiersCombo comboTiersE = new TiersCombo();
	CategorieCombo comboCategorieE = new CategorieCombo();

	public OngletEcheancier() {
		setTopComponent(vTopE);
		setBottomComponent(vBottomE);
		setOrientation(JSplitPane.VERTICAL_SPLIT);


		vTopE.setLayout(new BorderLayout());

		// Tableau echeancier
		tableEcheancier = new JTable(new EcheancierTableau());
		vTopE.add(new JScrollPane(tableEcheancier), BorderLayout.CENTER);
		tableEcheancier.setAutoCreateRowSorter(true);

		vBottomE.setLayout(new BorderLayout());
		JPanel boutonPanE = new JPanel();
		boutonPanE.add(boutonAnnulEch);
		boutonPanE.add(boutonOKEch);
		vBottomE.add(boutonPanE, BorderLayout.EAST);
		boutonOKEch.addActionListener(new BoutonOKEchListener());
		// Bouton Supprimer
		JButton boutonSupprEch = new JButton("Supprimer");
		boutonSupprEch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tableIdx = tableEcheancier.getSelectedRow();
				int modelIdx = tableEcheancier.convertRowIndexToModel(tableIdx);
				EcheancierTableau model = ((EcheancierTableau) tableEcheancier.getModel());
				model.deleteRow(modelIdx);
			}
		});

		boutonAnnulEch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearSaisieEch();
			}
		});
		boutonPanE.add(boutonSupprEch);
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
		// nico en faire une fonction ?
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
		// Ecoute clavier sur JTF4
		// jtfDetailOpe.addKeyListener(new ClavierListener());

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
		jtfMontantEch.setText("");
	}
}
