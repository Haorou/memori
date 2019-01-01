package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jeu.Plateau;
import jeu.Plateau_Memori;
import jeu.Plateau_PetitVerger;

public abstract class PlateauDAO extends DAO<Plateau> 
{
	private static final String TABLE = "plateau";
	private static final String CLE = "id_plateau";
	
	public static Map<Integer, Integer> dicoCompteurPlateau = new HashMap<>();

	public abstract String getJeu();
	
	@Override
	public boolean create(Plateau obj) {
		boolean succes = true;
		try {
			String requete = "INSERT INTO " + TABLE
					+ " (temps_de_jeu,date_utilisation,type_jeu) VALUES(?,?,?)";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete,  Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, (int) Plateau.tempsDeJeuMillis);
			pst.setTimestamp(2, TransTypeSQL_GregorianCalendar.DateToTimestamp(Plateau.date_nouvelle_utilisation));
			pst.setString(3, obj.getJeu());
			pst.executeUpdate();
			
			ResultSet rs = pst.getGeneratedKeys();
			if(rs.next())
			{
				Plateau.id_plateau = rs.getInt(1);
			}
			
			String requete2 = "INSERT INTO joueur_courant"
					+ " (fk_id_joueur,fk_id_plateau) VALUES(?,?)";
			PreparedStatement pst2 = Connexion.getInstance().prepareStatement(requete2,  Statement.RETURN_GENERATED_KEYS);

			pst2.setInt(1, Plateau.joueurActuel.getNumeroJoueur());
			pst2.setInt(2, Plateau.id_plateau);
			pst2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			succes = false;
		} catch (ParseException e) {
			e.printStackTrace();
			succes = false;
		}

