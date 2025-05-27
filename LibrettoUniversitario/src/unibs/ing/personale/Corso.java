package unibs.ing.personale;

import java.io.Serializable;


public class Corso implements Serializable {

	public static final int LUNGHEZZA_SPAZI = 5;
	private static final long serialVersionUID = 1L;
	//
	public String nomeCorso;
	public int CFU;
	private int voto;
	public int anno;
	public boolean lode;
	
	
	//
	public Corso(String nomeCorso, int voto, boolean lode, int CFU, int anno) {
		super();
		this.nomeCorso = nomeCorso;
		this.voto = voto;
		this.CFU = CFU;
		this.anno = anno;
		this.lode = lode;
	}
	
	public Corso(String nomeCorso, int CFU, int anno) {
		super();
		this.nomeCorso = nomeCorso;
		this.CFU = CFU;
		this.anno = anno;
		this.voto = -1;
	}


	//
	public String getNomeCorso() {
		return nomeCorso;
	}
	public void setNomeCorso(String nomeCorso) {
		this.nomeCorso = nomeCorso;
	}
	public double getVoto() {
		return voto;
	}
	public boolean getLode() {
		return lode;
	}
	public void setVoto(int voto) {
		this.voto = voto;
	}
	public int getCFU() {
		return CFU;
	}
	public void setCFU(int cFU) {
		CFU = cFU;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public void setLode(boolean lode) {
		this.lode = lode;
	}
	
	
	//
	public double peso() {
		return CFU * voto;
	}

	
	public String toString() {
		StringBuffer array = new StringBuffer();
		

		
		if (voto == -1)
			array.append(nomeCorso + " [CFU=" + CFU + ", Anno=" + anno + "]");
		else if (lode)	
			array.append(nomeCorso + " [CFU=" + CFU + ", Voto=" + voto + "L, Anno=" + anno + "]");
		else	
			array.append(nomeCorso + " [CFU=" + CFU + ", Voto=" + voto + ", Anno=" + anno + "]");
		return array.toString();
	}
	

}
