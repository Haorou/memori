package joueur;

import java.util.Scanner;

import carte.Carte;
import view.ConsoleView;

public abstract class Joueur 
{	
	private int id_joueur;
	
	public static final Scanner SCANNER = new Scanner(System.in);
	private static int nombreDeJoueurs = 0;
	private int numeroDuJoueur = 0;

	private String nom = "";
	private int nombreErreurs = 0;
	private int nombrePoints = 0;
	private int anneeDeNaissance = 0;

	public Joueur()
	{		
		nombreDeJoueurs++;
		this.numeroDuJoueur = nombreDeJoueurs;
		this.nom = choisirSonNom();
		this.anneeDeNaissance = choisirSonAnneeDeNaissance();

	}
	
	public Joueur(String nomDB, int nombreErreursDB, int nombrePointsDB, int numeroDuJoueurDB, int anneDeNaissanceDB, int id_joueurDB)
	{
		this.nom = nomDB;
		this.nombreErreurs = nombreErreursDB;
		this.nombrePoints = nombrePointsDB;
		this.numeroDuJoueur = numeroDuJoueurDB;
		this.anneeDeNaissance = anneDeNaissanceDB;
		this.id_joueur = id_joueurDB;
	}
	
	public Joueur(String nomDB, int nombreErreursDB, int nombrePointsDB, Carte[] choixJoueurDB, int numeroDuJoueurDB, int id_joueurDB)
	{
		this.nom = nomDB;
		this.nombreErreurs = nombreErreursDB;
		this.nombrePoints = nombrePointsDB;
		this.setChoixJoueurs(choixJoueurDB);
		this.numeroDuJoueur = numeroDuJoueurDB;
		this.id_joueur = id_joueurDB;
	}
	
	public abstract void setChoixJoueurs(Carte[] cartes);
	public abstract void setPremiereCarte(Carte nouvelCarte);
	public abstract void setSecondeCarte(Carte nouvelCarte);
	public abstract void reinitialiserSesCartes();
	public abstract Carte getPremiereCarte();
	public abstract Carte getSecondeCarte();
	
	
	public int getId()
	{
		return this.id_joueur;
	}
	
	public void setId(int id)
	{
		this.id_joueur = id;
	}
	
	public String getNom()
	{
		return this.nom;
	}
	
	public int getAnneeDeNaissance() {
		return anneeDeNaissance;
	}

	public void setAnneeDeNaissance(int anneeDeNaissance) {
		this.anneeDeNaissance = anneeDeNaissance;
	}
	
	public int getNumeroJoueur()
	{
		return this.numeroDuJoueur;
	}

	public void setNumeroDuJoueur(int numeroDuJoueur) {
		this.numeroDuJoueur = numeroDuJoueur;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setNombreErreurs(int nombreErreurs) {
		this.nombreErreurs = nombreErreurs;
	}

	public void setNombrePoints(int nombrePoints) {
		this.nombrePoints = nombrePoints;
	}

	public int getNombreErreurs()
	{
		return this.nombreErreurs;		
	}

	public int getNombrePoints()
	{
		return this.nombrePoints;		
	}
	
	public void ajouterUneErreur()
	{
		this.nombreErreurs++;
	}
	
	public void ajouterUnPoint()
	{
		this.nombrePoints++;
	}
	
	private String choisirSonNom()
	{
		ConsoleView.afficherMessage("");
		ConsoleView.afficherMessage("Veuillez choisir un nom pour le joueur : " + this.getNumeroJoueur());
		ConsoleView.afficherMessage("");		
		return SCANNER.next();
	}
	
	private int choisirSonAnneeDeNaissance()
	{
		ConsoleView.afficherMessage("");
		ConsoleView.afficherMessage(this.nom + ", veuillez donner votre année de naissance : ");
		ConsoleView.afficherMessage("");
		return SCANNER.nextInt();
	}
}
