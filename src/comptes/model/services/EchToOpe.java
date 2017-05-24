package comptes.model.services;

import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.DMUL;

import comptes.model.bo.EcheancierBO;
import comptes.model.db.dao.EcheancierDAO;
import comptes.model.db.entity.Echeancier;
import comptes.model.db.entity.Operation;
import comptes.model.facade.EcheancierFacade;
import comptes.model.facade.OperationFacade;
import comptes.util.MyDate;
import comptes.util.log.LogEcheancier;
import comptes.util.log.Logger;

public class EchToOpe {
	private MyDate dateJour;
	private MyDate dateEchSuiv;

	public EchToOpe () {
		LogEcheancier.logDebug("Début echToOpe");
		ArrayList<EcheancierBO> listEcheancierBO = new EcheancierDAO().findAllEchBO();
		EcheancierBO myEcheancierBO = new EcheancierBO();
		dateJour = new MyDate();
		for (int i = 0; i < listEcheancierBO.size(); i++) {
			myEcheancierBO = listEcheancierBO.get(i);
			int nbEch=myEcheancierBO.getNbEch();
			LogEcheancier.logDebug("Dans EchToOpe run : dateJourLong " + dateJour.toLongValue());
			LogEcheancier.logDebug("Dans EchToOpe run : myEcheancierBO.getDateEchLong() " + myEcheancierBO.getDateEch().toLongValue());
			
		if (myEcheancierBO.getDateEch().toLongValue() <= dateJour.toLongValue() && nbEch > 0) {
//			if (myEcheancierBO.getDateEch().compareTo(dateJour) <= 0 && nbEch > 0) {
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
				} else {
					if (typeEch == "Virenment"){
						typeEch = "VIR_EMIS";
					}
				}
				myOperation.setTypeOpe(myEcheancierBO.getTypeEch());
				OperationFacade myOperationFacade = new OperationFacade();
				myOperationFacade.create(myOperation);
				EcheancierFacade myEcheancierFacade=new EcheancierFacade();
				
				Echeancier myEcheancier=new Echeancier();
				myEcheancierBO.setNbEch(nbEch - 1);
				
				LogEcheancier.logDebug("Dans EchToOpe run : dateEch " + myEcheancierBO.getDateEch());
				dateEchSuiv = myEcheancierBO.getDateEch();
				dateEchSuiv.plusMonth(1);
				LogEcheancier.logDebug("Dans EchToOpe : dateEchSuiv " + dateEchSuiv);
				myEcheancierBO.setDateEch(dateEchSuiv);
				myEcheancier=EcheancierUtil.boToEcheancier(myEcheancierBO);
				myEcheancierFacade.update(myEcheancier);
			}
		}
	}

}

// Rattrapage date echeancier
// update echeancier set dateEch='2017-01-26', dateEchLong='17192';
// commit;
//
// delete from operation where operation.echID > 0;
// Commit;