package comptes.gui.onglets;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import comptes.gui.dto.OperationDTO;
import comptes.gui.manager.RapproManager;
import comptes.gui.manager.RapproSommesManager;
import comptes.gui.tableaux.BnpNrTableau;
import comptes.gui.tableaux.OpeNrTableau;
import comptes.gui.tableaux.RapproTableau;
import comptes.model.db.entity.Bnp;
import comptes.model.db.entity.Operation;
import comptes.model.services.OperationUtil;
import comptes.util.MyDate;

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
		panelRappro.getBoutonStartRappro().addActionListener(new BoutonRapprocherListener(this));
		// panelCreationOperation.getBoutonOKOpe().addActionListener(new BoutonOKListener());
		// // Tableau rappro
		//// old myGestionRappro.ecritOpeCredit();
		myRapproSommesManager = new RapproSommesManager(this);
		myRapproMngr = new RapproManager(this);
		myRapproMngr.prepaRappro(myRapproSommesManager);
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
		//ICI

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"csv", "jpg", "gif","csv");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " +
					chooser.getSelectedFile().getName());
			File f = chooser.getSelectedFile();
			f.renameTo(new File("res/essai"+(new MyDate()).toDbFormat()+".csv"));
		}
		//		    FINICI
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
				JOptionPane.showMessageDialog(frameO, resOpe, "Saisie erronée", JOptionPane.WARNING_MESSAGE);
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
			} 
				else {
					
			 double	mtDiffARapprocher= myRapproSommesManager.initResteAPointer(Double.parseDouble(getPanelRappro().getJtfMtInitial().getText()),
							Double.parseDouble(getPanelRappro().getJtfMtFinal().getText()));
					panelRappro.getJtfDiff().setText(""+mtDiffARapprocher);
					panelRappro.getBoutonStartRappro().setEnabled(false);
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
