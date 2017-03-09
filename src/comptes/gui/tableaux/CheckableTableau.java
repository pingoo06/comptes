package comptes.gui.tableaux;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import comptes.gui.manager.RapproManager;
import comptes.util.log.LogRappro;

public abstract class CheckableTableau extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	protected ArrayList<Integer> tabSelectedRapproManu;
	protected int tabSelectedCreationCheck;
	protected RapproManager myRapproMngr;
	

	// Remplit le tableau
	public CheckableTableau(RapproManager rapproMngr) {
		LogRappro.logDebug("Début : CheckableTableau");
		tabSelectedRapproManu=new ArrayList<>();
		tabSelectedCreationCheck=-1;
		this.myRapproMngr=rapproMngr;
	}


	
	public ArrayList<Integer> getTabSelectedRapproManu() {
		return tabSelectedRapproManu;
	}


	public void resetTabSelected() {
		tabSelectedRapproManu.clear();
	}


	public int getTabSelectedCreationCheck() {
		return tabSelectedCreationCheck;
	}


	public void resetTabSelectedCreationCheck() {
		tabSelectedCreationCheck=-1;
	}

}