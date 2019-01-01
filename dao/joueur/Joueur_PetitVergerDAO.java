package dao.joueur;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.Connexion;
import dao.cartes.CartesDAO;
import dao.cartes.Cartes_PetitVergerDAO;
import joueur.Joueur;
import joueur.Joueur_PetitVerger;

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
	
	@Override
	public Joueur read(int numero) 
	{
		ResultSet rs1 = Connexion.executeQuery("SELECT * FROM " + TABLE + " INNER JOIN "+TABLE_PAR+" ON "+TABLE_PAR+".fk_id_joueur = joueur.id_joueur "
				+ "WHERE " + CLE + " = " + numero);

		Joueur joueurLu = null;
		try 
		{
			if(rs1.next())
			{
				joueurLu = new Joueur_PetitVerger(rs1.getString("nom"),
									  			rs1.getInt("nombre_erreurs"),
									  			rs1.getInt("nombre_points"),
									  			rs1.getInt("numero_joueur"),
									  			rs1.getInt("annee_naissance"),
									  			rs1.getInt("id_joueur")
									  			);
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return joueurLu;
	}
}
