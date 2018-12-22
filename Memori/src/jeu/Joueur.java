package jeu;

import java.util.Scanner;

import carte.Carte;

public class Joueur 
{	
	private int id_joueur;
	
	public static final Scanner SCANNER = new Scanner(System.in);
	private static int nombreDeJoueurs = 0;
	private int numeroDuJoueur = 0;

	private Carte[] choixJoueur = new Carte[] {new Carte(), new Carte()};
	
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
		this.choixJoueur = choixJoueurDB;
		this.numeroDuJoueur = numeroDuJoueurDB;
		this.id_joueur = id_joueurDB;
	}
	
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
	
	public Carte getPremiereCarte()
	{
		return this.choixJoueur[0];
	}
	
	public Carte getSecondeCarte()
	{
		return this.choixJoueur[1];		
	}
	
	public void setPremiereCarte(Carte nouvelCarte)
	{
		this.choixJoueur[0] = nouvelCarte;
	}
	
	public void setSecondeCarte(Carte nouvelCarte)
	{
		this.choixJoueur[1] = nouvelCarte;		
	}

	public void setNumeroDuJoueur(int numeroDuJoueur) {
		this.numeroDuJoueur = numeroDuJoueur;
	}

	public void setChoixJoueur(Carte[] choixJoueur) {
		this.choixJoueur = choixJoueur;
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
	
	public void reinitialiserSesCartes()
	{
		this.choixJoueur =  new Carte[] {new Carte(), new Carte()};
	}
	
	private String choisirSonNom()
	{
		System.out.println("Veuillez choisir un nom pour le joueur : " + this.getNumeroJoueur() );
		return SCANNER.next();
	}
	
	private int choisirSonAnneeDeNaissance()
	{
		System.out.println(this.nom + ", veuillez donner votre année de naissance : ");
		return SCANNER.nextInt();
	}
}
