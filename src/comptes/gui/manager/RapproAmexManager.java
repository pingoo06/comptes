package comptes.gui.manager;

import java.util.ArrayList;

import comptes.model.db.entity.Operation;
import comptes.util.log.LogRappro;

public class RapproAmexManager {
	private double mtAmexBnp;
	private double sumOpeAmex;
	private ArrayList<Operation> myOpeAmexList;

	public RapproAmexManager(double montantBnp) {
		mtAmexBnp = 0;
		sumOpeAmex = 0;
		myOpeAmexList = new ArrayList<Operation>();
	}

	public void chekAmex(Operation opearation) {
		if (!myOpeAmexList.contains(opearation)) {
			myOpeAmexList.add(opearation);
			sumOpeAmex += opearation.getMontantOpe();
			LogRappro.logInfo("mt AmexBnp " + mtAmexBnp + " mt amex Ope " + sumOpeAmex);
		}
	}

	public void uncheckRappro(Operation opearation) {
		if (myOpeAmexList.contains(opearation)) {
			myOpeAmexList.remove(opearation);
			sumOpeAmex -= opearation.getMontantOpe();
			LogRappro.logInfo(" uncheck mt AmexBnp " + mtAmexBnp + " mt amex Ope " + sumOpeAmex);
		}

	}

	public double getMtAmexBnp() {
		return mtAmexBnp;
	}

	public void setMtAmexBnp(double mtAmexBnp) {
		this.mtAmexBnp = mtAmexBnp;
	}

	public double getSumOpeAmex() {
		return sumOpeAmex;
	}

	public void setSumOpeAmex(double sumOpeAmex) {
		this.sumOpeAmex = sumOpeAmex;
	}

	public ArrayList<Operation> getMyOpeAmexList() {
		return myOpeAmexList;
	}
	
	public boolean isComplete() {
		return sumOpeAmex == mtAmexBnp;
	}
	
	public void reset() {
		mtAmexBnp = 0;
		sumOpeAmex = 0;
		myOpeAmexList.clear();
	}
	

}
