package ihm;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Fenetre4 extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private static final int LARGEUR = 500;
	private static final int HAUTEUR = 700;
	
	public Fenetre4() 
	{
		setSize(LARGEUR,HAUTEUR);
		JPanel panel = new JPanel();

		/* Associer le gridbagLayout et le panel */
		panel.setLayout(new GridBagLayout());

		/* Le gridBagConstraints permet de définir
		position et taille des éléments */
		GridBagConstraints gc = new GridBagConstraints();
		
		/* fill pour définir comment remplir le composant. BOTH
		permet de remplir horizontalement et verticalement vs
		HORIZONTAL et VERTICAL */
		gc.fill = GridBagConstraints.BOTH;

		/* insets définit les marges entre les composant
		(margeSupérieure, margeGauche, margeInférieur, margeDroite) */
		gc.insets = new Insets(5, 5, 5, 5);
		
		/* ipady place le composant s'il n'occupe pas la
		totalité de l'espace disponible */
		//gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		
		/* weightx/y définit le nombre de cases (abs, ord) */
		gc.weightx = 3; gc.weighty = 3;
		
		/* Les coordonnées du composant en position (i, j) */
		gc.gridx = 0; gc.gridy = 0;
		
		/* Ajout de composants au panel en précisant le
		GridBagConstraints */
		panel.add(new JTextArea("0-0"), gc);
		gc.gridx = 1; gc.gridy = 0;
		panel.add(new JTextArea("1-0"), gc);
		gc.gridx = 2; gc.gridy = 0;
		panel.add(new JTextArea("2-0"), gc);
		gc.gridx = 0; gc.gridy = 1;
		panel.add(new JTextArea("0-1"), gc);
		
		/* On peut définir un composant sur plusieurs cases */
		gc.gridx = 1; gc.gridy = 1; gc.gridwidth = 2;
		panel.add(new JTextArea("1-1 x 2"), gc);
		gc.gridx = 0; gc.gridy = 2; gc.gridwidth = 3;
		panel.add(new JTextArea("0-3 x3"), gc);

		/* Définition de la fenêtre */
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(panel); f.setSize(500, 300);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	public static void main(String [] args)
	{
		new Fenetre4();
	}
}