package comptes.model.services;

import java.util.ArrayList;
import java.util.Iterator;

import comptes.model.bo.RapproBO;
import comptes.model.db.dao.RapproDAO;
import comptes.model.db.entity.Bnp;
import comptes.model.db.entity.Matching;
import comptes.model.db.entity.Operation;
import comptes.model.db.entity.Tiers;
import comptes.model.facade.BnpFacade;
import comptes.model.facade.CategorieFacade;
import comptes.model.facade.MatchingFacade;
import comptes.model.facade.OperationFacade;
import comptes.model.facade.TiersFacade;
import comptes.util.log.LogRappro;

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
		myBnpFacade = new BnpFacade();
		
	}

	public void ecritOpeCredit() {
		ArrayList<Bnp> myBnpList = myBnpFacade.findAll();
		transcoTiers(myBnpList);
		TiersFacade myTiersFacade = new TiersFacade();
		ArrayList<Tiers> myTiersList = myTiersFacade.findAll();
		for(Bnp bnp : myBnpList) {
			if (bnp.getMontantBnp() > 0){
				for(Tiers tiers : myTiersList){
					if (bnp.getLibOpeBnp().toUpperCase().contains(tiers.getLibTiers().toUpperCase()) && !"Virement".equals(tiers.getLibTiers())){
						bnpToOpe(bnp,tiers);
						LogRappro.logDebug("pour bnp " + bnp);
						LogRappro.logDebug("trouve tiers " + tiers);
					}
				}
			}
		}
	}
	
	public void 	transcoTiers(ArrayList<Bnp>myBnpList){
		MatchingFacade myMatchingFacade=new MatchingFacade();
		ArrayList<Matching> myMatchingList = myMatchingFacade.findAll();
		for(Bnp bnp : myBnpList) {
			for (Matching matching : myMatchingList){
				if (bnp.getLibOpeBnp().toUpperCase().contains(matching.getlibBnp())){
					bnp.setLibOpeBnp(matching.getlibTier());
				}
			}
		}
	}

	public void bnpToOpe(Bnp bnp, Tiers tiers) {
		Operation myOperation = new Operation();
		CategorieFacade myCategorieFacade = new CategorieFacade();
		myOperation.setId(0);
		myOperation.setTiersId(tiers.getId());
		myOperation.setCategOpeId(myCategorieFacade.findLib(tiers.getDerCategDeTiers()));
		myOperation.setDateOpe(bnp.getDateBnp());
		myOperation.setDetailOpe(null);
		myOperation.setEchId(0);
		myOperation.setEtatOpe("NR");
		myOperation.setMontantOpe(bnp.getMontantBnp());
		myOperation.setTypeOpe(bnp.getTypeOpeBnp().toString());
		myOperationFacade.create(myOperation);
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
		LogRappro.logInfo("myBnpList size avant ménage" + myBnpListNr.size());
		Iterator<Bnp> it = myBnpListNr.iterator();
		while(it.hasNext()) {
			if (myBnpList.contains(it.next())) {
				it.remove();
			}
		}
		LogRappro.logInfo("myBnpList size apres ménage" + myBnpListNr.size());
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
