package dao;

public class Plateau_PetitVergerDAO extends PlateauDAO 
{
	private static final String JEU = "Memori";

	private static Plateau_PetitVergerDAO instance = null;

	public static Plateau_PetitVergerDAO getInstance() {
		if (instance == null) {
			instance = new Plateau_PetitVergerDAO();
		}
		return instance;
	}

	@Override
	public String getJeu() {
		return JEU;
	}  
}
