package comptes.gui.tableaux;

import javax.swing.table.AbstractTableModel;

import comptes.gui.manager.RapproManager;
import comptes.util.log.LogRappro;

public abstract class CheckableTableau extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	protected int tabSelected;
	protected RapproManager myRapproMngr;
	

	// Remplit le tableau
	public CheckableTableau(RapproManager rapproMngr) {
		LogRappro.logDebug("Début : constructeur RapproTableau tableau");
		tabSelected=-1;
		this.myRapproMngr=rapproMngr;
	}


	public int getTabSelected() {
		return tabSelected;
	}
	
	public void resetTabSelected() {
		tabSelected=-1;
	}

}