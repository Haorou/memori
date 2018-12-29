package carte.motif;

public enum Motif implements IMotif
{
	DOS("#########"), VIDE("         ");
		
	private static final IMotif[] TABLEAU = Motif.values();
	String message = "";
	
	private Motif(String message)
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
