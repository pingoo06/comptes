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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import comptes.gui.combo.CategorieCombo;
import comptes.gui.combo.TiersCombo;
import comptes.gui.combo.TypeOpeCombo;
import comptes.gui.dto.OperationDTO;
import comptes.gui.listener.DateDocumentListener;
import comptes.gui.manager.RapproManager;
import comptes.gui.tableaux.BnpNrTableau;
import comptes.gui.tableaux.OpeNrTableau;
import comptes.gui.tableaux.RapproTableau;
import comptes.model.db.entity.DerRappro;
import comptes.model.db.entity.Tiers;
import comptes.model.facade.DerRapproFacade;
import comptes.model.facade.TiersFacade;
import comptes.model.services.GestionOperation;
import comptes.model.services.GestionRappro;
import comptes.util.DateUtil;
import comptes.util.log.LogRappro;

public class OngletRappro extends JSplitPane {

	private static final long serialVersionUID = 1L;

	private RapproManager myRapproMngr;
	private GestionRappro myGestionRappro;

	private JTextField jtfMtInitial ;
	private JTextField jtfMtFinal ;
	private JTextField jtfDateRappro ;
	private JTextField jtfSommeDeb ;
	private JTextField jtfSommeCred ;
	private JTextField jtfDiff ;

	private JLabel labelMtInitial ;
	private JLabel labelMtFinal;
	private JLabel labelDateRappro;
	private JLabel labelSommeDeb ;
	private JLabel labelSommeCred ;
	private JLabel labelDiff;

	private JButton boutonValidRappro ;
	private JButton boutonAnnulRappro ;
	
	private JPanel vTopR ;
	private JPanel vBottomR ;
	private JPanel paramPanR ;
	private JPanel saisieOpePan;
	private JPanel boutonPan ;

	private JTable tableRappro;
	private JTable tableBnpNr;
	private JTable tableOpeNr;

	//bottom
	private JButton boutonOKOpe;
	private JButton boutonAnnulOpe;
	private JTextField jtfDateOpe;
	private JTextField jtfDetailOpe;
//	private JTextField jtfTypeOpe;
//	private JTextField jtfCategOpe;
	private JTextField jtfDebit;
	private JTextField jtfCredit;
//	private JTextField jtfTiers;

	// combo operation
	private TypeOpeCombo comboTypeOpe;
	private TiersCombo comboTiers;
	private CategorieCombo comboCategorie ;


	private JLabel labelTypeOpe;
	private JLabel labelTiers;
	private JLabel labelDateOpe;
	private JLabel labelDebit;
	private JLabel labelCredit;
	private JLabel labelCategOpe;
	private JLabel labelDetailOpe;

