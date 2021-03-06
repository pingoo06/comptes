package comptes.model.facade;

import java.util.ArrayList;

import comptes.model.csvParser.MyCsvParser;
import comptes.model.db.dao.DAO;
import comptes.model.db.dao.OperationDAO;
import comptes.model.db.entity.Operation;
import comptes.model.db.entity.Tiers;
import comptes.util.MyDate;
import comptes.util.log.LogOperation;
import comptes.util.log.Logger;

public class OperationFacade {
	private OperationDAO operationDAO;

	public OperationFacade() {
		this.operationDAO = new OperationDAO();
	}

	public void create(Operation myOperation) {
		operationDAO.create(myOperation);
	}

	public Operation find(int id) {
		return operationDAO.find(id);
	}

	public ArrayList<Operation> findAll() {
		return operationDAO.findAll();
	}
	public long findDerChq() {
		return operationDAO.findDerChq();
	}
	public void update(Operation myOperation) {
		operationDAO.update(myOperation);
	}

	public void delete(Operation myOperation) {
		operationDAO.delete(myOperation);
	}
	public ArrayList<Operation> findOpeNr() {
		return operationDAO.findOpeNr();
	}
	public int createReturnId(Operation myOperation) {
		return operationDAO.createReturnId(myOperation);
	}

	public void initOperation() {
		LogOperation.logInfo("D�but init operation");
		int nbLines = 0;
		DAO<Operation> myOperationDAO = new OperationDAO();
		TiersFacade myTiersFacade = new TiersFacade();
		Tiers myTiers=new Tiers(0,null,null);
		CategorieFacade mycategorieFacade = new CategorieFacade();
		int idTiers = 0;
		int idLibCateg = 0;
		
		double myMontant = 0;
		MyCsvParser moneyParser = MyCsvParser.getMoneyParser("res/money.csv");
		Operation myOperation = new Operation();
		while (moneyParser.next()) {
			myOperation = new Operation();
			myOperation.setDateOpe(new MyDate(moneyParser.getString("Date"), "dd/MM/yyyy hh:mm:ss"));
			Logger.logDebug("dateOpe" + myOperation.getDateOpe());
			myMontant = moneyParser.getDouble("Payment");
			if (myMontant > 0) {
				myMontant = myMontant * -1;
			} else {
				myMontant = moneyParser.getDouble("Deposit");
			}
			myOperation.setMontantOpe(myMontant);
			idTiers = myTiersFacade.findLib(moneyParser.getString("Payee"));
			myOperation.setTiersId(idTiers);
			myOperation.setDetailOpe(moneyParser.getString("Memo"));
			idLibCateg = mycategorieFacade.findLib(moneyParser.getString("Category"));
			myOperation.setCategOpeId(idLibCateg);
			String rappro = moneyParser.getString("Unknown");
			if (!"X".equals(rappro)) {
				rappro="NR";}
			myOperation.setEtatOpe(rappro);
			
			myOperationDAO.create(myOperation);
			myTiers.setId(idTiers);
			myTiers.setLibTiers(moneyParser.getString("Payee"));
			myTiers.setDerCategDeTiers(moneyParser.getString("Category"));
			myTiersFacade.update(myTiers);
			LogOperation.logDebug("Creation Operation dans Operation Facade : NB Lines" + nbLines);
			nbLines++;
		}
		LogOperation.logInfo("Creation Operation dans Operation Facade : TOTAL LINES: " + nbLines);
	}
	
	public double sumOperationUntil(MyDate endDate) {
		return operationDAO.sumOperationUntil(endDate);
	}
	
	public double sumOperation() {
		return operationDAO.sumOperation();
	}

	public double sumOperationPointe() {
		return operationDAO.sumOperationPointe();
	}
}
