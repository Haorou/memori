package controller;

import carte.PaquetCartes;
import dao.gestionnaire.Gestionnaire;
import dao.gestionnaire.GestionnaireMemori;
import jeu.Joueur;
import jeu.Plateau;
import jeu.Plateau_Memori;
import view.ConsoleView;

public class ControllerMemori extends Controller
{
	private Plateau plateauDeJeu = new Plateau_Memori();
	private final Gestionnaire gestionnaire = new GestionnaireMemori();
	
	@Override
	public Plateau getPlateau()
	{
		return this.plateauDeJeu;
	}
	
	@Override
	public Gestionnaire getGestionnaire()
	{
		return this.gestionnaire;
	}

	@Override
	public void setPlateau(Plateau plateauCharge) {
		this.plateauDeJeu = plateauCharge;
	}

	@Override
	public void preparePaquetCarte() {
		PaquetCartes.premierPaquetCartes_Memori(combienDeMotifsEnJeu());
	}
	
	private int combienDeMotifsEnJeu()
	{
		int nombreDeMotifs = 0;

		while(nombreDeMotifs < 4 || nombreDeMotifs > 10)
		{
			ConsoleView.afficherMessage("");
			ConsoleView.afficherMessage("Combien de motifs voulez vous ? [4 à 10]");
			ConsoleView.afficherMessage("");
			nombreDeMotifs = Joueur.SCANNER.nextInt();			
		}
		return nombreDeMotifs;
	}

}
