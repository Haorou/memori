package dao.joueur;

import dao.cartes.CartesDAO;
import dao.cartes.Cartes_PetitVergerDAO;

public class Joueur_PetitVergerDAO extends JoueurDAO
{	
	private CartesDAO gestionCartes = Cartes_PetitVergerDAO.getInstance();
	
	private static Joueur_PetitVergerDAO instance = null;
	
	public static Joueur_PetitVergerDAO getInstance()
	{
		if(instance == null)
		{
			instance = new Joueur_PetitVergerDAO();
		}
		return instance;
	}
	
	@Override
	public CartesDAO getGestionCartes()
	{
		return this.gestionCartes;
	}
}
