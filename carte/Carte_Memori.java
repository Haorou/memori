package carte;

import carte.motif.IMotif;
import carte.motif.Motif_Memori;

public class Carte_Memori extends Carte {
	
	private IMotif dos = Motif_Memori.DOS;
	private IMotif enleve = Motif_Memori.VIDE;
	
	public Carte_Memori() {
		super();
	}
	
	public Carte_Memori(IMotif iMotif) {
		super(iMotif);
	}

	public Carte_Memori(IMotif motif, boolean b, int positionIndex) {
		super(motif,b,positionIndex);
	}

	@Override
	public IMotif getDos() {
		return dos;
	}
	
	@Override
	public void setDos(IMotif dos) {
		this.dos = dos;
	}
	
	@Override
	public IMotif getEnleve() {
		return enleve;
	}
}