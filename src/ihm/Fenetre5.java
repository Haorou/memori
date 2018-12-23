package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fenetre5 extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private static final int LARGEUR = 500;
	private static final int HAUTEUR = 700;
	
	public Fenetre5() 
	{;

		JButton bouton1 = new JButton("1");
		bouton1.setSize(40, 500);
		JButton bouton2 = new JButton("2");
		JButton bouton3 = new JButton("3");
		JButton bouton4 = new JButton("4");
		JButton bouton5 = new JButton("5");

		getContentPane().add(bouton1, BorderLayout.NORTH);
		getContentPane().add(bouton2, BorderLayout.WEST);
		getContentPane().add(bouton3, BorderLayout.CENTER);
		getContentPane().add(bouton4, BorderLayout.EAST);
		getContentPane().add(bouton5, BorderLayout.SOUTH);
		
		for (int i = 0; i < 5; i++) 
		{
			getContentPane().getComponent(i).setPreferredSize(new Dimension(150,75));
		}
		setSize(500,300);
		setVisible(true);
	}
	public static void main(String [] args)
	{
		new Fenetre5();
	}
}