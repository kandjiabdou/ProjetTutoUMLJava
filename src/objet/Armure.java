package objet;

public class Armure {
    private Casque casque;

    private Vetement vetement;

    private Chaussure chaussure;

    public Armure() {
    	this.casque=null;
    	this.vetement=null;
    	this.chaussure=null;
    }
    public Casque getCasque() {
        return casque;
    }

    public void setCasque(Casque casque) {
        this.casque = casque;
    }

    public Vetement getVetement() {
        return vetement;
    }

    public void setVetement(Vetement vetement) {
        this.vetement = vetement;
    }

    public Chaussure getChaussure() {
        return chaussure;
    }

    public void setChaussure(Chaussure chaussure) {
        this.chaussure = chaussure;
    }

    public int getSolidite() {
    	int s=0;
    	s+= this.getCasque()==null ? 0 : this.getCasque().getSolidite();
    	s+= this.getVetement()==null ? 0 : this.getVetement().getSolidite();
    	s+= this.getChaussure()==null ? 0 : this.getChaussure().getSolidite();
    	return s/3;
    }
    public int getEncombrement() {
    	int e=0;
    	e+= this.getCasque()==null ? 0 : this.getCasque().getEncombrement();
    	e+= this.getVetement()==null ? 0 : this.getVetement().getEncombrement();
    	e+= this.getChaussure()==null ? 0 : this.getChaussure().getEncombrement();
    	return e/3;
    }
    public void infoArmure() {
    	String i="";
    	i+="Armure\n"
    			+ "\tCasque    : "+this.getCasque()+"\n";
    	i+="\tVetement  : "+this.getVetement()+"\n";
    	i+="\tChaussure : "+this.getChaussure();
    	i+="\tEncmB : "+this.getEncombrement()+", Sold : "+this.getSolidite();
    	System.out.println(i);
    	
    }
    
    public String armureEquipe() {
    	String ca= this.getCasque()==null ?"aucaun":this.getCasque().getNom();
    	String v=this.getVetement()==null ?"aucaun":this.getVetement().getNom();
    	String ch=this.getChaussure()==null ?"aucaun":this.getChaussure().getNom();
    	return ca+"/"+v+"/"+ch;
    }


}
