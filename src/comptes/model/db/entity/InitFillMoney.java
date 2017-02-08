package comptes.model.db.entity;

import java.util.ArrayList;

import comptes.model.Application;
import comptes.model.facade.MatchingFacade;
import comptes.util.log.Logger;

public class InitFillMoney {

	public static void initFillAll() {
		// Categorie
		Logger.logInfo("appel init categ");
		Application.getCategorieFacade().initCategorie();
		// Tiers
		Logger.logInfo("Appel init tiers");
		Application.getTiersFacade().initTiers();
		Logger.logInfo("Init tiers termin�");
		// Operation
		Logger.logInfo("appel facade init operation");
		Application.getOperationFacade().initOperation();
		Logger.logInfo("fin facade init operation");
		// Bnp
			Logger.logInfo("Appel init BNP");
			Application.getBnpFacade().remplitBnp();
			Logger.logInfo("Fin init BNP");
		// Matching
		Logger.logInfo("\n appel premier create Matching  ");
		Matching myMatching = new Matching(0, "Bouygues", "BouyguesPlvt");
		MatchingFacade myMatchingFacade = new MatchingFacade();
		myMatchingFacade.create(myMatching);
		Logger.logInfo("\n appel deuxieme create Matching ");
		myMatching.setLibCateg("SFR");
		myMatching.setlibBnp("SRFSEPA");
		myMatchingFacade.create(myMatching);
		myMatching.setLibCateg("Sosh");
		myMatching.setlibBnp("SoshSEPA");
		myMatchingFacade.create(myMatching);

		Logger.logInfo("\n Apr�s 3 create Matching :");
		ArrayList<Matching> myMatchingList = null;
		myMatchingList = new ArrayList<Matching>();
		myMatchingList = myMatchingFacade.findAll();
		Logger.logInfo("\n vidage liste Matching ");
		for (int i = 0; i < myMatchingList.size(); i++) {
			Logger.logInfo(myMatchingList.get(i).toString());
		}
		//
		myMatching = myMatchingFacade.find(1);
		myMatching.setLibCateg("newBouygues");
		myMatchingFacade.update(myMatching);
		myMatchingList = myMatchingFacade.findAll();
		Logger.logInfo("\n vidage liste Matching apr�s update ");
		for (int i = 0; i < myMatchingList.size(); i++) {
			Logger.logInfo(myMatchingList.get(i).toString());
		}
		myMatching = myMatchingFacade.find(3);
		myMatchingFacade.delete(myMatching);
		myMatchingList = myMatchingFacade.findAll();
		Logger.logInfo("\n vidage liste Matching apr�s delete ");
		for (int i = 0; i < myMatchingList.size(); i++) {
			Logger.logInfo(myMatchingList.get(i).toString());
		}
	}
}
//
