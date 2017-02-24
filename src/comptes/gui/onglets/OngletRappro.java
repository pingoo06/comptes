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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import comptes.gui.dto.OperationDTO;
import comptes.gui.listener.DateDocumentListener;
import comptes.gui.manager.RapproManager;
import comptes.gui.tableaux.BnpNrTableau;
import comptes.gui.tableaux.OpeNrTableau;
import comptes.gui.tableaux.RapproTableau;
import comptes.model.db.entity.DerRappro;
import comptes.model.facade.DerRapproFacade;
import comptes.model.services.OperationUtil;
import comptes.util.DateUtil;

public class OngletRappro extends JSplitPane {

	private static final long serialVersionUID = 1L;

	private RapproManager myRapproMngr;

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

	private JTable tableRappro;
	private JTable tableBnpNr;
	private JTable tableOpeNr;

	private PanelCreationOperation panelCreationOperation;
	//bottom

	public OngletRappro() {
		myRapproMngr=new RapproManager(this);
		vTopR 	  = new JPanel();
		vBottomR  = new JPanel();
		 Box b1 = Box.createHorizontalBox();
		 Box b2 = Box.createVerticalBox();
		 
		 
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

		// Tableau rappro
//		myGestionRappro.ecritOpeCredit();
		myRapproMngr.prepaRappro();
		tableRappro = new JTable(new RapproTableau(myRapproMngr));
		vTopR.add(new JScrollPane(tableRappro), BorderLayout.CENTER);
		tableRappro.setAutoCreateRowSorter(true);
		tableOpeNr = new JTable(new OpeNrTableau(myRapproMngr));
		tableOpeNr.setAutoCreateRowSorter(true);
		tableBnpNr = new JTable(new BnpNrTableau(myRapproMngr));
		b1.add(new JScrollPane(tableBnpNr));
		b1.add(new JScrollPane(tableOpeNr));
		b2.add(b1);
		panelCreationOperation = new PanelCreationOperation();
		
		panelCreationOperation.getBoutonOKOpe().addActionListener(new BoutonOKListener());
		
		b2.add(panelCreationOperation);
		vBottomR.add(b2);
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
		private OperationUtil myGestionOperation;
		public void actionPerformed(ActionEvent e) {
			myOperationDTO = panelCreationOperation.createOpeDtoFromField();
			myGestionOperation = new OperationUtil();
			myGestionOperation.create(myOperationDTO);
			panelCreationOperation.clearSaisieOpe();
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

}
