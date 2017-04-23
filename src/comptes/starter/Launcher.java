package comptes.starter;

import comptes.gui.Fenetre;
import comptes.model.db.SqliteConnector;
import comptes.model.facade.BnpFacade;
import comptes.model.facade.CategorieFacade;
import comptes.model.facade.DerRapproFacade;
import comptes.model.facade.OperationFacade;
import comptes.model.facade.TiersFacade;
import comptes.model.services.EchToOpe;

public class Launcher {
	
////AVANT TOUT :
//modifier tous les caractères pourris
//Mettre NR sur toutes les lignes d'opération non pointées
//Enlever les apostrophes des Tiers et des categories
//Initialiser le dernier solde pointé dans la table rappro
//vérifier s'il y a des tiers vides
	
	public static void main(String[] args) {

		SqliteConnector.getInstance();
//		initCategorie();
//		initTiers();
//		initEcheancier();
//		initOperation();
//		initBnp();
		EchToOpe echToOpe = new EchToOpe();
//		initDerRappro();
		
		new Fenetre();

	}
	
	static void initCategorie() {
		 SqliteConnector.getInstanceUneTable("categorie");
		 CategorieFacade myCategorieFacade=new CategorieFacade();
		 myCategorieFacade.initCategorie();
	}
	
	static void initTiers() {
		 SqliteConnector.getInstanceUneTable("tiers");
		 TiersFacade myTiersFacade=new TiersFacade();
		 myTiersFacade.initTiers();
		
	}
	
	static void initEcheancier() {
		SqliteConnector.getInstanceUneTable("echeancier");
	}
	
	static void initOperation() {
		SqliteConnector.getInstanceUneTable("operation");
		OperationFacade myOperationFacade=new OperationFacade();
		myOperationFacade.initOperation();
	}
	static void initBnp() {
		SqliteConnector.getInstanceUneTable("bnp");
//		BnpFacade myBnpFacade=new BnpFacade();
//		myBnpFacade.remplitBnp();
	}
	static void initDerRappro() {
		SqliteConnector.getInstanceUneTable("der_rappro");
		DerRapproFacade myDerRapproFacade = new DerRapproFacade();
		myDerRapproFacade.remplitDerRappro();
	}
}
