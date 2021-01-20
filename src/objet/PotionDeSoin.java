package objet;


public class PotionDeSoin extends Outil {
	private int doseSoin;
	private int nombreUtilisation;
	
	public static String[] NOM_PTION={"Potion S (I)","Potion M (II)","Potion Legend(III)"};
	public static String[] TO_STRING={"p","p","p"};
	public static int[] DOSE={5,8,10};
	public static int[] NOMBRE_UTILISATION={3,5,6};
    public PotionDeSoin(String nom) {
        super(nom);
    }
    public PotionDeSoin(int niveau) {
        this(NOM_PTION[niveau]);
        this.setNiveau(niveau);
        this.setDoseSoin(DOSE[niveau]);
        this.setNombreUtilisation(DOSE[niveau]);
    }
    public static PotionDeSoin creerPotionDeSoin() {
    	int n=Outil.random.nextInt(3);
    	return new PotionDeSoin(n);
    }

    public String toString() {
        return TO_STRING[this.getNiveau()];
    }
	public int getDoseSoin() {
		return doseSoin;
	}
	
	public void setDoseSoin(int doseSoin) {
		this.doseSoin = doseSoin;
	}
	/**
	 * @return the nombreUtilisation
	 */
	public int getNombreUtilisation() {
		return nombreUtilisation;
	}
	/**
	 * @param nombreUtilisation the nombreUtilisation to set
	 */
	public void setNombreUtilisation(int nombreUtilisation) {
		this.nombreUtilisation = nombreUtilisation;
	}
	
	public int utiliser() {
		if(!estVide()) {
			this.nombreUtilisation--;
			System.out.println("Dose du potion "+this.getDoseSoin());
			return this.getDoseSoin();
		}else {
			System.out.println("Dose du potion 0");
			return 0;
			}
	}
	
	public boolean estVide() {
		return this.nombreUtilisation==0;
	}
	
	

}
