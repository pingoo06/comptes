package comptes.gui.onglets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import comptes.gui.combo.CategorieCombo;
import comptes.gui.combo.TiersCombo;
import comptes.gui.combo.TypeOpeCombo;
import comptes.gui.component.MyJTextField;
import comptes.gui.dto.OperationDTO;
import comptes.gui.listener.DateDocumentListener;
import comptes.model.db.entity.Tiers;
import comptes.model.facade.OperationFacade;
import comptes.model.facade.TiersFacade;
import comptes.util.DateUtil;
import comptes.util.log.LogOperation;
import comptes.util.log.LogRappro;

public class PanelCreationOperation extends Box {

	private static final long serialVersionUID = 1084560641919471528L;
	private JLabel labelTypeOpe;
	private JLabel labelNumChq;
	private JLabel labelTiers;
	private JLabel labelDateOpe;
	private JLabel labelDebit;
	private JLabel labelCredit;
	private JLabel labelCategOpe;
	private JLabel labelDetailOpe;

	private JButton boutonOKOpe;
	private JButton boutonAnnulOpe;
	private MyJTextField jtfDateOpe;
	private MyJTextField jtfNumChq;
	private MyJTextField jtfDetailOpe;
	private MyJTextField jtfDebit;
	private MyJTextField jtfCredit;

	// combo operation
	private TypeOpeCombo comboTypeOpe;
	private TiersCombo comboTiers;
	private CategorieCombo comboCategorie;

	private Box b1;
	private Box b2;
	private Box b3;

