package comptes.gui.combo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import comptes.model.db.entity.Tiers;
import comptes.model.facade.TiersFacade;

public class TiersCombo extends AutoCompletedComboBox {
	private static final long serialVersionUID = -1551563618558051924L;

	public TiersCombo() {
		// remplissage combo Tiers
		//String[] tab = new String[1000];
		this.addItem("Tout");
		TiersFacade myTiersFacade = new TiersFacade();
		ArrayList<Tiers> myTiersList = myTiersFacade.findAll();
		Collections.sort(myTiersList);
		Iterator<Tiers> it = myTiersList.iterator();
		while (it.hasNext()) {
			Tiers myTiers = it.next();
			this.addItem(myTiers.getLibTiers());
		}
	}
}