package controller;

import java.util.List;

import carte.PaquetCartes;
import dao.gestionnaire.Gestionnaire;
import jeu.Plateau;
import jeu.Plateau_PetitVerger;
import view.consoleView;

public class ControllerPetitVerger 
{
	private static List<String> partieDispo;
	private static List<String> scoresDispo;
	private static Plateau plateauDeJeu;
	
	public static void main (String[] args)
	{
		consoleView.afficherTitre("Petit Verger");
		
		consoleView.afficherOptions("Parties en cours");

		/*
		partieDispo = Gestionnaire.listDePartieEnCours();
		if(partieDispo.isEmpty())
		{
			consoleView.afficherMessage("");
			consoleView.afficherMessage("Aucune partie en cours disponible");
		}
		else
			consoleView.afficherMessages(partieDispo);			
		 */
		
		consoleView.afficherMessage("");	
		consoleView.afficherOptions("Scores");
		
		/*
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
		*/
		nouvellePartie();
	}
	
	private static void reprisePartie()
	{
		consoleView.afficherOptions("Reprise de partie");
		consoleView.afficherMessage("");
		consoleView.afficherMessage("Quelle partie voulez vous reprendre ? [n�]");
		consoleView.afficherMessage("");
		
		plateauDeJeu = Gestionnaire.preparerCartesJoueursEtPlateau();

		plateauDeJeu.jouer();
	}
	
	private static void nouvellePartie()
	{
		consoleView.afficherOptions("Lancement nouvelle parie");
	
		plateauDeJeu = new Plateau_PetitVerger();
		
		PaquetCartes.premierPaquetCartes_PetitVerger();
		
		plateauDeJeu.combienCreerDeJoueurs();
		
//		Gestionnaire.createDataPartie();	
		plateauDeJeu.jouer();
		
	}
}
