package comptes.gui.manager;

import java.util.ArrayList;
import java.util.Iterator;

import comptes.gui.onglets.OngletRappro;
import comptes.gui.onglets.PanelRappro;
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

public class RapproManager {
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
	private OpeNrTableau myOpeNrTableau;
	private Bnp selectedBnp;
	private Tiers myTiers;

	private RapproAmexManager amexManager;
	private RapproSommesManager rapproSommesManager;

	public RapproManager(OngletRappro myOngletRappro) {
		super();
		this.myOngletRappro = myOngletRappro;
		myOperationFacade = new OperationFacade();
		myBnpFacade = new BnpFacade();
		myOpeListNr = new ArrayList<Operation>();
		myBnpListNr = new ArrayList<Bnp>();
		myRapproDAO = new RapproDAO();
		myOperationUtil = new OperationUtil();
		myMatchingFacade = new MatchingFacade();
		myOperation = new Operation();
		myTiersFacade = new TiersFacade();
		selectedBnp = new Bnp();
		myTiers = new Tiers();
		amexManager = new RapproAmexManager(-1);
		// rapproSommesManager = new RapproSommesManager();
	}

	public void chekNr() {
		ArrayList<Integer> opeNrSelected = myOpeNrTableau.getTabSelectedRapproManu();
		ArrayList<Integer> bnpNrSelected = myBnpNrTableau.getTabSelectedRapproManu();
		if (!bnpNrSelected.isEmpty()) {
			Bnp myBnp = myBnpListNr.get(bnpNrSelected.get(0));
			if (isAmex(myBnp)) {
				amexManager.setMtAmexBnp(myBnp.getMontantBnp());
				for (int i : opeNrSelected) {
					amexManager.chekAmex(myOpeListNr.get(i));
				}
				if (amexManager.isComplete()) {
					for (Operation ope : amexManager.getMyOpeAmexList()) {
						doRappro(myBnp, ope);
						myOngletRappro.getMyRapproSommesManager().addRappro(myOperation.getMontantOpe());
					}
					rapproEnd();
					amexManager.reset();
				}
			} else {
				amexManager.reset();
				if (!opeNrSelected.isEmpty()) {
					Operation myOperation = myOpeListNr.get(opeNrSelected.get(0));
					doRappro(myBnp, myOperation);
					myOngletRappro.getMyRapproSommesManager().addRappro(myOperation.getMontantOpe());
					rapproEnd();
					while (opeNrSelected.size() > 1) {
						opeNrSelected.remove(0);
					}
				}
			}
		} else {
			// evite de pouvoir cocher plusieurs operations si aucun BNP n'est
			// s�lectionn�
			while (opeNrSelected.size() > 1) {
				opeNrSelected.remove(0);
			}
		}
	}

	public void doRappro(Bnp bnp, Operation operation) {
		/*
		 * rapproche un BNP et une OPE et modifie les tableaux
		 */
		String libTiers = myOperationUtil.getLibTiersFromOpe(operation);
		RapproBO myRapproBo = new RapproBO(bnp, operation, libTiers);
		myBnpListNr.remove(bnp);
		myOpeListNr.remove(operation);
		myRapproBOList.add(myRapproBo);
	}

	public void uncheckRappro(int rowIndex) {
		RapproBO myRapproBo = myRapproBOList.remove(rowIndex);
		myBnpListNr.add(myRapproBo.getBnp());
		myOpeListNr.add(myRapproBo.getOperation());
		myOngletRappro.getMyRapproSommesManager().minusRappro(myRapproBo.getOperation().getMontantOpe());
		OpeNrTableau myOpeNrTableau = (OpeNrTableau) myOngletRappro.getTableOpeNr().getModel();
		BnpNrTableau myBnpNrTableau = (BnpNrTableau) myOngletRappro.getTableBnpNr().getModel();
		myRapproTableau.fireTableDataChanged();
		myBnpNrTableau.fireTableDataChanged();
		myOpeNrTableau.fireTableDataChanged();
	}

