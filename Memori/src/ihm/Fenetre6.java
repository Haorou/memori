package ihm;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSplitPane;

public class Fenetre6 extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private static final int LARGEUR = 500;
	private static final int HAUTEUR = 700;
	
	public Fenetre6() 
	{
		
		JButton[] boutons = new JButton[7];
		boutons[0] = new JButton("Lundi");
		boutons[1] = new JButton("Mardi");
		boutons[2] = new JButton("Mercredi");
	
		JList liste = new JList<JButton>();
		
//		JSplitPane jsplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JLabel("bonjour"), new JLabel("coucou"));
//		JSplitPane jsplit2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsplit, new JLabel("Goodbye"));
		getContentPane().add(liste, BorderLayout.CENTER);
		setSize(500,300);
		setVisible(true);
	}
	public static void main(String [] args)
	{
		new Fenetre6();
	}
}