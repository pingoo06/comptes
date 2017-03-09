package comptes.gui.manager;

import comptes.gui.onglets.OngletRappro;
import comptes.gui.onglets.PanelRappro;
import comptes.util.log.LogRappro;

public class RapproSommesManager {
	private OngletRappro myOngletRappro;
	private double sumDebBnp;
	private double sumCredBnp;
	private double resteAPointer;
	private PanelRappro panelRappro;
	


	public RapproSommesManager(OngletRappro myOngletRappro) {
	super();
	this.myOngletRappro=myOngletRappro;
	this.sumDebBnp = 0;
	this.sumCredBnp = 0;
	this.resteAPointer = 0;
//	this.resteAPointer = montantInit - sumDebBnp + sumCredBnp -montantFinal;
}

	//A implementer
//	public boolean isComplete() {
//		return sumOpeAmex == mtAmexBnp;
//	}

	
	public void init(double montantInit, double montantFinal) {
		resteAPointer = montantInit - sumDebBnp + sumCredBnp -montantFinal;
	}
	
	public void addRappro(double mtRappro){
		if (mtRappro < 0) {
			sumDebBnp += mtRappro;
			myOngletRappro.getPanelRappro().getJtfSommeDeb().setText(String.valueOf(sumDebBnp * -1));
			LogRappro.logInfo("ajoute dans somme deb");
		} else {
			sumCredBnp += mtRappro;
			myOngletRappro.getPanelRappro().getJtfSommeCred().setText(String.valueOf(sumCredBnp));
		}
		resteAPointer += mtRappro;
		myOngletRappro.getPanelRappro().getJtfDiff().setText(String.valueOf(resteAPointer));
	}
	
	public void minusRappro(double mtRappro){
		if (mtRappro < 0) {
			sumDebBnp -= mtRappro;
			myOngletRappro.getPanelRappro().getJtfSommeDeb().setText(String.valueOf(sumDebBnp * -1));
			LogRappro.logInfo("ajoute dans somme deb");
		} else {
			sumCredBnp -= mtRappro;
			myOngletRappro.getPanelRappro().getJtfSommeCred().setText(String.valueOf(sumCredBnp));
		}
		resteAPointer -= mtRappro;
		myOngletRappro.getPanelRappro().getJtfDiff().setText(String.valueOf(resteAPointer));
	}
	

	public double getSumDebBnp() {
		return sumDebBnp;
	}




	public void setSumDebBnp(double sumDebBnp) {
		this.sumDebBnp = sumDebBnp;
	}




	public double getSumCredBnp() {
		return sumCredBnp;
	}




	public void setSumCredBnp(double sumCredBnp) {
		this.sumCredBnp = sumCredBnp;
	}




	public double getResteAPointer() {
		return resteAPointer;
	}




	public void setResteAPointer(double resteAPointer) {
		this.resteAPointer = resteAPointer;
	}




	@Override
	public String toString() {
		return "RapproSommesManager [sumDebBnp=" + sumDebBnp + ", sumCredBnp=" + sumCredBnp + ", resteAPointer="
				+ resteAPointer + "]";
	}




}
