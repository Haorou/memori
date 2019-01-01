package joueur;

import carte.Carte;
import carte.Carte_PetitVerger;

public class Joueur_PetitVerger extends Joueur
{	 
	private Carte[] choixJoueur = new Carte[] {new Carte_PetitVerger(),new Carte_PetitVerger() };
	
	public Joueur_PetitVerger() {
		super();
	}
	
	public Joueur_PetitVerger(String string, int int1, int int2, int int3, int int4, int int5) {
		super(string,int1,int2,int3,int4,int5);
	}

	@Override
	public void reinitialiserSesCartes()
	{
		this.choixJoueur =  new Carte[] {new Carte_PetitVerger(),new Carte_PetitVerger() };
	}
	
	@Override
	public void setChoixJoueurs(Carte[] cartes)
	{
		this.choixJoueur = cartes;
	}

	@Override
	public Carte getPremiereCarte()
	{
		return this.choixJoueur[0];
	}
	
	@Override
	public Carte getSecondeCarte()
	{
		return this.choixJoueur[1];		
	}
	
	@Override
	public void setPremiereCarte(Carte nouvelCarte)
	{
		this.choixJoueur[0] = nouvelCarte;
	}
	
	@Override
	public void setSecondeCarte(Carte nouvelCarte)
	{
		this.choixJoueur[1] = nouvelCarte;		
	}
}
