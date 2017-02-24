package comptes.model.services;

import comptes.gui.dto.OperationDTO;
import comptes.model.bo.OperationBO;
import comptes.model.db.entity.Categorie;
import comptes.model.db.entity.Operation;
import comptes.model.db.entity.Tiers;
import comptes.model.facade.CategorieFacade;
import comptes.model.facade.OperationFacade;
import comptes.model.facade.TiersFacade;
import comptes.util.MyDate;
import comptes.util.log.Logger;

public class OperationUtil {
	private OperationFacade myOperationFacade = new OperationFacade();
	private TiersFacade myTiersFacade = new TiersFacade();
	private CategorieFacade myCategorieFacade = new CategorieFacade();

	// Constructeur
	public OperationUtil() {
		super();
		Logger.logDebug("Début : constructeur gestion operation ");
	}

	public void create(OperationDTO myOperationDTO) {

		Logger.logDebug("Début create operation DTO");
		myOperationFacade.create(dtoToOperation(myOperationDTO));
	}
	

	public Operation dtoToOperation(OperationDTO myOperationDTO) {
		Logger.logDebug("Début dtoToOperation");
		Operation myOperation = new Operation();
		myOperation.setId(myOperationDTO.getId());
		myOperation.setTypeOpe(myOperationDTO.getTypeOpe());
		myOperation.setDateOpe(new MyDate(myOperationDTO.getDateOpe()));
		Logger.logDebug("dans dtoToOperation de Gestion Operation : date dto" + myOperationDTO.getDateOpe());
		Logger.logDebug("dans dtoToOperation de Gestion Operation : myOperation.getDateOpe" + myOperation.getDateOpe());

		if (myOperationDTO.getDebitOpe() != 0) {
			myOperation.setMontantOpe(myOperationDTO.getDebitOpe() * -1);
		} else {
			myOperation.setMontantOpe(myOperationDTO.getCreditOpe());
		}
		String libCateg = myOperationDTO.getCategOpe();
		int idCategorie = myCategorieFacade.findLib(libCateg);
		if (idCategorie == 0) {
			Categorie myCategorie = new Categorie(0, null);
			myCategorie.setId(0);
			myCategorie.setLibCateg(myOperationDTO.getCategOpe());
			myCategorieFacade.create(myCategorie);
			idCategorie = myCategorieFacade.findLib(myOperationDTO.getCategOpe());
		}
		myOperation.setCategOpeId(idCategorie);
		// Tiers ID
		String libTiers = myOperationDTO.getTiers();
		int idTiers = myTiersFacade.findLib(libTiers);
		System.out.println("dans dtoToOperation : id Tiers après le findLib" + idTiers);
		Tiers myTiers = new Tiers(0, null, null);
		myTiers.setLibTiers(myOperationDTO.getTiers());
		myTiers.setDerCategDeTiers(myOperationDTO.getCategOpe());
		if (idTiers == 0) {
			myTiers.setId(0);
			myTiersFacade.create(myTiers);
			idTiers = myTiersFacade.findLib(libTiers);
		} else {
			myTiers.setId(idTiers);
			myTiersFacade.update(myTiers);

		}
		myOperation.setTiersId(idTiers);
		myOperation.setDetailOpe(myOperationDTO.getDetailOpe());
		myOperation.setEtatOpe(myOperationDTO.getEtatOpe());
		myOperation.setEchId(0);

		return myOperation;
	}
	

	public OperationDTO opeToDtoOperation (Operation ope){
		OperationDTO myOperationDTO=new OperationDTO();
		myOperationDTO.setCategOpe(myCategorieFacade.find(ope.getCategOpeId()).getLibCateg());
		if (ope.getMontantOpe() < 0) {
			myOperationDTO.setDebitOpe(ope.getMontantOpe() * -1);}
		else {
			myOperationDTO.setCreditOpe(ope.getMontantOpe());
		}
		myOperationDTO.setDateOpe(ope.getDateOpe().toString());
		myOperationDTO.setDetailOpe(null);
		myOperationDTO.setEchId(0);
		myOperationDTO.setEtatOpe("NR");
		myOperationDTO.setId(0);
		myOperationDTO.setTiers(myTiersFacade.find(ope.getTiersId()).getLibTiers());
		myOperationDTO.setTypeOpe(ope.getTypeOpe());
		return myOperationDTO;
	}

	public OperationBO buildOperationBo(OperationDTO ope) {
		System.out.println("Début : buildOperationBO");
		int idTiers = myTiersFacade.findLib(ope.getTiers());
		OperationBO res = new OperationBO(dtoToOperation(ope));
		Tiers myTiers = myTiersFacade.find(idTiers);
		int idCategorie = myCategorieFacade.findLib(ope.getCategOpe());
		Categorie myCategorie = myCategorieFacade.find(idCategorie);
		res.setTiersBO(myTiers);
		res.setCategorieBo(myCategorie);
		return res;
	}
	
	public String getLibTiersFromOpe(Operation myOperation) {
		TiersFacade myTiersFacade = new TiersFacade();
		Tiers myTiers = new Tiers();
		myTiers = myTiersFacade.find(myOperation.getTiersId());
		return myTiers.getLibTiers();
	}
}
