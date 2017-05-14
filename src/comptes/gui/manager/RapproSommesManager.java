package comptes.gui.manager;

import comptes.gui.onglets.OngletRappro;
import comptes.gui.onglets.PanelRappro;
import comptes.util.log.LogRappro;

public class RapproSommesManager {
	private OngletRappro myOngletRappro;
	private double sumDebBnp;
	private double sumCredBnp;
	private double resteAPointer;
//	private PanelRappro panelRappro;
	private java.text.DecimalFormat df = new java.text.DecimalFormat("0.##");
	


	public RapproSommesManager(OngletRappro myOngletRappro) {
	super();
	this.myOngletRappro=myOngletRappro;
	this.sumDebBnp = 0;
	this.sumCredBnp = 0;
	this.resteAPointer = 0;
}
/**
 * Indique si le rapprochement est terminé
 * @return true si resteAPointer = 0
 */
	public boolean isCompleteRappro() {
		LogRappro.logInfo("reste à pointer" + resteAPointer);
		return "0".equals(String.valueOf(df.format(resteAPointer)));
//		return resteAPointer == 0;
	}

	
	public double initResteAPointer (double montantInit, double montantFinal) {
		resteAPointer = montantInit + sumDebBnp + sumCredBnp - montantFinal;
		LogRappro.logInfo("reste à pointer" + resteAPointer);
		return resteAPointer;
	}
	
	public void addRappro(double mtRappro){
		if (mtRappro < 0) {
			sumDebBnp += mtRappro;
			myOngletRappro.getPanelRappro().getJtfSommeDeb().setText(String.valueOf(df.format(sumDebBnp * -1)));
			LogRappro.logDebug("ajoute dans somme deb : " + sumDebBnp + "; mt rappro : " + mtRappro);
		} else {
			LogRappro.logInfo("avant ajoute dans somme cred : " + sumCredBnp + "mtRappro" + mtRappro);
			sumCredBnp += mtRappro;
			myOngletRappro.getPanelRappro().getJtfSommeCred().setText(String.valueOf(df.format(sumCredBnp)));
			LogRappro.logInfo("ajoute dans somme cred : " + sumCredBnp);
		}
		resteAPointer += mtRappro;
		myOngletRappro.getPanelRappro().getJtfDiff().setText(String.valueOf(df.format(resteAPointer)));
	}
	
	public void minusRappro(double mtRappro){
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.##");
		if (mtRappro < 0) {
			sumDebBnp -= mtRappro;
			myOngletRappro.getPanelRappro().getJtfSommeDeb().setText(String.valueOf(df.format(sumDebBnp * -1)));
			LogRappro.logInfo("enlève dans somme deb : " + sumDebBnp + "; mt rappro : " + mtRappro);
		} else {
			sumCredBnp -= mtRappro;
			myOngletRappro.getPanelRappro().getJtfSommeCred().setText(String.valueOf(df.format(sumCredBnp)));
		}
		resteAPointer -= mtRappro;
		myOngletRappro.getPanelRappro().getJtfDiff().setText(String.valueOf(df.format(resteAPointer)));
	}
	

	public double getSumDebBnp() {
		return sumDebBnp;
	}




	public void setSumDebBnp(double sumDebBnp) {
		this.sumDebBnp=sumDebBnp ;
	}




	public double getSumCredBnp() {
		return sumCredBnp;
	}




	public void setSumCredBnp(double sumCredBnp) {
		this.sumCredBnp=sumCredBnp ;
	}




	public double getResteAPointer() {
		return resteAPointer;
	}




	public void setResteAPointer(double resteAPointer) {
		this.resteAPointer = resteAPointer ;
	}




	@Override
	public String toString() {
		return "RapproSommesManager [sumDebBnp=" + sumDebBnp + ", sumCredBnp=" + sumCredBnp + ", resteAPointer="
				+ resteAPointer + "]";
	}




}
