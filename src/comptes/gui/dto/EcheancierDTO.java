package comptes.gui.dto;

public class EcheancierDTO {
	private int id = 0;
	private String typeEch = "";
	private String tiersEch = "";
	private String categEch = "";
	private String dateEch = "";
	private double montantEch = 0;
	private int nbEch = 0;

	public EcheancierDTO() {
		this.id = 0;
		this.typeEch = "";
		this.tiersEch = "";
		this.categEch = "";
		this.dateEch = "";
		this.montantEch = 0;
		this.nbEch = 0;
	}

	public EcheancierDTO(int id, String typeEch, String tiersEch, String categEch, String dateEch, double montantEch,
			int nbEch) {
		super();
		this.id = id;
		this.typeEch = typeEch;
		this.tiersEch = tiersEch;
		this.categEch = categEch;
		this.dateEch = dateEch;
		this.montantEch = montantEch;
		this.nbEch = nbEch;
	}

	public void reset() {
		this.id = 0;
		this.typeEch = "";
		this.tiersEch = "";
		this.categEch = "";
		this.dateEch = "";
		this.montantEch = 0;
		this.nbEch = 0;
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

	public String getTiersEch() {
		return tiersEch;
	}

	public void setTiersEch(String tiersEch) {
		this.tiersEch = tiersEch;
	}

	public String getCategEch() {
		return categEch;
	}

	public void setCategEch(String categEch) {
		this.categEch = categEch;
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
}