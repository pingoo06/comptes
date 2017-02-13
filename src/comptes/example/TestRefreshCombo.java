package comptes.example;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import comptes.gui.combo.TiersCombo;

public class TestRefreshCombo {

	public static void main(String[] args) {
		JFrame f = new JFrame();
		
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(500,500));
		
		TiersCombo c = new TiersCombo();
		jp.add(c);
		
		JButton b = new JButton("add");
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				c.removeAllItems();
				c.addItem("Pouet" + (new Random()).nextInt(40250));
			}
		});
		jp.add(b);
		f.getContentPane().add(jp);
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
