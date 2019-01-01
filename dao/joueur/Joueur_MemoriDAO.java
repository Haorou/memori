package dao.joueur;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.Connexion;
import dao.cartes.CartesDAO;
import dao.cartes.Cartes_MemoriDAO;
import joueur.Joueur;
import joueur.Joueur_Memori;

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
	
	@Override
	public Joueur read(int numero) 
	{
		ResultSet rs1 = Connexion.executeQuery("SELECT * FROM " + TABLE + " INNER JOIN "+TABLE_PAR+" ON "+TABLE_PAR+".fk_id_joueur = joueur.id_joueur "
				+ "WHERE " + CLE + " = " + numero);
		
		ResultSet rs2 = Connexion.executeQuery("SELECT * FROM " + TABLE + " INNER JOIN cartes_en_main ON cartes_en_main.fk_id_joueur = joueur.id_joueur WHERE " + CLE + " =" + numero);

		Joueur joueurLu = null;
		CartesDAO gestionCarte = getGestionCartes();
		try 
		{
			if(rs1.next())
			{
				joueurLu = new Joueur_Memori(rs1.getString("nom"),
									  		rs1.getInt("nombre_erreurs"),
									  		rs1.getInt("nombre_points"),
									  		rs1.getInt("numero_joueur"),
									  		rs1.getInt("annee_naissance"),
									  		rs1.getInt("id_joueur")
									  		);
			}

			if(rs2.next())
			{
				joueurLu.setPremiereCarte(gestionCarte.read(rs2.getInt("fk_id_carte")));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return joueurLu;
	}
	
}
