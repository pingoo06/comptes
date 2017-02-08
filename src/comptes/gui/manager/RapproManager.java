package comptes.gui.manager;

import java.util.ArrayList;
import java.util.Iterator;

import comptes.gui.onglets.OngletRappro;
import comptes.gui.tableaux.BnpNrTableau;
import comptes.gui.tableaux.OpeNrTableau;
import comptes.gui.tableaux.RapproTableau;
import comptes.model.bo.RapproBO;
import comptes.model.db.entity.Bnp;
import comptes.model.db.entity.DerRappro;
import comptes.model.db.entity.Operation;
import comptes.model.facade.DerRapproFacade;
import comptes.model.facade.OperationFacade;

public class RapproManager {
	private OngletRappro myOngletRappro;

	public RapproManager(OngletRappro myOngletRappro) {
		super();
		this.myOngletRappro = myOngletRappro;
	}
	public void chekNr(){
		OpeNrTableau myOpeNrTableau = (OpeNrTableau)myOngletRappro.getTableOpeNr().getModel();
		int tabSelectedOpe = myOpeNrTableau.getTabSelected();
		BnpNrTableau myBnpNrTableau = (BnpNrTableau)myOngletRappro.getTableBnpNr().getModel();
		int tabSelectedBnp = myBnpNrTableau.getTabSelected();
		if (tabSelectedOpe != -1 && tabSelectedBnp != -1) {
			Bnp myBnp= myOngletRappro.getMyGestionRappro().getMyBnpListNr().get(tabSelectedBnp);
			Operation myOperation= myOngletRappro.getMyGestionRappro().getMyOpeListNr().get(tabSelectedOpe);
			String libTiers=myOngletRappro.getMyGestionRappro().getLibTiersFromOpe(myOperation);
			RapproBO myRapproBo = new RapproBO(myBnp, myOperation, libTiers);
			myOngletRappro.getMyGestionRappro().getMyBnpListNr().remove(tabSelectedBnp);
			myOngletRappro.getMyGestionRappro().getMyOpeListNr().remove(tabSelectedOpe);
			RapproTableau myRapproTableau = (RapproTableau)myOngletRappro.getTableRappro().getModel();
			myOngletRappro.getMyGestionRappro().getMyRapproBOList().add(myRapproBo);
			myBnpNrTableau.resetTabSelected();
			myOpeNrTableau.resetTabSelected();
			myRapproTableau.fireTableDataChanged();
			myBnpNrTableau.fireTableDataChanged();
			myOpeNrTableau.fireTableDataChanged();
		}
	}
	public void uncheckRappro(int rowIndex){
		RapproTableau myRapproTableau = (RapproTableau)myOngletRappro.getTableRappro().getModel();
		RapproBO myRapproBo = myOngletRappro.getMyGestionRappro().getMyRapproBOList().remove(rowIndex);
		myOngletRappro.getMyGestionRappro().getMyBnpListNr().add(myRapproBo.getBnp());
		myOngletRappro.getMyGestionRappro().getMyOpeListNr().add(myRapproBo.getOperation());
		OpeNrTableau myOpeNrTableau = (OpeNrTableau)myOngletRappro.getTableOpeNr().getModel();
		BnpNrTableau myBnpNrTableau = (BnpNrTableau)myOngletRappro.getTableBnpNr().getModel();
		myRapproTableau.fireTableDataChanged();
		myBnpNrTableau.fireTableDataChanged();
		myOpeNrTableau.fireTableDataChanged();

	}
	public void validateRappro() {
		//ajouter un test sur solde = 0
		RapproBO rapproBo = new RapproBO();
		Operation myOperation = new Operation();
		OperationFacade myOperationFacade=new OperationFacade();
		ArrayList<RapproBO> myRapproBoList = myOngletRappro.getMyGestionRappro().getMyRapproBOList();
		Iterator<RapproBO> it = myRapproBoList.iterator();
		while(it.hasNext()) {
			rapproBo = it.next();
			myOperation=rapproBo.getOperation();
			myOperation.setEtatOpe("X");
			myOperationFacade.update(myOperation);
			it.remove();
			}
		myOngletRappro.getJtfDateRappro();
		DerRappro myDerRappro = new DerRappro();
		DerRapproFacade myDerRapproFacade = new DerRapproFacade();
		myDerRappro=myDerRapproFacade.find(1);
		myDerRappro.setDateDerRappro(myOngletRappro.getJtfDateRappro().getText());
		myDerRappro.setDerSolde(Double.parseDouble(myOngletRappro.getJtfMtFinal().getText()));
		myDerRapproFacade.update(myDerRappro);
		RapproTableau myRapproTableau = (RapproTableau)myOngletRappro.getTableRappro().getModel();
		myRapproTableau.fireTableDataChanged();
	}
}
