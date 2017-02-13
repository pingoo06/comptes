package comptes.gui.tableaux;

import javax.swing.table.AbstractTableModel;

import comptes.gui.manager.RapproManager;
import comptes.model.services.GestionRappro;
import comptes.util.log.LogRappro;

public abstract class CheckableTableau extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	protected int tabSelected;
	protected RapproManager myRapproMngr;
	protected GestionRappro myGestionRappro;
	

	// Remplit le tableau
	public CheckableTableau(RapproManager rapproMngr, GestionRappro gestionRappro) {
		LogRappro.logDebug("Début : constructeur RapproTableau tableau");
		tabSelected=-1;
		this.myRapproMngr=rapproMngr;
		this.myGestionRappro = gestionRappro;
	}


	public int getTabSelected() {
		return tabSelected;
	}
	
	public void resetTabSelected() {
		tabSelected=-1;
	}

}