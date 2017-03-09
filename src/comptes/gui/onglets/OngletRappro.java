package comptes.gui.onglets;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import comptes.gui.dto.OperationDTO;
import comptes.gui.manager.RapproManager;
import comptes.gui.tableaux.BnpNrTableau;
import comptes.gui.tableaux.OpeNrTableau;
import comptes.gui.tableaux.RapproTableau;
import comptes.model.db.entity.Bnp;
import comptes.model.db.entity.Operation;
import comptes.model.services.OperationUtil;

public class OngletRappro extends JSplitPane {

	private static final long serialVersionUID = 1L;

	private RapproManager myRapproMngr;
	
	private JPanel vTopR ;
	private JPanel vBottomR ;

	private JTable tableRappro;
	private JTable tableBnpNr;
	private JTable tableOpeNr;

	private PanelMontantsRappro panelMontantsRappro;
	private PanelCreationOperation panelCreationOperation;
	//bottom

	public OngletRappro() {
		
		vTopR 	  = new JPanel();
		vBottomR  = new JPanel();
//		 Box b1 = Box.createHorizontalBox();
//		 Box b2 = Box.createVerticalBox();
		 
		setTopComponent(vTopR);
		setBottomComponent(vBottomR);
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setDividerLocation(250);
		vTopR.setLayout(new BorderLayout());
		panelMontantsRappro = new PanelMontantsRappro();
		vTopR.add(panelMontantsRappro, BorderLayout.NORTH);
		panelMontantsRappro.getBoutonValidRappro().addActionListener(new BoutonValidRapproListener());
		panelMontantsRappro.getBoutonAnnulRappro().addActionListener(new BoutonAnnulRapproListener());

//		// Tableau rappro
////old		myGestionRappro.ecritOpeCredit();
		myRapproMngr=new RapproManager(this);
//		myRapproMngr.prepaRappro();
//		tableRappro = new JTable(new RapproTableau(myRapproMngr));
//		vTopR.add(new JScrollPane(tableRappro), BorderLayout.CENTER);
//		tableRappro.setAutoCreateRowSorter(true);
//		tableOpeNr = new JTable(new OpeNrTableau(myRapproMngr));
//		tableOpeNr.setAutoCreateRowSorter(true);
//		tableBnpNr = new JTable(new BnpNrTableau(myRapproMngr));
//		b1.add(new JScrollPane(tableBnpNr));
//		b1.add(new JScrollPane(tableOpeNr));
//		b2.add(b1);
//	
//		panelCreationOperation = new PanelCreationOperation();
//		panelCreationOperation.getBoutonOKOpe().addActionListener(new BoutonOKListener());
//		
//		b2.add(panelCreationOperation);
//		vBottomR.add(b2);
//		tableBnpNr.setAutoCreateRowSorter(true);
//		myRapproMngr.updateTableaux();
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
				JOptionPane.showMessageDialog(frame, res, "Saisie erronée", JOptionPane.WARNING_MESSAGE);
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

	class BoutonStartRapproListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			 Box b1 = Box.createHorizontalBox();
			 Box b2 = Box.createVerticalBox();
				// Tableau rappro
			//old		myGestionRappro.ecritOpeCredit();
//					myRapproMngr=new RapproManager(this);
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
					myRapproMngr.updateTableaux();
//					myRapproMngr.startRappro();
			
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
//	public MyJTextField getJtfDateRappro() {
//		return jtfDateRappro;
//	}
//	public void setJtfDateRappro(MyJTextField jtfDateRappro) {
//		this.jtfDateRappro = jtfDateRappro;
//	}
//
//	public MyJTextField getJtfMtInitial() {
//		return jtfMtInitial;
//	}
//	public void setJtfMtInitial(MyJTextField jtfMtInitial) {
//		this.jtfMtInitial = jtfMtInitial;
//	}
//	public MyJTextField getJtfMtFinal() {
//		return jtfMtFinal;;
//	}
//	public void setJtfMtFinal(MyJTextField jtfMtFinal) {
//		this.jtfMtFinal = jtfMtFinal;
//	}
	public PanelCreationOperation getPanelCreationOperation() {
		return panelCreationOperation;
	}
	public void setPanelCreationOperation(PanelCreationOperation panelCreationOperation) {
		this.panelCreationOperation = panelCreationOperation;
	}
	public PanelMontantsRappro getPanelMontantsRappro() {
		return panelMontantsRappro;
	}
	public void setPanelMontantsRappro(PanelMontantsRappro panelMontantsRappro) {
		this.panelMontantsRappro = panelMontantsRappro;
	}

}