	public OngletRappro() {
		myRapproMngr=new RapproManager(this);
		vTopR 	  = new JPanel();
		vBottomR  = new JPanel();
		 Box b1 = Box.createHorizontalBox();
		 Box b2 = Box.createHorizontalBox();
		 Box b2suite = Box.createHorizontalBox();
		 Box b3 = Box.createHorizontalBox();
		 Box b4 = Box.createVerticalBox();
		 
		 
		setTopComponent(vTopR);
		setBottomComponent(vBottomR);
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setDividerLocation(250);

		DerRappro myDerRappro = new DerRappro();
		DerRapproFacade myDerRapproFacade = new DerRapproFacade();
		myDerRappro=myDerRapproFacade.find(1);
		jtfMtInitial = new JTextField(myDerRappro.getDerSolde().toString());
		jtfMtFinal = new JTextField();
		LocalDate dateJour = LocalDate.now();
		String dateJourStr = DateUtil.format(dateJour, "dd/MM/yyyy");
		jtfDateRappro = new JTextField(dateJourStr);
		jtfSommeDeb = new JTextField();
		jtfSommeCred = new JTextField();
		jtfDiff = new JTextField();


		labelMtInitial = new JLabel("Solde Initial");
		labelMtFinal = new JLabel("Solde Final");
		labelDateRappro = new JLabel("Date rapprochement");
		labelSommeDeb = new JLabel("Somme Debits");
		labelSommeCred = new JLabel("Somme Credits");
		labelDiff = new JLabel("Reste à pointer");

		boutonValidRappro = new JButton("Valider");
		boutonAnnulRappro = new JButton("Annuler");
		Font police = new Font("Arial", Font.BOLD, 12);
		labelMtInitial.setFont(police);
		jtfMtInitial.setFont(police);
		jtfMtInitial.setPreferredSize(new Dimension(100, 20));
		jtfMtInitial.setForeground(Color.BLUE);
		labelMtFinal.setFont(police);
		jtfMtFinal.setFont(police);
		jtfMtFinal.setPreferredSize(new Dimension(100, 20));
		jtfMtFinal.setForeground(Color.BLUE);
		labelDateRappro.setFont(police);
		jtfDateRappro.setFont(police);
		jtfDateRappro.setPreferredSize(new Dimension(100, 20));
		jtfDateRappro.setForeground(Color.BLUE);
		labelSommeDeb.setFont(police);
		jtfSommeDeb.setFont(police);
		jtfSommeDeb.setPreferredSize(new Dimension(100, 20));
		jtfSommeDeb.setForeground(Color.BLUE);
		labelSommeCred.setFont(police);
		jtfSommeCred.setFont(police);
		jtfSommeCred.setPreferredSize(new Dimension(100, 20));
		jtfSommeCred.setForeground(Color.BLUE);
		labelDiff.setFont(police);
		jtfDiff.setFont(police);
		jtfDiff.setPreferredSize(new Dimension(100, 20));
		jtfDiff.setForeground(Color.BLUE);

		vTopR.setLayout(new BorderLayout());
		paramPanR = new JPanel();
		paramPanR.setPreferredSize(new Dimension(900, 80));
		paramPanR.add(labelMtInitial);
		paramPanR.add(jtfMtInitial);
		paramPanR.add(labelMtFinal);
		paramPanR.add(jtfMtFinal);
		paramPanR.add(labelDateRappro);
		paramPanR.add(jtfDateRappro);
		paramPanR.add(labelSommeDeb);
		paramPanR.add(jtfSommeDeb);
		paramPanR.add(labelSommeCred);
		paramPanR.add(jtfSommeCred);
		paramPanR.add(labelDiff);
		paramPanR.add(jtfDiff);
		paramPanR.add(boutonAnnulRappro);
		paramPanR.add(boutonValidRappro);
		vTopR.add(paramPanR, BorderLayout.NORTH);
		boutonValidRappro.addActionListener(new BoutonValidRapproListener());
		boutonAnnulRappro.addActionListener(new BoutonAnnulRapproListener());
		//saisie Operation
		jtfDateOpe = new JTextField(dateJourStr);
		jtfDetailOpe = new JTextField("");
//		jtfTypeOpe = new JTextField("");
//		jtfCategOpe = new JTextField("");
		jtfDebit = new JTextField("");
		jtfCredit = new JTextField("");
//		jtfTiers = new JTextField("");
		saisieOpePan = new JPanel();

		// combo operation
		comboTypeOpe = new TypeOpeCombo();
		comboTiers = new TiersCombo();
		comboCategorie = new CategorieCombo();

		//		JPanel vTop = new JPanel();
		//		JPanel vBottom = new JPanel();

		labelTypeOpe = new JLabel("TypeOpe");
		labelTiers = new JLabel("Tiers");
		labelDateOpe = new JLabel("Date Ope");
		labelDebit = new JLabel("Debit");
		labelCredit = new JLabel("Credit");
		labelCategOpe = new JLabel("Categorie");
		labelDetailOpe = new JLabel("DetailOpe");

		boutonPan = new JPanel();
		boutonAnnulOpe = new JButton("Annuler"); 
		boutonOKOpe = new JButton("Valider"); 
		boutonPan.add(boutonAnnulOpe);
		boutonPan.add(boutonOKOpe);

		boutonOKOpe.addActionListener(new BoutonOKListener());
		boutonAnnulOpe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearSaisieOpe();
			}
		});
		comboTiers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				jtfTiers.setText(comboTiers.getSelectedItem().toString());
				TiersFacade myTiersFacade = new TiersFacade();
				int idTiers = myTiersFacade.findLib(comboTiers.getSelectedItem().toString());
				Tiers myTiers = myTiersFacade.find(idTiers);
				comboCategorie.setSelectedItem(myTiers.getDerCategDeTiers());
