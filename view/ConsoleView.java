package view;

import java.util.List;

public class ConsoleView 
{
	
	private static int nombreDeTirets;
	private static String tiretsNomJeu = "";
	
	public static void afficherTitre(String titre)
	{
		for(int x = 0; x <= titre.length() ; x++)
			tiretsNomJeu += "-";
		
		System.out.println("|---------------------------------"+ tiretsNomJeu +"---------------------------------|\n"
						 + "|                                 "+ titre +"                                  |\n"
						 + "|---------------------------------"+ tiretsNomJeu +"---------------------------------|");
		
		nombreDeTirets = 66 + tiretsNomJeu.length();
	}

	
	public static void afficherOptions(String message)
	{
		int nombreDeTiretsAffichageables = nombreDeTirets - message.length();
		
		String tirets = "";
		for(int x = 0; x < nombreDeTirets ; x++)
			tirets += "-";
		
		String espaces = "";
		for(int x = 0; x < nombreDeTiretsAffichageables / 2 ; x++)
			espaces += " ";
		
		String espaceSup = "";
		if(message.length() %2 == 0)
			espaceSup = " ";

		System.out.println("|"+ tirets + "|");
		System.out.println("|"+espaces+ message +espaces+ espaceSup + "|");
		System.out.println("|"+ tirets + "|");
	}
	
	public static void afficherMessage(String message)
	{
		int nombreDeTiretsAffichageables = nombreDeTirets - message.length();
		
		String espaces = "";
		for(int x = 0; x < nombreDeTiretsAffichageables / 2 ; x++)
			espaces += " ";
		
		String espaceSup = "";
		if(message.length() %2 == 0)
			espaceSup = " ";

		
		System.out.println("|"+espaces+ message +espaces+ espaceSup + "|");
	}
	
	public static void afficherMessages(List<String> messages)
	{
		int nombreDeTiretsAffichageables = 0;
		String espaces = "";
		String espaceSup = "";
		for (String message : messages) 
		{
			nombreDeTiretsAffichageables = nombreDeTirets - message.length();
			espaces = "";
			for(int x = 0; x < nombreDeTiretsAffichageables / 2 ; x++)
				espaces += " ";
			
			espaceSup = "";
			
			if(message.length() %2 == 0)
				espaceSup = " ";
			
			System.out.println("|"+espaces+ message +espaces+ espaceSup + "|");
		}

	}
}
