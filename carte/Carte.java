package carte;

import carte.motif.IMotif;

public abstract class Carte {
	
	private int id_carte;
	
	private final IMotif motifCarte;
	private IMotif affichage;
	private boolean estTrouve = false;
	private int positionIndexPaquet = -1;
	
	public abstract IMotif getDos();
	public abstract void setDos(IMotif dos);
	public abstract IMotif getEnleve();
	
	public Carte()
	{
		this.motifCarte = this.getDos();
		this.affichage = this.getDos();
	}
	
	public Carte(IMotif motif) 
	{
		this.affichage = this.getDos();
		this.motifCarte = motif;
	}

	public Carte(IMotif motifDB, boolean estTrouveDB, int positionIndexPaquetDB, int id_carteDB)
	{
		this.motifCarte = motifDB;
		this.estTrouve = estTrouveDB;
		if(this.estTrouve)
		{
			this.affichage = this.motifCarte;
		}
		this.positionIndexPaquet = positionIndexPaquetDB;
		this.id_carte = id_carteDB;
	}
	
	public Carte(IMotif motifDB, boolean estTrouveDB, int positionIndexPaquetDB)
	{
		this.motifCarte = motifDB;
		this.estTrouve = estTrouveDB;
		if(this.estTrouve)
		{
			this.affichage = this.motifCarte;
		}
		this.positionIndexPaquet = positionIndexPaquetDB;
	}
	
	public int getId()              {   return this.id_carte;   }
	public void setId(int id)       {   this.id_carte=id;   	}
	public IMotif getMotif()			{	return this.motifCarte;	}
	public IMotif getAffichage()		{	return this.affichage;	}
	public boolean getEstTrouve()	{	return this.estTrouve;	}
	
	
	public int getPositionIndexPaquet() {
		return positionIndexPaquet;
	}

	public void setPositionIndexPaquet(int positionIndexPaquet) {
		this.positionIndexPaquet = positionIndexPaquet;
	}

	public void setEstTrouve(boolean bool) {
		this.estTrouve = bool;
	}

	public void carteEnleve()
	{
		this.affichage = getEnleve();
	}
	
	public void carteRetourneVersMotif() {
		this.affichage = this.motifCarte;
	}
	
	public void carteRetourneVersDos() {
		this.affichage = this.getDos();
	}
	
	public boolean equals(Carte autre)
	{
		return this.getMotif() == autre.getMotif();
	}

}