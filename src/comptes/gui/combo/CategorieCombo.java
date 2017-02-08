package comptes.gui.combo;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComboBox;

import comptes.model.db.entity.Categorie;
import comptes.model.facade.CategorieFacade;
import comptes.util.log.LogCategorie;

public class CategorieCombo extends AutoCompletedComboBox {
	public CategorieCombo() {
		LogCategorie.logDebug(" remplissage combo Categorie");
		CategorieFacade myCategorieFacade = new CategorieFacade();
		ArrayList<Categorie> myCategorieList = myCategorieFacade.findAll();
		Iterator<Categorie> it = myCategorieList.iterator();
		while (it.hasNext()) {
			Categorie myCategorie = it.next();
			this.addItem(myCategorie.getLibCateg());
		}
		this.setPreferredSize(new Dimension(100, 20));
	}
}