//				jtfCategOpe.setText(myTiers.getDerCategDeTiers());
			}
		});
		comboTypeOpe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				jtfTypeOpe.setText(comboTypeOpe.getSelectedItem().toString());
				String libType = comboTypeOpe.getSelectedItem().toString();
				if (libType == "RETRAIT") {
					comboTiers.setSelectedItem("RETRAIT");
//					jtfTiers.setText("RETRAIT");
					comboCategorie.setSelectedItem("Retrait d especes");
//					jtfCategOpe.setText("Retrait d especes");
					jtfDebit.requestFocus();
				}
			}
		});

		comboCategorie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				jtfCategOpe.setText(comboCategorie.getSelectedItem().toString());
				// System.out.println("dans combo categ add action table
				// selected item : "
				// + comboCategorie.getSelectedItem().toString());
			}
		});

		jtfDateOpe.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});

		jtfDateOpe.getDocument().addDocumentListener(new DateDocumentListener(jtfDateOpe));


		// Operation
//		jtfTypeOpe.setFont(police);
//		jtfTypeOpe.setPreferredSize(new Dimension(100, 20));
//		jtfTypeOpe.setForeground(Color.BLUE);
//		jtfTiers.setFont(police);
//		jtfTiers.setPreferredSize(new Dimension(100, 20));
//		jtfTiers.setForeground(Color.BLUE);
//		jtfCategOpe.setFont(police);
//		jtfCategOpe.setPreferredSize(new Dimension(100, 20));
//		jtfCategOpe.setForeground(Color.BLUE);
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

		b2.add(labelDateOpe, BorderLayout.CENTER);
		b2.add(jtfDateOpe, BorderLayout.CENTER);
		b2.add(labelTypeOpe, BorderLayout.CENTER);
		b2.add(comboTypeOpe, BorderLayout.CENTER);
//		b2.add(jtfTypeOpe, BorderLayout.CENTER);
		b2.add(labelTiers, BorderLayout.CENTER);
		b2.add(comboTiers, BorderLayout.CENTER);
//		b2.add(jtfTiers, BorderLayout.CENTER);
		b2.add(labelCategOpe, BorderLayout.CENTER);
		b2.add(comboCategorie, BorderLayout.CENTER);
//		b2.add(jtfCategOpe, BorderLayout.CENTER);
		b2suite.add(labelDebit, BorderLayout.CENTER);
		b2suite.add(jtfDebit, BorderLayout.CENTER);
		b2suite.add(labelCredit, BorderLayout.CENTER);
		b2suite.add(jtfCredit, BorderLayout.CENTER);
		b2suite.add(labelDetailOpe, BorderLayout.CENTER);
		b2suite.add(jtfDetailOpe, BorderLayout.CENTER);
		b3.add(boutonAnnulOpe,BorderLayout.EAST);
		b3.add(boutonOKOpe,BorderLayout.EAST);
