package controller;

import java.util.List;

import carte.PaquetCartes;
import dao.gestionnaire.Gestionnaire;
import jeu.Joueur;
import jeu.Plateau;
import view.ConsoleView;

public abstract class Controller
{
	private static List<String> partieDispo;
	private static List<String> scoresDispo;
	
	public abstract Plateau getPlateau();
	public abstract void setPlateau(Plateau plateauCharge);
	public abstract Gestionnaire getGestionnaire();
	
	public void lancerPartie ()
	{
		ConsoleView.afficherTitre(getPlateau().getJeu());
		
		ConsoleView.afficherOptions("Parties en cours");

		partieDispo = getGestionnaire().listDePartieEnCours();
		if(partieDispo.isEmpty())
		{
			ConsoleView.afficherMessage("");
			ConsoleView.afficherMessage("Aucune partie en cours disponible");
		}
		else
			ConsoleView.afficherMessages(partieDispo);			
		
		ConsoleView.afficherMessage("");	
		ConsoleView.afficherOptions("Scores");
		
		scoresDispo = getGestionnaire().listDePartieFinie();
		if(scoresDispo.isEmpty())
		{
			ConsoleView.afficherMessage("");
			ConsoleView.afficherMessage("Aucune partie finie disponible");			
			ConsoleView.afficherMessage("");
		}
		else
		{
			ConsoleView.afficherMessages(scoresDispo);			
			ConsoleView.afficherMessage("");
		}

		
		if(!partieDispo.isEmpty())
		{
			ConsoleView.afficherOptions("Souhaitez vous reprendre une partie en cours ? [O]");

			if(Joueur.SCANNER.next().toLowerCase().equals("o"))
				reprisePartie();
			else
				nouvellePartie();
		}
		else
		{
			nouvellePartie();
		}

		nouvellePartie();
	}
	
	private void reprisePartie()
	{
		ConsoleView.afficherOptions("Reprise de partie");
		ConsoleView.afficherMessage("");
		ConsoleView.afficherMessage("Quelle partie voulez vous reprendre ? [n°]");
		ConsoleView.afficherMessage("");
		
		setPlateau(getGestionnaire().preparerCartesJoueursEtPlateau());

		getPlateau().jouer();
	}
	
	private void nouvellePartie()
	{
		ConsoleView.afficherOptions("Lancement nouvelle parie");
		
		PaquetCartes.premierPaquetCartes_PetitVerger();
		
		getPlateau().combienCreerDeJoueurs();
		
		getGestionnaire().createDataPartie(getPlateau());
		getPlateau().jouer();
		
	}
}
