package comptes.model.db.entity;

public class DerRappro {
	protected int id = 0;
	protected Double derSolde = new Double(0);
	protected String dateDerRappro = "";
	protected long dateDerRapproLong = 0;
	
	public DerRappro() {
		super();
	};

	public DerRappro(int id, Double derSolde, String dateDerRappro, long dateDerRapproLong) {
		super();
		this.id = id;
		this.derSolde = derSolde;
		this.dateDerRappro = dateDerRappro;
		this.dateDerRapproLong = dateDerRapproLong;
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

	public String getDateDerRappro() {
		return dateDerRappro;
	}

	public void setDateDerRappro(String dateDerRappro) {
		this.dateDerRappro = dateDerRappro;
	}

	public long getDateDerRapproLong() {
		return dateDerRapproLong;
	}

	public void setDateDerRapproLong(long dateDerRapproLong) {
		this.dateDerRapproLong = dateDerRapproLong;
	}

	@Override
	public String toString() {
		return "Rappro [id=" + id + ", derSolde=" + derSolde + ", dateDerRappro=" + dateDerRappro
				+ ", dateDerRapproLong=" + dateDerRapproLong + "]";
	}
}
