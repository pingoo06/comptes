package comptes.model.facade;

import java.util.ArrayList;

import comptes.model.csvParser.MyCsvParser;
import comptes.model.db.dao.BnpDAO;
import comptes.model.db.dao.DAO;
import comptes.model.db.entity.Bnp;
import comptes.util.log.LogBnp;

public class BnpFacade {

	private BnpDAO bnpDao;
	
	public BnpFacade() {
		this.bnpDao = new BnpDAO();
	}
	
	public void create(Bnp bnp) {
		bnpDao.create(bnp);
	}
	
	public Bnp find(int id) {
		return bnpDao.find(id);
	}
	
	public ArrayList<Bnp> findAll() {
		return bnpDao.findAll();
	}
	
	public void update(Bnp myBnp) {
		bnpDao.update(myBnp);
	}
	
	public void delete(Bnp myBnp) {
		bnpDao.delete(myBnp);	
	}
	
	public void remplitBnp()  {
		LogBnp.logInfo(" remplissage de la table BNP a partir du fichier");
		MyCsvParser myBnpParser = MyCsvParser.getBnpParser("res/bnp.csv");
		DAO<Bnp> myBnpDAO = new BnpDAO();
		Bnp myBnp;
		int nbLines = 0;
		while (myBnpParser.next()) {
			myBnp = new Bnp(0, myBnpParser.getString(0), myBnpParser.getString(1), myBnpParser.getString(2), 
					myBnpParser.getString(3), myBnpParser.getDouble(4), "NR");
			LogBnp.logDebug(myBnp.toString());
			myBnpDAO.create(myBnp);
			nbLines++;
		}
		
		

		LogBnp.logInfo(" FIN remplissage de la table BNP ");
		LogBnp.logInfo("TOTAL LINES: " + nbLines);
	}
}
