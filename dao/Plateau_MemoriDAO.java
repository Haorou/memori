package dao;

public class Plateau_MemoriDAO extends PlateauDAO 
{
	private static final String JEU = "Memori";

	private static Plateau_MemoriDAO instance = null;

	public static Plateau_MemoriDAO getInstance() {
		if (instance == null) {
			instance = new Plateau_MemoriDAO();
		}
		return instance;
	}

	@Override
	public String getJeu() {
		return JEU;
	}  
}
