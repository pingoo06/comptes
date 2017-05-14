package comptes.gui.manager;

import java.util.ArrayList;
import java.util.Iterator;

import comptes.gui.dto.OperationDTO;
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
import comptes.util.DoubleFormater;
import comptes.util.MyDate;
import comptes.util.log.LogBnp;
import comptes.util.log.LogMatching;
import comptes.util.log.LogOperation;
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

	/**
	 * Constructeur de rappro manager Cette classe g�re le m�tier de tout
	 * l'onglet rapprochement
	 * 
	 * @param myOngletRappro
	 */
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
	}

	/**
	 * Fonction appel�e � chaque fois que la colonne rappro manuel de bnp ou
	 * op�ration est coch�e Si un bnp est coch� si c'est un amex on appelle la
	 * gestion des amex sinon on remet � z�ro tout ce qui aurait �tre pu fait
	 * pour amex et on lance le rapprochement si une op�ration est s�lectionn�e.
	 * Empeche de cocher une op�ration si aucun bnp n'est coch�
	 */
	public void chekNr() {
		ArrayList<Integer> opeNrSelected = myOpeNrTableau.getTabSelectedRapproManu();
		ArrayList<Integer> bnpNrSelected = myBnpNrTableau.getTabSelectedRapproManu();
		if (!bnpNrSelected.isEmpty()) {
			Bnp myBnp = myBnpListNr.get(bnpNrSelected.get(0));
			if (isAmex(myBnp)) {
				amexManager.setMtAmexBnp(myBnp.getMontantBnp());
				for (int i : opeNrSelected) {
					amexManager.chekAmex(myOpeListNr.get(i), myOngletRappro);
				}
				if (amexManager.isComplete()) {
					for (Operation ope : amexManager.getMyOpeAmexList()) {
						doRappro(myBnp, ope);
					}
					rapproRefreshTableaux();
					amexManager.reset(myOngletRappro);
				}
			} else {
				amexManager.reset(myOngletRappro);
				if (!opeNrSelected.isEmpty()) {
					Operation myOperation = myOpeListNr.get(opeNrSelected.get(0));
					doRappro(myBnp, myOperation);
					myOngletRappro.getMyRapproSommesManager().addRappro(myOperation.getMontantOpe());
					rapproRefreshTableaux();
					while (opeNrSelected.size() > 1) {
						opeNrSelected.remove(0);
					}
				}
			}
			if (myOngletRappro.getMyRapproSommesManager().isCompleteRappro()) {
				finaliseRappro();
			}
		} else {
			// Empeche de cocher une op�ration si aucun bnp n'est coch�
			while (opeNrSelected.size() >= 1) {
				opeNrSelected.remove(0);
			}
		}
	}

	/**
	 * lance le rapprochement d'un BNP et d'une OPE et modifie les listes
	 * 
	 * @param bnp
	 * @param operation
	 */
	public void doRappro(Bnp bnp, Operation operation) {
		String libTiers = myOperationUtil.getLibTiersFromOpe(operation);
		RapproBO myRapproBo = new RapproBO(bnp, operation, libTiers);
		myBnpListNr.remove(bnp);
		myOpeListNr.remove(operation);
		myRapproBOList.add(myRapproBo);
	}

	/**
	 * Fonction appel�e lorsque la colonne check du tableau des rapproch�e est
	 * d�coch�e Elle g�re les d�rapprochements
	 * 
	 * @param rowIndex
	 */
	public void uncheckRappro(int rowIndex) {
		RapproBO myRapproBo = myRapproBOList.remove(rowIndex);
		myBnpListNr.add(myRapproBo.getBnp());
		myOpeListNr.add(myRapproBo.getOperation());
		myOngletRappro.getMyRapproSommesManager().minusRappro(myRapproBo.getOperation().getMontantOpe());
		if (isAmex(myRapproBo.getBnp())) {
			Bnp myBnpAmex = myRapproBo.getBnp();
			Iterator<RapproBO> it = myRapproBOList.iterator();
			RapproBO rapproBo;
			while (it.hasNext()) {
				rapproBo = it.next();
				if (rapproBo.getBnp().equals(myBnpAmex)) {
					it.remove();
					myBnpListNr.add(rapproBo.getBnp());
					myOpeListNr.add(rapproBo.getOperation());
					myOngletRappro.getMyRapproSommesManager().minusRappro(myRapproBo.getOperation().getMontantOpe());
				}
			}
		}
		OpeNrTableau myOpeNrTableau = (OpeNrTableau) myOngletRappro.getTableOpeNr().getModel();
		BnpNrTableau myBnpNrTableau = (BnpNrTableau) myOngletRappro.getTableBnpNr().getModel();
		myRapproTableau.fireTableDataChanged();
		myBnpNrTableau.fireTableDataChanged();
		myOpeNrTableau.fireTableDataChanged();
	}

	/**
	 * Rafraichissement des tableaux de l'onglet rappro
	 */
	public void rapproRefreshTableaux() {
		// ajout 26/03
		// myRapproTableau = (RapproTableau)
		// myOngletRappro.getTableRappro().getModel();
		// fin ajout 2603
		myBnpNrTableau.resetTabSelected();
		myOpeNrTableau.resetTabSelected();
		myRapproTableau.fireTableDataChanged();
		myBnpNrTableau.fireTableDataChanged();
		myOpeNrTableau.fireTableDataChanged();
	}

	/**
	 * Met � jour les op�rations dans la base avec le code X : rapproch� lorsque
	 * le rapprochement est termin�
	 */
	public void finaliseRappro() {
		LogRappro.logInfo("finalise rappro");
		OperationFacade myOperationFacade = new OperationFacade();
		Operation myOperation = new Operation();
		Iterator<RapproBO> it = myRapproBOList.iterator();
		RapproBO rapproBo;
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

	/**
	 * Retourne vrai si on a coch� la ligne AMEX dans BNP
	 * 
	 * @param bnp
	 * @return
	 */
	public boolean isAmex(Bnp bnp) {
		return bnp.getLibOpeBnp().contains("AMERICAN EXPRESS");
	}

	public void uncheckBnp() {
		amexManager.reset(myOngletRappro);
	}

	public void uncheckOperation(int rowIndex) {
		// Modif 17/3
		Operation ope = myOpeListNr.get(rowIndex);
		// Operation ope = myOpeListNr.remove(rowIndex);
		amexManager.uncheckRapproAmex(ope, myOngletRappro);
	}

	/**
	 * Apr�s cr�ation de l'operation � partir du BNP, ajoute l'�l�ment dans la
	 * liste et dans le tableau des rappproch�s
	 * 
	 * @param myBnp
	 * @param myOperation
	 * @param libTiers
	 */
	public void bnpListNrToRapproTableau(Bnp myBnp, Operation myOperation, String libTiers) {
		RapproBO myRapproBo = new RapproBO(myBnp, myOperation, libTiers);
		myRapproBOList.add(myRapproBo);
		myOngletRappro.getMyRapproSommesManager().addRappro(myOperation.getMontantOpe());
		myBnpListNr.remove(tabSelectedCreationCheckBnp);
		myRapproTableau = (RapproTableau) myOngletRappro.getTableRappro().getModel();
		myRapproTableau.fireTableDataChanged();
		BnpNrTableau myBnpNrTableau = (BnpNrTableau) myOngletRappro.getTableBnpNr().getModel();
		myBnpNrTableau.resetTabSelectedCreationCheck();
		myBnpNrTableau.fireTableDataChanged();
	}

	/**
	 * Creation d'une op�ration quand on coche la colonne "creation" dans le
	 * tableau BNP de l onglet rappro
	 */
	public void createOpeFromBnpNr() {
		int nbTiersReconnu = 0;
		String svLibTiers = "";
		BnpNrTableau myBnpNrTableau = (BnpNrTableau) myOngletRappro.getTableBnpNr().getModel();
		tabSelectedCreationCheckBnp = myBnpNrTableau.getTabSelectedCreationCheck();
		selectedBnp = myBnpListNr.get(tabSelectedCreationCheckBnp);
		transcoTiers(selectedBnp);
		String libOpeBnp = selectedBnp.getLibOpeBnp().toUpperCase();
		ArrayList<Tiers> myTiersList = myTiersFacade.findAll();
		for (Tiers tiers : myTiersList) {
			if (libOpeBnp.contains(tiers.getLibTiers().toUpperCase()) && !"Virement".equals(tiers.getLibTiers())) {
				myOperation = bnpToOpe(selectedBnp, tiers);
				svLibTiers = tiers.getLibTiers();
				nbTiersReconnu++;
			}
		}
		if (nbTiersReconnu == 1) {
			myOperationFacade.create(myOperation);
			bnpListNrToRapproTableau(selectedBnp, myOperation, svLibTiers);
		} else {
			myTiers = myTiersFacade.find(myTiersFacade.findLib("?"));
			myOperation = bnpToOpe(selectedBnp, myTiers);
			myOngletRappro.getPanelCreationOperation()
					.fillFieldFromOpeDto(myOperationUtil.opeToDtoOperation(myOperation));
		}
	}

	/**
	 * A partir de la liste initiale de BNP, recherche les tiers puis �crit
	 * automatiquement les op�rations correspondantes si tiers trouv�s
	 */
	public void ecritOpeCredit() {
		String svLibTiers = "";
		int nbTiersReconnus = 0;
		LogOperation.logInfo("D�but ecritOpeCredit ");
		ArrayList<Bnp> myBnpList = myBnpFacade.findAll();
		transcoTiers(myBnpList);
		ArrayList<Tiers> myTiersList = myTiersFacade.findAll();
		for (Bnp bnp : myBnpList) {
			if (bnp.getMontantBnp() > 0) {
				nbTiersReconnus = 0;
				for (Tiers tiers : myTiersList) {
					if (bnp.getLibOpeBnp().toUpperCase().contains(tiers.getLibTiers().toUpperCase())
							&& !"Virement".equals(tiers.getLibTiers())) {
						myOperation = bnpToOpe(bnp, tiers);
						svLibTiers = tiers.getLibTiers();
						nbTiersReconnus++;
						LogRappro.logDebug("pour bnp " + bnp);
						LogRappro.logDebug("trouve tiers " + tiers);
					}
				}
				if (nbTiersReconnus == 1) {
					myOperationFacade.create(myOperation);
				}
				if (nbTiersReconnus > 1) {
					LogBnp.logInfo("tiers en double " + svLibTiers);
				}
			}
		}
	}

	/**
	 * Cr�e une op�ration saisie dans le panel Operation de l'onglet rappro
	 * 
	 * @param myOperationDTO
	 */
	public void createNewOpe(OperationDTO myOperationDTO) {
		myOperationUtil = new OperationUtil();
		myOperationUtil.create(myOperationDTO);
		Operation ope = myOperationUtil.dtoToOperation(myOperationDTO);
		tabSelectedCreationCheckBnp = myBnpNrTableau.getTabSelectedCreationCheck();
		if (tabSelectedCreationCheckBnp != -1) {
			bnpListNrToRapproTableau(selectedBnp, ope, myOperationDTO.getTiers());
		} else {
			myOpeListNr.add(ope);
			updateTableaux();
		}
	}

	public void transcoTiers(ArrayList<Bnp> myBnpList) {
		LogOperation.logInfo("D�but transcoTiers ");
		for (Bnp bnp : myBnpList) {
			transcoTiers(bnp);
		}
	}

	public void transcoTiers(Bnp myBnp) {
		ArrayList<Matching> myMatchingList = new ArrayList<Matching>();
		myMatchingList = myMatchingFacade.findAll();
		for (Matching matching : myMatchingList) {
			LogMatching.logDebug("myBnp : " + myBnp);
			LogMatching.logDebug("myBnp.getLibOpeBnp() : " + myBnp.getLibOpeBnp());
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

	/**
	 * Lance le rapprochement auto puis cr�e les listes des non rapproch�s
	 */
	public void prepaRappro() {
		LogRappro.logInfo("arrive dans preparappro");
		myRapproBOList = myRapproDAO.rapproAuto();
		ArrayList<Operation> myOpeListRappro = new ArrayList<Operation>();
		ArrayList<Bnp> myBnpListRappro = new ArrayList<Bnp>();
		for (RapproBO rappro : myRapproBOList) {
			myOpeListRappro.add(rappro.getOperation());
			myOngletRappro.getMyRapproSommesManager().addRappro(rappro.getOperation().getMontantOpe());
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
		amexManager.reset(myOngletRappro);
		myOpeNrTableau.resetTabSelected();
		myOpeNrTableau.fireTableDataChanged();
	}
}
