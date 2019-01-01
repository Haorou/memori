package dao.cartes;

import java.sql.ResultSet;
import java.sql.SQLException;

import carte.Carte;
import carte.Carte_Memori;
import carte.motif.IMotif;
import carte.motif.Motif_Memori;
import dao.Connexion;

public class Cartes_MemoriDAO extends CartesDAO {

	public IMotif[] motifCarte = Motif_Memori.values();
			
	private static Cartes_MemoriDAO instance = null;

	public static Cartes_MemoriDAO getInstance() {
		if (instance == null) {
			instance = new Cartes_MemoriDAO();
		}
		return instance;
	}
	
	public IMotif getMotif(int index)
	{
		return this.motifCarte[index];
	}
	
	@Override
	public Carte read(int id) {
		ResultSet rs = Connexion.executeQuery("SELECT * FROM " + TABLE + " INNER JOIN dos_carte ON carte.id_carte = dos_carte.fk_id_carte WHERE " + CLE + " =" + id);

		Carte carteLu = null;
		int positionIndex;
		int dos_de_carte;
		try 
		{
			if (rs.next()) 
			{	
				positionIndex = rs.getInt("position_carte");
				if(rs.wasNull())
					positionIndex = -1;
				
				carteLu = new Carte_Memori(
						getMotif(rs.getInt("fk_id_motif_carte")), 
						rs.getInt("est_trouve") == 1 ?true:false,
						positionIndex);
				
				carteLu.setId(rs.getInt("id_carte"));
				
				dos_de_carte = rs.getInt("fk_id_motif_dos_carte");
				if(!rs.wasNull())
				{
					if(carteLu.getEstTrouve())
					{
						carteLu.setDos(Motif_Memori.VIDE);
						carteLu.carteRetourneVersDos();	
					}
					else
					{	
						carteLu.setDos(getMotif(dos_de_carte));					
						carteLu.carteRetourneVersDos();	
					}
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return carteLu;
	}
}
