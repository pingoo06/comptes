package comptes.model.facade;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import comptes.model.csvParser.MyCsvParser;
import comptes.model.db.dao.BnpDAO;
import comptes.model.db.dao.DAO;
import comptes.model.db.entity.Bnp;
import comptes.util.MyDate;
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

	public boolean isFull() {
		return bnpDao.isFull();
	}

	public void remplitBnp() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("csv", "jpg", "gif", "csv");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(chooser);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			File f = chooser.getSelectedFile();
			f.renameTo(new File("res/bnp.csv"));
			MyCsvParser myBnpParser = MyCsvParser.getBnpParser("res/bnp.csv");
			DAO<Bnp> myBnpDAO = new BnpDAO();
			Bnp myBnp;
			int nbLines = 0;
			while (myBnpParser.next()) {
				LogBnp.logInfo("myBnpParser.getString(0)" + myBnpParser.getString(0) + " - myBnpParser.getString(1)"
						+ myBnpParser.getString(1) + " - myBnpParser.getString(2)" + myBnpParser.getString(2));
				myBnp = new Bnp(0, new MyDate(myBnpParser.getString(0)), myBnpParser.getString(1),
						myBnpParser.getString(2), myBnpParser.getString(3), myBnpParser.getDouble(4), "NR");
				LogBnp.logDebug(myBnp.toString());
				myBnpDAO.create(myBnp);
				nbLines++;
			}
			f.renameTo(new File("bnp" + (new MyDate()).toDbFormat() + ".csv"));
			LogBnp.logInfo(" FIN remplissage de la table BNP ");
			LogBnp.logInfo("TOTAL LINES: " + nbLines);
		}

	}
}
