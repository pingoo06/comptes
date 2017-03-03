package comptes.model.db.entity;

public class Matching {

	private int id = 0;
	private String libTiers = "";
	private String libBnp = "";

	public Matching() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Matching(int id, String libTiers, String libBnp) {
		this.id = id;
		this.libTiers = libTiers;
		this.libBnp = libBnp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getlibTiers() {
		return libTiers;
	}

	public void setLibTiers(String libTiers) {
		this.libTiers = libTiers;
	}

	public String getlibBnp() {
		return libBnp;
	}

	public void setlibBnp(String libBnp) {
		this.libBnp = libBnp;
	}

	public String toString() {
		String str = "\n............Matching.........................\n";
		str += "Matching :" + this.getId() + "\n";
		str += "Matching libTier : " + this.getlibTiers() + "\n";
		str += "Matching libBnp : " + this.getlibBnp() + "\n";
		// str += this.langage.toString();
		str += "\n.....................................\n";
		return str;
	}
}
