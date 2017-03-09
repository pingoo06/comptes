package comptes.gui.manager;

public class RapproSommesManager {
	
	private double sumDebBnp;
	private double sumCredBnp;
	private double resteAPointer;	
	
public RapproSommesManager() {
		super();
		sumDebBnp = 0;
		sumCredBnp = 0;
		resteAPointer = 0;
	}

	public RapproSommesManager(double sumDebBnp, double sumCredBnp,	double montantInit, double montantFinal) {
	super();
	this.sumDebBnp = sumDebBnp;
	this.sumCredBnp = sumCredBnp;
	this.resteAPointer = resteAPointer;
}

	//A implementer
//	public boolean isComplete() {
//		return sumOpeAmex == mtAmexBnp;
//	}
	
	public void addRappro(double mtRappro){
		if (mtRappro < 0) {
			sumDebBnp += mtRappro;
		} else {
			sumCredBnp += mtRappro;
		}
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
