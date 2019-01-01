package controller;

import dao.gestionnaire.Gestionnaire;
import dao.gestionnaire.GestionnaireMemori;
import jeu.Plateau;
import jeu.Plateau_Memori;

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

}
