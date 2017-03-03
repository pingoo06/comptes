package comptes.gui.onglets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import comptes.gui.combo.TiersCombo;
import comptes.gui.component.MyJTextField;
import comptes.gui.dto.MatchingDTO;
import comptes.gui.tableaux.MatchingTableau;
import comptes.model.db.entity.Matching;
import comptes.model.services.MatchingUtil;

public class OngletMatching extends JSplitPane {

	private static final long serialVersionUID = 1L;

	private JLabel labelLibBnp ;
	private MyJTextField jtfLibBnp ;
	private JLabel labelTiers;
	private TiersCombo comboTiers ;

	private JButton boutonOkMatch ;
	private JButton boutonSupprMatch ;
	private JButton boutonAnnulMatch ;

	private JPanel matchingPan;
	private JPanel vTopM ;
	private JPanel vBottomM ;
	private JPanel boutonPanM ;

	private JTable tableMatching;

	public OngletMatching() {
		labelLibBnp = new JLabel("Libellé BNP");
		jtfLibBnp = new MyJTextField();
		labelTiers= new JLabel("Tiers");
		comboTiers = new TiersCombo();
		
		boutonOkMatch = new JButton("OK");
		boutonOkMatch.addActionListener(new boutonOkMatchListener());
		boutonSupprMatch = new JButton("Supprimer");
		boutonSupprMatch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tableIdx = tableMatching.getSelectedRow();
				int modelIdx = tableMatching.convertRowIndexToModel(tableIdx);
				MatchingTableau model = ((MatchingTableau) tableMatching.getModel());
				model.deleteRow(modelIdx);
			}
		});
		boutonAnnulMatch = new JButton("Annuler");
		boutonAnnulMatch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearSaisieMatching();
			}
		});
		
		vTopM = new JPanel();
		setTopComponent(vTopM);
		vTopM.setLayout(new BorderLayout());
		vBottomM = new JPanel();
		setBottomComponent(vBottomM);
		vBottomM.setLayout(new BorderLayout());
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		matchingPan = new JPanel();
		boutonPanM = new JPanel();
		boutonPanM.add(boutonAnnulMatch);
		boutonPanM.add(boutonOkMatch);
		boutonPanM.add(boutonSupprMatch);
		vBottomM.add(boutonPanM, BorderLayout.EAST);

		// Tableau matching
		tableMatching = new JTable(new MatchingTableau());
		tableMatching.setAutoCreateRowSorter(true);
		JScrollPane jScrollPane = new JScrollPane(tableMatching);
		JPanel jp = new JPanel();
		jScrollPane.setSize(new Dimension(100, 200));
		jp.setPreferredSize(new Dimension(250,500));
		jp.add(jScrollPane);
		vTopM.add(jp);
		

//		comboTiers.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				jtfTiersEch.setText(comboTiers.getSelectedItem().toString());
//				TiersFacade myTiersFacade = new TiersFacade();
//				int idTiers = myTiersFacade.findLib(comboTiers.getSelectedItem().toString());
//				Tiers myTiers = myTiersFacade.find(idTiers);
//				jtfCategEch.setText(myTiers.getDerCategDeTiers());
//				jtfDateEch.requestFocus();
//			}
//		});

		Font police = new Font("Arial", Font.BOLD, 12);
		jtfLibBnp.setFont(police);
		jtfLibBnp.setPreferredSize(new Dimension(100, 20));
		jtfLibBnp.setForeground(Color.BLUE);

		matchingPan.add(labelLibBnp);
		matchingPan.add(jtfLibBnp);
		matchingPan.add(labelTiers);
		matchingPan.add(comboTiers);
		vBottomM.add(matchingPan, BorderLayout.CENTER);
		labelLibBnp.requestFocus();
	}

	class boutonOkMatchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MatchingDTO myMatchingDTO = new MatchingDTO();
			myMatchingDTO.setId(0);
			myMatchingDTO.setLibBnp(jtfLibBnp.getText());
			myMatchingDTO.setLibTier(comboTiers.getSelectedItem().toString());
			MatchingUtil myMatchingUtil = new MatchingUtil();
			myMatchingUtil.create(myMatchingDTO);
			clearSaisieMatching();
			Matching myMatching = new Matching();
			myMatching =myMatchingUtil.DTOToMatching(myMatchingDTO);
			MatchingTableau model = ((MatchingTableau) tableMatching.getModel());
			model.getListMatching().add(myMatching);
			model.fireTableDataChanged();

		}
	}

	public void clearSaisieMatching() {
		comboTiers.setSelectedIndex(0);
		jtfLibBnp.setText("");
		jtfLibBnp.requestFocus();
	}
}
