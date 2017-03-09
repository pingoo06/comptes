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
import comptes.gui.manager.RapproSommesManager;
import comptes.gui.tableaux.BnpNrTableau;
import comptes.gui.tableaux.OpeNrTableau;
import comptes.gui.tableaux.RapproTableau;
import comptes.model.db.entity.Bnp;
import comptes.model.db.entity.Operation;
import comptes.model.services.OperationUtil;

public class OngletRappro extends JSplitPane {

	private static final long serialVersionUID = 1L;

	private RapproManager myRapproMngr;
	private RapproSommesManager myRapproSommesManager;
	private JPanel vTopR;
	private JPanel vBottomR;

	private JTable tableRappro;
	private JTable tableBnpNr;
	private JTable tableOpeNr;

	private PanelRappro panelRappro;
	private PanelCreationOperation panelCreationOperation;
	// bottom

	public OngletRappro() {
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createVerticalBox();
		vTopR = new JPanel();
		vBottomR = new JPanel();
		// Box b1 = Box.createHorizontalBox();
		// Box b2 = Box.createVerticalBox();

		setTopComponent(vTopR);
		setBottomComponent(vBottomR);
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setDividerLocation(250);
		vTopR.setLayout(new BorderLayout());
		panelRappro = new PanelRappro();
		panelCreationOperation = new PanelCreationOperation();
		vTopR.add(panelRappro, BorderLayout.NORTH);
		
		panelRappro.getBoutonValidRappro().addActionListener(new BoutonValidRapproListener());
		panelRappro.getBoutonAnnulRappro().addActionListener(new BoutonAnnulRapproListener());
		panelRappro.getBoutonStartRappro().addActionListener(new BoutonStartRapproListener(this));
		// panelCreationOperation.getBoutonOKOpe().addActionListener(new BoutonOKListener());
		// // Tableau rappro
		//// old myGestionRappro.ecritOpeCredit();
		myRapproSommesManager = new RapproSommesManager(this);
		myRapproMngr = new RapproManager(this);
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
		panelCreationOperation.getBoutonOKOpe().addActionListener(new
		 BoutonOKListener());
	
		 b2.add(panelCreationOperation);
		 vBottomR.add(b2);
		 tableBnpNr.setAutoCreateRowSorter(true);
		 myRapproMngr.updateTableaux();
	}

	

	
	// Execution du bouton OK Operation
	class BoutonOKListener implements ActionListener {
		private OperationDTO myOperationDTO;
		private OperationUtil myGestionOperation;

		public void actionPerformed(ActionEvent e) {
			myOperationDTO = panelCreationOperation.createOpeDtoFromField();
			final JOptionPane frameO;
			String resOpe = panelCreationOperation.validateSaisieOpe();
			if (resOpe != "") {
				frameO = new JOptionPane();
				JOptionPane.showMessageDialog(frameO, resOpe, "Saisie erron�e", JOptionPane.WARNING_MESSAGE);
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
		private OngletRappro myOngletRappro;
		public BoutonStartRapproListener(OngletRappro myOngletRappro) {
			super();
			this.myOngletRappro = myOngletRappro;
		}

		public void actionPerformed(ActionEvent e) {
			final JOptionPane frameR;
			Box b1 = Box.createHorizontalBox();
			Box b2 = Box.createVerticalBox();
			// Tableau rappro
			// old myGestionRappro.ecritOpeCredit();
			// myRapproMngr=new RapproManager(this);
			String resRappro = panelRappro.validateSaisieRappro();
			if (!resRappro.equals("")) {
				frameR = new JOptionPane();
				JOptionPane.showMessageDialog(frameR, resRappro, "Saisie erron�e", JOptionPane.WARNING_MESSAGE);
			} 
				else {
					myRapproSommesManager.init(Double.parseDouble(getPanelRappro().getJtfMtInitial().getText()),
							Double.parseDouble(getPanelRappro().getJtfMtFinal().getText()));

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
			panelRappro.reset();
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

	// public MyJTextField getJtfDateRappro() {
	// return jtfDateRappro;
	// }
	// public void setJtfDateRappro(MyJTextField jtfDateRappro) {
	// this.jtfDateRappro = jtfDateRappro;
	// }
	//
	// public MyJTextField getJtfMtInitial() {
	// return jtfMtInitial;
	// }
	// public void setJtfMtInitial(MyJTextField jtfMtInitial) {
	// this.jtfMtInitial = jtfMtInitial;
	// }
	// public MyJTextField getJtfMtFinal() {
	// return jtfMtFinal;;
	// }
	// public void setJtfMtFinal(MyJTextField jtfMtFinal) {
	// this.jtfMtFinal = jtfMtFinal;
	// }
	public PanelCreationOperation getPanelCreationOperation() {
		return panelCreationOperation;
	}

	public void setPanelCreationOperation(PanelCreationOperation panelCreationOperation) {
		this.panelCreationOperation = panelCreationOperation;
	}

	public PanelRappro getPanelRappro() {
		return panelRappro;
	}

	public void setPanelMontantsRappro(PanelRappro panelMontantsRappro) {
		this.panelRappro = panelMontantsRappro;
	}


	public RapproSommesManager getMyRapproSommesManager() {
		return myRapproSommesManager;
	}

}
