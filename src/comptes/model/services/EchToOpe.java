package comptes.model.services;

import java.time.LocalDate;
import java.util.ArrayList;

import comptes.model.bo.EcheancierBO;
import comptes.model.db.dao.EcheancierDAO;
import comptes.model.db.entity.Categorie;
import comptes.model.db.entity.Echeancier;
import comptes.model.db.entity.Operation;
import comptes.model.db.entity.Tiers;
import comptes.model.facade.CategorieFacade;
import comptes.model.facade.EcheancierFacade;
import comptes.model.facade.OperationFacade;
import comptes.model.facade.TiersFacade;
import comptes.util.DateUtil;
import comptes.util.log.Logger;

public class EchToOpe extends Thread {
	@Override
	public void run() {
		ArrayList<EcheancierBO> listEcheancierBO = new EcheancierDAO().findAllEchBO();
		EcheancierBO myEcheancierBO = new EcheancierBO();
		LocalDate dateJour = LocalDate.now();
		LocalDate dateEch = null;
		long dateJourLong = dateJour.toEpochDay();
		for (int i = 0; i < listEcheancierBO.size(); i++) {
			myEcheancierBO = listEcheancierBO.get(i);
			int nbEch=myEcheancierBO.getNbEch();
			Logger.logDebug("Dans EchToOpe run : dateJourLong " + dateJourLong);
			Logger.logDebug("Dans EchToOpe run : myEcheancierBO.getDateEchLong() " + myEcheancierBO.getDateEchLong());
			
			if (myEcheancierBO.getDateEchLong() <= dateJourLong && nbEch > 0) {
				Operation myOperation = new Operation();
				myOperation.setCategOpeId(myEcheancierBO.getCategorieBo().getId());
				myOperation.setDateOpe(myEcheancierBO.getDateEch());
				myOperation.setDateOpeLong(myEcheancierBO.getDateEchLong());
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
				
				Logger.logDebug("Dans EchToOpe run : dateEch " + dateEch);
				dateEch = DateUtil.parse(myEcheancierBO.getDateEch(), "yyyy-MM-dd");
				dateEch= dateEch.plusMonths(1);
				Logger.logDebug("Dans EchToOpe run : dateEch +1 mois " + dateEch);
				myEcheancierBO.setDateEch(DateUtil.format(dateEch, "yyyy-MM-dd"));
				Logger.logDebug("Dans EchToOpe run : date ech de echeancier bo après le set " + myEcheancierBO.getDateEch());
				myEcheancier.setDateEchLong(dateEch.toEpochDay());
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