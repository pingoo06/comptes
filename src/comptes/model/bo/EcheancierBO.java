package comptes.model.bo;

import comptes.model.db.entity.Categorie;
import comptes.model.db.entity.Echeancier;
import comptes.model.db.entity.Tiers;

public class EcheancierBO extends Echeancier{
	private Categorie categorieBo;
	private Tiers tiersBo;

	public EcheancierBO() {
		id = 0;
	}

	public EcheancierBO(Echeancier ech) {
		id = ech.getId();
		super.typeEch = ech.getTypeEch();
		super.tiersEchId = ech.getTiersEchId();
		super.categEchId = ech.getCategEchId();
		super.dateEch=ech.getDateEch();
		super.montantEch = ech.getMontantEch();
		super.nbEch = ech.getNbEch();
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
