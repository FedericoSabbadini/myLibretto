package unibs.ing.personale;

public class Archivio {

	public Archivio(Libretto librettoDiSessionePrecedente) {

		this.librettoDiSessionePrecedente = librettoDiSessionePrecedente;
	}
	
	public Archivio( ) {

	}
	
	public Libretto librettoDiSessionePrecedente;
	
    public Libretto getLibrettoDiSessionePrecedente() {
		return librettoDiSessionePrecedente;
	}
	public void setLibrettoDiSessione(Libretto libretto) {
		this.librettoDiSessionePrecedente = libretto;
	}
}