		return succes;
	}

	public boolean create() {
		boolean succes = true;
		try {
			String requete = "INSERT INTO " + TABLE
					+ " (temps_de_jeu,date_utilisation) VALUES(?,?)";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete,  Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, (int) Plateau.tempsDeJeuMillis);
			pst.setTimestamp(2, TransTypeSQL_GregorianCalendar.DateToTimestamp(Plateau.date_nouvelle_utilisation));
			
			pst.executeUpdate();
			
			ResultSet rs = pst.getGeneratedKeys();
			if(rs.next())
			{
				Plateau.id_plateau = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			succes = false;
		} catch (ParseException e) {
			e.printStackTrace();
			succes = false;
		}

		return succes;
	}
	
	public boolean create_joueur_courant() {
		boolean succes = true;
		try {			
			String requete = "INSERT INTO joueur_courant"
					+ " (fk_id_joueur,fk_id_plateau) VALUES(?,?)";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete);

			pst.setInt(1, Plateau.joueurActuel.getId());
			pst.setInt(2, Plateau.id_plateau);
			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			succes = false;
		}

		return succes;
	}
	
	public boolean update_joueur_courant() {
		boolean succes = true;
		try {			
			String requete = "UPDATE joueur_courant SET fk_id_joueur = ?"
					+ " WHERE fk_id_plateau = ?";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete);

			pst.setInt(1, Plateau.joueurActuel.getId());
			pst.setInt(2, Plateau.id_plateau);
			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			succes = false;
		}

		return succes;
	}
	
	@Override
	public boolean delete(Plateau obj) {
		boolean succes = true;
		try 
		{
			PreparedStatement pst = Connexion.getInstance()
					.prepareStatement("DELETE FROM " + TABLE + " WHERE "+CLE+" = ?");
			pst.setInt(1, 1);
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
	public boolean update(Plateau obj) {
		boolean succes = true;
		try {
			PreparedStatement pst = Connexion.getInstance()
					.prepareStatement("UPDATE " + TABLE + " "
							+ "SET joueur_actuel = ?, temps_de_jeu = ?, date_utilisation = ? WHERE "
							+ CLE + " = ?");

			pst.setInt(1, Plateau.joueurActuel.getId());
			pst.setInt(2, (int) Plateau.tempsDeJeuMillis);
			pst.setTimestamp(3, TransTypeSQL_GregorianCalendar.DateToTimestamp(Plateau.date_nouvelle_utilisation));
			pst.setInt(4, Plateau.id_plateau);
			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			succes = false;
		} catch (ParseException e) {
			e.printStackTrace();
			succes = false;
		}

		return succes;
	}
	
	public boolean update() {
		boolean succes = true;
		try {
			PreparedStatement pst = Connexion.getInstance()
					.prepareStatement("UPDATE " + TABLE + " "
							+ "SET temps_de_jeu = ?, date_utilisation = ? WHERE "
							+ CLE + " = ?");

			pst.setInt(1, (int) Plateau.tempsDeJeuMillis);
			pst.setTimestamp(2, TransTypeSQL_GregorianCalendar.DateToTimestamp(Plateau.date_nouvelle_utilisation));
			pst.setInt(3, Plateau.id_plateau);
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			succes = false;
		} catch (ParseException e) {
			e.printStackTrace();
			succes = false;
		}

		return succes;
	}

	@Override
	public Plateau read(int id) {
		ResultSet rs = Connexion.executeQuery("SELECT * FROM " + TABLE + " INNER JOIN joueur_courant ON joueur_courant.fk_id_plateau = plateau.id_plateau "
				+ "WHERE " + CLE + " = " + id);
		
		Plateau plateauLu = null;
		try {
			JoueurDAO joueurs_db = JoueurDAO.getInstance();
			if (rs.next()) 
			{
				if(rs.getString("type_jeu") == "Memori")
				{
					plateauLu = new Plateau_Memori(
							rs.getInt("temps_de_jeu"),
							TransTypeSQL_GregorianCalendar.TimestampToDate(rs.getTimestamp("date_utilisation")),
							joueurs_db.lireJoueursPlateauCourant(rs.getInt("id_plateau")),
							joueurs_db.read(rs.getInt("fk_id_joueur")).getNumeroJoueur(),
							rs.getInt("id_plateau")
							);
				}
				else if(rs.getString("type_jeu") == "PetitVerger")
				{
					plateauLu = new Plateau_PetitVerger(
							rs.getInt("temps_de_jeu"),
							TransTypeSQL_GregorianCalendar.TimestampToDate(rs.getTimestamp("date_utilisation")),
							joueurs_db.lireJoueursPlateauCourant(rs.getInt("id_plateau")),
							joueurs_db.read(rs.getInt("fk_id_joueur")).getNumeroJoueur(),
							rs.getInt("id_plateau")
							);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return plateauLu;
	}
	
	public List<String> listDePartieEnCours()
	{
		String requete1 = "SELECT * FROM " + TABLE + " INNER JOIN participe ON plateau.id_plateau = participe.fk_id_plateau INNER JOIN joueur ON joueur.id_joueur = participe.fk_id_joueur WHERE plateau.type_jeu = '"+ this.getJeu() +"' AND plateau.id_plateau NOT IN ";
		String requete2 = "(SELECT fk_id_plateau FROM gagnee) ORDER BY plateau.id_plateau";
		ResultSet rs = Connexion.executeQuery(requete1 + requete2);

		List<String> messages = new ArrayList<String>();
		int compteur = 0;
		int id_partie = 0;
		try 
		{
			while(rs.next())
			{
				if(id_partie != rs.getInt("id_plateau"))
				{
					compteur++;
					id_partie = rs.getInt("id_plateau");
					dicoCompteurPlateau.put(compteur,id_partie);
					           
					messages.add("");
					messages.add(" La partie n°" + compteur + " ["+rs.getDate("date_utilisation")+"] est en cours.");
					messages.add(" Joueur "+rs.getInt("numero_joueur")+" : " + rs.getString("nom") + " [points = "+ rs.getInt("nombre_points")+" | erreurs = "+ rs.getString("nombre_erreurs") +"]");					
				}
				else
				{
					messages.add(" Joueur "+rs.getInt("numero_joueur")+" : " + rs.getString("nom") + " [points = "+ rs.getInt("nombre_points")+" | erreurs = "+ rs.getString("nombre_erreurs") +"]");
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return messages;
	}
	
	public List<String> listDePartieFinie()
	{
		String requete1 = "SELECT * FROM " + TABLE + " INNER JOIN participe ON plateau.id_plateau = participe.fk_id_plateau INNER JOIN joueur ON joueur.id_joueur = participe.fk_id_joueur WHERE plateau.type_jeu = '"+ this.getJeu() +"' AND participe.fk_id_joueur IN ";
		String requete2 = "(SELECT fk_id_joueur FROM gagnee) AND participe.fk_id_plateau IN (SELECT fk_id_plateau FROM gagnee) ";

		ResultSet rs = Connexion.executeQuery(requete1 + requete2);

		List<String> messages = new ArrayList<String>();
		try 
		{
			while(rs.next())
			{
				messages.add("");
				messages.add(" Lors de la partie ["+rs.getDate("date_utilisation")+"] :");
				messages.add(" Le joueur " + rs.getString("nom") + " à gagné [points = "+ rs.getInt("nombre_points")+" | erreurs = "+ rs.getString("nombre_erreurs") +"]");					
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return messages;
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

}
