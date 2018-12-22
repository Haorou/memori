package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

/**
 * �tape 1 : la connexion � la base de donn�es
 */
public class Connexion {

	private static Connection connect = null;

	private static final String ID = "JulienB";
	private static final String MDP = "jean";
	private static final String NOM_SERVEUR = "localhost\\SQLEXPRESS";
	private static final String NOM_BD = "MemoriAlt";

//	private static final int LARGEUR_COLONNE_TEXTE = 10;
//	private static final int LARGEUR_COLONNE_ENTIER = 6;
//	private static final int LARGEUR_COLONNE_DATE = 11;

	/**
	 * Patron de conception Singleton
	 * @return l'instance unique de connexion
	 */
	public static Connection getInstance() {
		if (connect==null) {
			// Si la connexion n'a pas encore �t� faite, on la fait.
			try {
				SQLServerDataSource ds = new SQLServerDataSource();
				ds.setUser(ID);
				ds.setPassword(MDP);
				ds.setServerName(NOM_SERVEUR);
				ds.setDatabaseName(NOM_BD);
				connect = ds.getConnection();
			}
			catch (SQLException e){
				System.out.println("Echec de la tentative de connexion : " + e.getMessage() + e.getStackTrace()) ;
			}
		}
		return connect;
	}

	/**
	 * M�thode statique d'ex�cution d'une requ�te de r�cup�ration de donn�es
	 * @param requete
	 * @return
	 */
	public static ResultSet executeQuery(String requete){
		Statement st = null ;
		ResultSet rs = null;
		
		//System.out.println("requete = "+requete);
		try{
			st = getInstance().createStatement() ;
			rs = st.executeQuery(requete) ;
		}catch(SQLException e){
			System.out.println("Echec de la tentative d'ex�cution de requete : " +requete + " ["+ e.getMessage()+"]") ;
		}
		return rs;
	}

	/**
	 * Une m�thode statique qui permet de faire une mise � jour d'une table (INSERT / UPDATE / DELETE)
	 * Mais cette m�thode n'est pas utilis�e puisqu'on passe par des prepared statement
	 * dans les classes DAO et on fait des execute update directement sur ces preparedStatement.
	 * @param requete
	 * @return
	 */
	public static boolean executeUpdate(String requete){
		boolean succes = true;
		//System.out.println(requete);
		Statement st = null ;
		try{
			st = getInstance().createStatement() ;
			st.executeUpdate(requete) ;
		}catch(SQLException e){
			succes = false;
			System.out.println("Echec de la tentative d'ex�cution de Mise � Jour : " +requete + " ["+ e.getMessage()+"]") ;
		}
		return succes;
	}

	/**
	 * Fermeture de la connexion au SGBD SQL ServerExpress
	 */
	public static void fermer()
	{
		try
		{
			getInstance().close();
			System.out.println("deconnexion ok");
		}
		catch (SQLException e)
		{
			connect=null;
			System.out.println("echec de la fermeture");
		}
	}

	/**
	 * Requ�te qui permet de voir le contenu d'une table
	 * Attention � ne pas perdre la premi�re ligne en testant la table vide
	 * @param table
	 */
	public static void afficheSelectEtoile(String table, String clauseWhere){
		Statement st = null;
		ResultSet rs = null;
		try
		{
			st = getInstance().createStatement();
			rs = st.executeQuery("SELECT * FROM " + table + " WHERE " + clauseWhere);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int nbrDeColonnes = rsmd.getColumnCount();
			
			while(rs.next())
			{
				for (int i = 1; i <= nbrDeColonnes; i++) {
					System.out.println(rs.getString(i));
				}
			}
		}
		catch(SQLException e){
			System.out.println("Echec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
	}


	/**
	 * Requ�te qui permet de r�cup�rer le plus grand id de la table, a priori celui qui vient d'�tre affect�
	 * � une ligne cr��e via identity. Utiliser MAX
	 * @param cle
	 * @param table
	 * @return
	 */
	public static int getMaxId(String cle, String table) {
		int id= -1;
		
		Statement st = null ;
		ResultSet rs = null;

		try 
		{
			st = getInstance().createStatement() ;
			rs = st.executeQuery("SELECT MAX(" + cle + ") FROM " + table) ;
			
			while(rs.next())
			{
				id = Integer.valueOf(rs.getInt(1));				
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Cette m�thode correspond � la m�thode create de la classe AvionDAO
	 * On peut la tester dans le main avec
	 * Avion avion=AvionDAO.getInstance().read(5);		
	 * AvionDAO.getInstance().create(avion);
	 * System.out.println(avion);
	 * --> Notez que AvionDAO est d�fini comme un singleton, unique instance pour tout le programme 
	 * @param av
	 */
	
	/*
	public static boolean exempleCreatePreparedStatement(Avion av) 
	{
		boolean succes=true;
		// constantes qui peuvent �tre d�clar�es dans vos classes DAO
		String TABLE = "AVION";
		String CLE_PRIMAIRE = "numAv";
		try {
			
			String requeteDelete = "DELETE FROM "+TABLE+" WHERE "+CLE_PRIMAIRE+" = ?";
			String requeteUpdate = "UPDATE "+TABLE+" SET nomav = ?, loc = ?, capacite = ? WHERE "+CLE_PRIMAIRE+" = ?";
			String requete = "INSERT INTO "+TABLE+" (nomav, loc, capacite) VALUES (?, ?, ?)";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			// on pose un String en param�tre 1 -1er '?'- et ce String est le nom de l'avion
			pst.setString(1, av.getNOMAV());
			pst.setString(2, av.getLOC());
			pst.setInt(3, av.getCAPACITE());
			// on ex�cute la mise � jour
			pst.executeUpdate();

			//R�cup�rer la cl� qui a �t� g�n�r�e et la pousser dans l'objet initial
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				av.setNUMAV(rs.getInt(1));
			}

		} catch (SQLException e) {
			succes=false;
			e.printStackTrace();
		}

		return succes;
	}
*/
}
