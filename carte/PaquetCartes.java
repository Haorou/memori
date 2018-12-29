package carte;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import carte.motif.Motif_Memori;
import carte.motif.Motif_PetitVerger;

public class PaquetCartes 
{
	public static List<Carte> paquetCartes;
	private static int taillePaquet;
		
	public static void premierPaquetCartes_Memori(int nombreDeMotifs) {
	
		taillePaquet = nombreDeMotifs * 2;
		remplirPaquetCartes_Memori(nombreDeMotifs);
		
		Collections.shuffle(paquetCartes);
		
		setIndexCarte();
	}
	
	public static void premierPaquetCartes_PetitVerger() 
	{	
		taillePaquet = 15;
		remplirPaquetCartes_PetitVerger();
		
		Collections.shuffle(paquetCartes);
		
		setIndexCarte();
	}
	
	public static void PaquetCartesDB()
	{
		paquetCartes = new ArrayList<Carte>();
	}
	
	public static void setSizeTaillePaquet()
	{
		taillePaquet = paquetCartes.size();
	}
	
	public static int getTaillePaquet() {
		return taillePaquet;
	}
	
	public static Carte get(int indice) {
		 Carte carte = indice== -1?new Carte():paquetCartes.get(indice);
		
		return carte;
	}
	
	public static boolean add(Carte carte) {
		return paquetCartes.add(carte);
	}
	
	private static void setIndexCarte()
	{
		int index = 0;
		for (Carte carte : paquetCartes) 
		{
			carte.setPositionIndexPaquet(index++);
		}
	}
	
	private static void remplirPaquetCartes_Memori(int nombreDeMotifs) {
		paquetCartes = new ArrayList<Carte>(taillePaquet);
		
		for (int i = 0; i < nombreDeMotifs; i++) {
			for (int j = 0; j < 2; j++) {
				add( new Carte( Motif_Memori.get(i) ) );	
			}
		}
	}
	
	private static void remplirPaquetCartes_PetitVerger() {
		paquetCartes = new ArrayList<Carte>(taillePaquet);
		
		for (int i = 0; i < 6; i++) 
		{
			add( new Carte( Motif_PetitVerger.CORBEAU ) );	
		}
		
		for (int i = 0; i < 5; i++) 
		{
			add( new Carte( Motif_PetitVerger.CERISE ) );	
		}
		
		for (int i = 0; i < 4; i++) 
		{
			add( new Carte( Motif_PetitVerger.ANIMAL ) );	
		}
	}

}
