package personnage;

import java.util.Random;

import java.util.TimerTask;
import java.util.Timer;

import carte.*;
import objet.*;
public class PersonnageJoueur extends PersonnageNonJoueur {
// Attributs
    private int pointAction=16;
    private Timer tempsPA;
    
    private Sac sac= new Sac(15) ;
    private Random aleatoir=new Random();
// Constructeurs
    public PersonnageJoueur(Carte carte,String nom) {
        super(carte,nom);
        super.setAdresse(4);
        super.setForce(8);
        super.setResistance(6);
        super.setExperiance(10);
        tempsPA = new Timer();
		tempsPA.schedule(new TimerTask() {
						public void run() {
							pointAction+=5;
							//System.out.println("Point d'action du :"+pointAction);
						}
		},1, 5000);
    }
 // Methodes des personnage
    public void deplacer(int s) {
    	if (this.pointAction>=2) {
    		String sens = null;
        	switch (s) {
        	case 1:
        		sens="h";
        		this.pointAction-=2;			
        		break;
        	case 2:
        		sens="b";
        		this.pointAction-=2;
        		break;
        	case 3:
        		sens="d";
        		this.pointAction-=2;
        		break;
        	case 4:
        		sens="g";
        		this.pointAction-=2;
        		break;
        	}
        	super.deplacer(sens);
        }
    }

    public void attaquer(int cote) {
    	// si les points d'actions ne suff
    	// Si le cote d'attaque est 1 on attaque avec la main droite sinon avec la gauche
    	Outil outil = cote==1 ? this.getMainDroite() : (Outil) this.getMainGauche();
    	// Si cette main pocéde un outil ou il n'a pas de point
    	if(this.pointAction>=3) {
    		// les point d'action sont réduits
    		this.pointAction-=3;
    		if(outil!=null && (outil instanceof Arme)) {//Attaque avec la main qui a l'outil
    			int exp=((Arme) outil).faireAttaque(this.getPositionYL(), this.getPositionXl(), this.getAttaque(),this.getDegat(),this.getDirectionFace(),this.getExperiance());
    			this.setExperiance(this.getExperiance()+exp);
    		}else {// Sinon il attaque avec la main
        		// mais le dégat peut etre lié à l'expériance !!
        		int j=this.getPositionYL();
                int i=this.getPositionXl();
                System.out.println("Je fais un attaque avec la main ");
                System.out.println("Position : y : "+j+", x : "+i+", face "+this.getDirectionFace());
                switch (this.getDirectionFace()) {
	                case "h":
	                    j--;
	                    break;
	                case "b":
	                    j++;
	                    break;
	                case "d":
	                    i++;
	                    break;
	                case "g":
	                    i--;
	                    break;
	                default:
	                    break;
                }
                
                Objet o= carte.getObjet(j, i);
                System.out.println("Position impacte y : "+j+", x : "+i+"| objet : "+o.getClass());
                if(o instanceof Personnage) {
                	int exp=super.attaquer((Personnage)o, this.getExperiance());
                	this.setExperiance(this.getExperiance()+exp);
                }
        	}
    	}
    }

    public void utiliser(int cote) {
    	Outil outil = cote==1 ? this.getMainDroite() : (Outil) this.getMainGauche();
    	if(outil instanceof PotionDeSoin) {
    		if(!((PotionDeSoin) outil).estVide()) {
    			System.out.println("J'utilise le potion ");
    			this.setNiveauVie(this.getNiveauVie()+((PotionDeSoin) outil).utiliser());
    		}else {
    			System.out.println("le potion potion est vide");
    			if(cote==1)this.setMainDroite(null);
    			else this.setManGauche(null);
    		}
    	}
    }
    
