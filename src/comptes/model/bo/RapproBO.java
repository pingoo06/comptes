package comptes.model.bo;

import comptes.model.db.entity.Bnp;
import comptes.model.db.entity.Operation;

public class RapproBO {
	private Bnp Bnp;
	private Operation Operation;
	private String LibTiers;


	public RapproBO(Bnp Bnp, Operation Operation, String LibTiers) {
		super();
		this.Bnp = Bnp;
		this.Operation = Operation;
		this.LibTiers=LibTiers;
	}


	public RapproBO() {
		// TODO Auto-generated constructor stub
	}


	public Bnp getBnp() {
		return Bnp;
	}


	public void setBnp(Bnp Bnp) {
		this.Bnp = Bnp;
	}


	public Operation getOperation() {
		return Operation;
	}


	public void setOperation(Operation Operation) {
		this.Operation = Operation;
	}


	public String getLibTiers() {
		return LibTiers;
	}


	public void setLibTiers(String LibTiers) {
		this.LibTiers = LibTiers;
	}

	
}