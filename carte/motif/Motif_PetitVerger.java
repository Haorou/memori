package carte.motif;

public enum Motif_PetitVerger implements IMotif 
{
CERISE("  Cerise "), ANIMAL("  Animal "),CORBEAU(" Corbeau "),
BLEU("--BLEU---"), ROUGE("--ROUGE--"),VIOLET("--VIOLET-"),JAUNE("--JAUNE--"), VERT("--VERT---");
	
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
