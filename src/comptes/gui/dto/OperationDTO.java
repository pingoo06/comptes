package comptes.gui.dto;

public class OperationDTO {
		private int id;
		private String typeOpe;
		private String  dateOpe;
		private double debitOpe;
		private double creditOpe;
		private String detailOpe;
		private String etatOpe ;
		private String categOpe;
		private String tiers;
		private int echId;
		private String numCheque;
	
	public OperationDTO(){
		this.id = 0;
		this.typeOpe = "";
		this. dateOpe = "";
		this.debitOpe = 0;
		this.creditOpe = 0;
		this.detailOpe = "";
		this.etatOpe = "";
		this.categOpe= "";
		this.tiers= "";
		this.numCheque="";
	}
	public OperationDTO(int id, String typeOpe, String dateOpe, double debitOpe, double creditOpe,int categOpeId, 
			String categOpe, int tiersId,String tiers, String detailOpe, String etatOpe,
			int echId) {
		this.id = id;
		this.typeOpe = typeOpe;
		this.dateOpe = dateOpe;
		this.debitOpe = debitOpe;
		this.creditOpe = creditOpe;
		this.setCategOpe(categOpe);
		this.setTiers(tiers);
		this.detailOpe = detailOpe;
		this.etatOpe = etatOpe;
		this.echId = echId;
		this.numCheque="";
	}
	public void reset() {
		this.id = 0;
		this.typeOpe = "";
		this. dateOpe = "";
		this.debitOpe = 0;
		this.creditOpe = 0;
		this.detailOpe = "";
		this.etatOpe = "";
		this.categOpe= "";
		this.tiers= "";
		this.numCheque="";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeOpe() {
		return typeOpe;
	}
	public void setTypeOpe(String typeOpe) {
		this.typeOpe = typeOpe;
	}
	public String getDateOpe() {
		return dateOpe;
	}
	public void setDateOpe(String dateOpe) {
		this.dateOpe = dateOpe;
	}
	public double getDebitOpe() {
		return debitOpe;
	}
	public void setDebitOpe(double debitOpe) {
		this.debitOpe = debitOpe;
	}
	public double getCreditOpe() {
		return creditOpe;
	}
	public void setCreditOpe(double creditOpe) {
		this.creditOpe = creditOpe;
	}
	public String getDetailOpe() {
		return detailOpe;
	}
	public void setDetailOpe(String detailOpe) {
		this.detailOpe = detailOpe;
	}
	public String getEtatOpe() {
		return etatOpe;
	}
	public void setEtatOpe(String etatOpe) {
		this.etatOpe = etatOpe;
	}
	public int getEchId() {
		return echId;
	}
	public void setEchId(int echId) {
		this.echId = echId;
	}
	public String getCategOpe() {
		return categOpe;
	}
	public void setCategOpe(String categOpe) {
		this.categOpe = categOpe;
	}
	public String getTiers() {
		return tiers;
	}
	public void setTiers(String tiers) {
		this.tiers = tiers;
	}
	public String getNumCheque() {
		return numCheque;
	}
	public void setNumCheque(String numCheque) {
		this.numCheque = numCheque;
	};

}
