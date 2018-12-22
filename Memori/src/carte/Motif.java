package carte;

public enum Motif {
LOUP("  Loup   "), BICHE("  Biche  "),ABEILLE(" Abeille "),GUEPE("  Guêpe  "),CROCODILE("Crocodile"),
PIEUVRE(" Pieuvre "),HUITRE(" Huitre "),CHIEN("  Chien  "),CHAT("  Chat   "),COCHON(" Cochon  "), DOS("#########"), VIDE("         ");
	
	private static final Motif[] TABLEAU = Motif.values();
	String message = "";
	
	private Motif(String message)
	{
		this.message = message;
	}
	
	public static Motif get(int indice)
	{
		return TABLEAU[indice];
	}
	
	@Override
	public String toString() 
	{
		return message;
	}	
}
