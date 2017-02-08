package comptes.model.facade;

import java.util.ArrayList;

import comptes.model.db.dao.EcheancierDAO;
import comptes.model.db.entity.Echeancier;

public class EcheancierFacade {
	private EcheancierDAO echeancierDAO;

	public EcheancierFacade() {
		this.echeancierDAO = new EcheancierDAO();
	}

	public void create(Echeancier myEcheancier) {
		echeancierDAO.create(myEcheancier);
	}

	public Echeancier find(int id) {
		return echeancierDAO.find(id);
	}

	public ArrayList<Echeancier> findAll() {
		return echeancierDAO.findAll();
	}

	public void update(Echeancier myEcheancier) {
		echeancierDAO.update(myEcheancier);
	}

	public void delete(Echeancier myEcheancier) {
		echeancierDAO.delete(myEcheancier);
	}

}
