package comptes.gui.onglets;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import comptes.gui.component.MyJTextField;
import comptes.gui.dto.OperationDTO;
import comptes.gui.listener.DateDocumentListener;
import comptes.gui.manager.RapproManager;
import comptes.gui.tableaux.BnpNrTableau;
import comptes.gui.tableaux.OpeNrTableau;
import comptes.gui.tableaux.RapproTableau;
import comptes.model.db.entity.Bnp;
import comptes.model.db.entity.DerRappro;
import comptes.model.db.entity.Operation;
import comptes.model.facade.DerRapproFacade;
import comptes.model.services.OperationUtil;
import comptes.util.DateUtil;

public class OngletRappro extends JSplitPane {

	private static final long serialVersionUID = 1L;

	private RapproManager myRapproMngr;
	private MyJTextField jtfMtInitial ;
	private MyJTextField jtfMtFinal ;
	private MyJTextField jtfDateRappro ;
	private MyJTextField jtfSommeDeb ;
	private MyJTextField jtfSommeCred ;
	private MyJTextField jtfDiff ;

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
		jtfMtInitial = new MyJTextField(myDerRappro.getDerSolde().toString());
		jtfMtFinal = new MyJTextField();
		LocalDate dateJour = LocalDate.now();
		String dateJourStr = DateUtil.format(dateJour, "dd/MM/yyyy");
		jtfDateRappro = new MyJTextField(dateJourStr);
		jtfSommeDeb = new MyJTextField();
		jtfSommeCred = new MyJTextField();
		jtfDiff = new MyJTextField();


		labelMtInitial = new JLabel("Solde Initial");
		labelMtFinal = new JLabel("Solde Final");
		labelDateRappro = new JLabel("Date rapprochement");
		labelSommeDeb = new JLabel("Somme Debits");
		labelSommeCred = new JLabel("Somme Credits");
		labelDiff = new JLabel("Reste � pointer");

		boutonValidRappro = new JButton("Valider");
		boutonAnnulRappro = new JButton("Annuler");

		Font police = new Font("Arial", Font.BOLD, 12);
		labelMtInitial.setFont(police);
		labelMtFinal.setFont(police);
		labelDateRappro.setFont(police);
		labelSommeDeb.setFont(police);
		labelSommeCred.setFont(police);
		labelDiff.setFont(police);

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
		myRapproMngr=new RapproManager(this);
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

		myRapproMngr.updateTableaux();
	}

	//Execution du bouton OK Operation
	class BoutonOKListener implements ActionListener {
		private OperationDTO myOperationDTO;
		private OperationUtil myGestionOperation;

		public void actionPerformed(ActionEvent e) {
			myOperationDTO = panelCreationOperation.createOpeDtoFromField();
			final JOptionPane frame;
			String res = panelCreationOperation.validateSaisieOpe();
			if (res != "") {
				frame = new JOptionPane();
				JOptionPane.showMessageDialog(frame, res, "Saisie erron�e", JOptionPane.WARNING_MESSAGE);
			} else {
				myGestionOperation = new OperationUtil();
				myGestionOperation.create(myOperationDTO);
				if (myRapproMngr.getTabSelectedCreationCheckBnp() != -1) {
					Bnp myBnp = myRapproMngr.getSelectedBnp();
					Operation myOperation = myRapproMngr.getMyOperation();
					// Tiers myTiers=myRapproMngr.getMyTiers();
					myRapproMngr.bnpListNrToRapproTableau(myBnp, myOperation, myOperationDTO.getTiers());
				}
				panelCreationOperation.clearSaisieOpe();
			}
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
	public MyJTextField getJtfDateRappro() {
		return jtfDateRappro;
	}
	public void setJtfDateRappro(MyJTextField jtfDateRappro) {
		this.jtfDateRappro = jtfDateRappro;
	}

	public MyJTextField getJtfMtInitial() {
		return jtfMtInitial;
	}
	public void setJtfMtInitial(MyJTextField jtfMtInitial) {
		this.jtfMtInitial = jtfMtInitial;
	}
	public MyJTextField getJtfMtFinal() {
		return jtfMtFinal;
	}
	public void setJtfMtFinal(MyJTextField jtfMtFinal) {
		this.jtfMtFinal = jtfMtFinal;
	}
	public PanelCreationOperation getPanelCreationOperation() {
		return panelCreationOperation;
	}
	public void setPanelCreationOperation(PanelCreationOperation panelCreationOperation) {
		this.panelCreationOperation = panelCreationOperation;
	}

}
