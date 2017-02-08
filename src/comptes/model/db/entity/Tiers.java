package comptes.model.db.entity;

public class Tiers implements Comparable<Tiers>{
	private int id = 0;
	private String libTiers = "";
	private String derCategDeTiers;

	public Tiers() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Tiers(int id, String libTiers, String  derCategDeTiers) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.libTiers = libTiers;
		this.derCategDeTiers = derCategDeTiers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibTiers() {
		return libTiers;
	}

	public void setLibTiers(String libTiers) {
		this.libTiers = libTiers;
	}

	public String getDerCategDeTiers() {
		return derCategDeTiers;
	}

	public void setDerCategDeTiers(String derCategDeTiers) {
		this.derCategDeTiers = derCategDeTiers;
	}

	public String toString() {
		String str = "\n............Tiers.........................\n";
		 str += "Tiers ID : " + this.getId() + "\n";
		str += "Tiers tiers : " + this.getLibTiers() + "\n";
		str += "Tiers derCategDeTiers : " + this.getDerCategDeTiers() + "\n";
		// str += this.langage.toString();
		str += "\n.....................................\n";

		return str;
	}

	@Override
	public int compareTo(Tiers o) {
		// TODO Auto-generated method stub
		return libTiers.compareTo(o.getLibTiers());
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tiers other = (Tiers) obj;
		if (libTiers == null) {
			if (other.libTiers != null)
				return false;
		} else if (!libTiers.equals(other.libTiers))
			return false;
		return true;
	}
}
