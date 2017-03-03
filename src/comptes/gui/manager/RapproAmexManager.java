package comptes.gui.manager;

import java.util.ArrayList;
import java.util.Iterator;

import comptes.gui.onglets.OngletRappro;
import comptes.gui.tableaux.BnpNrTableau;
import comptes.gui.tableaux.OpeNrTableau;
import comptes.gui.tableaux.RapproTableau;
import comptes.model.bo.RapproBO;
import comptes.model.db.dao.RapproDAO;
import comptes.model.db.entity.Bnp;
import comptes.model.db.entity.DerRappro;
import comptes.model.db.entity.Matching;
import comptes.model.db.entity.Operation;
import comptes.model.db.entity.Tiers;
import comptes.model.facade.BnpFacade;
import comptes.model.facade.CategorieFacade;
import comptes.model.facade.DerRapproFacade;
import comptes.model.facade.MatchingFacade;
import comptes.model.facade.OperationFacade;
import comptes.model.facade.TiersFacade;
import comptes.model.services.OperationUtil;
import comptes.util.MyDate;
import comptes.util.log.LogRappro;

public class RapproAmexManager {
	private OngletRappro myOngletRappro;
	private OperationFacade myOperationFacade;
	private RapproDAO myRapproDAO;
	private BnpFacade myBnpFacade;
	private ArrayList<Operation> myOpeListNr;
	private ArrayList<Bnp> myBnpListNr;
	private ArrayList<RapproBO> myRapproBOList;
	private OperationUtil myOperationUtil;
	private MatchingFacade myMatchingFacade;
	private Operation myOperation;
	private TiersFacade myTiersFacade;
	private RapproTableau myRapproTableau;
	private int tabSelectedCreationCheckBnp;
	private BnpNrTableau myBnpNrTableau;
	private Bnp selectedBnp;
	private Tiers myTiers;
	private double mtAmexBnp ;
	private double sumOpeAmex;
	private ArrayList<Operation> myOpeAmexList;

	public RapproAmexManager(OngletRappro myOngletRappro) {
		super();
		this.myOngletRappro = myOngletRappro;
		myOperationFacade = new OperationFacade();
		myBnpFacade = new BnpFacade();
//		myOpeListNr = new ArrayList<Operation>();
//		myBnpListNr = new ArrayList<Bnp>();
		myRapproDAO = new RapproDAO();
		myOperationUtil = new OperationUtil();
		myMatchingFacade = new MatchingFacade();
		myOperation = new Operation();
		myTiersFacade = new TiersFacade();
		selectedBnp = new Bnp();
		myTiers = new Tiers();
		mtAmexBnp = 0;
		sumOpeAmex = 0;
		myOpeAmexList = new ArrayList<Operation>();
	}

	public void chekAmex() {
		OpeNrTableau myOpeNrTableau = (OpeNrTableau) myOngletRappro.getTableOpeNr().getModel();
		int tabSelectedOpe = myOpeNrTableau.getTabSelectedRapproManu();
		myBnpNrTableau = (BnpNrTableau) myOngletRappro.getTableBnpNr().getModel();
		int tabSelectedBnp = myBnpNrTableau.getTabSelectedRapproManu();
		LogRappro.logInfo("tabSelectedOpe " + tabSelectedOpe + " tabSelectedBnp " + tabSelectedBnp);
		if (tabSelectedOpe != -1 && tabSelectedBnp != -1) {
			Bnp myBnp = myBnpListNr.get(tabSelectedBnp);
			mtAmexBnp = myBnp.getMontantBnp();
			//Bnp myBnp = myBnpListNr.get(tabSelectedBnp);
			Operation myOperation = myOpeListNr.get(tabSelectedOpe);
			if (!myOpeAmexList.contains(myOperation)){
			myOpeAmexList.add(myOperation);	
			sumOpeAmex=sumOpeAmex + myOperation.getMontantOpe();
			LogRappro.logInfo("mt AmexBnp " + mtAmexBnp + " mt amex Ope " + sumOpeAmex);
			}
		}
			
//			
//			String libTiers = myOperationUtil.getLibTiersFromOpe(myOperation);
//			RapproBO myRapproBo = new RapproBO(myBnp, myOperation, libTiers);
//			myBnpListNr.remove(tabSelectedBnp);
//			myOpeListNr.remove(tabSelectedOpe);
//			myRapproTableau = (RapproTableau) myOngletRappro.getTableRappro().getModel();
//			myRapproBOList.add(myRapproBo);
//			myBnpNrTableau.resetTabSelected();
//			myOpeNrTableau.resetTabSelected();
//			myRapproTableau.fireTableDataChanged();
//			myBnpNrTableau.fireTableDataChanged();
//			myOpeNrTableau.fireTableDataChanged();
//		}
	}

