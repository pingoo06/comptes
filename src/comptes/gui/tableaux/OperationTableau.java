package comptes.gui.tableaux;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import comptes.model.bo.OperationBO;
import comptes.model.db.dao.OperationDAO;
import comptes.model.db.entity.Categorie;
import comptes.model.db.entity.Tiers;
import comptes.model.facade.CategorieFacade;
import comptes.model.facade.OperationFacade;
import comptes.model.facade.TiersFacade;
import comptes.util.log.LogOperation;
public class OperationTableau extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "Type Ope", "Date Ope", "Tiers", "Categorie", "Détail opé", "Débit", "Crédit",
			"Etat" };
	private List<OperationBO> listOperationBO;

	private OperationFacade operationFacade;

	// Remplit le tableau avec toutes les opérations
	public OperationTableau() {
		LogOperation.logDebug("Début : constructeur operation tableau");
		listOperationBO = new OperationDAO().findAllOpeBO();
		this.operationFacade = new OperationFacade();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return listOperationBO.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	// renvoie le contenu de chaque colonne de la liste
	public Object getValueAt(int rowIndex, int columnIndex) {
//		System.out.println("Début : getValueAt de operationTableau");
//		System.out.println("Dans GetValueAt de OperationTableau : columnIndex" + columnIndex);
//		System.out.println("Dans GetValueAt de OperationTableau : rowIndex" + rowIndex);
		
		OperationBO current = listOperationBO.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return current.getTypeOpe();
		case 1:
			return current.getDateOpe();
		case 2:
			return current.getTiersBO().getLibTiers();
		case 3:
//			System.out.println("Dans GetValueAt de OperationTableau : current" + current);
//			System.out.println("Dans GetValueAt de OperationTableau : current.getCategorieBO()" + current.getCategorieBO());
//		System.out.println("Dans GetValueAt de OperationTableau : current.getCategorieBO().getLibCateg()" + current.getCategorieBO().getLibCateg());
		
			return current.getCategorieBO().getLibCateg();
		case 4:
			return current.getDetailOpe();
		case 5:
			if (current.getMontantOpe() > 0) {
				return null;
			} else {
				return current.getMontantOpe() * -1;
			}
		case 6:
			if (current.getMontantOpe() < 0) {
				return null;
			} else {
				return current.getMontantOpe();
			}
		case 7:
			return current.getEtatOpe();

		default:
			throw new IllegalArgumentException("Invalid column index");
		}

	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//		System.out.println("Début : Set ValueAt de operationTableau");
		OperationBO current = listOperationBO.get(rowIndex);
//		System.out.println("row: " + rowIndex + " colIdx: " + columnIndex);
		TiersFacade myTiersFacade= new TiersFacade();
		String libTiers = aValue.toString();
		String libCateg = aValue.toString();
		switch (columnIndex) {
		case 0:
			current.setTypeOpe(aValue.toString());
			operationFacade.update(current);
			break;
		case 1:
			current.getDateOpe().update(aValue.toString());
			operationFacade.update(current);
			break;
		case 2:
			myTiersFacade = new TiersFacade();
			int idTiers=0;
			idTiers = myTiersFacade.findLib(libTiers);
			if (idTiers == 0) {
				Tiers myTiers = new Tiers(0, null, null);
				myTiers.setId(0);
				myTiers.setLibTiers(libTiers);
				myTiers.setDerCategDeTiers(null);
				myTiersFacade.create(myTiers);
				idTiers = myTiersFacade.findLib(libTiers);
			}
			current.setTiersId(idTiers);
			current.setTiersBO(myTiersFacade.find(idTiers));
			operationFacade.update(current);
			break;
		case 3:
			CategorieFacade myCategorieFacade = new CategorieFacade();
//			System.out.println("dans case 3 de getValueAt de OperationTableau : lib categorie : " + libCateg);
			int idCategorie = myCategorieFacade.findLib(libCateg);
//			System.out.println("dans case 3 de getValueAt de OperationTableau : id categorie : " + idCategorie);
			if (idCategorie == 0) {
				Categorie myCategorie = new Categorie(0, null);
				myCategorie.setId(0);
				myCategorie.setLibCateg(libCateg);
				myCategorieFacade.create(myCategorie);
				idCategorie = myCategorieFacade.findLib(libCateg);
			}
			current.setCategOpeId(idCategorie);
			current.setCategorieBo(myCategorieFacade.find(idCategorie));
			//mise à jour de la dernière catégorie dans le tiers
			myTiersFacade = new TiersFacade();
			Tiers myTiers = new Tiers(0, null, null);
				myTiers=current.getTiersBO();
				myTiers.setDerCategDeTiers(libCateg);
				myTiersFacade.update(myTiers);
			//Fin mise à jour de la dernière catégorie dans le tiers
					System.out.println("dans case 3 de getValueAt de OperationTableau : (myCategorieFacade.find(idCategorie): " +myCategorieFacade.find(idCategorie));
			operationFacade.update(current);
			break;
		case 4:
			current.setDetailOpe(aValue.toString());
			operationFacade.update(current);
			break;
		case 5:
			current.setMontantOpe((Double) aValue * -1);
			current.setCreditBO(0);
			operationFacade.update(current);
			break;
		case 6:
			current.setMontantOpe((Double) aValue);
			current.setDebitBO(0);
			operationFacade.update(current);
			break;
		case 7:
			current.setEtatOpe(aValue.toString());
			operationFacade.update(current);
			break;
		default:
			throw new IllegalArgumentException("Invalid column index");
		}
		fireTableRowsUpdated(rowIndex, rowIndex);
	}

	// Retourne le type de chaque colonne
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (listOperationBO.isEmpty()) {
			return Object.class;
		}
