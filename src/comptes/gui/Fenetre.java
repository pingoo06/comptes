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
		screenSize.setSize(screenSize.getWidth(), screenSize.getHeight()-30);
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



/*
 * TOUT EN COMMENTAIRE A PARTIR D'ICI // Mis en commentaire le 5/1 // class
 * ItemActionTiers implements ActionListener { // public void
 * actionPerformed(ActionEvent e) { //
 * jtTiers.setText(comboTiers.getSelectedItem().toString()); //
 * System.out.println("ActionListener : action sur " + //
 * comboTiers.getSelectedItem()); // } // }
 * 
 * // class ClavierListener implements KeyListener { // // public void
 * keyPressed(KeyEvent event) { // System.out.println("Code touche pressée : " +
 * event.getKeyCode() + " - // caractère touche pressée : " // +
 * event.getKeyChar()); // pause(); // } // // public void keyReleased(KeyEvent
 * event) { // System.out.println("Code touche relâchée : " + event.getKeyCode()
 * + " - // caractère touche relâchée : " // + event.getKeyChar()); // pause();
 * // } // // public void keyTyped(KeyEvent event) { // System.out.println( //
 * "Code touche tapée : " + event.getKeyCode() + " - caractère touche tapée // :
 * " + event.getKeyChar()); // pause(); // } // }
 * 
 * // private void pause() { // try { // Thread.sleep(1000); // } catch
 * (InterruptedException e) { // e.printStackTrace(); // } // } //}
 * 
 * // private void go() { // x = pan.getPosX(); // y = pan.getPosY(); // while
 * (this.animated) { // if (x < 1) // backX = false; // if (x > pan.getWidth() -
 * 50) // backX = true; // if (y < 1) // backY = false; // if (y >
 * pan.getHeight() - 50) // backY = true; // if (!backX) // pan.setPosX(++x); //
 * else // pan.setPosX(--x); // if (!backY) // pan.setPosY(++y); // else //
 * pan.setPosY(--y); // pan.repaint(); // try { // Thread.sleep(3); // } catch
 * (InterruptedException e) { // e.printStackTrace(); // } // }
 * 
 * // // Classe écoutant notre bouton // public class BoutonListener implements
 * ActionListener { // public void actionPerformed(ActionEvent arg0) { //
 * animated = true; // t = new Thread(new PlayAnimation()); // t.start(); //
 * boutonGo.setEnabled(false); // boutonStop.setEnabled(true); // } // }
 * 
 * // class Bouton2Listener implements ActionListener { // public void
 * actionPerformed(ActionEvent e) { // animated = false; //
 * boutonGo.setEnabled(true); // boutonStop.setEnabled(false); // } // }
 * 
 * // public class Fenetre extends JFrame { // private Bouton boutonGo = new
 * Bouton("Mon premier bouton"); // private Panneau pan = new Panneau();
 * CardLayout cl = new CardLayout(); // int indice = 0; // // public Fenetre() {
 * // Panneau content = new Panneau(); // CardLayout cl = new CardLayout(); //
 * String[] listContent = {"CARD_1", "CARD_2", "CARD_3"}; // // Possibilité 1 :
 * instanciation avec le libellé // this.setTitle("Mon Money"); //
 * this.setSize(400, 300); // // au centre // this.setLocationRelativeTo(null);
 * // // Termine le processus lorsqu'on clique sur la croix rouge //
 * this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // //this.setLocation(0,
 * 0); // // setResizable(true); // // laisse toujours la fenetre au premier
 * plan si true // //setAlwaysOnTop(false); // // Enleve les contours de la
 * fenetre si true // //setUndecorated(false); // // Instanciation d'un objet
 * Panneau // // Définition de sa couleur de fond //
 * pan.setBackground(Color.ORANGE); // //pan.add(bouton); // //
 * this.getContentPane().add(bouton); // // On prévient notre JFrame que notre
 * Panneau sera son content pane // //this.setContentPane(new Panneau()); // //
 * //// GridLayout gl = new GridLayout(); //// gl.setColumns(2); ////
 * gl.setRows(3); //// this.getContentPane().add(new JButton("1")); ////
 * this.getContentPane().add(new JButton("2")); ////
 * this.getContentPane().add(new JButton("3")); //// ////
 * this.setLayout(gl);this.getContentPane().add(new JButton("SOUTH"), //
 * BorderLayout.SOUTH); // // // new // // //On crée un conteneur avec gestion
 * horizontale //// Box b1 = Box.createHorizontalBox(); //// b1.add(new
 * JButton("Bouton 1")); //// //Idem //// Box b2 = Box.createHorizontalBox();
 * //// b2.add(new JButton("Bouton 2")); //// b2.add(new JButton("Bouton 3"));
 * //// //Idem //// Box b3 = Box.createHorizontalBox(); //// b3.add(new
 * JButton("Bouton 4")); //// b3.add(new JButton("Bouton 5")); //// b3.add(new
 * JButton("Bouton 6")); //// //On crée un conteneur avec gestion verticale ////
 * Box b4 = Box.createVerticalBox(); //// b4.add(b1); //// b4.add(b2); ////
 * b4.add(b3); //// this.getContentPane().add(b4); // // //On crée trois
 * conteneurs de couleur différente // Panneau card1 = new Panneau(); //
 * card1.setBackground(Color.blue); // Panneau card2 = new Panneau(); //
 * card2.setBackground(Color.red); // Panneau card3 = new Panneau(); //
 * card3.setBackground(Color.green); // // Panneau boutonPane = new Panneau();
 * // Bouton bouton = new Bouton("Contenu suivant"); // //Définition de l'action
 * du bouton // bouton.addActionListener(new ActionListener(){ // public void
 * actionPerformed(ActionEvent event){ // //Via cette instruction, on passe au
 * prochain conteneur de la pile // cl.next(content); // } // }); // // Bouton
 * boutonStop = new Bouton("Contenu par indice"); // //Définition de l'action du
 * boutonStop // boutonStop.addActionListener(new ActionListener(){ // public
 * void actionPerformed(ActionEvent event){ // if(++indice > 2) // indice = 0;
 * // //Via cette instruction, on passe au conteneur correspondant au nom fourni
 * en // paramètre // cl.show(content, listContent[indice]); // } // }); // //
 * boutonPane.add(bouton); // boutonPane.add(boutonStop); // //On définit le
 * layout // content.setLayout(cl); // //On ajoute les cartes à la pile avec un
 * nom pour les retrouver // content.add(card1, listContent[0]); //
 * content.add(card2, listContent[1]); // content.add(card3, listContent[2]); //
 * // this.getContentPane().add(boutonPane, BorderLayout.NORTH); //
 * this.getContentPane().add(content, BorderLayout.CENTER); //
 * this.setVisible(true); // //this.setContentPane(pan); //
 * this.setVisible(true); // } //}
 * 
 */