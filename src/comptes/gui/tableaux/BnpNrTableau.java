package comptes.gui.tableaux;

import comptes.gui.manager.RapproManager;
import comptes.model.db.entity.Bnp;
import comptes.util.log.LogRappro;

public class BnpNrTableau extends CheckableTableau {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "Date BNP", "Lib Ope BNP", "Montant BNP", "Check", "Creation" };

	/**
	 * Constructeur du tableau des BNP non rapproch�s
	 * 
	 * @param rapproMngr
	 */
	public BnpNrTableau(RapproManager rapproMngr) {
		super(rapproMngr);
		LogRappro.logInfo("D�but : constructeur BnpNrTableau tableau");
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
			return tabSelectedRapproManu.contains(rowIndex);
		case 4:
			return rowIndex == tabSelectedCreationCheck;
		default:
			throw new IllegalArgumentException("Dans Get ValueAT de BnpNrTableau TableauInvalid column index");
		}

	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		LogRappro.logDebug("D�but : Set ValueAt de Rappro Tableau");
		if (columnIndex == 3) {
			boolean checked = (boolean) aValue;
			if (!tabSelectedRapproManu.isEmpty()
					&& myRapproMngr.isAmex(myRapproMngr.getMyBnpListNr().get(tabSelectedRapproManu.get(0)))) {
				myRapproMngr.clearAmex();
			}
			if (checked) {
				tabSelectedRapproManu.clear();
				tabSelectedRapproManu.add(rowIndex);
				myRapproMngr.chekNr();
			} else {
				myRapproMngr.uncheckBnp();
				resetTabSelected();
			}
		}
		if (columnIndex == 4) {
			boolean checked = (boolean) aValue;
			if (checked) {
				tabSelectedCreationCheck = rowIndex;
				myRapproMngr.createOpeFromBnpNr();
			} else {
				tabSelectedCreationCheck = -1;
				myRapproMngr.getMyOngletRappro().getPanelCreationOperation().clearSaisieOpe();
			}
		}
		LogRappro.logDebug(" fire");
		fireTableDataChanged();
	}

	/**
	 * Retourne le type de chaque colonne
	 */
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

	/**
	 * seules les colonnes avec les checkBox sont modifiables
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (columnIndex == 3 || columnIndex == 4);
	}
}
