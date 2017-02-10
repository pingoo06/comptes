package comptes.model.services;

import java.time.LocalDate;
import java.util.ArrayList;

import comptes.model.bo.EcheancierBO;
import comptes.model.db.dao.EcheancierDAO;
import comptes.model.db.entity.Echeancier;
import comptes.model.db.entity.Operation;
import comptes.model.facade.EcheancierFacade;
import comptes.model.facade.OperationFacade;
import comptes.util.MyDate;
import comptes.util.log.Logger;

public class EchToOpe extends Thread {
	@Override
	public void run() {
		ArrayList<EcheancierBO> listEcheancierBO = new EcheancierDAO().findAllEchBO();
		EcheancierBO myEcheancierBO = new EcheancierBO();
		MyDate dateJour = new MyDate();
		for (int i = 0; i < listEcheancierBO.size(); i++) {
			myEcheancierBO = listEcheancierBO.get(i);
			int nbEch=myEcheancierBO.getNbEch();
			Logger.logDebug("Dans EchToOpe run : dateJourLong " + dateJour.toLongValue());
			Logger.logDebug("Dans EchToOpe run : myEcheancierBO.getDateEchLong() " + myEcheancierBO.getDateEch().toLongValue());
			
			if (myEcheancierBO.getDateEch().toLongValue() <= dateJour.toLongValue() && nbEch > 0) {
				Operation myOperation = new Operation();
				myOperation.setCategOpeId(myEcheancierBO.getCategorieBo().getId());
				myOperation.setDateOpe(myEcheancierBO.getDateEch());
				myOperation.setDetailOpe("");
				myOperation.setEchId(myEcheancierBO.getId());
				myOperation.setEtatOpe("NR");
				myOperation.setMontantOpe(myEcheancierBO.getMontantEch());
				myOperation.setTiersId(myEcheancierBO.getTiersBo().getId());
				String typeEch = myEcheancierBO.getTypeEch();
				if (typeEch == "Prelevement"){
					typeEch = "PRLV";
				}
				myOperation.setTypeOpe(myEcheancierBO.getTypeEch());
				OperationFacade myOperationFacade = new OperationFacade();
				myOperationFacade.create(myOperation);
				EcheancierFacade myEcheancierFacade=new EcheancierFacade();
				
				Echeancier myEcheancier=new Echeancier();
				myEcheancierBO.setNbEch(nbEch - 1);
				
				Logger.logDebug("Dans EchToOpe run : dateEch " + myEcheancierBO.getDateEch());
				myEcheancierBO.getDateEch().plusMonth(1);
				myEcheancier=GestionEcheancier.boToEcheancier(myEcheancierBO);
				myEcheancierFacade.update(myEcheancier);
			}
		}
	}
	
}

//Rattrapage date echeancier
//update  echeancier set dateEch='2017-01-26', dateEchLong='17192';
//commit;
//
//delete from operation where operation.echID > 0;
//Commit;