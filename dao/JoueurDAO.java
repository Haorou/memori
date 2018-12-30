package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jeu.Joueur;
import jeu.Plateau_Memori;

public class JoueurDAO extends DAO<Joueur> 
{
	private static final String TABLE = "joueur";
	private static final String TABLE_PAR = "participe";
	private static final String TABLE_JC = "joueur_courant";
	private static final String CLE = "id_joueur";
	
	private static JoueurDAO instance = null;
	
	public static JoueurDAO getInstance()
	{
		if(instance == null)
		{
			instance = new JoueurDAO();
		}
		return instance;
	}

	
	@Override
	public boolean create(Joueur obj) 
	{
		boolean succes = true;
		try 
		{
			String requete = "INSERT INTO " + TABLE +" (nom, annee_naissance) VALUES(?,?)";
			PreparedStatement pst1 = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst1.setString(1, obj.getNom());
			pst1.setInt(2, obj.getAnneeDeNaissance());

			pst1.executeUpdate();
			
			ResultSet rs = pst1.getGeneratedKeys();
			if(rs.next())
			{
				obj.setId(rs.getInt(1));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			succes = false;
		}

		return succes;
	}
	
	public int isPlayerExistReturnId(Joueur obj) 
	{
		int id = 0;
		try 
		{
			String requete = "SELECT id_joueur FROM " + TABLE +" WHERE nom = '"+ obj.getNom() +"' AND annee_naissance = "+ obj.getAnneeDeNaissance();
			ResultSet rs = Connexion.executeQuery(requete);
			
			if(rs.next())
			{
				id = rs.getInt("id_joueur");
			}			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			id = -1;
		}

		return id;
	}
	
	public boolean insertIntoParticipe(Joueur obj)
	{
		boolean succes = true;
		try 
		{
			String requete2 = "INSERT INTO "+TABLE_PAR+" (nombre_erreurs,nombre_points,numero_joueur,fk_id_joueur, fk_id_plateau) VALUES(?,?,?,?,?)";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete2, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, obj.getNombreErreurs());
			pst.setInt(2, obj.getNombrePoints());
			pst.setInt(3, obj.getNumeroJoueur());
			pst.setInt(4, obj.getId());			
			pst.setInt(5, Plateau_Memori.id_plateau);
			pst.executeUpdate();
		} 
		catch (SQLException e) 
		{
			succes = false;
			e.printStackTrace();
		}
		
		return succes;
	}
	
	
	@Override
	public boolean delete(Joueur obj) 
	{
		boolean succes = true;
		try 
		{
			PreparedStatement pst = Connexion.getInstance().prepareStatement("DELETE FROM " + TABLE +" WHERE " + CLE + " = ?");
			pst.setInt(1, obj.getId());
			pst.executeUpdate();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			succes = false;
		}

		return succes;
	}

	@Override
	public boolean update(Joueur obj) {
		boolean succes = true;
		try 
		{
			String requete = "UPDATE "+TABLE_PAR+" SET nombre_erreurs = ?, nombre_points = ? "
					+ "WHERE fk_id_joueur = ? AND fk_id_plateau = ?";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete);
			pst.setInt(1, obj.getNombreErreurs());
			pst.setInt(2, obj.getNombrePoints());
			pst.setInt(3, obj.getId());			
			pst.setInt(4, Plateau_Memori.id_plateau);
			pst.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			succes = false;
		}

		return succes;
	}

	public boolean enregistrerJoueurVainqueur()
	{
		boolean succes = true;
		String requete = "INSERT INTO gagnee (fk_id_plateau, fk_id_joueur) VALUES(?,?)";
		try {
			if(Plateau_Memori.joueurVainqueur == null)
			{
				PreparedStatement pst1 = Connexion.getInstance().prepareStatement(requete);
				pst1.setInt(1, Plateau_Memori.id_plateau);
				pst1.setInt(2, Plateau_Memori.getJoueur(0).getId());
				pst1.executeUpdate();
				
				PreparedStatement pst2 = Connexion.getInstance().prepareStatement(requete);
				pst2.setInt(1, Plateau_Memori.id_plateau);
				pst2.setInt(2, Plateau_Memori.getJoueur(1).getId());
				pst2.executeUpdate();
			}
			else
			{
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requete);
				pst.setInt(1, Plateau_Memori.id_plateau);
				pst.setInt(2, Plateau_Memori.joueurVainqueur.getId());
				pst.executeUpdate();
			}			
		}
		catch (SQLException e) 
		{
			succes = false;
			e.printStackTrace();
		}			

		return succes;
	}
	
	@Override
	public Joueur read(int numero) 
	{
		ResultSet rs1 = Connexion.executeQuery("SELECT * FROM " + TABLE + " INNER JOIN "+TABLE_PAR+" ON "+TABLE_PAR+".fk_id_joueur = joueur.id_joueur "
				+ "WHERE " + CLE + " = " + numero);
		
		ResultSet rs2 = Connexion.executeQuery("SELECT * FROM " + TABLE + " INNER JOIN cartes_en_main ON cartes_en_main.fk_id_joueur = joueur.id_joueur WHERE " + CLE + " =" + numero);

		Joueur joueurLu = null;
		CartesDAO gestionCarte = new CartesDAO();
		try 
		{
			if(rs1.next())
			{
				joueurLu = new Joueur(rs1.getString("nom"),
									  rs1.getInt("nombre_erreurs"),
									  rs1.getInt("nombre_points"),
									  rs1.getInt("numero_joueur"),
									  rs1.getInt("annee_naissance"),
									  rs1.getInt("id_joueur")
									  );
			}

			if(rs2.next())
			{
				System.out.println(rs2.getInt("fk_id_carte"));
				joueurLu.setPremiereCarte(gestionCarte.read(rs2.getInt("fk_id_carte")));
				System.out.println("id joueur : " + numero + " "+joueurLu.getPremiereCarte());
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return joueurLu;
	}
	
	public List<Joueur> lireJoueursPlateauCourant(int id_plateau)
	{
		List<Joueur> joueurs = new ArrayList<Joueur>(2);
		
		ResultSet rs = Connexion.executeQuery("SELECT * FROM plateau INNER JOIN "+TABLE_PAR+" ON "+TABLE_PAR+".fk_id_plateau = plateau.id_plateau"
				+ " INNER JOIN "+ TABLE +" ON "+TABLE_PAR+".fk_id_joueur = joueur.id_joueur"
				+ "  WHERE id_plateau =" + id_plateau);
		try 
		{
			while(rs.next())
			{
				joueurs.add(this.read(rs.getInt("id_joueur")));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return joueurs;
	}
	
	public boolean deleteAll()
	{
		boolean succes = true;
		try 
		{
			PreparedStatement pst = Connexion.getInstance().prepareStatement("DELETE FROM " + TABLE);
			pst.executeUpdate();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			succes = false;
		}

		return succes;
	}


	public boolean supprimerJoueurCourant() {
		boolean succes = true;
		try 
		{
			PreparedStatement pst = Connexion.getInstance().prepareStatement("DELETE FROM "+TABLE_JC+" WHERE fk_id_plateau = " + Plateau_Memori.id_plateau);
			pst.executeUpdate();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			succes = false;
		}

		return succes;
	}
}
