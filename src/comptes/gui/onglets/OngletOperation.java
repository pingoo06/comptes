package comptes.gui.onglets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import comptes.gui.combo.CategorieCombo;
import comptes.gui.combo.FiltreDateCombo;
import comptes.gui.combo.FiltreRapproCombo;
import comptes.gui.combo.TiersCombo;
import comptes.gui.combo.TypeOpeCombo;
import comptes.gui.dto.OperationDTO;
import comptes.gui.listener.DateDocumentListener;
import comptes.gui.tableaux.OperationTableau;
import comptes.model.db.entity.Tiers;
import comptes.model.facade.TiersFacade;
import comptes.model.services.GestionOperation;
import comptes.util.DateUtil;

public class OngletOperation extends JSplitPane {

	private static final long serialVersionUID = 1L;

	LocalDate dateJour = LocalDate.now();
	String dateJourStr = DateUtil.format(dateJour, "dd/MM/yyyy");

	private JButton boutonOKOpe;
	private JButton boutonFiltreOpe;
	private JButton boutonAnnulOpe;

	// Pour operation
	private JTextField jtfDateOpe;
	private JTextField jtfDetailOpe;
	private JTextField jtfTypeOpe;
	private JTextField jtfCategOpe;
	private JTextField jtfDebit;
	private JTextField jtfCredit;
	private JTextField jtfTiers;

	private JTable tableOperation;

	JPanel saisieOpePan = new JPanel();

	// combo operation
	TypeOpeCombo comboTypeOpe = new TypeOpeCombo();
	TiersCombo comboTiers = new TiersCombo();
	CategorieCombo comboCategorie = new CategorieCombo();
	TiersCombo comboFiltreTiers = new TiersCombo();
	FiltreDateCombo comboFiltreDuree = new FiltreDateCombo();
	FiltreRapproCombo comboFiltreRappro = new FiltreRapproCombo();

	JPanel vTop = new JPanel();
	JPanel vBottom = new JPanel();

	JPanel pFilters = new JPanel();

	private JLabel labelTypeOpe = new JLabel("TypeOpe");
	private JLabel labelTiers = new JLabel("Tiers");
	private JLabel labelDateOpe = new JLabel("Date Ope");
	private JLabel labelDebit = new JLabel("Debit");
	private JLabel labelCredit = new JLabel("Credit");
	private JLabel labelCategOpe = new JLabel("Categorie");
	private JLabel labelDetailOpe = new JLabel("DetailOpe");
	private JLabel labelDuree = new JLabel("Duree");
	private JLabel labelRappro = new JLabel("Rapprochement");

	private String valFiltreTiers = "";
	private String valFiltreDuree = "";
	private String valFiltreRappro = "";

	String whereClause = "";

