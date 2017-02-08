package comptes.model.services;

import comptes.gui.dto.EcheancierDTO;
import comptes.model.bo.EcheancierBO;
import comptes.model.db.entity.Categorie;
import comptes.model.db.entity.Echeancier;
import comptes.model.db.entity.Tiers;
import comptes.model.facade.CategorieFacade;
import comptes.model.facade.EcheancierFacade;
import comptes.model.facade.TiersFacade;
import comptes.util.DateUtil;

public class GestionEcheancier {
	private TiersFacade myTiersFacade = new TiersFacade();
	private CategorieFacade myCategorieFacade = new CategorieFacade();
	private EcheancierFacade myEcheancierFacade = new EcheancierFacade();

	// Constructeur
	public GestionEcheancier() {
		super();
		System.out.println("Début : constructeur gestion echeancier ");
	}

	public void create(EcheancierDTO myEcheancierDTO) {
		System.out.println("Début create echeancier BO dans gestion echeancier");
		myEcheancierFacade.create(DTOToEcheancier(myEcheancierDTO));
	}

	public Echeancier DTOToEcheancier(EcheancierDTO myEcheancierDTO) {
		System.out.println("Début BOToEcheancier dans gestion operationO");
		Echeancier myEcheancier = new Echeancier();
		myEcheancier.setId(myEcheancierDTO.getId());
		myEcheancier.setTypeEch(myEcheancierDTO.getTypeEch());
		myEcheancier.setDateEch(DateUtil.format(DateUtil.parse(myEcheancierDTO.getDateEch()),"yyyy-MM-dd"));
		myEcheancier.setMontantEch(myEcheancierDTO.getMontantEch());
		myEcheancier.setNbEch(myEcheancierDTO.getNbEch());
		String libCateg = myEcheancierDTO.getCategEch();
		int idCategorie = myCategorieFacade.findLib(libCateg);
		if (idCategorie == 0) {
			Categorie myCategorie = new Categorie(0, null);
			myCategorie.setId(0);
			myCategorie.setLibCateg(libCateg);
			myCategorieFacade.create(myCategorie);
			idCategorie = (myCategorieFacade.findLib(libCateg));
		}
		myEcheancier.setCategEchId(idCategorie);
		String libTiers = myEcheancierDTO.getTiersEch();
		int idTiers = myTiersFacade.findLib(libTiers);
		Tiers myTiers = new Tiers(0, null, null);
		myTiers.setLibTiers(libTiers);
		myTiers.setDerCategDeTiers(libCateg);
		if (idTiers == 0) {
			myTiers.setId(0);
			myTiersFacade.create(myTiers);
			idTiers = myTiersFacade.findLib(libTiers);
		} else {
			myTiers.setId(idTiers);
			myTiersFacade.update(myTiers);
		}
		myEcheancier.setTiersEchId(idTiers);
		return myEcheancier;
	}

	public EcheancierBO buildEcheancierBo(EcheancierDTO ech) {
		System.out.println("Début : buildEcheanciernBO");
		int idTiers = myTiersFacade.findLib(ech.getTiersEch());
		EcheancierBO res = new EcheancierBO(DTOToEcheancier(ech));
		Tiers myTiers = myTiersFacade.find(idTiers);
		int idCategorie = myCategorieFacade.findLib(ech.getCategEch());
		Categorie myCategorie = myCategorieFacade.find(idCategorie);
		res.setTiersBo(myTiers);
		res.setCategorieBo(myCategorie);
		return res;
	}

		public static Echeancier boToEcheancier(EcheancierBO myEcheancierBO) {
			TiersFacade myTiersFacade = new TiersFacade();
			CategorieFacade myCategorieFacade = new CategorieFacade();
			System.out.println("Début : BoToEcheancier");
			Echeancier myEcheancier = new Echeancier();
			myEcheancier.setId(myEcheancierBO.getId());
			myEcheancier.setTypeEch(myEcheancierBO.getTypeEch());
			myEcheancier.setDateEch(myEcheancierBO.getDateEch());
			myEcheancier.setDateEchLong(myEcheancierBO.getDateEchLong());
			System.out.println("Dans BoToEcheancier myEcheancier.getDateEch" + myEcheancier.getDateEch()); 
			myEcheancier.setMontantEch(myEcheancierBO.getMontantEch());
			myEcheancier.setNbEch(myEcheancierBO.getNbEch());
			String libCateg = myEcheancierBO.getCategorieBo().getLibCateg();
			int idCategorie = myCategorieFacade.findLib(libCateg);
			if (idCategorie == 0) {
				Categorie myCategorie = new Categorie(0, null);
				myCategorie.setId(0);
				myCategorie.setLibCateg(libCateg);
				myCategorieFacade.create(myCategorie);
				idCategorie = (myCategorieFacade.findLib(libCateg));
			}
			myEcheancier.setCategEchId(idCategorie);
			String libTiers = myEcheancierBO.getTiersBo().getLibTiers();
			int idTiers = myTiersFacade.findLib(libTiers);
			Tiers myTiers = new Tiers(0, null, null);
			myTiers.setLibTiers(libTiers);
			myTiers.setDerCategDeTiers(libCateg);
			if (idTiers == 0) {
				myTiers.setId(0);
				myTiersFacade.create(myTiers);
				idTiers = myTiersFacade.findLib(libTiers);
			} else {
				myTiers.setId(idTiers);
				myTiersFacade.update(myTiers);
			}
			myEcheancier.setTiersEchId(idTiers);
			return myEcheancier;
		}

	

}
