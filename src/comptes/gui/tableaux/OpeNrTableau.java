package comptes.gui.tableaux;

import comptes.gui.manager.RapproManager;
import comptes.model.db.entity.Operation;
import comptes.model.facade.OperationFacade;
import comptes.util.log.LogRappro;

public class OpeNrTableau extends CheckableTableau {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "Date Ope", "Tiers", "Montant", "Check" };

	// Remplit le tableau
	public OpeNrTableau(RapproManager rapproMngr) {
		super(rapproMngr);
		LogRappro.logInfo("Début : constructeur OpeNrTableau tableau");
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return myRapproMngr.getMyOpeListNr().size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	// renvoie le contenu de chaque colonne de la liste
	public Object getValueAt(int rowIndex, int columnIndex) {
		LogRappro.logDebug("columnIndex : " + columnIndex + "RowIndex" + rowIndex);
		Operation current = myRapproMngr.getMyOpeListNr().get(rowIndex);
		LogRappro.logDebug("operation : " + current);
		switch (columnIndex) {
		case 0:
			return current.getDateOpe();
		case 1:
			return myRapproMngr.getMyOperationUtil().getLibTiersFromOpe(current);
		case 2:
			// 03/03
			// if (current.getMontantOpe() > 0) {
			// return current.getMontantOpe();
			// } else {
			// return current.getMontantOpe() * -1;
			// }
			return current.getMontantOpe();
		case 3:
			return tabSelectedRapproManu.contains(rowIndex);
		default:
			throw new IllegalArgumentException("Dans Get ValueAT de RapproTableau TableauInvalid column index");
		}

	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		LogRappro.logInfo("Début : Set ValueAt de OpeNR Tableau");
		Operation current = myRapproMngr.getMyOpeListNr().get(rowIndex);
		OperationFacade operationFacade = new OperationFacade();
		switch (columnIndex) {
		case 2 :
			current.setMontantOpe((Double) aValue);
			operationFacade.update(current);
			fireTableDataChanged();
			break;
		case 3 :
			boolean checked = (boolean) aValue;
			if (checked) {
				tabSelectedRapproManu.add(rowIndex);
				myRapproMngr.chekNr();
			} else {
				myRapproMngr.uncheckOperation(rowIndex);
				tabSelectedRapproManu.remove(new Integer(rowIndex));
			}
		fireTableDataChanged();
		}
	}

	// Retourne le type de chaque colonne
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (myRapproMngr.getMyOpeListNr().isEmpty()) {
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

	// seules les colonnes checkBox et montant sont modifiables
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (columnIndex == 2 || columnIndex ==3);
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
