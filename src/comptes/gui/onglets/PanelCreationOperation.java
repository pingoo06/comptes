package comptes.gui.onglets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import comptes.gui.combo.CategorieCombo;
import comptes.gui.combo.TiersCombo;
import comptes.gui.combo.TypeOpeCombo;
import comptes.gui.dto.OperationDTO;
import comptes.gui.listener.DateDocumentListener;
import comptes.model.db.entity.Tiers;
import comptes.model.facade.TiersFacade;
import comptes.util.DateUtil;
import comptes.util.log.LogRappro;

public class PanelCreationOperation extends Box {

	private static final long serialVersionUID = 1084560641919471528L;
	private JLabel labelTypeOpe;
	private JLabel labelTiers;
	private JLabel labelDateOpe;
	private JLabel labelDebit;
	private JLabel labelCredit;
	private JLabel labelCategOpe;
	private JLabel labelDetailOpe;

	private JButton boutonOKOpe;
	private JButton boutonAnnulOpe;
	private JTextField jtfDateOpe;
	private JTextField jtfDetailOpe;
	private JTextField jtfDebit;
	private JTextField jtfCredit;

	// combo operation
	private TypeOpeCombo comboTypeOpe;
	private TiersCombo comboTiers;
	private CategorieCombo comboCategorie;

	private Box b1;
	private Box b2 ;
	private Box b3;
	
	public PanelCreationOperation() {
		super(BoxLayout.PAGE_AXIS);
		b1 = Box.createHorizontalBox();
		b2 = Box.createHorizontalBox();
		b3 = Box.createHorizontalBox();
		Font police = new Font("Arial", Font.BOLD, 12);
		LocalDate dateJour = LocalDate.now();
		String dateJourStr = DateUtil.format(dateJour, "dd/MM/yyyy");
		jtfDateOpe = new JTextField(dateJourStr);
		jtfDetailOpe = new JTextField("");
		jtfDebit = new JTextField("");
		jtfCredit = new JTextField("");

		// combo operation
		comboTypeOpe = new TypeOpeCombo();
		comboTiers = new TiersCombo();
		comboCategorie = new CategorieCombo();

		labelTypeOpe = new JLabel("TypeOpe");
		labelTiers = new JLabel("Tiers");
		labelDateOpe = new JLabel("Date Ope");
		labelDebit = new JLabel("Debit");
		labelCredit = new JLabel("Credit");
		labelCategOpe = new JLabel("Categorie");
		labelDetailOpe = new JLabel("DetailOpe");

		// boutonPan = new JPanel();
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
				if(idTiers != 0 ) {
				Tiers myTiers = myTiersFacade.find(idTiers);
				String derCategDeTiers = myTiers.getDerCategDeTiers();
					comboCategorie.setSelectedItem(derCategDeTiers);
				}else {
					comboCategorie.setSelectedIndex(0);
				}
				
			}
		});
		comboTypeOpe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// jtfTypeOpe.setText(comboTypeOpe.getSelectedItem().toString());
				String libType = comboTypeOpe.getSelectedItem().toString();
				if (libType == "RETRAIT") {
					comboTiers.setSelectedItem("RETRAIT");
					// jtfTiers.setText("RETRAIT");
					comboCategorie.setSelectedItem("Retrait d especes");
					// jtfCategOpe.setText("Retrait d especes");
					jtfDebit.requestFocus();
				}
			}
		});


		jtfDateOpe.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});

		jtfDateOpe.getDocument().addDocumentListener(new DateDocumentListener(jtfDateOpe));

		jtfDebit.setFont(police);
		jtfDebit.setPreferredSize(new Dimension(100, 20));
		jtfDebit.setForeground(Color.BLUE);
		jtfCredit.setFont(police);
		jtfCredit.setPreferredSize(new Dimension(100, 20));
		jtfCredit.setForeground(Color.BLUE);
		jtfDateOpe.setFont(police);
		jtfDateOpe.setPreferredSize(new Dimension(100, 20));
		jtfDateOpe.setForeground(Color.GREEN);
		jtfDateOpe.setFont(police);
		jtfDetailOpe.setPreferredSize(new Dimension(100, 20));
		jtfDetailOpe.setFont(police);
		jtfDetailOpe.setForeground(Color.RED);

		b1.add(labelDateOpe);
		b1.add(jtfDateOpe);
		b1.add(labelTypeOpe);
		b1.add(comboTypeOpe);
		b1.add(labelTiers);
		b1.add(comboTiers);
		b1.add(labelCategOpe);
		b1.add(comboCategorie);
		b2.add(labelDebit);
		b2.add(jtfDebit);
		b2.add(labelCredit);
		b2.add(jtfCredit);
		b2.add(labelDetailOpe);
		b2.add(jtfDetailOpe);
		b3.add(boutonAnnulOpe);
		b3.add(boutonOKOpe);

		b3.add(boutonAnnulOpe);
		b3.add(boutonOKOpe);
		
		
		add(b1);
		add(b2);
		add(b3);
	}

	public OperationDTO getDto() {
		OperationDTO myOperationDTO  = new OperationDTO();
		myOperationDTO.setTypeOpe(comboTypeOpe.getSelectedItem().toString());
		myOperationDTO.setDateOpe(jtfDateOpe.getText());
		myOperationDTO.setTiers(comboTiers.getSelectedItem().toString());
		myOperationDTO.setCategOpe(comboCategorie.getSelectedItem().toString());
		LogRappro.logDebug("Dans BoutonOKListener de fenetre : jtfDebit : " + jtfDebit.getText());
		if (jtfDebit.getText() != "" && jtfDebit.getText() != "0" && jtfDebit.getText().length() != 0) {
			try {
				myOperationDTO.setDebitOpe(Double.parseDouble(jtfDebit.getText()));
			} catch (NumberFormatException e1) {
				LogRappro.logError("Montant DEBIT KO : " + jtfDebit.getText(),e1);
			}
		} else {
			myOperationDTO.setDebitOpe(0);
		}
		if (jtfCredit.getText() != "" && jtfCredit.getText() != "0" && jtfCredit.getText().length() != 0) {
			try {
				myOperationDTO.setCreditOpe(Double.parseDouble(jtfCredit.getText()));
			} catch (NumberFormatException e1) {
				LogRappro.logError("Montant Credit KO : " + jtfCredit.getText(),e1);
			}
		} else {
			myOperationDTO.setCreditOpe(0);
		}
		LogRappro.logDebug("debit d'operation DTO : " + myOperationDTO.getDebitOpe());
		LogRappro.logDebug("debit d'operation DTO :  " + myOperationDTO.getCreditOpe());

		myOperationDTO.setDetailOpe(jtfDetailOpe.getText());
		myOperationDTO.setEtatOpe("NR"); 
		myOperationDTO.setEchId(0);

		return myOperationDTO;
	}
	
	public void clearSaisieOpe() {
		comboTiers.setSelectedIndex(0);
		comboCategorie.setSelectedIndex(0);
		comboTypeOpe.setSelectedIndex(0);
		jtfDateOpe.requestFocus();
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

	public JTextField getJtfDateOpe() {
		return jtfDateOpe;
	}

	public JTextField getJtfDetailOpe() {
		return jtfDetailOpe;
	}

	public JTextField getJtfDebit() {
		return jtfDebit;
	}

	public JTextField getJtfCredit() {
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
	
	

}
