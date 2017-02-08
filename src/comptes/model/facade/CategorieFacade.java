package comptes.model.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import comptes.model.csvParser.MyCsvParser;
import comptes.model.db.dao.CategorieDAO;
import comptes.model.db.dao.DAO;
import comptes.model.db.entity.Categorie;
import comptes.util.log.LogCategorie;

public class CategorieFacade {
	private CategorieDAO categorieDAO;
	public CategorieFacade() {
		this.categorieDAO = new CategorieDAO();
	}

	public void create(Categorie myCategorie) {
		categorieDAO.create(myCategorie);
	}


	public Categorie find(int id) {
		;
		return categorieDAO.find(id);
	}

	public int findLib(String libCateg) {
		return categorieDAO.findLib (libCateg);
	}

	public ArrayList<Categorie> findAll() {
		ArrayList<Categorie> allCategories = categorieDAO.findAll();
		Collections.sort(allCategories);
		return allCategories;
	}

	public void update(Categorie myCategorie) {
		categorieDAO.update(myCategorie);
	}

	public void delete(Categorie myCategorie) {
		categorieDAO.delete(myCategorie);
	}
	public void initCategorie() {
		Set<String> set = new HashSet<>();
		int nbLines = 0;
		DAO<Categorie> myCategorieDAO = new CategorieDAO();
		
		LogCategorie.logInfo("---------DEBUT REMPL categ------------------");
		MyCsvParser moneyParser = MyCsvParser.getMoneyParser("res/money.csv");
		LogCategorie.logDebug("---------avant while pour remplir set------------------");
		while (moneyParser.next()) {
			set.add(moneyParser.getString("Category"));
			nbLines++;
		}
		LogCategorie.logDebug("----------Fin while pour remplir set------------------");
		ArrayList<String> myNewList;
		myNewList = new ArrayList<String>();
		myNewList.addAll(set);
		LogCategorie.logInfo("TOTAL LINES: " + nbLines);
		LogCategorie.logInfo("taille après : " + myNewList.size());
		 for (int i = 0; i < myNewList.size(); i++) {
			Categorie myCategorie = new Categorie(0, null);
			myCategorie.setId(0);
			myCategorie.setLibCateg(myNewList.get(i));
			myCategorieDAO.create(myCategorie);
		 }
		 LogCategorie.logInfo("table categorie remplie");
	}
}
