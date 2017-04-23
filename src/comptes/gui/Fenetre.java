package comptes.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import comptes.gui.onglets.OngletEcheancier;
import comptes.gui.onglets.OngletMatching;
import comptes.gui.onglets.OngletOperation;
import comptes.gui.onglets.OngletRappro;

public class Fenetre extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel container = new JPanel();

	// Onglets
	private JTabbedPane onglets;

	public Fenetre() {
		this.setTitle("Text Field");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());
		onglets = new JTabbedPane(JTabbedPane.LEFT);

		OngletOperation operationPanel = new OngletOperation();
		onglets.add("Opérations", operationPanel);
		onglets.add("Echeancier", getEcheancierPanel());
		onglets.add("Rappro", getRapproPanel());
		onglets.add("Matching", getMatchingPanel());
//		onglets.add("Categorie", getRapproPanel());
//		onglets.add("Tiers", getRapproPanel());

		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				if(index==0) {
					operationPanel.refresh();
				}
			}
		};
		onglets.addChangeListener(changeListener);   
		container.add(onglets);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setPreferredSize(screenSize);
		this.setContentPane(container);
		this.setVisible(true);
		setLocation(0, 0);
		pack();
	}


	// ECHEANCIER
	public Component getEcheancierPanel() {
		return new OngletEcheancier();
	}

	// RAPPRO
	public Component getRapproPanel() {
		return new OngletRappro();
	}
	// Matching
	public Component getMatchingPanel() {
		return new OngletMatching();
	}
	
	
	
}

