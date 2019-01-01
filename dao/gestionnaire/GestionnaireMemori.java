package dao.gestionnaire;

import dao.cartes.CartesDAO;
import dao.cartes.Cartes_MemoriDAO;
import dao.joueur.JoueurDAO;
import dao.joueur.Joueur_MemoriDAO;
import dao.plateau.PlateauDAO;
import dao.plateau.Plateau_MemoriDAO;

public class GestionnaireMemori extends Gestionnaire 
{
	private final JoueurDAO gestionnaireJoueur = Joueur_MemoriDAO.getInstance();
	private final PlateauDAO gestionnairePlateau = Plateau_MemoriDAO.getInstance();
	private final CartesDAO gestionnaireCartes = Cartes_MemoriDAO.getInstance();
	
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
	public JoueurDAO getGestionnaireJoueur() {
		return this.gestionnaireJoueur;
	}	

}
