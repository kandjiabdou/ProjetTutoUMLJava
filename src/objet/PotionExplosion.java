package objet;

import carte.Carte;
import personnage.Personnage;

public class PotionExplosion extends Arme {

	public static String[] NOM_EPEE={"Epee traditionnel (I)","Epee classique(II)","Epee Legendaire(III)"};
	public static String[] TO_STRING={"x","x","x"};
	public static int[] DEAGAT={3,4,7};
	public static int[] MANIABILITE={3,3,2};
	public static int[] PORTEE={1,2,3};
	
    public PotionExplosion(Carte carte,String nom) {
        super(carte,nom);
    }
    public PotionExplosion(Carte carte, int niveau) {
        this(carte,NOM_EPEE[niveau]);
        this.setNiveau(niveau);
        this.setDegat(DEAGAT[niveau]);
        this.setManiabilite(MANIABILITE[niveau]);
        this.setPortee(PORTEE[niveau]);
    }
    public static PotionExplosion creerPotionExplosion(Carte c) {
    	int n=Arme.random.nextInt(3);
    	return new PotionExplosion(c,n);
    }

    public String toString() {
        return TO_STRING[this.getNiveau()];
    }

    public int faireAttaque(int y, int x, int a,int d,String sens,int exp) {

        int j=y;
        int i=x;
        int distance=0;
        System.out.println("Je fais un attaque avec un fusil ");
        System.out.println("Position : y : "+j+", x : "+i+", face "+sens);
        do {
            switch (sens) {
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
            distance++;
            }while((carte.estVide(j, i) || carte.estOutil(j, i)) && distance<this.getPortee());        
        Objet o= carte.getObjet(j, i);
        System.out.println("Position impacte y : "+j+", x : "+i+"| objet : "+o);
        if(o instanceof Personnage) {
        	int e=((Personnage) o).getEsquive();
        	System.out.println("J'attaque un personnage avec "+a+" | il esquive "+e);
        	if(e<a) {
        		//on procede à la determination des blessure
        		int blessure= d-((Personnage) o).getDefense();
        		System.out.println("\tDétermination de blessure qui est de "+blessure);
        		if(blessure>0) {
        			// on inflige les degats
        			System.out.println("\t\tInflige des degats"+blessure);
        			((Personnage) o).setNiveauVie(((Personnage) o).getNiveauVie()-blessure);
        			if(((Personnage) o).getNiveauVie()<15 && ((Personnage) o).getNiveauVie()>0) {
        				// le personnage meurt
        			}
        			if(((Personnage) o).getNiveauVie()<0) {
        				// le personnage meurt
        				System.out.println("\t\t\tle personnage est meurt !!!!!");
        				int exper= (((Personnage) o).getExperiance()/exp);
        				((Personnage) o).mourir();
        				return exper;
        			}
        			System.out.println("\t\tFin Inflige des degats ......");
        		}
        		System.out.println("\tFin détermination de blessure ..........");
        	}
        	System.out.println("Fin attaque ................");
        }
		return 0;
    }
}
