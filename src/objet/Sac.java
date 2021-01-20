package objet;

public class Sac {
    private int capacite;

    public Outil[] outils;

    public Sac(int c) {
        this.capacite=c;
        this.outils= new Outil[c];
    }

    public int getCapacite() {
        return capacite;
    }
    
    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }
    public int getNombreOutil() {
    	int compteur=0;
    	for(Outil o : outils) {
    		if(o!=null) compteur++;
    	}
		return compteur;
	}
	
    public boolean ajouter(Outil outil) {
    	boolean ajoutee=false;
		for(int i=0;i<capacite && !ajoutee;i++) {
			if(outils[i]==null) { 
				outils[i]=outil;
				//System.out.println("Moi sac j'ai ajouté l'objet : "+!ajoutee);
				ajoutee=true;
			}
		}
		//System.out.println("Moi sac je retoune : "+ajoutee);
    	return ajoutee;
    }
    public Outil enlever(int i) {
    	if (i<capacite && outils[i]!=null) {
    		Outil o=outils[i];
    		outils[i]=null;
    		boolean jArretPas=true;
    		for(int j=i+1;j<(this.getCapacite()-1) && jArretPas;j++) {
    			if (outils[j]==null) {
    				jArretPas=false;
    			}else {
    				outils[j-1]=outils[j];
    				outils[j]=null;
    			}
    		}
    		return o;
    	}else return null;
    }
    
    public String infoSac() {
    	String inf="/"+getNombreOutil();
    	
    	for(int i=0;i<getNombreOutil();i++) {
			if(outils[i]!=null) {
				inf+="/ "+outils[i].getNom();
			}
		}
    	System.out.print("Le nombre d'outil "+getNombreOutil());
		return inf;
    }
}
