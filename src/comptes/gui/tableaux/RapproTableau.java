package comptes.gui.tableaux;

import comptes.gui.manager.RapproManager;
import comptes.model.bo.RapproBO;
import comptes.util.log.LogRappro;

public class RapproTableau extends CheckableTableau {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "Date BNP", "Date Ope", "Lib Ope BNP", "Tiers", "Montant BNP", "Montant",
			"Check" };

	public RapproTableau(RapproManager rapproMngr) {
		super(rapproMngr);
		LogRappro.logInfo("D�but : constructeur RapproTableau tableau");
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return myRapproMngr.getMyRapproBOList().size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	// renvoie le contenu de chaque colonne de la liste
	public Object getValueAt(int rowIndex, int columnIndex) {
		LogRappro.logDebug("columnIndex : " + columnIndex + "RowIndex" + rowIndex);
		RapproBO current = myRapproMngr.getMyRapproBOList().get(rowIndex);
		LogRappro.logDebug("Dans GetValueAt de echeancierTableau : RapproBO :  : " + current);
		switch (columnIndex) {
		case 0:
			return current.getBnp().getDateBnpCalc();
		case 1:
			return current.getOperation().getDateOpe();
		case 2:
			return current.getBnp().getLibOpeBnp();
		case 3:
			return current.getLibTiers();
		case 4:
			return current.getBnp().getMontantBnp();
		case 5:
			return current.getOperation().getMontantOpe();
		case 6:
			return true;
		default:
			throw new IllegalArgumentException("Dans Get ValueAT de RapproTableau TableauInvalid column index");
		}

	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		LogRappro.logDebug("D�but : Set ValueAt de Rappro Tableau");
		if (columnIndex == 6) {
			myRapproMngr.uncheckRappro(rowIndex);
		}
	}

	// Retourne le type de chaque colonne
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (myRapproMngr.getMyRapproBOList().isEmpty()) {
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

	/**
	 * seule la colonne avec la checkBox est modifiable
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 6;
	}

}
