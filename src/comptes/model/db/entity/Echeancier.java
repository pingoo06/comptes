package comptes.model.db.entity;

import comptes.util.MyDate;

public class Echeancier {
	protected int id = 0;
	protected String typeEch = "";
	protected int tiersEchId;
	protected int categEchId;
	protected MyDate dateEch;
	protected double montantEch;
	protected int nbEch;
	
	public Echeancier() {
		super();
	};

	public Echeancier(int id, String typeEch, int tiersEchId, int categEchId, MyDate dateEch, double montantEch,
			int nbEch) {
		super();
		this.id = id;
		this.typeEch = typeEch;
		this.tiersEchId = tiersEchId;
		this.categEchId = categEchId;
		this.dateEch = dateEch;
		this.montantEch = montantEch;
		this.nbEch = nbEch;
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

	
	public MyDate getDateEch() {
		return dateEch;
	}

	public void setDateEch(MyDate dateEch) {
		this.dateEch = dateEch;
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
		str += "\n.....................................\n";
		return str;
	}
}
