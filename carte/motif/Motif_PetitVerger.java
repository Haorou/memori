package carte.motif;

public enum Motif_PetitVerger implements IMotif 
{
ROUGE("  Rouge  "), BLEU("  Bleu   "),JAUNE("  Jaune  "),BLANC("  Blanc  "),
VIOLET("  Violet "),CORBEAU(" Corbeau ");
	
	private static final IMotif[] TABLEAU = Motif_PetitVerger.values();
	String message = "";
	
	private Motif_PetitVerger(String message)
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
