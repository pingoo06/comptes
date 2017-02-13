package comptes.gui.tableaux;

import comptes.gui.manager.RapproManager;
import comptes.model.db.entity.Operation;
import comptes.model.services.GestionRappro;
import comptes.util.log.LogRappro;

public class OpeNrTableau extends CheckableTableau {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "Date Ope", "Tiers", "Montant", "Check" };

	
	// Remplit le tableau
	public OpeNrTableau(RapproManager rapproMngr, GestionRappro gestionRappro) {
		super(rapproMngr,gestionRappro);
		LogRappro.logInfo("Début : constructeur OpeNrTableau tableau");
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return myGestionRappro.getMyOpeListNr().size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	// renvoie le contenu de chaque colonne de la liste
	public Object getValueAt(int rowIndex, int columnIndex) {
		LogRappro.logDebug("columnIndex : " + columnIndex + "RowIndex" + rowIndex);
		Operation current = myGestionRappro.getMyOpeListNr().get(rowIndex);
		LogRappro.logDebug("operation : " + current);
		switch (columnIndex) {
		case 0:
			return current.getDateOpe();
		case 1:
			return myGestionRappro.getLibTiersFromOpe(current);
		case 2:
			if (current.getMontantOpe() > 0) {
				return current.getMontantOpe();
			} else {
				return current.getMontantOpe() * -1;
			}
		case 3:
			return rowIndex==tabSelected ;
		default:
			throw new IllegalArgumentException("Dans Get ValueAT de RapproTableau TableauInvalid column index");
		}

	}

	// A implementer
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		 LogRappro.logInfo("Début : Set ValueAt de OpeNR Tableau"); 
		 if (columnIndex == 3) {
			 boolean checked = (boolean)aValue;
			 if(checked){
				 tabSelected=rowIndex;
				 myRapproMngr.chekNr();
			 }
			 else {
				 tabSelected=-1;
			 }
		 }
fireTableDataChanged();
	}

	// Retourne le type de chaque colonne
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (myGestionRappro.getMyOpeListNr().isEmpty()) {
			return Object.class;
		}
		LogRappro.logDebug("column	 index : " + columnIndex);
		Object val = getValueAt(0, columnIndex);
		LogRappro.logDebug("val : " + val);
		if (val == null) {
			return String.class;
		}
		return val.getClass();
	}

	// seule la colonne avec la checkBox est modifiable
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 3;
	}

	// supression de ligne
	public void deleteRow(int idx) {
		// LogRappro.LogInfo("Début : deleteRow de echeancier Tableau");
		// EcheancierBO echeancierBO = listEcheancierBO.get(idx);
		// listEcheancierBO.remove(idx);
		// fireTableRowsDeleted(idx, idx);
		// LogRappro.LogInfo(echeancierBO);
		// echeancierFacade.delete(echeancierBO);
	}

}
