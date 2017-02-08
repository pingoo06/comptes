package comptes.model;

import comptes.model.facade.BnpFacade;
import comptes.model.facade.CategorieFacade;
import comptes.model.facade.MatchingFacade;
import comptes.model.facade.OperationFacade;
import comptes.model.facade.TiersFacade;

public class Application {

	private BnpFacade bnpFacade;
	private CategorieFacade categorieFacade;
	private MatchingFacade matchingFacade;
	private OperationFacade operationFacade;
	private TiersFacade tiersFacade;

	private static Application instance;
	
	private Application() {
		bnpFacade = new BnpFacade();
		categorieFacade = new CategorieFacade();
		matchingFacade = new MatchingFacade();
		operationFacade = new OperationFacade();
		tiersFacade = new TiersFacade();
	}
	
	private static Application getInstance() {
		if(instance == null) {
			instance = new Application();
		}
		return instance;
	}
	
	public static BnpFacade getBnpFacade() {
		return getInstance().bnpFacade; 
	}

	public static CategorieFacade getCategorieFacade() {
		return getInstance().categorieFacade; 
	}
	public static MatchingFacade getMatchingFacade() {
		return getInstance().matchingFacade; 
	}
	public static OperationFacade getOperationFacade() {
		return getInstance().operationFacade; 
	}
	public static TiersFacade getTiersFacade() {
		return getInstance().tiersFacade; 
	}
	
}
