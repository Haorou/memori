package controller;

import dao.gestionnaire.Gestionnaire;
import dao.gestionnaire.GestionnairePetitVerger;
import jeu.Plateau;
import jeu.Plateau_PetitVerger;

public class ControllerPetitVerger extends Controller
{
	private Plateau plateauDeJeu = new Plateau_PetitVerger();
	private final Gestionnaire gestionnaire = new GestionnairePetitVerger();
	
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
