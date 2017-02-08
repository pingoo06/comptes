package comptes.model.db.entity;

public class Matching {

	private int id = 0;
	private String libTier = "";
	private String libBnp = "";

	public Matching(int id, String libTier, String libBnp) {
		this.id = id;
		this.libTier = libTier;
		this.libBnp = libBnp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getlibTier() {
		return libTier;
	}

	public void setLibCateg(String libTier) {
		this.libTier = libTier;
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
		str += "Matching libTier : " + this.getlibTier() + "\n";
		str += "Matching libBnp : " + this.getlibBnp() + "\n";
		// str += this.langage.toString();
		str += "\n.....................................\n";
		return str;
	}
}
