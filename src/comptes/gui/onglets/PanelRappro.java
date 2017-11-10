package comptes.gui.onglets;

import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import comptes.gui.component.MyJTextField;
import comptes.gui.listener.DateDocumentListener;
import comptes.gui.manager.RapproSommesManager;
import comptes.model.Application;
import comptes.model.db.entity.DerRappro;
import comptes.model.facade.DerRapproFacade;
import comptes.util.DateUtil;
import comptes.util.DoubleFormater;
import comptes.util.StringFormater;
import comptes.util.log.LogRappro;

public class PanelRappro extends Box {

	private static final long serialVersionUID = 1084560641919471528L;

	private MyJTextField jtfMtInitial;
	private MyJTextField jtfMtFinal;
	private MyJTextField jtfDateRappro;
	private MyJTextField jtfSommeDeb;
	private MyJTextField jtfSommeCred;
	private MyJTextField jtfDiff;

	private JLabel labelMtInitial;
	private JLabel labelMtFinal;
	private JLabel labelDateRappro;
	private JLabel labelSommeDeb;
	private JLabel labelSommeCred;
	private JLabel labelDiff;
	private JLabel soldePointe;

	private JButton boutonStartRappro;
	private JButton boutonValidRappro;
	private JButton boutonAnnulRappro;
	
	private DerRappro myDerRappro;
	
	private RapproSommesManager rapproSommesManager;

