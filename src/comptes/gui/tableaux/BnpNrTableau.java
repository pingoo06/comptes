package comptes.gui.tableaux;

import comptes.gui.manager.RapproManager;
import comptes.model.db.entity.Bnp;
import comptes.util.log.LogRappro;

public class BnpNrTableau extends CheckableTableau {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "Date BNP", "Lib Ope BNP", "Montant BNP", "Check","Creation" };

	// Remplit le tableau
	public BnpNrTableau(RapproManager rapproMngr) {
		super(rapproMngr);
		LogRappro.logInfo("Début : constructeur BnpNrTableau tableau");
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return myRapproMngr.getMyBnpListNr().size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	// renvoie le contenu de chaque colonne de la liste
	public Object getValueAt(int rowIndex, int columnIndex) {
		LogRappro.logDebug("columnIndex " + columnIndex + "RowIndex" + rowIndex);
		Bnp current = myRapproMngr.getMyBnpListNr().get(rowIndex);
		LogRappro.logDebug("Bnp : " + current);
		switch (columnIndex) {
		case 0:
			return current.getDateBnpCalc();
		case 1:
			return current.getLibOpeBnp();
		case 2:
			return current.getMontantBnp();
		case 3:
			return rowIndex == tabSelectedRapproManu;
		case 4:
			return rowIndex == tabSelectedCreationCheck;
		default:
			throw new IllegalArgumentException("Dans Get ValueAT de BnpNrTableau TableauInvalid column index");
		}

	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		LogRappro.logDebug("Début : Set ValueAt de Rappro Tableau");
		if (columnIndex == 3) {
			boolean checked = (boolean) aValue;
			if (checked) {
				tabSelectedRapproManu = rowIndex;
				myRapproMngr.chekNr();
			} else {
				tabSelectedRapproManu = -1;
			}
		}
		if (columnIndex == 4) {
			boolean checked = (boolean) aValue;
			if (checked) {
				tabSelectedCreationCheck = rowIndex;
				myRapproMngr.creationOpeNr();
			} else {
				tabSelectedCreationCheck = -1;
			}
		}
		LogRappro.logDebug(" fire");
		fireTableDataChanged();
	}

	// Retourne le type de chaque colonne
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (myRapproMngr.getMyBnpListNr().isEmpty()) {
			return Object.class;
		}
		LogRappro.logDebug("column	 index " + columnIndex);
		Object val = getValueAt(0, columnIndex);
		LogRappro.logDebug("val " + val);
		if (val == null) {
			return String.class;
		}
		return val.getClass();
	}

	// seule la colonne avec la checkBox est modifiable
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (columnIndex == 3 || columnIndex == 4);
	}

	// supression de ligne
	public void deleteRow(int idx) {
		// LogRappro.LogDebug("Début ");
		// EcheancierBO echeancierBO = listEcheancierBO.get(idx);
		// listEcheancierBO.remove(idx);
		// fireTableRowsDeleted(idx, idx);
		// LogRappro.LogInfo(echeancierBO);
		// echeancierFacade.delete(echeancierBO);
	}

}
