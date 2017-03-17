package comptes.gui.manager;

import java.util.ArrayList;

import comptes.gui.onglets.OngletRappro;
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

	public void chekAmex(Operation operation, OngletRappro myOngletRappro) {
		if (!myOpeAmexList.contains(operation)) {
			myOpeAmexList.add(operation);
			sumOpeAmex += operation.getMontantOpe();
			LogRappro.logInfo("mt AmexBnp " + mtAmexBnp + " mt amex Ope " + sumOpeAmex);
			myOngletRappro.getMyRapproSommesManager().addRappro(operation.getMontantOpe());
		}
	}

	public void uncheckRapproAmex(Operation operation, OngletRappro myOngletRappro) {
		if (myOpeAmexList.contains(operation)) {
			myOpeAmexList.remove(operation);
			sumOpeAmex -= operation.getMontantOpe();
			LogRappro.logInfo(" uncheck mt AmexBnp " + mtAmexBnp + " mt amex Ope " + sumOpeAmex);
			myOngletRappro.getMyRapproSommesManager().minusRappro(operation.getMontantOpe());
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

	public void reset(OngletRappro myOngletRappro) {
		mtAmexBnp = 0;
		sumOpeAmex = 0;
		if (!isComplete()) {
			for (Operation ope : getMyOpeAmexList()) {
				myOngletRappro.getMyRapproSommesManager().minusRappro(ope.getMontantOpe());
			}
		}
		myOpeAmexList.clear();
	}

}
