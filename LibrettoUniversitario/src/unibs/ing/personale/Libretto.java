package unibs.ing.personale;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Libretto implements Serializable {

	private static final long serialVersionUID = 1L;
	//
	private String nome;
	public HashMap<String, Corso> libretto;
	private int numeroLodi;
	//public boolean type; // true=universitario, false=scolastico
	
	
	public int getNumeroLodi() {
		return numeroLodi;
	}
	//
	public Libretto(String nome) {
		super();
		this.nome = nome;
		this.libretto = new HashMap<>();
		this.numeroLodi = 0;
	}
	public String getNome() {
		return nome;
	}
	/*
	 * 	public Libretto(HashMap<String, Corso> libretto, boolean type) {
		super();
		this.libretto = libretto;
	}
	 * 
	 */

	public Libretto(HashMap<String, Corso> libretto) {
		super();
		this.libretto = libretto;
	}

	//
	public HashMap<String, Corso> getLibretto() {
		return libretto;
	}
	

	public void aggiungiCorso(Corso corso, String valoreID) {
		libretto.put(valoreID, corso);
	}
	
	//
	public double calcolaMedia() {
		int dimensione = libretto.size();
		double voti_tot = 0;
		int numero_corsi = 0;
		
		Corso[] corsi = new Corso[dimensione];
		libretto.values().toArray(corsi);
		for (int i=0; i<dimensione; i++) {
			if (corsi[i].getVoto()!=-1) {
				voti_tot = voti_tot + corsi[i].getVoto();
				numero_corsi++;
			}
			
			
		}
		return voti_tot/numero_corsi;
	}
	
	
	public double calcolaMediaPonderata() {
	
		int dimensione = libretto.size();
		int CFU_tot = 0;
		double peso_tot = 0;
		
		Corso[] corsi = new Corso[dimensione];
		libretto.values().toArray(corsi);
		for (int i=0; i<dimensione; i++) {
			if (corsi[i].getVoto()!=-1) {
				CFU_tot = CFU_tot + corsi[i].getCFU();
				peso_tot = peso_tot + corsi[i].peso();	
			}
			
		}
		return peso_tot/CFU_tot;
	}
	
	public Corso getCorso(String iD) {
		return libretto.get(iD);
	}
	
	public void aggiungiVoto(int voto, String iD) {
		Corso corso = getCorso(iD);
		corso.setVoto(voto);

	}
	public void aggiungiLode(boolean lode, String iD) {
		Corso corso = getCorso(iD);
		corso.setLode(lode);

	}

	
	public String toString() {
	    int dimensione = libretto.size();
	    Corso[] corsi = new Corso[dimensione];
	    libretto.values().toArray(corsi);

	    int sizeMax = 0;
	    for (int i = 0; i < dimensione; i++) {
	        if (corsi[i].getNomeCorso().length() > sizeMax)
	            sizeMax = corsi[i].getNomeCorso().length();
	    }

	    StringBuilder stringa = new StringBuilder();

	    for (int anno = 1; anno <= 3; anno++) {
	        for (int i = 0; i < dimensione; i++) {
	            if (corsi[i].getAnno() == anno) {
	                int padding = sizeMax - corsi[i].getNomeCorso().length();
	                for (int x = 0; x < padding; x++) {
	                    stringa.append(" "); // Usare spazi per l'indentazione
	                }
	                stringa.append(corsi[i]).append("\n");
	            }
	        }
	    }

	    return stringa.toString();
	}

	
	
	
	
	
	
	
	public double calcolaMedia(ArrayList<Corso> cors) {
		int dimensione = libretto.size();
		
		double voti_tot = 0;
		int numero_corsi=0;
		
		for (int i=0; i< cors.size(); i++) {
			voti_tot = voti_tot + cors.get(i).getVoto();
			numero_corsi++;
		}

		
		
		Corso[] corsi = new Corso[dimensione];
		libretto.values().toArray(corsi);
		for (int i=0; i<dimensione; i++) {
			if (corsi[i].getVoto()!=-1) {
				voti_tot = voti_tot + corsi[i].getVoto();
				numero_corsi++;
			}
			
			
		}
		return voti_tot/numero_corsi;
	}
	
	
	public double calcolaMediaPonderata(ArrayList<Corso> cors) {
	
		int dimensione = libretto.size();
		
		int CFU_tot = 0;
		double peso_tot = 0;
		
		for (int i=0; i< cors.size(); i++) {
			CFU_tot = CFU_tot + cors.get(i).getCFU();
			peso_tot = peso_tot + cors.get(i).peso();
		}
		

		
		Corso[] corsi = new Corso[dimensione];
		libretto.values().toArray(corsi);
		for (int i=0; i<dimensione; i++) {
			if (corsi[i].getVoto()!=-1) {
				CFU_tot = CFU_tot + corsi[i].getCFU();
				peso_tot = peso_tot + corsi[i].peso();	
			}
			
		}
		return peso_tot/CFU_tot;
	}
	
	public double calcolaMedia110() {	
		return calcolaMediaPonderata()*110/30;
	}
	public double calcolaMedia110(ArrayList<Corso> cors) {
		return calcolaMediaPonderata(cors)*110/30;
	}
	public int lodi () {
		
		int dimensione = libretto.size();
		int lodi = 0;

		
		Corso[] corsi = new Corso[dimensione];
		libretto.values().toArray(corsi);
		for (int i=0; i<dimensione; i++) {
			if (corsi[i].getLode()) {
				lodi++ ;
				this.numeroLodi++;
			}
			
			
		}
		return lodi;
	}
	public int lodi (ArrayList<Corso> cors) {
		
		int dimensione = cors.size();
		int lodi = 0;

		
		for (int i=0; i<dimensione; i++) {
			if (cors.get(i).getLode()) {
				lodi++ ;
				this.numeroLodi++;
			}
			
			
		}
		return lodi;
	}
	
	
	
	
	
	
	


}
