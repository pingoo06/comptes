package comptes.model.bo;

import comptes.model.db.entity.Categorie;
import comptes.model.db.entity.Echeancier;
import comptes.model.db.entity.Tiers;

public class EcheancierBO extends Echeancier{
	private Categorie categorieBo;
	private Tiers tiersBo;

	public EcheancierBO() {
		id = 0;
		super.typeEch = "";
		super.tiersEchId = 0;
		super.categEchId = 0;
		super.dateEch = "";
		super.montantEch = 0;
		super.nbEch = 0;
		super.dateEchLong = 0;
	}

	public EcheancierBO(Echeancier ech) {
		// TODO Auto-generated constructor stub
		id = ech.getId();
		super.typeEch = ech.getTypeEch();
		super.tiersEchId = ech.getTiersEchId();
		super.categEchId = ech.getCategEchId();
		super.dateEch=ech.getDateEch();
		super.montantEch = ech.getMontantEch();
		super.nbEch = ech.getNbEch();
		super.dateEchLong = ech.getDateEchLong();
	}

	public Categorie getCategorieBo() {
		return categorieBo;
	}

	public void setCategorieBo(Categorie categorieBo) {
		this.categorieBo = categorieBo;
	}

	public Tiers getTiersBo() {
		return tiersBo;
	}

	public void setTiersBo(Tiers tiersBo) {
		this.tiersBo = tiersBo;
	}

//	public void setMontantEch(String text) {
//		// TODO Auto-generated method stub
//		
//	}

}
