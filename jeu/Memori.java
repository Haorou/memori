package jeu;

import carte.PaquetCartes;
import dao.Gestionnaire;

public class Memori 
{
	private static boolean partieDispo;
	
	public static void main (String[] args)
	{
		System.out.println("Bonjour et bienvenue dans le jeu du Memori \n");
		partieDispo = Gestionnaire.afficherData();
		Gestionnaire.afficherScore();

		if(partieDispo)
		{
			System.out.println("Souhaitez vous reprendre une partie en cours ? [O]");

			if(Joueur.SCANNER.next().toLowerCase().equals("o"))
				reprisePartie();
			else
				nouvellePartie();
		}
		else
		{
			System.out.println("Aucune partie n'est en cours \n");
			nouvellePartie();
		}
	}
	
	private static void reprisePartie()
	{
		System.out.println("|---------------REPRISE DE PARTIE---------------|");

		System.out.println("Quelle partie voulez vous reprendre ?");
		Gestionnaire.preparerCartesJoueursEtPlateau();

		Plateau.jouerAuMemori();
	}
	
	private static void nouvellePartie()
	{
		System.out.println("|----------LANCEMENT NOUVELLE PARTIE----------|");
		PaquetCartes.PremierPaquetCartes(combienDeMotifsEnJeu());
		
		Plateau.combienCreerDeJoueurs();
		
		Gestionnaire.createDataPartie();

		Plateau.jouerAuMemori();
	}
	
	private static int combienDeMotifsEnJeu()
	{
		int nombreDeMotifs = 0;
		
		while(nombreDeMotifs < 4 || nombreDeMotifs > 10)
		{
			System.out.println("Combien de motifs voulez vous ? [4 à 10]");
			nombreDeMotifs = Joueur.SCANNER.nextInt();			
		}
		return nombreDeMotifs;
	}

}
