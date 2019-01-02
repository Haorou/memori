package carte.motif;

public enum Motif_Memori implements IMotif 
{
	LOUP("  Loup   "), BICHE("  Biche  "),ABEILLE(" Abeille "),GUEPE("  Guêpe  "),CROCODILE("Crocodile"),
	PIEUVRE(" Pieuvre "),HUITRE(" Huitre "),CHIEN("  Chien  "),CHAT("  Chat   "),COCHON(" Cochon  "),DOS("#########"), VIDE("         ");
	
	private static final IMotif[] TABLEAU = Motif_Memori.values();
	String message = "";
	
	private Motif_Memori(String message)
	{
		this.message = message;
	}
	
	public static IMotif get(int indice)
	{
		return TABLEAU[indice];
	}
	
	@Override
	public String toString() 
	{
		return message;
	}	
}
