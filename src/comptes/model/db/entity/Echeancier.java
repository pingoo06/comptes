package comptes.model.db.entity;

public class Echeancier {
	protected int id = 0;
	protected String typeEch = "";
	protected int tiersEchId = 0;
	protected int categEchId = 0;
	protected String dateEch="";
	protected double montantEch = 0;
	protected int nbEch = 0;
	protected long dateEchLong = 0;
	
	public Echeancier() {
		super();
	};

	public Echeancier(int id, String typeEch, int tiersEchId, int categEchId, String dateEch, double montantEch,
			int nbEch, long dateEchLong) {
		super();
		this.id = id;
		this.typeEch = typeEch;
		this.tiersEchId = tiersEchId;
		this.categEchId = categEchId;
		this.dateEch = dateEch;
		this.montantEch = montantEch;
		this.nbEch = nbEch;
		this.dateEchLong = dateEchLong;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeEch() {
		return typeEch;
	}

	public void setTypeEch(String typeEch) {
		this.typeEch = typeEch;
	}

	public int getTiersEchId() {
		return tiersEchId;
	}

	public void setTiersEchId(int tiersEchId) {
		this.tiersEchId = tiersEchId;
	}

	public int getCategEchId() {
		return categEchId;
	}

	public void setCategEchId(int categEchId) {
		this.categEchId = categEchId;
	}

	public String getDateEch() {
		return dateEch;
	}

	public void setDateEch(String dateEch) {
		this.dateEch = dateEch;
	}

	public double getMontantEch() {
		return montantEch;
	}

	public void setMontantEch(double montantEch) {
		this.montantEch = montantEch;
	}

	public int getNbEch() {
		return nbEch;
	}

	public void setNbEch(int nbEch) {
		this.nbEch = nbEch;
	}

	public long getDateEchLong() {
		return dateEchLong;
	}

	public void setDateEchLong(long dateEchLong) {
		this.dateEchLong=dateEchLong;
	}
	public String toString() {
		String str = "\n............Operation.........................\n";
		 str += "Echeancier ID : " + this.getId() + "\n";
		 str += "Echeancier TypeEch : " + this.getTypeEch() + "\n";
		 str += "Echeancier tiersEchId : " +this.getTiersEchId() + "\n";
		 str += "Echeancier categEchId : " +this.getCategEchId() + "\n";
		 str += "Echeancier dateEch : " +this.getDateEch() + "\n";
		 str += "Echeancier :montantEch " +this.getMontantEch() + "\n";
		 str += "Echeancier nbEch : " +this.getNbEch() + "\n";
		 str += "Echeancier dateEchLong : " +this.getDateEchLong() + "\n";
		str += "\n.....................................\n";
		return str;
	}
}
