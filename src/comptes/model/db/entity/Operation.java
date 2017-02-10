 package comptes.model.db.entity;

import java.time.LocalDate;

import comptes.util.DateUtil;
import comptes.util.MyDate;

public class Operation {
	protected int id = 0;
	protected String typeOpe = "";
	protected MyDate dateOpe;
	protected double montantOpe = 0;
	protected int categOpeId = 0;
	protected int tiersId = 0;
	protected String detailOpe = "";
	protected String etatOpe = "";
	protected int echId = 0;
	
	public Operation(){
		super();
	};
	public Operation(int id, String typeOpe, MyDate dateOpe, double montantOpe,int categOpeId, int tiersId, String detailOpe, String etatOpe,
			int echId) {
		this.id = id;
		this.typeOpe = typeOpe;
		this.dateOpe = dateOpe;
		this.montantOpe = montantOpe;
		this.categOpeId = categOpeId;
		this.tiersId = tiersId;
		this.detailOpe = detailOpe;
		this.etatOpe = etatOpe;
		this.echId = echId;
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

	
	public MyDate getDateOpe() {
		return dateOpe;
	}
	public void setDateOpe(MyDate dateOpe) {
		this.dateOpe = dateOpe;
	}
	public double getMontantOpe() {
		return montantOpe;
	}

	public void setMontantOpe(double montantOpe) {
		this.montantOpe = montantOpe;
	}
	public int getCategOpeId() {
		return categOpeId;
	}

	public void setCategOpeId(int categOpeId) {
		this.categOpeId = categOpeId ;
	}
	public int getTiersId() {
		return tiersId;
	}

	public void setTiersId(int tiersId) {
		this.tiersId = tiersId;
	}

	public String getDetailOpe() {
		return detailOpe;
	}

	public void setDetailOpe(String detailOpe) {
		this.detailOpe = detailOpe;
	}
	public int getEchId() {
		return echId;
	}

	public void setEchId(int echId) {
		this.echId = echId;
	}

	public String getEtatOpe() {
		return etatOpe;
	}

	public void setEtatOpe(String etatOpe) {
		this.etatOpe = etatOpe;
	}
	public String toString() {
		String str = "\n............Operation.........................\n";
		 str += "Operation ID : " + this.getId() + "\n";
		 str += "Operation TypeOpe : " + this.getTypeOpe() + "\n";
		 str += "Operation dateOpe : " +this.getDateOpe() + "\n";
		 str += "Operation montantOpe : " +this.getMontantOpe() + "\n";
		 str += "OperationgetCategOpeId : " +this.getCategOpeId() + "\n";
		 str += "OperationgetTiersId : " +this.getTiersId() + "\n";
		 str += "Operation EtatOpe : " +this.getEtatOpe() + "\n";
		 str += "Operation EchId : " +this.getEchId() + "\n";
		 
		// str += this.langage.toString();
		str += "\n.....................................\n";
		return str;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categOpeId;
		result = prime * result + ((dateOpe == null) ? 0 : dateOpe.hashCode());
		result = prime * result + ((detailOpe == null) ? 0 : detailOpe.hashCode());
		result = prime * result + echId;
		result = prime * result + ((etatOpe == null) ? 0 : etatOpe.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(montantOpe);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + tiersId;
		result = prime * result + ((typeOpe == null) ? 0 : typeOpe.hashCode());
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
		Operation other = (Operation) obj;
		if (categOpeId != other.categOpeId)
			return false;
		if (dateOpe == null) {
			if (other.dateOpe != null)
				return false;
		} else if (!dateOpe.equals(other.dateOpe))
			return false;
		if (detailOpe == null) {
			if (other.detailOpe != null)
				return false;
		} else if (!detailOpe.equals(other.detailOpe))
			return false;
		if (echId != other.echId)
			return false;
		if (etatOpe == null) {
			if (other.etatOpe != null)
				return false;
		} else if (!etatOpe.equals(other.etatOpe))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(montantOpe) != Double.doubleToLongBits(other.montantOpe))
			return false;
		if (tiersId != other.tiersId)
			return false;
		if (typeOpe == null) {
			if (other.typeOpe != null)
				return false;
		} else if (!typeOpe.equals(other.typeOpe))
			return false;
		return true;
	}
	
}
