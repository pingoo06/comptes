package comptes.model.bo;

import comptes.model.db.entity.Categorie;
import comptes.model.db.entity.Operation;
import comptes.model.db.entity.Tiers;

public class OperationBO extends Operation {
	private Categorie categorieBo;
	private Tiers tiersBo;
	private double debitBO;
	private double creditBO;
	private long dateOpeLong;

	public OperationBO() {
		id = 0;
		super.typeOpe = "";
		super.dateOpe = "";
		super.montantOpe = 0;
		super.categOpeId = 0;
		super.tiersId = 0;
		super.detailOpe = "";
		super.etatOpe = "";
		super.echId = 0;
		debitBO = 0;
		creditBO = 0;
		if (montantOpe < 0) {
			debitBO = montantOpe * -1;
		} else {
			creditBO = montantOpe;
		}
		dateOpeLong=0;
	}

	public OperationBO(Operation ope) {
		// TODO Auto-generated constructor stub
		id = ope.getId();
		super.typeOpe = ope.getTypeOpe();
		super.dateOpe = ope.getDateOpe();
		super.montantOpe = ope.getMontantOpe();
		super.categOpeId = ope.getCategOpeId();
		super.tiersId = ope.getTiersId();
		super.detailOpe = ope.getDetailOpe();
		super.etatOpe = ope.getEtatOpe();
		super.echId = ope.getEchId();
		super.dateOpeLong = ope.getDateOpeLong();
		debitBO = 0;
		creditBO = 0;
		if (montantOpe < 0) {
			debitBO = montantOpe * -1;
		} else {
			creditBO = montantOpe;
		}
	}

	public Categorie getCategorieBO() {
		return categorieBo;
	}

	public void setCategorieBo(Categorie categorieBo) {
		this.categorieBo = categorieBo;
	}

	public Tiers getTiersBO() {
		return tiersBo;
	}

	public void setTiersBO(Tiers tiersBo) {
		this.tiersBo = tiersBo;
	}

	public double getDebitBO() {
		return debitBO;
	}

	public void setDebitBO(double debitBO) {
		this.debitBO = debitBO;
	}

	public double getCreditBO() {
		return creditBO;
	}

	public void setCreditBO(double creditBO) {
		this.creditBO = creditBO;
	}

}
