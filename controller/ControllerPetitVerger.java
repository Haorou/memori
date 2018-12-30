package controller;

import java.util.List;

import carte.PaquetCartes;
import dao.Gestionnaire;
import jeu.Joueur;
import jeu.Plateau_Memori;
import jeu.Plateau_PetitVerger;
import view.consoleView;

public class ControllerPetitVerger 
{
	private static List<String> partieDispo;
	private static List<String> scoresDispo;
	
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
		consoleView.afficherMessage("Quelle partie voulez vous reprendre ? [n°]");
		consoleView.afficherMessage("");
		
		Gestionnaire.preparerCartesJoueursEtPlateau();

		Plateau_Memori.jouerAuMemori();
	}
	
	private static void nouvellePartie()
	{
		consoleView.afficherOptions("Lancement nouvelle parie");
		PaquetCartes.premierPaquetCartes_PetitVerger();
		
		Plateau_PetitVerger.combienCreerDeJoueurs();
		
//		Gestionnaire.createDataPartie();
		
		Plateau_PetitVerger.jouerAuPetitVerger();
	}
}
