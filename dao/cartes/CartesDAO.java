package dao.cartes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import carte.Carte;
import carte.PaquetCartes;
import carte.motif.IMotif;
import dao.Connexion;
import dao.DAO;
import plateau.Plateau;

public abstract class CartesDAO extends DAO<Carte> {
	protected static final String TABLE = "carte";
	protected static final String CLE = "id_carte";
	
	public abstract IMotif getMotif(int index);
	
	@Override
	public boolean create(Carte obj) {
		boolean succes = true;
		try {
			String requete = "INSERT INTO " + TABLE
					+ " (fk_id_motif_carte,est_trouve,position_carte,fk_id_plateau) VALUES(?,?,?,?)";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, (obj.getMotif()).ordinal());
			pst.setInt(2, obj.getEstTrouve()?1:0);
			pst.setInt(3, obj.getPositionIndexPaquet() == -1 ? null:obj.getPositionIndexPaquet());
			pst.setInt(4, Plateau.id_plateau);
			pst.executeUpdate();
			
			ResultSet rs = pst.getGeneratedKeys();
			if(rs.next())
			{
				obj.setId(rs.getInt(1));
			}
			
			String requete2 = "INSERT INTO dos_carte (fk_id_carte, fk_id_motif_dos_carte) VALUES(?,?)";
			PreparedStatement pst2 = Connexion.getInstance().prepareStatement(requete2);

			pst2.setInt(1, obj.getId());
			pst2.setInt(2, obj.getDos().ordinal());
			pst2.executeUpdate();
			
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

			pst.setInt(1, (obj.getMotif()).ordinal());
			pst.setInt(2, obj.getEstTrouve()?1:0);
			pst.setInt(3, obj.getId());
			pst.executeUpdate();
			
			String requete2 = "UPDATE dos_carte SET fk_id_motif_dos_carte = ? WHERE fk_id_carte = ?";
			PreparedStatement pst2 = Connexion.getInstance().prepareStatement(requete2);

			pst2.setInt(1, obj.getDos().ordinal());
			pst2.setInt(2, obj.getId());
			pst2.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			succes = false;
		}
		return succes;
	}

	
	public boolean createCartesEnMain()
	{
		boolean succes = true;
		try 
		{
			PreparedStatement pst = Connexion.getInstance()
					.prepareStatement("INSERT INTO cartes_en_main(fk_id_carte, fk_id_joueur) "
							+ "VALUES(?,?)");

			pst.setInt(1, Plateau.joueurActuel.getPremiereCarte().getId());
			pst.setInt(2, Plateau.joueurActuel.getId());
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

			pst.setInt(1, Plateau.joueurActuel.getId());
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
			PreparedStatement pst1 = Connexion.getInstance().prepareStatement("DELETE d FROM dos_carte d INNER JOIN carte c ON d.fk_id_carte = c.id_carte WHERE c.fk_id_plateau = " + Plateau.id_plateau);
			pst1.executeUpdate();
			
			PreparedStatement pst = Connexion.getInstance().prepareStatement("DELETE FROM " + TABLE + " WHERE fk_id_plateau = " + Plateau.id_plateau);
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
