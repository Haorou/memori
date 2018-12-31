package controller;

import java.util.List;

import carte.PaquetCartes;
import dao.gestionnaire.Gestionnaire;
import jeu.Joueur;
import jeu.Plateau;
import jeu.Plateau_Memori;
import jeu.Plateau_PetitVerger;
import view.consoleView;

public class ControllerMemori 
{
	private static List<String> partieDispo;
	private static List<String> scoresDispo;
	private static Plateau plateauDeJeu;
	
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
		
		plateauDeJeu = Gestionnaire.preparerCartesJoueursEtPlateau();

		plateauDeJeu.jouer();
	}
	
	private static void nouvellePartie()
	{
		consoleView.afficherOptions("Lancement nouvelle parie");
		
		plateauDeJeu = new Plateau_Memori();
		PaquetCartes.premierPaquetCartes_Memori(combienDeMotifsEnJeu());
		
		plateauDeJeu.combienCreerDeJoueurs();
		
		Gestionnaire.createDataPartie();

		plateauDeJeu.jouer();
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
