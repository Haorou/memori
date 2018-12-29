package jeu.memori;

import java.util.List;

import carte.PaquetCartes;
import dao.Gestionnaire;
import jeu.Joueur;
import view.consoleView;

public class Memori 
{
	private static List<String> partieDispo;
	private static List<String> scoresDispo;
	
	public static void main (String[] args)
	{
		consoleView.afficherTitre("Memori");
		
		consoleView.afficherOptions("Parties en cours");
		partieDispo = Gestionnaire.listDePartieEnCours();
		if(partieDispo.isEmpty())
		{
			consoleView.afficherMessage("");
			consoleView.afficherMessage("Aucune partie en cours disponible");
		}
		else
			consoleView.afficherMessages(partieDispo);			

		consoleView.afficherMessage("");	
		consoleView.afficherOptions("Scores");
		
		scoresDispo = Gestionnaire.listDePartieFinie();
		if(scoresDispo.isEmpty())
		{
			consoleView.afficherMessage("Aucune partie finie disponible");			
			consoleView.afficherMessage("");
		}
		else
		{
			consoleView.afficherMessages(scoresDispo);			
			consoleView.afficherMessage("");
		}

		
		if(!partieDispo.isEmpty())
		{
			consoleView.afficherOptions("Souhaitez vous reprendre une partie en cours ? [O]");

			if(Joueur.SCANNER.next().toLowerCase().equals("o"))
				reprisePartie();
			else
				nouvellePartie();
		}
		else
		{
			nouvellePartie();
		}
	}
	
	private static void reprisePartie()
	{
		consoleView.afficherOptions("Reprise de partie");
		consoleView.afficherMessage("");
		consoleView.afficherMessage("Quelle partie voulez vous reprendre ? [n°]");
		consoleView.afficherMessage("");
		
		Gestionnaire.preparerCartesJoueursEtPlateau();

		Plateau_Memori.jouerAuMemori();
	}
	
	private static void nouvellePartie()
	{
		consoleView.afficherOptions("Lancement nouvelle parie");
		PaquetCartes.premierPaquetCartes_Memori(combienDeMotifsEnJeu());
		
		Plateau_Memori.combienCreerDeJoueurs();
		
		Gestionnaire.createDataPartie();

		Plateau_Memori.jouerAuMemori();
	}
	
	private static int combienDeMotifsEnJeu()
	{
		int nombreDeMotifs = 0;
		
		while(nombreDeMotifs < 4 || nombreDeMotifs > 10)
		{
			consoleView.afficherMessage("");
			consoleView.afficherMessage("Combien de motifs voulez vous ? [4 à 10]");
			consoleView.afficherMessage("");
			nombreDeMotifs = Joueur.SCANNER.nextInt();			
		}
		return nombreDeMotifs;
	}

}