	public void rapproEnd() {
		myBnpNrTableau.resetTabSelected();
		myOpeNrTableau.resetTabSelected();
		myRapproTableau.fireTableDataChanged();
		myBnpNrTableau.fireTableDataChanged();
		myOpeNrTableau.fireTableDataChanged();
	}

	public boolean isAmex(Bnp bnp) {
		return bnp.getLibOpeBnp().contains("AMERICAN EXPRESS");
	}

	public void uncheckBnp() {
		amexManager.reset();
	}

	public void uncheckOperation(int rowIndex) {
		Operation op = myOpeListNr.remove(rowIndex);
		amexManager.uncheckRappro(op);
	}

	public void createOpeFromBnpNr() {
		/**
		 * Creation d'une op�ration quand on coche la colonne "creation" dans le
		 * tableau BNP de l onglet rappro
		 */
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
		/**
		 * Apr�s cr�ation de l'operation � partir du BNP, ajoute l'�l�ment dans
		 * la liste et dans le tableau des rappproch�s
		 */
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
		/**
		 * A partir de la liste initiale de BNP, recherche les tiers puis �crit
		 * automatiquement les op�rations correspondantes si tiers trouv�s
		 */
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
				myBnp.setLibOpeBnp(matching.getlibTiers());
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

	public void prepaRappro(RapproSommesManager rapproSommesManager) {
		/**
		 * Lance le rapprochement auto puis cr�e les listes des non rapproch�s
		 */
		LogRappro.logDebug("arrive dans preparappro");
		myRapproBOList = myRapproDAO.rapproAuto();
		ArrayList<Operation> myOpeListRappro = new ArrayList<Operation>();
		ArrayList<Bnp> myBnpListRappro = new ArrayList<Bnp>();
		for (RapproBO rappro : myRapproBOList) {
			myOpeListRappro.add(rappro.getOperation());
			rapproSommesManager.addRappro(rappro.getOperation().getMontantOpe());
			myBnpListRappro.add(rappro.getBnp());
		}
		myBnpListNr = myBnpFacade.findAll();
		LogRappro.logInfo("myBnpListNr size avant m�nage" + myBnpListNr.size());
		Iterator<Bnp> it = myBnpListNr.iterator();
		Bnp myBnp;
		while (it.hasNext()) {
			myBnp = it.next();
			LogRappro.logDebug("myBnp Id " + myBnp.getId());
			LogRappro.logDebug("myBnpListRappro" + myBnpListRappro);
			LogRappro.logDebug("myBnp" + myBnp);
			if (myBnpListRappro.contains(myBnp)) {
				LogRappro.logDebug("passe dans remove");
				it.remove();
			}
		}
		LogRappro.logInfo("myBnpListNr size apres m�nage" + myBnpListNr.size());
		myOpeListNr = myOperationFacade.findOpeNr();
		Iterator<Operation> it2 = myOpeListNr.iterator();
		while (it2.hasNext()) {
			if (myOpeListRappro.contains(it2.next())) {
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
		myOngletRappro.getPanelRappro().getJtfDateRappro();
		DerRappro myDerRappro = new DerRappro();
		DerRapproFacade myDerRapproFacade = new DerRapproFacade();
		myDerRappro = myDerRapproFacade.find(1);
		myDerRappro.setDateDerRappro(new MyDate(myOngletRappro.getPanelRappro().getJtfDateRappro().getText()));
		myDerRappro.setDerSolde(Double.parseDouble(myOngletRappro.getPanelRappro().getJtfMtFinal().getText()));
		myDerRapproFacade.update(myDerRappro);
		myRapproTableau = (RapproTableau) myOngletRappro.getTableRappro().getModel();
		myRapproTableau.fireTableDataChanged();
	}

	public void updateTableaux() {
		myOpeNrTableau = (OpeNrTableau) myOngletRappro.getTableOpeNr().getModel();
		myBnpNrTableau = (BnpNrTableau) myOngletRappro.getTableBnpNr().getModel();
		myRapproTableau = (RapproTableau) myOngletRappro.getTableRappro().getModel();
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

	public void clearAmex() {
		amexManager.reset();
		myOpeNrTableau.resetTabSelected();
		myOpeNrTableau.fireTableDataChanged();
	}
}
