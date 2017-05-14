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
import comptes.model.facade.BnpFacade;
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
	
	private Box b1;
	private Box b2;
	private Box b3;
	
	public OngletRappro() {
		b1 = Box.createHorizontalBox();
		b2 = Box.createVerticalBox();
		b3 = Box.createHorizontalBox();
		vTopR = new JPanel();
		vBottomR = new JPanel();

		setTopComponent(vTopR);
		setBottomComponent(vBottomR);
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setDividerLocation(250);
		vTopR.setLayout(new BorderLayout());
		panelRappro = new PanelRappro();
		panelCreationOperation = new PanelCreationOperation();
		vTopR.add(panelRappro, BorderLayout.NORTH);
		panelRappro.getBoutonAnnulRappro().addActionListener(new BoutonAnnulRapproListener());
		panelRappro.getBoutonStartRappro().addActionListener(new BoutonRapprocherListener(this));
		myRapproSommesManager = new RapproSommesManager(this);
		myRapproMngr = new RapproManager(this);
		BnpFacade myBnpFacade = new BnpFacade();
		if (myBnpFacade.isFull()) {
			remplitOngletRappro();
			}
		}

	
	public void remplitOngletRappro () {
		myRapproMngr.prepaRappro();
		tableRappro=new JTable(new RapproTableau(myRapproMngr));
		tableRappro.setAutoCreateRowSorter(true);
		tableOpeNr = new JTable(new OpeNrTableau(myRapproMngr));
		tableOpeNr.setAutoCreateRowSorter(true);
		tableBnpNr = new JTable(new BnpNrTableau(myRapproMngr));
		b1.add(new JScrollPane(tableBnpNr));
		b1.add(new JScrollPane(tableOpeNr));
		b3.add(new JScrollPane(tableRappro));
		b2.add(b1);
		panelCreationOperation = new PanelCreationOperation();
		panelCreationOperation.getBoutonOKOpe().addActionListener(new BoutonOKListener());
		b2.add(panelCreationOperation);
		vBottomR.add(b2);
		vTopR.add(b3, BorderLayout.CENTER);
		tableBnpNr.setAutoCreateRowSorter(true);
		myRapproMngr.updateTableaux();
		
	}
	
	// Execution du bouton OK Operation
	class BoutonOKListener implements ActionListener {
		private OperationDTO myOperationDTO;
		private OperationUtil myOperationUtil;

		public void actionPerformed(ActionEvent e) {
			myOperationDTO = panelCreationOperation.createOpeDtoFromField();
			final JOptionPane frameO;
			String resOpe = panelCreationOperation.validateSaisieOpe();
			if (resOpe != "") {
				frameO = new JOptionPane();
				JOptionPane.showMessageDialog(frameO, resOpe, "Saisie erronée", JOptionPane.WARNING_MESSAGE);
			} else {
				myRapproMngr.createNewOpe(myOperationDTO);
				panelCreationOperation.clearSaisieOpe();
			}
		}
	}

	class BoutonRapprocherListener implements ActionListener {
		private OngletRappro myOngletRappro;

		public BoutonRapprocherListener(OngletRappro myOngletRappro) {
			super();
			this.myOngletRappro = myOngletRappro;
		}

		public void actionPerformed(ActionEvent e) {
			final JOptionPane frameR;
			String resRappro = panelRappro.validateSaisieRappro();
			if (!resRappro.equals("")) {
				frameR = new JOptionPane();
				JOptionPane.showMessageDialog(frameR, resRappro, "Saisie erronée", JOptionPane.WARNING_MESSAGE);
			} else {
				BnpFacade myBnpFacade=new BnpFacade();
				if (!myBnpFacade.isFull()) {
					myBnpFacade.remplitBnp();
					myRapproMngr.ecritOpeCredit();
					remplitOngletRappro();
				}
				double mtDiffARapprocher = myRapproSommesManager.initResteAPointer(
						Double.parseDouble(getPanelRappro().getJtfMtInitial().getText()),
						Double.parseDouble(getPanelRappro().getJtfMtFinal().getText()));
				java.text.DecimalFormat df = new java.text.DecimalFormat("0.##");
				panelRappro.getJtfDiff().setText("" + df.format(mtDiffARapprocher));
				panelRappro.getBoutonStartRappro().setEnabled(false);
			}
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