//		System.out.println("Dans set value At de Operation tableau : column index " + columnIndex);
		Object val = getValueAt(0, columnIndex);
//		System.out.println("Dans set value At de Operation tableau : val " + val);
		if (val == null) {
			if (columnIndex < 5 || columnIndex == 7) {
				return String.class;
			} else {
				return Double.class;
			}
		}
		return val.getClass();
	}

	// seules les lignes non pointées sont modifiables
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		OperationBO current = listOperationBO.get(rowIndex);
		return !"X".equals(current.getEtatOpe());
	}

	// supression de ligne
	public void deleteRow(int idx) {
		System.out.println("Début : deleteRow de Operation Tableau");
		OperationBO operationBO = listOperationBO.get(idx);
		listOperationBO.remove(idx);
		fireTableRowsDeleted(idx, idx);
		System.out.println(operationBO);
		operationFacade.delete(operationBO);
	}

//	public void filterTiers(String libTiers) {
//		System.out.println("Début : filerTiers de Operation Tableau");
//		System.out.println("dans operation tableau dans filtertiers : longueur liste avant clear :" +listOperationBO.size()); 
//		listOperationBO.clear();
//		System.out.println("dans operation tableau dans filtertiers : longueur liste apres clear :" +listOperationBO.size()); 
//		listOperationBO=new OperationDAO().findOpeBOTiers(libTiers);
//		System.out.println("dans operation tableau dans filtertiers : longueur liste aprsè remplissage:" +listOperationBO.size()); 
//		this.operationFacade = new OperationFacade();
//		fireTableDataChanged();
//	}
	
	public void filters(String whereClause) {
		LogOperation.logDebug("Début : filters de Operation Tableau");
		LogOperation.logDebug("dans operation tableau dans filters : longueur liste avant clear :" +listOperationBO.size()); 
		listOperationBO.clear();
	//	System.out.println("dans operation tableau dans filters : longueur liste apres clear :" +listOperationBO.size()); 
		listOperationBO=new OperationDAO().findOpeBOFiltre(whereClause);
		LogOperation.logDebug("dans operation tableau dans filters : longueur liste aprsè remplissage:" +listOperationBO.size()); 
		this.operationFacade = new OperationFacade();
		fireTableDataChanged();
	}

	public List<OperationBO> getListOperationBO() {
		System.out.println("Début : getListOperationBO de operationTableau ");
		return listOperationBO;
	}

	public void setListOperationBO(List<OperationBO> listOperationBO) {
		System.out.println("Début : setListOperationBo de OperationTableau");
		this.listOperationBO = listOperationBO;
	}
	
	
	
}