	public PanelCreationOperation() {
		super(BoxLayout.PAGE_AXIS);
		b1 = Box.createHorizontalBox();
		b2 = Box.createHorizontalBox();
		b3 = Box.createHorizontalBox();
		LocalDate dateJour = LocalDate.now();
		String dateJourStr = DateUtil.format(dateJour, "dd/MM/yyyy");
		jtfDateOpe = new MyJTextField(dateJourStr);
		jtfNumChq = new MyJTextField("");
		jtfDetailOpe = new MyJTextField("");
		jtfDebit = new MyJTextField("");
		jtfCredit = new MyJTextField("");

		// combo operation
		comboTypeOpe = new TypeOpeCombo();
		comboTiers = new TiersCombo();
		comboCategorie = new CategorieCombo();

		labelTypeOpe = new JLabel("TypeOpe");
		labelTiers = new JLabel("Tiers");
		labelDateOpe = new JLabel("Date Ope");
		labelNumChq = new JLabel("N° cheque");
		labelDebit = new JLabel("Debit");
		labelCredit = new JLabel("Credit");
		labelCategOpe = new JLabel("Categorie");
		labelDetailOpe = new JLabel("DetailOpe");

		boutonAnnulOpe = new JButton("Annuler");
		boutonOKOpe = new JButton("Valider");

		boutonAnnulOpe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearSaisieOpe();
			}
		});

		comboTiers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TiersFacade myTiersFacade = new TiersFacade();
				int idTiers = myTiersFacade.findLib(comboTiers.getSelectedItem().toString());
				if (idTiers != 0) {
					Tiers myTiers = myTiersFacade.find(idTiers);
					String derCategDeTiers = myTiers.getDerCategDeTiers();
					comboCategorie.setSelectedItem(derCategDeTiers);
				} else {
					comboCategorie.setSelectedIndex(0);
				}

			}
		}
		);
		comboTypeOpe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// jtfTypeOpe.setText(comboTypeOpe.getSelectedItem().toString());
				String libType = comboTypeOpe.getSelectedItem().toString();
				if (libType == "RETRAIT") {
					comboTiers.setSelectedItem("RETRAIT");
					comboCategorie.setSelectedItem("Retrait d especes");
					jtfDebit.requestFocus();
				} else if (libType == "DEPOT") {
					comboTiers.setSelectedItem("Depot");
					comboCategorie.setSelectedItem("Depot");
					jtfCredit.requestFocus();
				} else if (libType == "CHQ") {
					OperationFacade myOperationFacade = new OperationFacade();
					String tmp = new Long(myOperationFacade.findDerChq() + 1).toString();
					jtfNumChq.setText(tmp);
					jtfCredit.requestFocus();
				}
			}
		});

		jtfDateOpe.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});

		jtfDateOpe.getDocument().addDocumentListener(new DateDocumentListener(jtfDateOpe));

		b1.add(labelDateOpe);
		b1.add(jtfDateOpe);
		b1.add(labelTypeOpe);
		b1.add(comboTypeOpe);
		b1.add(labelNumChq);
		b1.add(jtfNumChq);
		b1.add(labelTiers);
		b1.add(comboTiers);
		b1.add(labelCategOpe);
		b1.add(comboCategorie);
		JPanel jpB2 = new JPanel();
		jpB2.add(wrap(labelDebit, jtfDebit));
		jpB2.add(wrap(labelCredit, jtfCredit));
		jpB2.add(wrap(labelDetailOpe, jtfDetailOpe));
		b2.add(jpB2);
		b3.add(boutonAnnulOpe);
		b3.add(boutonOKOpe);

		b3.add(boutonAnnulOpe);
		b3.add(boutonOKOpe);

		add(b1);
		add(b2);
		add(b3);
	}

	private JPanel wrap(JLabel label, MyJTextField tf) {
		JPanel jp = new JPanel();
		jp.add(label);
		jp.add(tf);
		return jp;
	}

	public OperationDTO createOpeDtoFromField() {
		OperationDTO myOperationDTO = new OperationDTO();
		String choixTypeOpe = comboTypeOpe.getSelectedItem().toString();
		if ("CHQ".equals(choixTypeOpe)) {
			myOperationDTO.setTypeOpe(jtfNumChq.getText());
		} else {
			myOperationDTO.setTypeOpe(choixTypeOpe);
		}
		myOperationDTO.setDateOpe(jtfDateOpe.getText());
		myOperationDTO.setTiers(comboTiers.getSelectedItem().toString());
		myOperationDTO.setCategOpe(comboCategorie.getSelectedItem().toString());
		LogRappro.logDebug("Dans BoutonOKListener de fenetre : jtfDebit : " + jtfDebit.getText());
		if (jtfDebit.getText() != "" && jtfDebit.getText() != "0" && jtfDebit.getText().length() != 0) {
			try {
				myOperationDTO.setDebitOpe(Double.parseDouble(jtfDebit.getText()));
			} catch (NumberFormatException e1) {
				LogRappro.logError("Montant DEBIT KO : " + jtfDebit.getText(), e1);
			}
		} else {
			myOperationDTO.setDebitOpe(0);
		}
		if (jtfCredit.getText() != "" && jtfCredit.getText() != "0" && jtfCredit.getText().length() != 0) {
			try {
				myOperationDTO.setCreditOpe(Double.parseDouble(jtfCredit.getText()));
			} catch (NumberFormatException e1) {
				LogRappro.logError("Montant Credit KO : " + jtfCredit.getText(), e1);
			}
		} else {
			myOperationDTO.setCreditOpe(0);
		}
		LogRappro.logDebug("debit d'operation DTO : " + myOperationDTO.getDebitOpe());
		LogRappro.logDebug("debit d'operation DTO :  " + myOperationDTO.getCreditOpe());

		myOperationDTO.setDetailOpe(jtfDetailOpe.getText());
		myOperationDTO.setEtatOpe("NR");
		myOperationDTO.setEchId(0);
		myOperationDTO.setNumCheque(jtfNumChq.getText());

		return myOperationDTO;
	}

	public String validateSaisieOpe() {

		String res = "";
		LogOperation.logDebug("Debut validateSaisieOpe");

		// Date présente et correcte
		if (jtfDateOpe.getText().length() == 0) {
			res = "Saisir une date";
		} else {
			String dateSaisie = jtfDateOpe.getText();
			if (!dateSaisie.matches("[0123][0-9]/[01][0-9]/[0-9]{4}")) {
				res = "Saisir une date au format jj/mm/aaaa";
			}
		}

		// Tiers choisi
		if (comboTiers.getSelectedItem().toString().length() == 0
				|| comboTiers.getSelectedItem().toString() == "Tout") {
			res = "Saisir un tiers";
		}

		// categorie choisie
		if (comboCategorie.getSelectedItem().toString().length() == 0) {
			res = "Saisir une categorie";
		}

		// Au moins un montant
		if (jtfDebit.getText().length() == 0 && jtfCredit.getText().length() == 0) {
			res = "saisir un montant";
		}

		// Un seul montant
		if (jtfDebit.getText().length() != 0 && jtfCredit.getText().length() != 0) {
			res = "Saisir un seul montant";
		}

		// cohérence type Ope montant choisi
		if ("VIR_RECU".equals(comboTypeOpe.getSelectedItem().toString())
				|| "REMISE_CHQ".equals(comboTypeOpe.getSelectedItem().toString())
				|| "DEPOT".equals(comboTypeOpe.getSelectedItem().toString())) {
			if (jtfCredit.getText().length() == 0) {
				res = "Saisir un crédit et non un débit";
			}
		} else if (jtfDebit.getText().length() == 0) {
			res = "Saisir un débit et non un crédit";
		}

		// cohérence type Ope et saisie num chq
		if (jtfNumChq.getText().length() != 0 && !"CHQ".equals(comboTypeOpe.getSelectedItem().toString())) {
			res = "Incohérence entre type Ope et numéro de chèque";
		}
		return res;
	}

	public void fillFieldFromOpeDto(OperationDTO myOperationDTO) {
		comboTiers.setSelectedItem(myOperationDTO.getTiers());
		comboCategorie.setSelectedItem(myOperationDTO.getCategOpe());
		comboTypeOpe.setSelectedItem("CB");
		comboTypeOpe.requestFocus();
		jtfDateOpe.setText(myOperationDTO.getDateOpe());
		// A revoir
		jtfNumChq.setText(myOperationDTO.getNumCheque());
		jtfDetailOpe.setText(myOperationDTO.getDetailOpe());
		if (myOperationDTO.getDebitOpe() != 0) {
			jtfDebit.setText("" + myOperationDTO.getDebitOpe());
		} else {
			jtfCredit.setText("" + myOperationDTO.getCreditOpe());
		}
	}

	public void clearSaisieOpe() {
		comboTiers.setSelectedIndex(0);
		comboCategorie.setSelectedIndex(0);
		comboTypeOpe.setSelectedIndex(0);
		jtfDateOpe.requestFocus();
		jtfNumChq.setText("");
		jtfDetailOpe.setText("");
		jtfDebit.setText("");
		jtfCredit.setText("");
	}

	public JButton getBoutonOKOpe() {
		return boutonOKOpe;
	}

	public JButton getBoutonAnnulOpe() {
		return boutonAnnulOpe;
	}

	public MyJTextField getJtfDateOpe() {
		return jtfDateOpe;
	}

	public MyJTextField getJtfDetailOpe() {
		return jtfDetailOpe;
	}

	public MyJTextField getJtfDebit() {
		return jtfDebit;
	}

	public MyJTextField getJtfCredit() {
		return jtfCredit;
	}

	public TypeOpeCombo getComboTypeOpe() {
		return comboTypeOpe;
	}

	public TiersCombo getComboTiers() {
		return comboTiers;
	}

	public CategorieCombo getComboCategorie() {
		return comboCategorie;
	}

	public Box getB1() {
		return b1;
	}

	public Box getB2() {
		return b2;
	}

	public Box getB3() {
		return b3;
	}

	public MyJTextField getJtfNumChq() {
		return jtfNumChq;
	}

	public void setJtfNumChq(MyJTextField jtfNumChq) {
		this.jtfNumChq = jtfNumChq;
	}
}