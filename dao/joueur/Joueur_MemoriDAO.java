package dao.joueur;

import dao.cartes.CartesDAO;
import dao.cartes.Cartes_MemoriDAO;

public class Joueur_MemoriDAO extends JoueurDAO
{	
	private CartesDAO gestionCartes = Cartes_MemoriDAO.getInstance();
	
	private static Joueur_MemoriDAO instance = null;
	
	public static Joueur_MemoriDAO getInstance()
	{
		if(instance == null)
		{
			instance = new Joueur_MemoriDAO();
		}
		return instance;
	}

	@Override
	public CartesDAO getGestionCartes()
	{
		return this.gestionCartes;
	}
	
}
