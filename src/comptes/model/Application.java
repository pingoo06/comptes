package comptes.model;

import comptes.model.facade.BnpFacade;
import comptes.model.facade.CategorieFacade;
import comptes.model.facade.MatchingFacade;
import comptes.model.facade.OperationFacade;
import comptes.model.facade.TiersFacade;
import comptes.model.services.OperationUtil;
import comptes.util.MyDate;

public class Application {

	private BnpFacade bnpFacade;
	private CategorieFacade categorieFacade;
	private MatchingFacade matchingFacade;
	private OperationFacade operationFacade;
	private TiersFacade tiersFacade;

	private double solde;
	private double soldeADate;
	private double soldePointe;
	private static Application instance;

	private Application() {
		bnpFacade = new BnpFacade();
		categorieFacade = new CategorieFacade();
		matchingFacade = new MatchingFacade();
		operationFacade = new OperationFacade();
		tiersFacade = new TiersFacade();
		updateSolde();
		updateSoldeADate(new MyDate());
		updateSoldePointe();
	}

	public static Application getInstance() {
		if (instance == null) {
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

	public static double getSolde() {
		return getInstance().solde;
	}

	public void updateSolde() {
		solde = new OperationUtil().sumOperation();
	}

	public void updateSoldePointe() {
		soldePointe = new OperationUtil().sumOperationPointe();
	}

	public void updateSoldeADate(MyDate myDate) {
		soldeADate = new OperationUtil().sumOperationUntil(myDate);

	}

	public static double getSoldeADate() {
		return getInstance().soldeADate;
	}

	public static double getSoldePointe() {
		return getInstance().soldePointe;
	}

}