	public PanelRappro() {
		super(BoxLayout.PAGE_AXIS);
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		myDerRappro = new DerRappro();
		DerRapproFacade myDerRapproFacade = new DerRapproFacade();
		myDerRappro = myDerRapproFacade.find(1);
		jtfMtInitial = new MyJTextField(myDerRappro.getDerSolde().toString());
		jtfMtInitial.setEditable(false);
		jtfMtFinal = new MyJTextField();
		//nico pourquoi ca marche pas ?
		jtfMtFinal.requestFocus();
		LocalDate dateJour = LocalDate.now();
		String dateJourStr = DateUtil.format(dateJour, "dd/MM/yyyy");
		jtfDateRappro = new MyJTextField(dateJourStr);
		jtfSommeDeb = new MyJTextField();
		jtfSommeDeb.setEditable(false);
		jtfSommeCred = new MyJTextField();
		jtfSommeCred.setEditable(false);
		jtfDiff = new MyJTextField();
		jtfDiff.setEditable(false);

		labelMtInitial = new JLabel("Solde Initial");
		labelMtFinal = new JLabel("Solde Final");
		labelDateRappro = new JLabel("Date rapprochement");
		labelSommeDeb = new JLabel("Somme Debits");
		labelSommeCred = new JLabel("Somme Credits");
		labelDiff = new JLabel("Reste à pointer");
		soldePointe = new JLabel();
		updateLabelSoldePointe();
		boutonStartRappro = new JButton("Rapprocher");
		boutonValidRappro = new JButton("Valider");
		boutonAnnulRappro = new JButton("Annuler");
		boutonValidRappro.setEnabled(false);
		Font police = new Font("Arial", Font.BOLD, 12);
		labelMtInitial.setFont(police);
		labelMtFinal.setFont(police);
		labelDateRappro.setFont(police);
		labelSommeDeb.setFont(police);
		labelSommeCred.setFont(police);
		labelDiff.setFont(police);
		soldePointe.setFont(police);
		jtfDateRappro.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});

		jtfDateRappro.getDocument().addDocumentListener(new DateDocumentListener(jtfDateRappro));

		JPanel jpB1 = new JPanel();
		jpB1.add(wrap(labelMtInitial, jtfMtInitial));
		jpB1.add(wrap(labelMtFinal, jtfMtFinal));
		jpB1.add(wrap(labelDateRappro, jtfDateRappro));
		b1.add(jpB1);
		JPanel jpB2 = new JPanel();
		jpB2.add(wrap(labelSommeDeb, jtfSommeDeb));
		jpB2.add(wrap(labelSommeCred, jtfSommeCred));
		jpB2.add(wrap(labelDiff, jtfDiff));
		b2.add(jpB2);
		b3.add(boutonStartRappro);
		b3.add(boutonAnnulRappro);
		b3.add(boutonValidRappro);
		b4.add(soldePointe);
		add(b1);
		add(b2);
		add(b3);
		add(b4);
	}
	
	public String validateSaisieRappro() {
		String res = "";
		LogRappro.logDebug("Debut validateSaisieRappro");

		
		// Date présente et correcte
		if (jtfDateRappro.getText().length() == 0) {
			res = "Saisir une date";
		} else {
			String dateSaisie = jtfDateRappro.getText();
			if (!dateSaisie.matches("[0123][0-9]/[01][0-9]/[0-9]{4}")) {
				res = "Saisir une date au format jj/mm/aaaa";
			}
		} 
		
		if (jtfMtFinal.getText().length() == 0) {
			res = "Saisir un montant";
		} else {
			String mtFinalStr  = jtfMtFinal.getText();
			if (!StringFormater.estUnDouble(mtFinalStr)) {
				res = "Saisir un montant correct";
			}
		} 
		return res;
	}
	
	public boolean estUnDouble(String chaine) {
		try {
			Double.parseDouble(chaine);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}

	
	private JPanel wrap(JLabel label, MyJTextField tf) {
		JPanel jp = new JPanel();
		jp.add(label);
		jp.add(tf);
		return jp;
	}
	
	public void reset(){
		jtfMtFinal.setText("");
		//nico pourquoi ca marche pas ?
		jtfMtFinal.requestFocus();
		LocalDate dateJour = LocalDate.now();
		String dateJourStr = DateUtil.format(dateJour, "dd/MM/yyyy");
		jtfDateRappro = new MyJTextField(dateJourStr);
		boutonValidRappro.setEnabled(false);
	}

	public MyJTextField getJtfMtInitial() {
		return jtfMtInitial;
	}

	public void setJtfMtInitial(MyJTextField jtfMtInitial) {
		this.jtfMtInitial = jtfMtInitial;
	}

	public MyJTextField getJtfMtFinal() {
		return jtfMtFinal;
	}

	public void setJtfMtFinal(MyJTextField jtfMtFinal) {
		this.jtfMtFinal = jtfMtFinal;
	}

	public MyJTextField getJtfDateRappro() {
		return jtfDateRappro;
	}

	public void setJtfDateRappro(MyJTextField jtfDateRappro) {
		this.jtfDateRappro = jtfDateRappro;
	}

	public MyJTextField getJtfSommeDeb() {
		return jtfSommeDeb;
	}

	public void setJtfSommeDeb(MyJTextField jtfSommeDeb) {
		this.jtfSommeDeb = jtfSommeDeb;
	}

	public MyJTextField getJtfSommeCred() {
		return jtfSommeCred;
	}

	public void setJtfSommeCred(MyJTextField jtfSommeCred) {
		this.jtfSommeCred = jtfSommeCred;
	}

	public MyJTextField getJtfDiff() {
		return jtfDiff;
	}

	public void setJtfDiff(MyJTextField jtfDiff) {
		this.jtfDiff = jtfDiff;
	}

	public JLabel getLabelMtInitial() {
		return labelMtInitial;
	}

	public void setLabelMtInitial(JLabel labelMtInitial) {
		this.labelMtInitial = labelMtInitial;
	}

	public JLabel getLabelMtFinal() {
		return labelMtFinal;
	}

	public void setLabelMtFinal(JLabel labelMtFinal) {
		this.labelMtFinal = labelMtFinal;
	}

	public JLabel getLabelDateRappro() {
		return labelDateRappro;
	}

	public void setLabelDateRappro(JLabel labelDateRappro) {
		this.labelDateRappro = labelDateRappro;
	}

	public JLabel getLabelSommeDeb() {
		return labelSommeDeb;
	}

	public void setLabelSommeDeb(JLabel labelSommeDeb) {
		this.labelSommeDeb = labelSommeDeb;
	}

	public JLabel getLabelSommeCred() {
		return labelSommeCred;
	}

	public void setLabelSommeCred(JLabel labelSommeCred) {
		this.labelSommeCred = labelSommeCred;
	}

	public JLabel getLabelDiff() {
		return labelDiff;
	}

	public void setLabelDiff(JLabel labelDiff) {
		this.labelDiff = labelDiff;
	}

	public JButton getBoutonValidRappro() {
		return boutonValidRappro;
	}

	public void setBoutonValidRappro(JButton boutonValidRappro) {
		this.boutonValidRappro = boutonValidRappro;
	}

	public JButton getBoutonAnnulRappro() {
		return boutonAnnulRappro;
	}

	public void setBoutonAnnulRappro(JButton boutonAnnulRappro) {
		this.boutonAnnulRappro = boutonAnnulRappro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JButton getBoutonStartRappro() {
		return boutonStartRappro;
	}

	public void setBoutonStartRappro(JButton boutonStartRappro) {
		this.boutonStartRappro = boutonStartRappro;
	}
	
	public void updateLabelSoldePointe(){
		Application.getInstance().updateSoldePointe();
		soldePointe.setText("Solde Pointé DB : "+ DoubleFormater.formatDouble(Application.getSoldePointe()));
	}

	public JLabel getSoldePointe() {
		return soldePointe;
	}

}