	public OngletOperation() {

		// super(JSplitPane.VERTICAL_SPLIT, vTop, vBottom);
		// Gestion des filtres 
		setTopComponent(vTop);
		setBottomComponent(vBottom);
		setOrientation(JSplitPane.VERTICAL_SPLIT);

		boutonOKOpe = new JButton("OK");
		boutonFiltreOpe = new JButton("Filtrer");
		boutonAnnulOpe = new JButton("Annuler");

		// Pour operation
		jtfDateOpe = new JTextField(dateJourStr);
		jtfDetailOpe = new JTextField();
		jtfTypeOpe = new JTextField("CB");
		jtfCategOpe = new JTextField();
		jtfDebit = new JTextField();
		jtfCredit = new JTextField();
		jtfTiers = new JTextField();
		pFilters.add(labelDuree);
		pFilters.add(comboFiltreDuree);
		pFilters.add(labelRappro);
		pFilters.add(comboFiltreRappro);
		pFilters.add(labelTiers);
		pFilters.add(comboFiltreTiers);
		pFilters.add(boutonFiltreOpe);
		vTop.setLayout(new BorderLayout());
		boutonFiltreOpe.addActionListener(new BoutonFiltreListener());

		comboFiltreTiers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valFiltreTiers = comboFiltreTiers.getSelectedItem().toString();
			}
		});

		comboFiltreDuree.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valFiltreDuree = comboFiltreDuree.getSelectedItem().toString();
			}
		});

		comboFiltreRappro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valFiltreRappro = comboFiltreRappro.getSelectedItem().toString();
			}
		});

		vTop.add(pFilters, BorderLayout.NORTH);
		// Taleau des opérations
		tableOperation = new JTable(new OperationTableau());
		vTop.add(new JScrollPane(tableOperation), BorderLayout.CENTER);
		tableOperation.setAutoCreateRowSorter(true);

		vBottom.setLayout(new BorderLayout());
		JPanel boutonPan = new JPanel();
		boutonPan.add(boutonAnnulOpe);
		boutonPan.add(boutonOKOpe);
		vBottom.add(boutonPan, BorderLayout.EAST);
		boutonOKOpe.addActionListener(new BoutonOKListener());
		// Bouton Supprimer
		JButton boutonSuppr = new JButton("Supprimer");
		boutonSuppr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tableIdx = tableOperation.getSelectedRow();
				int modelIdx = tableOperation.convertRowIndexToModel(tableIdx);
				OperationTableau model = ((OperationTableau) tableOperation.getModel());
				model.deleteRow(modelIdx);
			}
		});

		boutonAnnulOpe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearSaisieOpe();
			}
		});
		boutonPan.add(boutonSuppr);
		comboTiers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jtfTiers.setText(comboTiers.getSelectedItem().toString());
				TiersFacade myTiersFacade = new TiersFacade();
				int idTiers = myTiersFacade.findLib(comboTiers.getSelectedItem().toString());
				Tiers myTiers = myTiersFacade.find(idTiers);
				jtfCategOpe.setText(myTiers.getDerCategDeTiers());
			}
		});
		comboTypeOpe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jtfTypeOpe.setText(comboTypeOpe.getSelectedItem().toString());
				String libType = comboTypeOpe.getSelectedItem().toString();
				if (libType == "RETRAIT") {
					jtfTiers.setText("RETRAIT");
					jtfCategOpe.setText("Retrait d especes");
					jtfDebit.requestFocus();
				}
			}
		});

		comboCategorie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jtfCategOpe.setText(comboCategorie.getSelectedItem().toString());
				// System.out.println("dans combo categ add action table
				// selected item : "
				// + comboCategorie.getSelectedItem().toString());
			}
		});

		// AJOUT TESTS SUR DATEOPE
		jtfDateOpe.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});

		jtfDateOpe.getDocument().addDocumentListener(new DateDocumentListener(jtfDateOpe));

		Font police = new Font("Arial", Font.BOLD, 12);

		// Operation
		jtfTypeOpe.setFont(police);
		jtfTypeOpe.setPreferredSize(new Dimension(100, 20));
		jtfTypeOpe.setForeground(Color.BLUE);
		jtfTiers.setFont(police);
		jtfTiers.setPreferredSize(new Dimension(100, 20));
		jtfTiers.setForeground(Color.BLUE);
		jtfCategOpe.setFont(police);
		jtfCategOpe.setPreferredSize(new Dimension(100, 20));
		jtfCategOpe.setForeground(Color.BLUE);
		jtfDebit.setFont(police);
		jtfDebit.setPreferredSize(new Dimension(100, 20));
		jtfDebit.setForeground(Color.BLUE);
		jtfCredit.setFont(police);
		jtfCredit.setPreferredSize(new Dimension(100, 20));
		jtfCredit.setForeground(Color.BLUE);
		jtfDateOpe.setFont(police);
		jtfDateOpe.setPreferredSize(new Dimension(100, 20));
		jtfDateOpe.setForeground(Color.GREEN);
		jtfDateOpe.setFont(police);
		jtfDetailOpe.setPreferredSize(new Dimension(100, 20));
		jtfDetailOpe.setFont(police);
		jtfDetailOpe.setForeground(Color.RED);
		// Ecoute clavier sur JTF4
		// jtfDetailOpe.addKeyListener(new ClavierListener());

		saisieOpePan.add(labelDateOpe);
		saisieOpePan.add(jtfDateOpe);
		saisieOpePan.add(labelTypeOpe);
		saisieOpePan.add(comboTypeOpe);
		saisieOpePan.add(jtfTypeOpe);
		saisieOpePan.add(labelTiers);
		saisieOpePan.add(comboTiers);
		saisieOpePan.add(jtfTiers);
		saisieOpePan.add(labelCategOpe);
		saisieOpePan.add(comboCategorie);
		saisieOpePan.add(jtfCategOpe);
		saisieOpePan.add(labelDebit);
		saisieOpePan.add(jtfDebit);// saisieOpePan.add(labelMtOpe);
		saisieOpePan.add(labelCredit);
		saisieOpePan.add(jtfCredit);
		saisieOpePan.add(labelDetailOpe);
		saisieOpePan.add(jtfDetailOpe);
		vBottom.add(saisieOpePan, BorderLayout.CENTER);

		// JSplitPane operationPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
		// vTop, vBottom);
		// return operationPanel;
		// this.add(operationPanel);
	}

	// Execution du bouton OK Operation
	class BoutonOKListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			OperationDTO myOperationDTO = new OperationDTO();
			GestionOperation myGestionOperation = new GestionOperation();
			myOperationDTO.setTypeOpe(jtfTypeOpe.getText());
			myOperationDTO.setDateOpe(jtfDateOpe.getText());
			myOperationDTO.setTiers(jtfTiers.getText());
			myOperationDTO.setCategOpe(jtfCategOpe.getText());
			System.out.println("Dans BoutonOKListener de fenetre : jtfDebit : " + jtfDebit.getText());
			if (jtfDebit.getText() != "" && jtfDebit.getText() != "0" && jtfDebit.getText().length() != 0) {
				try {
					myOperationDTO.setDebitOpe(Double.parseDouble(jtfDebit.getText()));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					System.out.println("Dans BoutonOK de fenetre : Montant DEBIT KO : " + jtfDebit.getText());
					e1.printStackTrace();
				}
			} else {
				myOperationDTO.setDebitOpe(0);
			}
			if (jtfCredit.getText() != "" && jtfCredit.getText() != "0" && jtfCredit.getText().length() != 0) {
				try {
					myOperationDTO.setCreditOpe(Double.parseDouble(jtfCredit.getText()));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					System.out.println("Dans BoutonOK de fenetre : Montant Credit KO : " + jtfCredit.getText());
				}
			} else {
				myOperationDTO.setCreditOpe(0);
			}
			System.out.println(
					"Dans BoutonOKListener de fenetre : debit d'operation DTO : " + myOperationDTO.getDebitOpe());
			System.out.println(
					"Dans BoutonOKListener de fenetre : debit d'operation DTO :  " + myOperationDTO.getCreditOpe());

			myOperationDTO.setDetailOpe(jtfDetailOpe.getText());
			myOperationDTO.setEtatOpe("NR"); // Etat non rapproché
			myOperationDTO.setEchId(0);
			myGestionOperation.create(myOperationDTO);
			clearSaisieOpe();
			// BoutonFiltreListener();
			// System.out.println("appel création Operation");
			OperationTableau model = ((OperationTableau) tableOperation.getModel());
			model.filters(whereClause);

		}

	}

	// Execution du bouton Filtrer
	class BoutonFiltreListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			long dateLong = 0;
			LocalDate dateLimite = null;
			String where1 = "";
			String where2 = "";
			String where3 = "";

			if (valFiltreTiers.isEmpty()) {
				valFiltreTiers = "Tout";
			}
			if (valFiltreTiers != "Tout") {
				where1 = "t.libTiers = '" + valFiltreTiers + "'";
				System.out.println("Dans BoutonFiltreListener de Fenetre : where1 : " + where1);
			}
			if (valFiltreDuree.isEmpty()) {
				valFiltreDuree = "Tout";
			}
			switch (valFiltreDuree) {
			case "Tout":
				break;
			case "1 mois":
				dateLimite = dateJour.minusMonths(1);
				dateLong = dateLimite.toEpochDay();
				where2 = " o.dateOpeLong >= '" + dateLong + "'";
				System.out.println("Dans BoutonFiltreListener de Fenetre : where2 : " + where2);
				break;
			case "3 mois":
				dateLimite = dateJour.minusMonths(3);
				dateLong = dateLimite.toEpochDay();
				where2 = " o.dateOpeLong >= '" + dateLong + "'";
				System.out.println("Dans BoutonFiltreListener de Fenetre : where2 : " + where2);
				break;
			case "1 an":
				dateLimite = dateJour.minusYears(1);
				dateLong = dateLimite.toEpochDay();
				where2 = " o.dateOpeLong >= '" + dateLong + "'";
				System.out.println("Dans BoutonFiltreListener de Fenetre : where2 : " + where2);
				break;
			default:
				throw new IllegalArgumentException(
						"Dans BoutonFiltreListener de fenetre: default du case valFiltreDuree" + valFiltreDuree);
			}
			if (valFiltreRappro.isEmpty()) {
				valFiltreRappro = "Tout";
			}
			switch (valFiltreRappro) {
			case "Tout":
				break;
			case "Non rapproches":
				where3 = "o.etatOpe != 'X'";
				break;
			case "Rapproches":
				where3 = "o.etatOpe = 'X'";
				break;
			default:
				throw new IllegalArgumentException(
						"Dans BoutonFiltreListener de fenetre: default du case valFiltrerappro" + valFiltreRappro);
			}
			System.out.println("dans BoutonFiltreListener de fenetre : valFiltreTiers: " + valFiltreTiers);
			System.out.println("dans BoutonFiltreListener de fenetre : valFiltreDuree: " + valFiltreDuree);
			System.out.println("dans BoutonFiltreListener de fenetre : valFiltreRappro: " + valFiltreRappro);
			if (!where1.isEmpty() || !where2.isEmpty() || !where3.isEmpty()) {
				if (!where1.isEmpty()) {
					whereClause = "where " + where1;
					if (!where2.isEmpty()) {
						System.out.println("Dans BoutonFiltreListener de fenetre   where2 != null");
						whereClause = whereClause + " and " + where2;
						System.out.println("Dans BoutonFiltreListener de Fenetre : whereClause : " + whereClause);
					}
					if (!where3.isEmpty()) {
						whereClause = whereClause + " and " + where3;
					}
				} else {
					if (!where2.isEmpty()) {
						whereClause = "where " + where2;
						if (!where3.isEmpty()) {
							whereClause = whereClause + " and " + where3;
						}
					} else
						whereClause = "where " + where3;
				}

			}
			System.out.println("Dans BoutonFiltreListener de Fenetre : whereClause : " + whereClause);
			OperationTableau model = ((OperationTableau) tableOperation.getModel());
			model.filters(whereClause);
		}
	}

	public void refresh() {
		System.out.println("refresh");
		OperationTableau model = ((OperationTableau) tableOperation.getModel());
		model.filters(whereClause);
		model.fireTableDataChanged();
	}
	
	public void clearSaisieOpe() {
		comboTiers.setSelectedIndex(0);
		comboCategorie.setSelectedIndex(0);
		comboTypeOpe.setSelectedIndex(0);
		jtfDateOpe.requestFocus();
		jtfTiers.setText("");
		jtfDetailOpe.setText("");
		jtfTypeOpe.setText("CB");
		jtfCategOpe.setText("");
		jtfDebit.setText("");
		jtfCredit.setText("");
	}

}