    public void ramasserSac() {
    	if(this.getSaveOjet()!=null && this.pointAction>=2) {
    		//System.out.println("j'ajoute l'objet dans le sac");
    		sac.ajouter(this.getSaveOjet());
    		this.setSaveOjet(null);
    		this.pointAction-=2;
    		System.out.println(infoSac());
    	}
    }
    public void deposerSac(int i) {
    	if(this.getSaveOjet()==null && this.pointAction>=2 && i<sac.getCapacite()) {
    		//System.out.println("Je dépose l'objet 1 du sac");
    		this.setSaveOjet(sac.enlever(i));
    		this.pointAction-=2;
    	}
    }
    public void equiper(int i) {
    	if(i<sac.getCapacite() && sac.getNombreOutil()>0) {
    		//Outil outilSave;
    		Outil o=sac.enlever(i); // l'outil est enlevé du sac
    		System.out.println("Pour equiper je dois en le l'outil "+i+" : "+o+" du sac");
    		if(o instanceof Arme) { 
    			sac.ajouter(this.getMainDroite());// ce qui est sur la main droite est dans le sac
    			this.setMainDroite((Arme) o);// si c'est une arme on mets sur la main droite
    		}else if(o instanceof PotionDeSoin) { 
    			sac.ajouter(this.getMainDroite());// ce qui est sur la main droite est dans le sac
    			this.setMainDroite((PotionDeSoin) o);// si c'est une arme on mets sur la main droite
    		}else if(o instanceof Vetement) {
    			sac.ajouter(this.getVetement());// On mets mets ses vetement dans le sac 
    			this.setVetement((Vetement)o);// le vetement qui était dans le sac est équipé 
    		}else if(o instanceof Casque) { // pareil !!!
    			sac.ajouter((Casque)o);
    			this.setCasque((Casque)o);
    		}else if(o instanceof Chaussure) {
    			sac.ajouter((Chaussure)o);
    			this.setChaussure((Chaussure)o);
    		}
    	}else if(sac.getNombreOutil()==0 && this.getMainDroite()!=null) {
    		sac.ajouter(this.getMainDroite());
    		this.setMainDroite(null);
    	}
    	System.out.println(infoSac());
    }
    
    public String infoCapacite(){
    	return infoInitiative()+"/"+infoAttaque()+"/"+infoEsquive()+"/"+infoDefense()+"/"+infoDegat();
    } 
    public String infoCaracteristique(){
    	return this.infoFRC()+"/"+infoADR()+"/"+infoRST();
    } 
    public String infoEtat() {
    	String i="";
    	i+=this.getPointAction()+"/"+this.getNiveauVie()+"/"+this.getExperiance();
    	return i;
    }
    public String infoMain() {
    	String i="";
    	i+= this.getMainDroite()==null? "aucun/":this.getMainDroite().getNom()+"/";
    	i+=this.getMainGauche()==null? "aucun":this.getMainGauche().getNom();
    	return i;
    }
    public String infoSac() {
    	
		return sac.infoSac();
    }

    public void finir() {
    }

// Getters
    
    public int getPointAction() {
		return pointAction;
	}

	public void setPointAction(int pointAction) {
		this.pointAction = pointAction;
	}

	public Sac getSac() {
		return sac;
	}

	public void setSac(Sac sac) {
		this.sac = sac;
	}
    
	public int getInitiative() {
		int init=0;
    	int nombreD=((this.getAdresse()/3)-this.getEncombrement()/3);
    	
        int	bonus=((this.getAdresse()%3)-this.getEncombrement()%3);
		for(int j=0;j<nombreD;j++) {
			int al=aleatoir.nextInt(6)+1;
			init+=al;
		}
		return (init+bonus);
	}

	public int getAttaque() {
		int atak=0;
    	int nombreD,bonus;
    	if(this.getMainDroite()==null || (this.getMainDroite() instanceof PotionDeSoin)){
    		nombreD=this.getAdresse()/3;
        	bonus=this.getAdresse()%3;
    	}else {
    		nombreD=((this.getAdresse()/3)+((Arme)this.getMainDroite()).getManiabilite()/3);
        	bonus=((this.getAdresse()%3)+((Arme)this.getMainDroite()).getManiabilite()%3);
    	}
		for(int j=0;j<nombreD;j++) {
			int al=aleatoir.nextInt(6)+1;
			atak+=al;
		}
		return (atak+bonus);
	}
	
	public int getEsquive() {
		int eskiv=0;
    	int nombreD=((this.getAdresse()/3)-this.getEncombrement()/3);
        int	bonus=((this.getAdresse()%3)-this.getEncombrement()%3);
		for(int j=0;j<nombreD;j++) {
			int al=aleatoir.nextInt(6)+1;
			eskiv+=al;
		}
		return (eskiv+bonus);
	}

	public int getDefense() {
		int def=0;
    	int nombreD=((this.getResistance()/3)+this.getSolidite()/3);
        int	bonus=((this.getResistance()%3)+this.getSolidite()%3);
		for(int j=0;j<nombreD;j++) {
			int al=aleatoir.nextInt(6)+1;
			def+=al;
		}
		return (def+bonus);
	}

