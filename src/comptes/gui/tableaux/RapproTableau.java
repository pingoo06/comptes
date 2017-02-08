package comptes.gui.tableaux;

import java.time.LocalDate;

import comptes.gui.manager.RapproManager;
import comptes.model.bo.RapproBO;
import comptes.model.services.GestionRappro;
import comptes.util.DateUtil;
import comptes.util.log.LogRappro;

public class RapproTableau extends CheckableTableau {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "Date BNP", "Date Ope", "Lib Ope BNP", "Tiers", "Montant BNP", "Montant",
			"Check" };

	// Remplit le tableau
	public RapproTableau(RapproManager rapproMngr, GestionRappro gestionRappro) {
		super(rapproMngr, gestionRappro);
		LogRappro.logInfo("Début : constructeur RapproTableau tableau");
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return myGestionRappro.getMyRapproBOList().size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	// renvoie le contenu de chaque colonne de la liste
	public Object getValueAt(int rowIndex, int columnIndex) {
		LogRappro.logDebug("columnIndex : " + columnIndex + "RowIndex" + rowIndex);
		RapproBO current = myGestionRappro.getMyRapproBOList().get(rowIndex);
		LogRappro.logDebug("Dans GetValueAt de echeancierTableau : RapproBO :  : " + current);
		switch (columnIndex) {
		case 0:
			LocalDate date = LocalDate.ofEpochDay(current.getBnp().getDateBnpCalc());
			return DateUtil.format(date, "dd/MM/yyyy");
		case 1:
			return DateUtil.format(DateUtil.parse(current.getOperation().getDateOpe(), "yyyy-MM-dd"), "dd/MM/yyyy");
		case 2:
			return current.getBnp().getLibOpeBnp();
		case 3:
			return current.getLibTiers();
		case 4:
			return current.getBnp().getMontantBnp();
		case 5:
			if (current.getOperation().getMontantOpe() > 0) {
				return current.getOperation().getMontantOpe();
			} else {
				return current.getOperation().getMontantOpe() * -1;
			}
		case 6:
			return true;
		default:
			throw new IllegalArgumentException("Dans Get ValueAT de RapproTableau TableauInvalid column index");
		}

	}

	// A implementer
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		LogRappro.logInfo("Début : Set ValueAt de Rappro Tableau");
		if (columnIndex == 6) {
			myRapproMngr.uncheckRappro(rowIndex);
		}
	}

	// Retourne le type de chaque colonne
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (myGestionRappro.getMyRapproBOList().isEmpty()) {
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
		return columnIndex == 6;
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
