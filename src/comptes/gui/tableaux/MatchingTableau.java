package comptes.gui.tableaux;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import comptes.model.db.dao.MatchingDAO;
import comptes.model.db.entity.Categorie;
import comptes.model.db.entity.Matching;
import comptes.model.db.entity.Tiers;
import comptes.model.facade.CategorieFacade;
import comptes.model.facade.MatchingFacade;
import comptes.model.facade.TiersFacade;
import comptes.util.log.LogMatching;

public class MatchingTableau extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "Libelle BNP", "Tiers" };
	private List<Matching> listMatching;
	private MatchingFacade matchingFacade;

	// Remplit le tableau avec toutes les opérations
	public MatchingTableau() {
		LogMatching.logDebug("Début : constructeur matching tableau");
		listMatching = new MatchingDAO().findAll();
		this.matchingFacade = new MatchingFacade();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return listMatching.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		LogMatching.logDebug("Dans GetValueAt  :  columnIndex " + columnIndex + "RowIndex" + rowIndex);
		Matching current = listMatching.get(rowIndex);
		LogMatching.logDebug("Dans GetValueAt  : Matching : " + current);
		switch (columnIndex) {
		case 0:
			return current.getlibBnp();
		case 1:
			return current.getlibTiers();
		default:
			throw new IllegalArgumentException("Dans Get ValueAT de Matching TableauInvalid column index");
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		LogMatching.logDebug("Début : Set ValueAt de matching");
		Matching current = listMatching.get(rowIndex);
		LogMatching.logDebug("Dans SetValueAt de matchingTableau  row: " + rowIndex + " colIdx: " + columnIndex);
		switch (columnIndex) {
		case 0:
			current.setlibBnp(aValue.toString());
			matchingFacade.update(current);
			break;
		case 1:
			current.setLibTiers(aValue.toString());
			matchingFacade.update(current);
			break;
		default:
			throw new IllegalArgumentException("Invalid column index");
		}
		LogMatching.logDebug("Dans SetValueAt de matching Tableau : fire");
		fireTableRowsUpdated(rowIndex, rowIndex);
	}

	// Retourne le type de chaque colonne
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (listMatching.isEmpty()) {
			return Object.class;
		}
		LogMatching.logDebug("Dans set value At de matching tableau : column	 index " + columnIndex);
		Object val = getValueAt(0, columnIndex);
		LogMatching.logDebug("Dans set value At de matching tableau : val " + val);
		if (val == null) {
			return String.class;
		} else {
			return val.getClass();
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	// supression de ligne
	public void deleteRow(int idx) {
		LogMatching.logDebug("Début : deleteRow de matching Tableau");
		Matching Matching = listMatching.get(idx);
		listMatching.remove(idx);
		matchingFacade.delete(Matching);
		fireTableRowsDeleted(idx, idx);
		LogMatching.logDebug(Matching.toString());
	}

	public List<Matching> getListMatching() {
		LogMatching.logDebug("Début : getListMatching de matchingTableau ");
		return listMatching;
	}

	public void setListMatching(List<Matching> listMatching) {
		LogMatching.logDebug("Début : setListMatching de matchingTableau");
		this.listMatching = listMatching;
	}

}
