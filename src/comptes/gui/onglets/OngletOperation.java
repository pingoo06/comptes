package comptes.gui.onglets;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import comptes.gui.combo.FiltreDateCombo;
import comptes.gui.combo.FiltreRapproCombo;
import comptes.gui.combo.TiersCombo;
import comptes.gui.dto.OperationDTO;
import comptes.gui.tableaux.OperationTableau;
import comptes.model.Application;
import comptes.model.services.OperationUtil;
import comptes.util.DateUtil;
import comptes.util.DoubleFormater;
import comptes.util.MyDate;
import comptes.util.log.LogOperation;

public class OngletOperation extends JSplitPane {

	private static final long serialVersionUID = 1L;

	LocalDate dateJour = LocalDate.now();
	String dateJourStr = DateUtil.format(dateJour, "dd/MM/yyyy");

	private JButton boutonFiltreOpe;
	private JTable tableOperation;
	JPanel saisieOpePan = new JPanel();

	TiersCombo comboFiltreTiers = new TiersCombo();
	FiltreDateCombo comboFiltreDuree = new FiltreDateCombo();
	FiltreRapproCombo comboFiltreRappro = new FiltreRapproCombo();

	JPanel vTop = new JPanel();
	JPanel vBottom = new JPanel();
	private PanelCreationOperation panelCreationOperation;
	JPanel pFilters = new JPanel();

	private JLabel labelTiers = new JLabel("Tiers");
	private JLabel labelDuree = new JLabel("Duree");
	private JLabel labelRappro = new JLabel("Rapprochement");
	private JLabel soldeGlobal;
	private JLabel soldeADate;
	private String valFiltreTiers = "";
	private String valFiltreDuree = "";
	private String valFiltreRappro = "";
	private JDatePickerImpl datePourSolde;
	private OperationTableau operationTableau;
	String whereClause = "";

