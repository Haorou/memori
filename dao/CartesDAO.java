package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import carte.Carte;
import carte.Motif;
import carte.PaquetCartes;
import jeu.memori.Plateau_Memori;

public class CartesDAO extends DAO<Carte> {
	private static final String TABLE = "carte";
	private static final String CLE = "id_carte";

	private static CartesDAO instance = null;

	public static CartesDAO getInstance() {
		if (instance == null) {
			instance = new CartesDAO();
		}
		return instance;
	}

	@Override
	public boolean create(Carte obj) {
		boolean succes = true;
		try {
			String requete = "INSERT INTO " + TABLE
					+ " (fk_id_motif_carte,est_trouve,position_carte,fk_id_plateau) VALUES(?,?,?,?)";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getMotif().ordinal());
			pst.setInt(2, obj.getEstTrouve()?1:0);
			pst.setInt(3, obj.getPositionIndexPaquet() == -1 ? null:obj.getPositionIndexPaquet());
			pst.setInt(4, Plateau_Memori.id_plateau);
			pst.executeUpdate();
			
			ResultSet rs = pst.getGeneratedKeys();
			if(rs.next())
			{
				obj.setId(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			succes = false;
		}
		return succes;
	}

	@Override
	public boolean delete(Carte obj) {
		boolean succes = true;
		try {
			PreparedStatement pst = Connexion.getInstance()
					.prepareStatement("DELETE FROM " + TABLE + " WHERE " + CLE + " = ?");
			pst.setInt(1, obj.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			succes = false;
		}

		return succes;
	}

	@Override
	public boolean update(Carte obj) {
		boolean succes = true;
		try 
		{
			PreparedStatement pst = Connexion.getInstance()
					.prepareStatement("UPDATE " + TABLE + " "
							+ "SET fk_id_motif_carte = ?, est_trouve = ? WHERE "
							+ CLE + " = ?");

			pst.setInt(1, obj.getMotif().ordinal());
			pst.setInt(2, obj.getEstTrouve()?1:0);
			pst.setInt(3, obj.getId());
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
	public Carte read(int id) {
		ResultSet rs = Connexion.executeQuery("SELECT * FROM " + TABLE + " WHERE " + CLE + " =" + id);

		Carte carteLu = null;
		int positionIndex;
		try 
		{
			if (rs.next()) 
			{	
				positionIndex = rs.getInt("position_carte");
				if(rs.wasNull())
					positionIndex = -1;
				
				carteLu = new Carte(
						Motif.get(rs.getInt("fk_id_motif_carte")), 
						rs.getInt("est_trouve") == 1 ?true:false,
						positionIndex);
				
				carteLu.setId(rs.getInt("id_carte"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return carteLu;
	}
	
	public boolean createCartesEnMain()
	{
		boolean succes = true;
		try 
		{
			PreparedStatement pst = Connexion.getInstance()
					.prepareStatement("INSERT INTO cartes_en_main(fk_id_carte, fk_id_joueur) "
							+ "VALUES(?,?)");

			pst.setInt(1, Plateau_Memori.joueurActuel.getPremiereCarte().getId());
			pst.setInt(2, Plateau_Memori.joueurActuel.getId());
			pst.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			succes = false;
		}
		return succes;
	}
	
	public boolean deleteCartesEnMain()
	{
		boolean succes = true;
		try 
		{
			PreparedStatement pst = Connexion.getInstance().prepareStatement("DELETE FROM cartes_en_main WHERE "
					+ "fk_id_joueur = ?");

			pst.setInt(1, Plateau_Memori.joueurActuel.getId());
			pst.executeUpdate();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			succes = false;
		}

		return succes;
	}
	
	
	
	public boolean lireCartesDuPlateau(int id)
	{
		boolean succes = true;
		ResultSet rs = Connexion.executeQuery("SELECT * FROM plateau INNER JOIN carte ON plateau.id_plateau = carte.fk_id_plateau WHERE "
				+ "plateau.id_plateau = " + id);
		
		PaquetCartes.PaquetCartesDB();

		try
		{
			while(rs.next())
			{	
				PaquetCartes.add(this.read(rs.getInt("id_carte")));
			}
			PaquetCartes.setSizeTaillePaquet();
		}
		catch(SQLException e)
		{
			succes = false;
			e.printStackTrace();
		}
		return succes;
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

	public boolean supprimerCartesDuPaquet() 
	{
		boolean succes = true;
		try 
		{
			PreparedStatement pst = Connexion.getInstance().prepareStatement("DELETE FROM " + TABLE + " WHERE fk_id_plateau = " + Plateau_Memori.id_plateau);
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
