package dao.gestionnaire;

import dao.cartes.CartesDAO;
import dao.cartes.Cartes_PetitVergerDAO;
import dao.joueur.JoueurDAO;
import dao.joueur.Joueur_PetitVergerDAO;
import dao.plateau.PlateauDAO;
import dao.plateau.Plateau_PetitVergerDAO;

public class GestionnairePetitVerger extends Gestionnaire 
{
	private final JoueurDAO gestionnaireJoueur = Joueur_PetitVergerDAO.getInstance();
	private final PlateauDAO gestionnairePlateau = Plateau_PetitVergerDAO.getInstance();
	private final CartesDAO gestionnaireCartes = Cartes_PetitVergerDAO.getInstance();
	
	public CartesDAO getGestionnaireCartes()
	{
		return this.gestionnaireCartes;
	}
	
	
	@Override
	public PlateauDAO getGestionnairePlateau() 
	{
		return this.gestionnairePlateau;
	}


	@Override
	public JoueurDAO getGestionnaireJoueur() 
	{
		return this.gestionnaireJoueur;
	}	
}