	public OngletOperation() {

		// Gestion des filtres
		setTopComponent(vTop);
		panelCreationOperation = new PanelCreationOperation();
		setBottomComponent(vBottom);
		setOrientation(JSplitPane.VERTICAL_SPLIT);

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePourSolde = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePourSolde.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateSoldeADate();
				updateSoldeLabel();
			}
		});
		boutonFiltreOpe = new JButton("Filtrer");

		// Pour operation
		pFilters.add(labelDuree);
		pFilters.add(comboFiltreDuree);
		pFilters.add(labelRappro);
		pFilters.add(comboFiltreRappro);
		pFilters.add(labelTiers);
		pFilters.add(comboFiltreTiers);
		pFilters.add(boutonFiltreOpe);
		soldeGlobal = new JLabel();
		soldeADate = new JLabel();
		pFilters.add(soldeGlobal);
		pFilters.add(datePourSolde);
		pFilters.add(soldeADate);
		
		updateSoldeLabel();
		vTop.setLayout(new BorderLayout());
		boutonFiltreOpe.addActionListener(new BoutonFiltreListener());

		comboFiltreTiers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valFiltreTiers = comboFiltreTiers.getSelectedItem().toString();
			}
		});

		comboFiltreDuree.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valFiltreDuree = comboFiltreDuree.getSelectedItem().toString();
			}
		});

		comboFiltreRappro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valFiltreRappro = comboFiltreRappro.getSelectedItem().toString();
			}
		});

		vTop.add(pFilters, BorderLayout.NORTH);
		// Taleau des opérations
		operationTableau = new OperationTableau();
		tableOperation = new JTable(operationTableau);
		vTop.add(new JScrollPane(tableOperation), BorderLayout.CENTER);
		tableOperation.setAutoCreateRowSorter(true);
		vBottom.add(panelCreationOperation);
		panelCreationOperation.getBoutonOKOpe().addActionListener(new BoutonOKListener());

		// Bouton Supprimer
		JButton boutonSuppr = new JButton("Supprimer");
		panelCreationOperation.getB3().add(boutonSuppr);
		boutonSuppr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tableIdx = tableOperation.getSelectedRow();
				int modelIdx = tableOperation.convertRowIndexToModel(tableIdx);
				OperationTableau model = ((OperationTableau) tableOperation.getModel());
				model.deleteRow(modelIdx);
				Application.getInstance().updateSolde();
				updateSoldeADate();
				updateSoldeLabel();
			}
		});
	}

	// Execution du bouton OK Operation
	class BoutonOKListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			final JOptionPane frame;
			LogOperation.logDebug("Dans Bouton OK listener");
			OperationDTO myOperationDTO = panelCreationOperation.createOpeDtoFromField();
			String res = panelCreationOperation.validateSaisieOpe() ;
			if (res != "") {
				frame = new JOptionPane();
				JOptionPane.showMessageDialog(frame, res, "Saisie erronée", JOptionPane.WARNING_MESSAGE);
			} else {
				LogOperation.logDebug("montant débit" + panelCreationOperation.getJtfDebit().getText());
				OperationUtil myGestionOperation = new OperationUtil();
				myGestionOperation.create(myOperationDTO);
				panelCreationOperation.clearSaisieOpe();
				OperationTableau model = ((OperationTableau) tableOperation.getModel());
				model.filters(whereClause);
				Application.getInstance().updateSolde();
				updateSoldeADate();
				updateSoldeLabel();
			}

		}
		
		

		
	}

	// Execution du bouton Filtrer
	class BoutonFiltreListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			long dateLong = 0;
			LocalDate dateLimite = null;
			String where1 = "";
			String where2 = "";
			String where3 = "";

			if (valFiltreTiers.isEmpty()) {
				valFiltreTiers = "Tout";
			}
			if (valFiltreTiers != "Tout") {
				where1 = "t.libTiers = '" + valFiltreTiers + "'";
				System.out.println("Dans BoutonFiltreListener de Fenetre : where1 : " + where1);
			}
			if (valFiltreDuree.isEmpty()) {
				valFiltreDuree = "Tout";
			}
			switch (valFiltreDuree) {
			case "Tout":
				where2 = "";
				break;
			case "1 mois":
				dateLimite = dateJour.minusMonths(1);
				dateLong = dateLimite.toEpochDay();
				where2 = " o.dateOpeLong >= '" + dateLong + "'";
				System.out.println("Dans BoutonFiltreListener de Fenetre : where2 : " + where2);
				break;
			case "3 mois":
				dateLimite = dateJour.minusMonths(3);
				dateLong = dateLimite.toEpochDay();
				where2 = " o.dateOpeLong >= '" + dateLong + "'";
				System.out.println("Dans BoutonFiltreListener de Fenetre : where2 : " + where2);
				break;
			case "1 an":
				dateLimite = dateJour.minusYears(1);
				dateLong = dateLimite.toEpochDay();
				where2 = " o.dateOpeLong >= '" + dateLong + "'";
				System.out.println("Dans BoutonFiltreListener de Fenetre : where2 : " + where2);
				break;
			default:
				throw new IllegalArgumentException(
						"Dans BoutonFiltreListener de fenetre: default du case valFiltreDuree" + valFiltreDuree);
			}
			if (valFiltreRappro.isEmpty()) {
				valFiltreRappro = "Tout";
			}
			switch (valFiltreRappro) {
			case "Tout":
				where3 = "";
				break;
			case "Non rapproches":
				where3 = "o.etatOpe != 'X'";
				break;
			case "Rapproches":
				where3 = "o.etatOpe = 'X'";
				break;
			default:
				throw new IllegalArgumentException(
						"Dans BoutonFiltreListener de fenetre: default du case valFiltrerappro" + valFiltreRappro);
			}
			LogOperation.logDebug("dans BoutonFiltreListener de fenetre : valFiltreTiers: " + valFiltreTiers);
			LogOperation.logDebug("dans BoutonFiltreListener de fenetre : valFiltreDuree: " + valFiltreDuree);
			LogOperation.logDebug("dans BoutonFiltreListener de fenetre : valFiltreRappro: " + valFiltreRappro);
			if (!where1.isEmpty() || !where2.isEmpty() || !where3.isEmpty()) {
				if (!where1.isEmpty()) {
					whereClause = "where " + where1;
					if (!where2.isEmpty()) {
						System.out.println("Dans BoutonFiltreListener de fenetre   where2 != null");
						whereClause = whereClause + " and " + where2;
						System.out.println("Dans BoutonFiltreListener de Fenetre : whereClause : " + whereClause);
					}
					if (!where3.isEmpty()) {
						whereClause = whereClause + " and " + where3;
					}
				} else {
					if (!where2.isEmpty()) {
						whereClause = "where " + where2;
						if (!where3.isEmpty()) {
							whereClause = whereClause + " and " + where3;
						}
					} else
						whereClause = "where " + where3;
				}

			} else {
				whereClause = "";
			}
			System.out.println("Dans BoutonFiltreListener de Fenetre : whereClause : " + whereClause);
			OperationTableau model = ((OperationTableau) tableOperation.getModel());
			model.filters(whereClause);
		}
	}

	public void refresh() {
		LogOperation.logInfo("refresh operation tableau avec la whereclause= " + whereClause);
		operationTableau.filters(whereClause);
		updateSoldeLabel();
	}
	
	private void updateSoldeADate() {
		MyDate date = new MyDate(datePourSolde.getJFormattedTextField().getText(), MyDate.DB_FORMAT);
		Application.getInstance().updateSoldeADate(date);
	}
	
	public void updateSoldeLabel(){
		soldeGlobal.setText("Solde global : " +DoubleFormater.formatDouble(Application.getSolde()));
		soldeADate.setText("Solde a date : " +DoubleFormater.formatDouble(Application.getSoldeADate()));
	}

}

class DateLabelFormatter extends AbstractFormatter {

    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}
