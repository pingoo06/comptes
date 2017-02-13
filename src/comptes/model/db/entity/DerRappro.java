package comptes.model.db.entity;

import comptes.util.MyDate;

public class DerRappro {
	protected int id = 0;
	protected Double derSolde = new Double(0);
	protected MyDate dateDerRappro;
	
	public DerRappro() {
		super();
	};

	public DerRappro(int id, Double derSolde, MyDate dateDerRappro, long dateDerRapproLong) {
		super();
		this.id = id;
		this.derSolde = derSolde;
		this.dateDerRappro = dateDerRappro;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getDerSolde() {
		return derSolde;
	}

	public void setDerSolde(Double derSolde) {
		this.derSolde = derSolde;
	}

	public MyDate getDateDerRappro() {
		return dateDerRappro;
	}

	public void setDateDerRappro(MyDate dateDerRappro) {
		this.dateDerRappro = dateDerRappro;
	}

	@Override
	public String toString() {
		return "Rappro [id=" + id + ", derSolde=" + derSolde + ", dateDerRappro=" + dateDerRappro
				+ ", dateDerRapproLong=" + dateDerRappro.toLongValue() + "]";
	}
}
