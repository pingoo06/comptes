package comptes.gui.tableaux;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import comptes.model.bo.EcheancierBO;
import comptes.model.db.dao.EcheancierDAO;
import comptes.model.db.entity.Categorie;
import comptes.model.db.entity.Tiers;
import comptes.model.facade.CategorieFacade;
import comptes.model.facade.EcheancierFacade;
import comptes.model.facade.TiersFacade;
import comptes.util.DateUtil;

public class EcheancierTableau extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "Type Ech", "Tiers", "Categorie", "Date Ech", "Montant", "Nb Ech" };
	private List<EcheancierBO> listEcheancierBO;
	private EcheancierFacade echeancierFacade;

	// Remplit le tableau avec toutes les op�rations
	public EcheancierTableau() {
		System.out.println("D�but : constructeur echeancier tableau");
		listEcheancierBO = new EcheancierDAO().findAllEchBO();
		this.echeancierFacade = new EcheancierFacade();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return listEcheancierBO.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	// renvoie le contenu de chaque colonne de la liste
	public Object getValueAt(int rowIndex, int columnIndex) {
//		 System.out.println("D�but : getValueAt de echeancierTableau");
		 System.out.println("Dans GetValueAt de echeancierTableau :  columnIndex " + columnIndex + "RowIndex" + rowIndex  );

		EcheancierBO current = listEcheancierBO.get(rowIndex);
		System.out.println("Dans GetValueAt de echeancierTableau : EcheancierBO : " + current);
		switch (columnIndex) {
		case 0:
			return current.getTypeEch();
		case 1:
			return current.getTiersBo().getLibTiers();
		case 2:
			return current.getCategorieBo().getLibCateg();
		case 3:
			return DateUtil.format(DateUtil.parse(current.getDateEch(),"yyyy-MM-dd"), "dd/MM/yyyy");
		case 4:
			if (current.getMontantEch() > 0) {
				return current.getMontantEch();
			} else {
				return current.getMontantEch() * -1;
			}
		case 5:
			return current.getNbEch();
		default:
			throw new IllegalArgumentException("Dans Get ValueAT de Echeancier TableauInvalid column index");
		}

	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		System.out.println("D�but : Set ValueAt de echeancierTableau");
		EcheancierBO current = listEcheancierBO.get(rowIndex);
		System.out.println("Dans SetValueAt de EcheancierTableau  row: " + rowIndex + " colIdx: " + columnIndex + "valeur " + aValue.toString());
		TiersFacade myTiersFacade = new TiersFacade();
		String libTiers = aValue.toString();
		String libCateg = aValue.toString();
		switch (columnIndex) {
		case 0:
			current.setTypeEch(aValue.toString());
			echeancierFacade.update(current);
			break;
		case 1:
			// current.setDateOpe(aValue.toString());
			// echeancierFacade.update(current);
			// break;
			myTiersFacade = new TiersFacade();
			int idTiers = 0;
			idTiers = myTiersFacade.findLib(libTiers);
			if (idTiers == 0) {
				Tiers myTiers = new Tiers(0, null, null);
				myTiers.setId(0);
				myTiers.setLibTiers(libTiers);
				myTiers.setDerCategDeTiers(null);
				myTiersFacade.create(myTiers);
				idTiers = myTiersFacade.findLib(libTiers);
			}
			current.setTiersEchId(idTiers);
			current.setTiersBo(myTiersFacade.find(idTiers));
			echeancierFacade.update(current);
			break;
		case 2:
			CategorieFacade myCategorieFacade = new CategorieFacade();
			// System.out.println("dans case 2 de getValueAt de
			// EcheancierTableau : lib categorie : " + libCateg);
			int idCategorie = myCategorieFacade.findLib(libCateg);
			// System.out.println("dans case 2 de getValueAt de
			// EcheancierTableau : id categorie : " + idCategorie);
			if (idCategorie == 0) {
				Categorie myCategorie = new Categorie(0, null);
				myCategorie.setId(0);
				myCategorie.setLibCateg(libCateg);
				myCategorieFacade.create(myCategorie);
				idCategorie = myCategorieFacade.findLib(libCateg);
			}
			current.setCategEchId(idCategorie);
			current.setCategorieBo(myCategorieFacade.find(idCategorie));
			// mise � jour de la derni�re cat�gorie dans le tiers
			myTiersFacade = new TiersFacade();
			Tiers myTiers = new Tiers(0, null, null);
			myTiers = current.getTiersBo();
			myTiers.setDerCategDeTiers(libCateg);
			myTiersFacade.update(myTiers);
			// Fin mise � jour de la derni�re cat�gorie dans le tiers
			System.out.println("dans case 2 de getValueAt de EcheancierTableau : (myCategorieFacade.find(idCategorie): "
					+ myCategorieFacade.find(idCategorie));
			echeancierFacade.update(current);
			break;
		case 3:
			current.setDateEch(DateUtil.format(DateUtil.parse(aValue.toString(),"dd/MM/yyyy"),"yyyy-MM-dd"));
			echeancierFacade.update(current);
			break;
		case 4:
			current.setMontantEch((Double) aValue * -1);
			echeancierFacade.update(current);
			break;
		case 5:
			current.setNbEch((Integer) (aValue));
			echeancierFacade.update(current);
			break;
		default:
			throw new IllegalArgumentException("Invalid column index");
		}
		System.out.println("Dans SetValueAt de Echeancier Tableau : fire");
		fireTableRowsUpdated(rowIndex, rowIndex);
	}

	// Retourne le type de chaque colonne
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (listEcheancierBO.isEmpty()) {
			return Object.class;
		}
		 System.out.println("Dans set value At de echeancier tableau : column	 index " + columnIndex);
		Object val = getValueAt(0, columnIndex);
		 System.out.println("Dans set value At de echeancier tableau : val " +
		 val);
		if (val == null) {
			if (columnIndex <= 3) {
				return String.class;
			} else {
				if (columnIndex == 4) {
					return Double.class;
				} else {
					return Integer.class;
				}
			}
		}
		return val.getClass();
	}

	// seules les lignes non point�es sont modifiables
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		EcheancierBO current = listEcheancierBO.get(rowIndex);
		return true;
	}

	// supression de ligne
	public void deleteRow(int idx) {
		System.out.println("D�but : deleteRow de echeancier Tableau");
		EcheancierBO echeancierBO = listEcheancierBO.get(idx);
		listEcheancierBO.remove(idx);
		fireTableRowsDeleted(idx, idx);
		System.out.println(echeancierBO);
		echeancierFacade.delete(echeancierBO);
	}

	public List<EcheancierBO> getListEcheancierBO() {
		System.out.println("D�but : getListEcheancierBO de echeancierTableau ");
		return listEcheancierBO;
	}

	public void setListEcheancierBO(List<EcheancierBO> listEcheancierBO) {
		System.out.println("D�but : setListEcheancierBo de EcheancierTableau");
		this.listEcheancierBO = listEcheancierBO;
	}

}
