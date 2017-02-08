package comptes.model.services;

import java.util.ArrayList;
import java.util.Iterator;

import comptes.model.bo.RapproBO;
import comptes.model.db.dao.RapproDAO;
import comptes.model.db.entity.Bnp;
import comptes.model.db.entity.Operation;
import comptes.model.db.entity.Tiers;
import comptes.model.facade.BnpFacade;
import comptes.model.facade.OperationFacade;
import comptes.model.facade.TiersFacade;

public class GestionRappro {
	private OperationFacade myOperationFacade;
	private RapproDAO myRapproDAO;
	private BnpFacade myBnpFacade;

	private ArrayList<Operation> myOpeListNr;
	private ArrayList<Bnp> myBnpListNr;
	private ArrayList<RapproBO> myRapproBOList;

	public GestionRappro() {
		myOperationFacade = new OperationFacade();
		myRapproDAO = new RapproDAO();
		myBnpFacade = new BnpFacade();
		myOpeListNr = new ArrayList<Operation>();
		myBnpListNr = new ArrayList<Bnp>();
	}

	public void prepaRappro() {
		myRapproBOList = myRapproDAO.rapproAuto();
		ArrayList<Operation> myOpeList = new ArrayList<Operation>();
		ArrayList<Bnp> myBnpList = new ArrayList<Bnp>();
		for (RapproBO rappro : myRapproBOList) {
			myOpeList.add(rappro.getOperation());
			myBnpList.add(rappro.getBnp());

		}
		myBnpListNr = myBnpFacade.findAll();
		Iterator<Bnp> it = myBnpListNr.iterator();
		while(it.hasNext()) {
			if (myBnpList.contains(it.next())) {
				it.remove();
			}
		}
		myOpeListNr = myOperationFacade.findOpeNr();
		Iterator<Operation> it2 = myOpeListNr.iterator();
		while(it2.hasNext()) {
			if (myOpeList.contains(it2.next())) {
				it2.remove();
			}
		}
	}
	public String getLibTiersFromOpe (Operation myOperation) {
	TiersFacade myTiersFacade = new TiersFacade();
	Tiers myTiers=new Tiers();
	myTiers=myTiersFacade.find(myOperation.getTiersId());
	return myTiers.getLibTiers();
	}
	public BnpFacade getMyBnpFacade() {
		return myBnpFacade;
	}

	public void setMyBnpFacade(BnpFacade myBnpFacade) {
		this.myBnpFacade = myBnpFacade;
	}

	public ArrayList<Operation> getMyOpeListNr() {
		return myOpeListNr;
	}

	public void setMyOpeListNr(ArrayList<Operation> myOpeListNr) {
		this.myOpeListNr = myOpeListNr;
	}

	public ArrayList<Bnp> getMyBnpListNr() {
		return myBnpListNr;
	}

	public void setMyBnpListNr(ArrayList<Bnp> myBnpListNr) {
		this.myBnpListNr = myBnpListNr;
	}

	public ArrayList<RapproBO> getMyRapproBOList() {
		return myRapproBOList;
	}

	public void setMyRapproBOList(ArrayList<RapproBO> myRapproBOList) {
		this.myRapproBOList = myRapproBOList;
	}

}
