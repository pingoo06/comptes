package comptes.model.facade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import comptes.model.csvParser.MyCsvParser;
import comptes.model.db.SqliteConnector;
import comptes.model.db.dao.DAO;
import comptes.model.db.dao.TiersDAO;
import comptes.model.db.entity.Tiers;
	public class TiersFacade {
		private TiersDAO tiersDAO;
		private ArrayList<Tiers> myListeTiersTri;
		public TiersFacade() {
			this.tiersDAO = new TiersDAO();
			myListeTiersTri= findAll();
			Collections.sort(myListeTiersTri);
		}
	public void create(Tiers myTiers) {
		System.out.println("Début : myTiersFacade.create");
		tiersDAO.create(myTiers);
	}
	
	public ArrayList<Tiers> getMyTiersCategTri() {
		return myListeTiersTri;
	}
	public int findLib(String libTiers) {
		return tiersDAO.findLib(libTiers);
	}

	public Tiers find(int id) {
		return tiersDAO.find(id);
	}

	public ArrayList<Tiers> findAll() {
		ArrayList<Tiers> allTiers = tiersDAO.findAll();
		Collections.sort(allTiers);
		return allTiers;
	}

	public void update(Tiers myTiers) {
		tiersDAO.update(myTiers);
	}

	public void delete(Tiers myTiers) {
		tiersDAO.delete(myTiers);
	}

	public void initTiers() {
//		//ajout à enlever
		final Connection connection;
		 final String FILE_PATH = "res/mydatabase.db";
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + FILE_PATH);
		} catch (SQLException e) {
			System.out.println("mon ajout idiot");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Set<String> set = new HashSet<>();
		int nbLines = 0;
		DAO<Tiers> myTiersDAO = new TiersDAO();
		
		System.out.println("----------DEBUT REMPL TIERS------------------");
		MyCsvParser moneyParser = MyCsvParser.getMoneyParser("res/money.csv");
		System.out.println("---------avant while pour remplir set------------------");
		while (moneyParser.next()) {
			set.add(moneyParser.getString("Payee"));
			nbLines++;
		}
		System.out.println("----------Fin while pour remplir set------------------");
		ArrayList<String> myNewList;
		myNewList = new ArrayList<String>();
		myNewList.addAll(set);
		System.out.println("TOTAL LINES: " + nbLines);
		System.out.println("taille après : " + myNewList.size());
		 for (int i = 0; i < myNewList.size(); i++) {
			Tiers myTiers = new Tiers(0, null, null);
			myTiers.setId(0);
			myTiers.setLibTiers(myNewList.get(i));
			myTiers.setDerCategDeTiers(null);
			myTiersDAO.create(myTiers);
		 }
			System.out.println("table tiers remplie");
	}

}