	public void uncheckRappro(int rowIndex) {
		myRapproTableau = (RapproTableau) myOngletRappro.getTableRappro().getModel();
		RapproBO myRapproBo = myRapproBOList.remove(rowIndex);
		myBnpListNr.add(myRapproBo.getBnp());
		myOpeListNr.add(myRapproBo.getOperation());
		OpeNrTableau myOpeNrTableau = (OpeNrTableau) myOngletRappro.getTableOpeNr().getModel();
		BnpNrTableau myBnpNrTableau = (BnpNrTableau) myOngletRappro.getTableBnpNr().getModel();
		myRapproTableau.fireTableDataChanged();
		myBnpNrTableau.fireTableDataChanged();
		myOpeNrTableau.fireTableDataChanged();

	}

	public void creationOpeNr() {
		boolean tiersReconnu = false;
		BnpNrTableau myBnpNrTableau = (BnpNrTableau) myOngletRappro.getTableBnpNr().getModel();
		tabSelectedCreationCheckBnp = myBnpNrTableau.getTabSelectedCreationCheck();

		selectedBnp = myBnpListNr.get(tabSelectedCreationCheckBnp);
		transcoTiers(selectedBnp);
		String libOpeBnp = selectedBnp.getLibOpeBnp().toUpperCase();
		ArrayList<Tiers> myTiersList = myTiersFacade.findAll();
		for (Tiers tiers : myTiersList) {
			if (libOpeBnp.contains(tiers.getLibTiers().toUpperCase()) && !"Virement".equals(tiers.getLibTiers())) {
				myOperation = bnpToOpe(selectedBnp, tiers);
				tiersReconnu = true;
				myOperationFacade.create(myOperation);
				bnpListNrToRapproTableau(selectedBnp, myOperation, tiers.getLibTiers());
			}
		}
		if (!tiersReconnu) {
			myTiers = myTiersFacade.find(myTiersFacade.findLib("?"));
			myOperation = bnpToOpe(selectedBnp, myTiers);
			myOngletRappro.getPanelCreationOperation()
					.fillFieldFromOpeDto(myOperationUtil.opeToDtoOperation(myOperation));
		}
	}

	public void bnpListNrToRapproTableau(Bnp myBnp, Operation myOperation, String libTiers) {
		RapproBO myRapproBo = new RapproBO(myBnp, myOperation, libTiers);
		myRapproBOList.add(myRapproBo);
		myBnpListNr.remove(tabSelectedCreationCheckBnp);
		myRapproTableau = (RapproTableau) myOngletRappro.getTableRappro().getModel();
		myRapproTableau.fireTableDataChanged();
		BnpNrTableau myBnpNrTableau = (BnpNrTableau) myOngletRappro.getTableBnpNr().getModel();
		myBnpNrTableau.resetTabSelectedCreationCheck();
		myBnpNrTableau.fireTableDataChanged();
	}

	public void ecritOpeCredit() {
		ArrayList<Bnp> myBnpList = myBnpFacade.findAll();
		transcoTiers(myBnpList);
		ArrayList<Tiers> myTiersList = myTiersFacade.findAll();
		for (Bnp bnp : myBnpList) {
			if (bnp.getMontantBnp() > 0) {
				for (Tiers tiers : myTiersList) {
					if (bnp.getLibOpeBnp().toUpperCase().contains(tiers.getLibTiers().toUpperCase())
							&& !"Virement".equals(tiers.getLibTiers())) {
						myOperation = bnpToOpe(bnp, tiers);
						myOperationFacade.create(myOperation);

						LogRappro.logDebug("pour bnp " + bnp);
						LogRappro.logDebug("trouve tiers " + tiers);
					}
				}
			}
		}
	}

