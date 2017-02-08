package comptes.model.facade;

import java.util.ArrayList;

import comptes.model.db.dao.MatchingDAO;
import comptes.model.db.entity.Matching;

public class MatchingFacade {
	private MatchingDAO matchingDAO;

	public MatchingFacade() {
		this.matchingDAO = new MatchingDAO();
	}

	public void create(Matching matching) {
		matchingDAO.create(matching);
	}

	public Matching find(int id) {
		return matchingDAO.find(id);
	}

	public ArrayList<Matching> findAll() {
		return matchingDAO.findAll();
	}

	public void update(Matching myMatching) {
		matchingDAO.update(myMatching);
	}

	public void delete(Matching myMatching) {
		matchingDAO.delete(myMatching);
	}

}
