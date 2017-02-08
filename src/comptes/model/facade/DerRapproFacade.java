package comptes.model.facade;

import java.time.LocalDate;

import comptes.model.db.dao.DAO;
import comptes.model.db.dao.DerRapproDAO;
import comptes.model.db.entity.DerRappro;
import comptes.util.DateUtil;
import comptes.util.log.LogRappro;

public class DerRapproFacade {

	private DerRapproDAO derRapproDao;

	public DerRapproFacade() {
		this.derRapproDao = new DerRapproDAO();
	}

	public void create(DerRappro derRappro) {
		derRapproDao.create(derRappro);
	}

	public DerRappro find(int id) {
		return derRapproDao.find(id);
	}

	//	public ArrayList<DerRappro> findAll() {
	//	}

	public void update(DerRappro derRappro) {
		derRapproDao.update(derRappro);
	}

	public void delete(DerRappro derRappro) {
		derRapproDao.delete(derRappro);	
	}

	public void remplitDerRappro()  {
		LogRappro.logInfo(" init de la table DerRapro");
		DAO<DerRappro> myDerRapproDAO = new DerRapproDAO();
		DerRappro myDerRappro;
		LocalDate date = DateUtil.parse("2015-01-01", "yyyy-MM-dd");
		long dateLong = date.toEpochDay();
		myDerRappro = new DerRappro (0,(double) 0,"2015-01-01",dateLong);
		LogRappro.logDebug(myDerRappro.toString());
		myDerRapproDAO.create(myDerRappro);
		LogRappro.logInfo(" init de la table DerRapro");
	}
}