	public void transcoTiers(ArrayList<Bnp> myBnpList) {
		for (Bnp bnp : myBnpList) {
			transcoTiers(bnp);
		}
	}

	public void transcoTiers(Bnp myBnp) {
		ArrayList<Matching> myMatchingList = myMatchingFacade.findAll();
		for (Matching matching : myMatchingList) {
			if (myBnp.getLibOpeBnp().toUpperCase().contains(matching.getlibBnp())) {
				myBnp.setLibOpeBnp(matching.getlibTier());
			}
		}
	}

	public Operation bnpToOpe(Bnp bnp, Tiers tiers) {
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
		return myOperation;
	}

	public void prepaRappro() {
		LogRappro.logDebug("arrive dans preparappro");
		// Bnp myBnp;
		myRapproBOList = myRapproDAO.rapproAuto();
		ArrayList<Operation> myOpeList = new ArrayList<Operation>();
		ArrayList<Bnp> myBnpList = new ArrayList<Bnp>();
		for (RapproBO rappro : myRapproBOList) {
			myOpeList.add(rappro.getOperation());
			myBnpList.add(rappro.getBnp());
		}
		myBnpListNr = myBnpFacade.findAll();
		LogRappro.logInfo("myBnpListNr size avant ménage" + myBnpListNr.size());
		Iterator<Bnp> it = myBnpListNr.iterator();
		Bnp myBnp;
		while (it.hasNext()) {
			myBnp = it.next();
			LogRappro.logDebug("myBnp Id " + myBnp.getId());
			// LogRappro.logDebug("myBnpList" + myBnpList);
			// LogRappro.logDebug("myBnp" + myBnp);
			if (myBnpList.contains(myBnp)) {
				LogRappro.logDebug("passe dans remove");
				it.remove();
			}
		}
		LogRappro.logInfo("myBnpListNr size apres ménage" + myBnpListNr.size());
		myOpeListNr = myOperationFacade.findOpeNr();
		Iterator<Operation> it2 = myOpeListNr.iterator();
		while (it2.hasNext()) {
			if (myOpeList.contains(it2.next())) {
				it2.remove();
			}
		}
	}

	public void validateRappro() {
		// ajouter un test sur solde = 0
		RapproBO rapproBo = new RapproBO();
		Iterator<RapproBO> it = myRapproBOList.iterator();
		while (it.hasNext()) {
			rapproBo = it.next();
			myOperation = rapproBo.getOperation();
			myOperation.setEtatOpe("X");
			myOperationFacade.update(myOperation);
			it.remove();
		}
		myOngletRappro.getJtfDateRappro();
		DerRappro myDerRappro = new DerRappro();
		DerRapproFacade myDerRapproFacade = new DerRapproFacade();
		myDerRappro = myDerRapproFacade.find(1);
		myDerRappro.setDateDerRappro(new MyDate(myOngletRappro.getJtfDateRappro().getText()));
		myDerRappro.setDerSolde(Double.parseDouble(myOngletRappro.getJtfMtFinal().getText()));
		myDerRapproFacade.update(myDerRappro);
		myRapproTableau = (RapproTableau) myOngletRappro.getTableRappro().getModel();
		myRapproTableau.fireTableDataChanged();
	}

	public OngletRappro getMyOngletRappro() {
		return myOngletRappro;
	}

	public ArrayList<Operation> getMyOpeListNr() {
		return myOpeListNr;
	}

	public ArrayList<Bnp> getMyBnpListNr() {
		return myBnpListNr;
	}

	public ArrayList<RapproBO> getMyRapproBOList() {
		return myRapproBOList;
	}

	public OperationUtil getMyOperationUtil() {
		return myOperationUtil;
	}

	public Operation getMyOperation() {
		return myOperation;
	}

	public int getTabSelectedCreationCheckBnp() {
		return tabSelectedCreationCheckBnp;
	}

	public Bnp getSelectedBnp() {
		return selectedBnp;
	}

	public Tiers getMyTiers() {
		return myTiers;
	}
}