//		saisieOpePan.add(boutonPan);

		// Tableau rappro
		myGestionRappro = new GestionRappro();
		myGestionRappro.prepaRappro();
		tableRappro = new JTable(new RapproTableau(myRapproMngr,myGestionRappro));
		vTopR.add(new JScrollPane(tableRappro), BorderLayout.CENTER);
		tableRappro.setAutoCreateRowSorter(true);
		tableOpeNr = new JTable(new OpeNrTableau(myRapproMngr,myGestionRappro));
		tableOpeNr.setAutoCreateRowSorter(true);
		tableBnpNr = new JTable(new BnpNrTableau(myRapproMngr,myGestionRappro));
		b1.add(new JScrollPane(tableBnpNr));
		b1.add(new JScrollPane(tableOpeNr));
		b1.add(saisieOpePan);
		b4.add(b1);
		b4.add(b2);
		b4.add(b2suite);
		b4.add(b3);
		vBottomR.add(b4);
		tableBnpNr.setAutoCreateRowSorter(true);


		// AJOUT TESTS SUR DATECH
		// nico en faire une fonction ?
		jtfDateRappro.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});

		jtfDateRappro.getDocument().addDocumentListener(new DateDocumentListener(jtfDateRappro));



	}

	//Execution du bouton OK Operation
	class BoutonOKListener implements ActionListener {
		private OperationDTO myOperationDTO ;
		private GestionOperation myGestionOperation;
		public void actionPerformed(ActionEvent e) {
			myOperationDTO = new OperationDTO();
			myGestionOperation = new GestionOperation();
			myOperationDTO.setTypeOpe(comboTypeOpe.getSelectedItem().toString());
			myOperationDTO.setDateOpe(jtfDateOpe.getText());
			myOperationDTO.setTiers(comboTiers.getSelectedItem().toString());
			myOperationDTO.setCategOpe(comboCategorie.getSelectedItem().toString());
			LogRappro.logDebug("Dans BoutonOKListener de fenetre : jtfDebit : " + jtfDebit.getText());
			if (jtfDebit.getText() != "" && jtfDebit.getText() != "0" && jtfDebit.getText().length() != 0) {
				try {
					myOperationDTO.setDebitOpe(Double.parseDouble(jtfDebit.getText()));
				} catch (NumberFormatException e1) {
					LogRappro.logError("Montant DEBIT KO : " + jtfDebit.getText(),e1);
				}
			} else {
				myOperationDTO.setDebitOpe(0);
			}
			if (jtfCredit.getText() != "" && jtfCredit.getText() != "0" && jtfCredit.getText().length() != 0) {
				try {
					myOperationDTO.setCreditOpe(Double.parseDouble(jtfCredit.getText()));
				} catch (NumberFormatException e1) {
					LogRappro.logError("Montant Credit KO : " + jtfCredit.getText(),e1);
				}
			} else {
				myOperationDTO.setCreditOpe(0);
			}
			LogRappro.logDebug("debit d'operation DTO : " + myOperationDTO.getDebitOpe());
			LogRappro.logDebug("debit d'operation DTO :  " + myOperationDTO.getCreditOpe());

			myOperationDTO.setDetailOpe(jtfDetailOpe.getText());
			myOperationDTO.setEtatOpe("NR"); 
			myOperationDTO.setEchId(0);
			myGestionOperation.create(myOperationDTO);
			clearSaisieOpe();
		}
	}


	class BoutonValidRapproListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			myRapproMngr.validateRappro();
		}
	}

	class BoutonAnnulRapproListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}
	public void clearTop() {
		//		comboTiersE.setSelectedIndex(0);
		//		comboCategorieE.setSelectedIndex(0);
		//		comboTypeEch.setSelectedIndex(0);
		//		comboTiersE.requestFocus();
		//		jtfDateRappro.setText("");
		//		// jtfDateOpe.requestFocus();
		//		jtfTiersEch.setText("");
		//		jtfNbEch.setText("");
		//		jtfTypeEch.setText("Prelevement");
		//		jtfCategEch.setText("");
		//		jtfMontantEch.setText("");
	}
	public RapproManager getMyRapproMngr() {
		return myRapproMngr;
	}
	public JTable getTableRappro() {
		return tableRappro;
	}
	public JTable getTableBnpNr() {
		return tableBnpNr;
	}
	public JTable getTableOpeNr() {
		return tableOpeNr;
	}
	public GestionRappro getMyGestionRappro() {
		return myGestionRappro;
	}
	public JTextField getJtfDateRappro() {
		return jtfDateRappro;
	}
	public void setJtfDateRappro(JTextField jtfDateRappro) {
		this.jtfDateRappro = jtfDateRappro;
	}

	public JTextField getJtfMtInitial() {
		return jtfMtInitial;
	}
	public void setJtfMtInitial(JTextField jtfMtInitial) {
		this.jtfMtInitial = jtfMtInitial;
	}
	public JTextField getJtfMtFinal() {
		return jtfMtFinal;
	}
	public void setJtfMtFinal(JTextField jtfMtFinal) {
		this.jtfMtFinal = jtfMtFinal;
	}
	public void clearSaisieOpe() {
		comboTiers.setSelectedIndex(0);
		comboCategorie.setSelectedIndex(0);
		comboTypeOpe.setSelectedIndex(0);
		jtfDateOpe.requestFocus();
//		jtfTiers.setText("");
		jtfDetailOpe.setText("");
//		jtfTypeOpe.setText("");
//		jtfCategOpe.setText("");
		jtfDebit.setText("");
		jtfCredit.setText("");
	}
}
