package jeu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import carte.Carte;
import carte.PaquetCartes;
import carte.motif.IMotif;
import carte.motif.Motif;
import dao.gestionnaire.Gestionnaire;
import view.ConsoleView;

public abstract class Plateau 
{
	public static int id_plateau;
	
	protected long tempsDeJeuMillisDB;
	public static Date date_nouvelle_utilisation = new Date();
	public Date date_derniere_utilisationDB;
	public static long tempsDeJeuMillis;
	
	public static List<Joueur> joueurs = new ArrayList<Joueur>();
	public static Joueur joueurActuel;
	public static Joueur joueurVainqueur = null;

	public Plateau() {}
	
	public Plateau(long tempsDeJeuMillis, Date date_derniere_utilisation, List<Joueur> joueursDB, int indexDB, int id_plateauDB)
	{
		date_derniere_utilisationDB = date_derniere_utilisation;
		tempsDeJeuMillisDB = tempsDeJeuMillis;
		
		joueurs = joueursDB;

		joueurActuel = getJoueur(indexDB);
		
		id_plateau = id_plateauDB;
	}
	
	public Plateau(long tempsDeJeuMillis, Date date_derniere_utilisation, List<Joueur> joueursDB, int indexDB)
	{
		date_derniere_utilisationDB = date_derniere_utilisation;
		tempsDeJeuMillisDB = tempsDeJeuMillis;
		
		joueurs = joueursDB;

		joueurActuel = getJoueur(indexDB);
	}

	public abstract String getJeu();
	
	public abstract Gestionnaire getGestionnaire();
	
	public static Joueur getJoueur(int index) {
		return joueurs.get(index - 1);
	}

	public static int nombreDeJoueurs()
	{
		return joueurs.size();
	}
	
	public int getPaquetJeuTaille()
	{
		return PaquetCartes.getTaillePaquet();
	}

	public String getDate()
	{
		@SuppressWarnings("deprecation")
		String message = "Du " + date_derniere_utilisationDB.getDate() + "/" +
				date_derniere_utilisationDB.getMonth() + "/" 
				+ (1900 + date_derniere_utilisationDB.getYear()) +
				" à " +date_derniere_utilisationDB.getHours() + 
				"h et "+ date_derniere_utilisationDB.getMinutes() + "min";
		return message;
	}

	//------------------GESTION JEU ------------------//	
	public abstract void combienCreerDeJoueurs();

	public abstract void jouer();
	
	protected String tempsDeJeu()
	{
		tempsDeJeuMillis = ( new Date().getTime() - date_nouvelle_utilisation.getTime() ) + tempsDeJeuMillisDB;
		long minutes = TimeUnit.MILLISECONDS.toMinutes(tempsDeJeuMillis);
		long seconds  = TimeUnit.MILLISECONDS.toSeconds(tempsDeJeuMillis);
		return minutes + " minutes " + seconds + " secondes.";
	}
	
	protected Carte retournerCarte(int indice)
	{
		PaquetCartes.get(indice).carteRetourneVersMotif();
		
		return PaquetCartes.get(indice);
	}
	
	protected void passerAuJoueurSuivant()
	{
		int indexJoueurCourant = joueurs.indexOf(joueurActuel);
		int nombreDeJoueur = joueurs.size();
		
		if(indexJoueurCourant +1 < nombreDeJoueur)
			joueurActuel = joueurs.get(indexJoueurCourant + 1);
		else
			joueurActuel = joueurs.get(0);	
	}
			
	//----------------GERER AFFICHAGE PLATEAU----------------//
	protected void afficherPlateau()
	{	
		List<String> affichagePlateau = new ArrayList<String>();
		int tourDeBoucle;
		for (int i = 0; i < getPaquetJeuTaille(); i++) 
		{
			tourDeBoucle = i;
			affichagePlateau.add(lignePlateau(tourDeBoucle, i ,0));
			affichagePlateau.add(lignePlateau(tourDeBoucle, i ,1));
			affichagePlateau.add(lignePlateau(tourDeBoucle, i ,2));
			affichagePlateau.add(lignePlateau(tourDeBoucle, i ,1));
			affichagePlateau.add(lignePlateau(tourDeBoucle, i ,3));
			i += 3;
		}		
		ConsoleView.afficherMessages(affichagePlateau);
	}

	private String lignePlateau(int tourDeboucle, int tourDeBoucleIntra, int numeroTemplate)
	{
		String ligne = "";

		for (tourDeBoucleIntra = tourDeboucle; tourDeBoucleIntra < tourDeboucle + 4; tourDeBoucleIntra++) {
			ligne += retournerTemplate(numeroTemplate, tourDeBoucleIntra);
		}
		return ligne;
	}
	
	private String retournerTemplate(int numeroTemplate, int tourDeBoucleIntra)
	{
		String template = "";
		switch(numeroTemplate)
		{
		case 0:
			if(tourDeBoucleIntra + 1 >= 10)
				template = "|-----"+ (tourDeBoucleIntra+1) +"-----|";
			else
				template = "|------"+ (tourDeBoucleIntra+1) +"-----|";				
			break;
		case 1:
			template = "|            |";							
			break;
		case 2:
			template = "| "+ afficherCarte(tourDeBoucleIntra)  + "  |";
			break;
		default:
			template = "|------------|";					
		}
		return template;
	}
	
	private IMotif afficherCarte(int indice)
	{
		return indice < PaquetCartes.getTaillePaquet()? PaquetCartes.get(indice).getAffichage():Motif.VIDE;
	}

	//----------------GERER SAUVEGARDE BD----------------//
	protected void updateData()
	{
		getGestionnaire().updateDataPartie();
	}
	
	protected void createCartesEnMain()
	{
		getGestionnaire().createCartesEnMain();
	}
	
	protected void deleteCartesEnMain()
	{
		getGestionnaire().deleteCartesEnMain();
	}	
}