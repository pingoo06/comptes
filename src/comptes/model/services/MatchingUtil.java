package comptes.model.services;

import comptes.gui.dto.MatchingDTO;
import comptes.model.db.entity.Matching;
import comptes.model.facade.MatchingFacade;
import comptes.util.log.LogMatching;

public class MatchingUtil {
	private MatchingFacade myMatchingFacade;

	// Constructeur
	public MatchingUtil() {
		super();
	}

	public void create(MatchingDTO myMatchingDTO) {
		LogMatching.logDebug("create");
		myMatchingFacade.create(DTOToMatching(myMatchingDTO));
	}

	public Matching DTOToMatching(MatchingDTO myMatchingDTO) {
		LogMatching.logDebug("Début DTOToMatching ");
		Matching myMatching = new Matching();
		myMatching.setId(myMatchingDTO.getId());
		myMatching.setlibBnp(myMatchingDTO.getLibBnp());
		myMatching.setLibTiers(myMatchingDTO.getLibTier());
		return myMatching;
	}
}