	public int getDegat() {
		int dgt=0;
    	int nombreD,bonus;
    	if(this.getMainDroite()==null) {
    		nombreD=this.getForce()/3;
        	bonus=this.getForce()%3;
    	}else {
    		nombreD=((this.getForce()/3)+((Arme)this.getMainDroite()).getDegat()/3);
        	bonus=((this.getForce()%3)+((Arme)this.getMainDroite()).getDegat()%3);
    	}
		for(int j=0;j<nombreD;j++) {
			int al=aleatoir.nextInt(6)+1;
			dgt+=al;
			System.out.print(al+"("+j+"),");
		}
		return (dgt+bonus);
	}
		
    
    public String infoInitiative() {
    	String a="Initiative : ";
    	int ecmbrmt=0;
    	if (this.getMainDroite()!=null && (this.getMainDroite() instanceof Bouclier)) {
    		ecmbrmt+=  ((Bouclier)this.getMainDroite()).getEncombrement();
    	}
    	if (this.getMainGauche()!=null && (this.getMainGauche() instanceof Bouclier) ) {
    		ecmbrmt+= ((Bouclier)this.getMainGauche()).getEncombrement();
    	}
    	int d=((this.getAdresse()/3)-(this.getEncombrement()+ecmbrmt)/3);
    	int i=((this.getAdresse()%3)-(this.getEncombrement()+ecmbrmt)%3);
    	
    	a+= d==0 ? "" : String.valueOf(d)+"D";
    	a+= i==0 ? "" : "+"+String.valueOf(i);
    	return a;
    }
    public String infoAttaque() {
    	String a="Attaque : ";
    	int d,i;
    	if(this.getMainDroite()==null || (this.getMainDroite() instanceof PotionDeSoin)) {
    		d=this.getAdresse()/3;
        	i=this.getAdresse()%3;
    	}else {
    		d=((this.getAdresse()/3)+((Arme)this.getMainDroite()).getManiabilite()/3);
        	i=((this.getAdresse()%3)+((Arme)this.getMainDroite()).getManiabilite()%3);
    	}
    	a+= d==0 ? "": String.valueOf(d)+"D";
    	a+= i==0 ? "" : "+"+String.valueOf(i);
    	return a;
    }
    public String infoEsquive() {
    	String a="Esquive : ";
    	int ecmbrmt=0;
    	if (this.getMainDroite()!=null )ecmbrmt+= (this.getMainDroite() instanceof Protegeable) ? ((Bouclier)this.getMainDroite()).getEncombrement():0;
    	if (this.getMainGauche()!=null )ecmbrmt+= (this.getMainGauche() instanceof Protegeable) ? ((Bouclier)this.getMainGauche()).getEncombrement():0;
    	int d=((this.getAdresse()/3)-(this.getEncombrement()+ecmbrmt)/3);
    	int i=((this.getAdresse()%3)-(this.getEncombrement()+ecmbrmt)%3);
    	a+= d==0 ? "": String.valueOf(d)+"D";
    	a+= i==0 ? "" : "+"+String.valueOf(i);
    	return a;
    }
    public String infoDefense() {
    	String a="Defense :";
    	int sldt=0;
    	if (this.getMainDroite()!=null )sldt+= (this.getMainDroite() instanceof Protegeable) ? ((Bouclier)this.getMainDroite()).getSolidite():0;
    	if (this.getMainGauche()!=null )sldt+= (this.getMainGauche() instanceof Protegeable) ? ((Bouclier)this.getMainGauche()).getSolidite():0;
    	int d=((this.getResistance()/3)+(this.getSolidite()+sldt)/3);
    	int i=((this.getResistance()%3)+(this.getSolidite()+sldt)%3);
    	a+= d==0 ? "": String.valueOf(d)+"D";
    	a+= i==0 ? "" : "+"+String.valueOf(i);
    	return a;
    }
    public String infoDegat() {
    	String a="Degat : ";
    	int d,i;
    	if(this.getMainDroite()==null || (this.getMainDroite() instanceof PotionDeSoin)) {
    		d=(this.getForce()/3);
        	i=(this.getForce()%3);
    	}else {
    		d=((this.getForce()/3)+((Arme)this.getMainDroite()).getDegat()/3);
        	i=((this.getForce()%3)+((Arme)this.getMainDroite()).getDegat()%3);
    	}
    	a+= d==0 ? "": String.valueOf(d)+"D";
    	a+= i==0 ? "" : "+"+String.valueOf(i);
    	return a;
    }

    public String toString() {
    	return this.getNom();
    }

	public void enchangerMain() {
		super.setSaveOjet2(this.getMainDroite());
		this.setMainDroite(this.getMainGauche());
		this.setManGauche(super.getSaveOjet2());
	}

	public void mourir() {
		super.mourir();
		this.carte.casePlateau[this.getPositionYL()][this.getPositionXl()]=this.getMainDroite()!=null ? this.getMainDroite():new Vide();
	}
	
}
