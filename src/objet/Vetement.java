package objet;

public class Vetement extends Protege {
	
	public static String[] NOM_VETEMENT={"Caftan classique (I)","Costum Chevalier(II)","Veste Etoilée(III)"};
	public static String[] TO_STRING={"v","v'","'v'"};
	public static int[] SOLIDITE={4,5,6};
	public static int[] ENCOMBREMENT={3,4,3};
	
    public Vetement(String nom) {
        super(nom);
    }
    public Vetement(int niveau) {
        this(NOM_VETEMENT[niveau]);
        this.setNiveau(niveau);
        this.setSolidite(SOLIDITE[niveau]);
        this.setEncombrement(ENCOMBREMENT[niveau]);
    }
    public static Vetement creerVetement() {
    	int n=Outil.random.nextInt(3);
    	return new Vetement(n);
    }

    public String toString() {
        return TO_STRING[this.getNiveau()];
    }
}
