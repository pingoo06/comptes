package comptes.model.db.entity;

public class Categorie implements Comparable<Categorie>{
	private int id = 0;
	private String libCateg = "";

	public Categorie(int id, String libCateg) {
		this.id = id;
		this.libCateg = libCateg;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibCateg() {
		return libCateg;
	}

	public void setLibCateg(String libCateg) {
		this.libCateg = libCateg;
	}

	

	public String toString() {
		String str = "\n............Categorie.........................\n";
		str += "Categorie" + this.getId() + "\n";
		str += "Categorie libCateg : " + this.getLibCateg() + "\n";
		str += "\n.....................................\n";
		return str;
	}

	@Override
	public int compareTo(Categorie o) {
		// TODO Auto-generated method stub
		return libCateg.compareTo(o.getLibCateg());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((libCateg == null) ? 0 : libCateg.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categorie other = (Categorie) obj;
		if (libCateg == null) {
			if (other.libCateg != null)
				return false;
		} else if (!libCateg.equals(other.libCateg))
			return false;
		return true;
	}

	
	
}
