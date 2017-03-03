package comptes.gui.dto;

public class MatchingDTO {
	private int id;;
	private String libBnp;
	private String libTier ;

	public MatchingDTO() {
		this.id = 0;
		this.libBnp = "";
		this.libTier = "";
			}

	public MatchingDTO(int id, String libBnp, String libTier) {
		super();
		this.id = id;
		this.libBnp = libBnp;
		this.libTier = libTier;
		}

	public void reset() {
		this.id = 0;
		this.libBnp = "";
		this.libTier = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibBnp() {
		return libBnp;
	}

	public void setLibBnp(String libBnp) {
		this.libBnp = libBnp;
	}

	public String getLibTier() {
		return libTier;
	}

	public void setLibTier(String libTier) {
		this.libTier = libTier;
	}

	
}