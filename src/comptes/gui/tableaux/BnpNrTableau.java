package comptes.gui.tableaux;

import java.time.LocalDate;

import comptes.gui.manager.RapproManager;
import comptes.model.db.entity.Bnp;
import comptes.model.services.GestionRappro;
import comptes.util.DateUtil;
import comptes.util.log.LogRappro;

public class BnpNrTableau extends CheckableTableau {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "Date BNP", "Lib Ope BNP", "Montant BNP", "Check" };

	// Remplit le tableau
	public BnpNrTableau(RapproManager rapproMngr, GestionRappro gestionRappro) {
		super(rapproMngr, gestionRappro);
		LogRappro.logInfo("Début : constructeur BnpNrTableau tableau");
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return myGestionRappro.getMyBnpListNr().size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	// renvoie le contenu de chaque colonne de la liste
	public Object getValueAt(int rowIndex, int columnIndex) {
		LogRappro.logDebug("columnIndex " + columnIndex + "RowIndex" + rowIndex);
		Bnp current = myGestionRappro.getMyBnpListNr().get(rowIndex);
		LogRappro.logDebug("Bnp : " + current);
		switch (columnIndex) {
		case 0:
			LocalDate date = LocalDate.ofEpochDay(current.getDateBnpCalc());
			return DateUtil.format(date, "dd/MM/yyyy");
		case 1:
			return current.getLibOpeBnp();
		case 2:
			return current.getMontantBnp();
		case 3:
			return rowIndex == tabSelected;
		default:
			throw new IllegalArgumentException("Dans Get ValueAT de BnpNrTableau TableauInvalid column index");
		}

	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		LogRappro.logInfo("Début : Set ValueAt de Rappro Tableau");
		if (columnIndex == 3) {
			boolean checked = (boolean) aValue;
			if (checked) {
				tabSelected = rowIndex;
				myRapproMngr.chekNr();
			} else {
				tabSelected = -1;
			}
		}
		LogRappro.logDebug("Dans SetValueAt de Echeancier Tableau : fire");
		fireTableDataChanged();
	}

	// Retourne le type de chaque colonne
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (myGestionRappro.getMyBnpListNr().isEmpty()) {
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
