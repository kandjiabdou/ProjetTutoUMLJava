package serveur;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Client2 extends JFrame implements Runnable{
   // partie donné
	private static Client moi;
   private Socket connexion = null;
   private PrintWriter writer = null;
   private BufferedReader reader = null;
   private String host;
   private int port;
   private String msg;
   // partie IHM

   private static final long serialVersionUID = 1L;
   
   // ###############################
   // #### Information du joueur  ###
   // ###############################
   
   private JPanel panPrincipal ;
   private JButton rejoindre;
   
   JTextField idConnect ;
   JPasswordField mdpConnect;
   JTextField nomInscrit ;
   JTextField idInscrit ;
   JPasswordField mdpInscrit;
   
   // Niveau
   private JLabel labelPA;
   private JLabel labelVie;
   private JLabel labelExp;
   // Caracteristques
   private JLabel labelADR;
   private JLabel labelFRC;
   private JLabel labelRST;
   // Capacité
   private JLabel labelInitiative;
   private JLabel labelAttaque;
   private JLabel labelDefense;
   private JLabel labelEsquive;
   private JLabel labelDegat;
   
   private JLabel labelADR1;
   private JLabel labelFRC1;
   private JLabel labelRST1;
   
   // Item et équipements
   
   private JLabel labelMainDroite;
   private JLabel labelMainGauche;
   
   private JLabel labelVetement;
   private JLabel labelCasque;
   private JLabel labelChaussure;
   
   private JLabel labelSac;
   private JComboBox<String> itemSac= new JComboBox<String>();
   private String[] itemSacString= {"1","2","3","4","5","6","","","","","","","","",""};
   
   //Boutons
   private JButton haut;
   private JButton bas;
   private JButton droite;
   private JButton gauche;
   
   private JButton ramasser;
   private JButton deposer;
   private JButton attaquerG;
   private JButton attaquerD;
   private JButton utiliserD;
   private JButton utiliserG;
   
   private JButton equiper;
   private JButton echangerMain;
	
   private JButton boutonEnvoyer;

   // Entré text à envoyer
   public JTextField envoyerTexte = new JTextField(5);
   // Vue carte en format txt
	private JTextArea caseCarte;
	// Vue converversation en txt
	private JTextArea conversation= new JTextArea("");
			
	static final int BOUTON_HAUT = 1;
	static final int BOUTON_BAS = 2;
	static final int BOUTON_DROITE = 3;
	static final int BOUTON_GAUCHE = 4;
	
	static final int BOUTON_RAMASSER = 5;
	static final int BOUTON_DEPOSER= 6;
	
	static final int BOUTON_ATTAQUER_D = 7;
	static final int BOUTON_ATTAQUER_G = 8;
	
	static final int BOUTON_UTILISER_D = 9;
	static final int BOUTON_UTILISER_G = 10;
	
	static final int BOUTON_ENVOYER = 11;
	
	static final int BOUTON_EQUIPER = 12;
	static final int BOUTON_ECHANGER = 13;
	static final int BOUTON_REJOINDRE = 14;
	
	private static final String ATTAQUER = "attaquer";
	private static final String UTILISER = "utiliser";
	private static final String RAMASSER = "ramasser";
	private static final String DEPOSER = "deposer";
	private static final String HAUT = "haut";
	private static final String BAS = "bas";
	private static final String DROITE = "droite";
	private static final String GAUCHE = "gauche";
	//private static final String MESSAGE_CLOSE = "close";
	private static final String MESSAGE_CONVERSATION = "message";
	private static final String MESSAGE_CARTE = "catre";
	private static final String MESSAGE_PA = "pa";
	private static final String MESSAGE_VIE = "vie";
	private static final String MESSAGE_ARMURE = "armure";
	private static final String MESSAGE_DEPLACEMENT = "deplacement";
	private static final String MESSAGE_ACTION = "action";
	private static final String MESSAGE_MAIN = "main";
	private static final String MESSAGE_CARACTERISTIQUE = "caracteristique";
	private static final String MESSAGE_CAPACITE = "capacite";
	private static final String EQUIPER = "equiper";
	private static final String ECHANGER = "echanger";
	private JTextField textField;
	
	
	public Client2(String host, int port) {
		super("Jeu MMORPG");
		this.fentreDemarrage();
		this.initEcouteursBienvenu();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(700,500);
		this.port=port;
		this.host=host;
		this.setVisible(true);
	}
	public void run(){
		   Thread Lecteur = new Thread(new Lecteur());
		   Lecteur.start();
		   System.out.println("Lecteur créé .......................");
		   System.out.println(" Envoyeur créé .......................");
	   }
	public static void main(String[] args) {
		moi = new Client("localhost", 63334);	       		       
	}
	   public class Lecteur implements Runnable{
			public void run() {
				try {	    
					connexion = new Socket(host, port);
					reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
					writer = new PrintWriter(connexion.getOutputStream(), true);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					rejoindre.setVisible(false);
					fenetreJeu();
					initEcouteurs();
					// au début on recoit un message de bienvenu en 4 flush du serveur
					String reponse;
					for(int i=0; i<4;i++) {
						reponse = reader.readLine();
						//System.out.println(reponse);
						conversation.setText(conversation.getText()+reponse+"\n");
					}
					reponse = reader.readLine();
					caseCarte.setText(reponse);
					for(int i=0; i<21;i++) {
						reponse = reader.readLine();
						caseCarte.setText(caseCarte.getText()+"\n"+reponse);
					}
					//On attend la réponse
					
					while(true) {
						//TOUJOURS UTILISER flush() POUR ENVOYER RÉELLEMENT DES INFOS AU SERVEUR
						reponse = reader.readLine();
						//System.out.println("la réponse du server est : "+reponse);
						if(reponse.length()>0) {
							//System.out.println(reponse);
							try {
								@SuppressWarnings("resource")
								Scanner sc = new Scanner(reponse);
								sc.useDelimiter("/");
								String r = sc.next();
								switch(r) {
									case MESSAGE_CONVERSATION :{
										conversation.setText(conversation.getText()+"\n\tJoueur2 : "+sc.next());
										break;
									}
									case MESSAGE_CARTE :{
										caseCarte.setText(sc.next());
										for(int i=0;i<22;i++) {
											caseCarte.setText(caseCarte.getText()+"\n"+reader.readLine());
										}
										break;
									}
									case MESSAGE_PA :{
										labelPA.setText("Point d'action : "+sc.next());
										break;
									}
									case MESSAGE_VIE :{
										labelVie.setText("Niveau de Vie : "+sc.next());
										break;
									}
									case MESSAGE_MAIN :{
										labelMainDroite.setText(sc.next());
										labelMainGauche.setText(sc.next());
										break;
									}
									case MESSAGE_ARMURE :{
										labelCasque.setText(sc.next());
										labelVetement.setText(sc.next());
										labelChaussure.setText(sc.next());
										break;
									}
									case MESSAGE_CARACTERISTIQUE :{
										labelFRC.setText(sc.next());
										labelRST.setText(sc.next());
										labelADR.setText(sc.next());
										break;
									}
									case MESSAGE_CAPACITE :{
										labelInitiative.setText(sc.next());
										labelAttaque.setText(sc.next());
										labelEsquive.setText(sc.next());
										labelDefense.setText(sc.next());
										labelDegat.setText(sc.next());
										break;
									}
								}
							}catch(NullPointerException e) {
								e.printStackTrace();
								//System.out.println(reponse);
							}
						}
					}	
				}catch (ConnectException e) {
					   System.out.println("Le serveur n'est pas ouvert !!");
					   String message="Le serveur n'est pas ouvert !!";
					   JOptionPane.showMessageDialog(new JFrame(), message, "Serveur",JOptionPane.ERROR_MESSAGE);
			      } catch (IOException e) {
					   System.out.println("IO exp");
			      }
			}
	   	}
	   private void fentreDemarrage() {
			panPrincipal = new JPanel();	
			panPrincipal.setBackground(Color.LIGHT_GRAY);
			panPrincipal.setBorder(BorderFactory.createEtchedBorder());
			JLabel lblJeuEthmmorpg = new JLabel("Jeu EHLPTMMMORPGSVR");
			lblJeuEthmmorpg.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 20));
			lblJeuEthmmorpg.setLabelFor(this);
			getContentPane().add(panPrincipal, BorderLayout.NORTH);
			JLabel lblSelectionnerEtCliquer = new JLabel("Selectionner et cliquer ici pour rejoindre le serveur");
			lblSelectionnerEtCliquer.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
			
			JPanel panel = new JPanel();
			
			JPanel panel_1 = new JPanel();
			rejoindre =new JButton("Rejoindre");
			GroupLayout gl_panPrincipal = new GroupLayout(panPrincipal);
			gl_panPrincipal.setHorizontalGroup(
				gl_panPrincipal.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_panPrincipal.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panPrincipal.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panPrincipal.createSequentialGroup()
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
								.addContainerGap())
							.addGroup(Alignment.TRAILING, gl_panPrincipal.createSequentialGroup()
								.addComponent(rejoindre)
								.addGap(269))
							.addGroup(Alignment.TRAILING, gl_panPrincipal.createSequentialGroup()
								.addComponent(lblJeuEthmmorpg, GroupLayout.PREFERRED_SIZE, 421, GroupLayout.PREFERRED_SIZE)
								.addGap(29))
							.addGroup(Alignment.TRAILING, gl_panPrincipal.createSequentialGroup()
								.addComponent(lblSelectionnerEtCliquer)
								.addGap(122))))
			);
			gl_panPrincipal.setVerticalGroup(
				gl_panPrincipal.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_panPrincipal.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblJeuEthmmorpg, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
						.addGap(24)
						.addGroup(gl_panPrincipal.createParallelGroup(Alignment.LEADING)
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblSelectionnerEtCliquer)
						.addGap(11)
						.addComponent(rejoindre)
						.addGap(71))
			);
			panel_1.setLayout(null);
			
			JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Selectionner cette pour charger une partie et rejoindre");
			rdbtnNewRadioButton_1.setBounds(6, 179, 288, 23);
			panel_1.add(rdbtnNewRadioButton_1);
			
			JLabel lblNewLabel_1 = new JLabel("Charger une partie");
			lblNewLabel_1.setBounds(94, 11, 91, 14);
			panel_1.add(lblNewLabel_1);
			
			JLabel lblNewLabel_6 = new JLabel("aucune partie sauvegardée");
			lblNewLabel_6.setBounds(76, 69, 148, 33);
			panel_1.add(lblNewLabel_6);
			panel.setLayout(null);
			
			JLabel lblNewLabel_2 = new JLabel("Nom personnage");
			lblNewLabel_2.setBounds(29, 80, 91, 17);
			panel.add(lblNewLabel_2);
			
			JLabel lblNewLabel = new JLabel("Creer un personnage");
			lblNewLabel.setBounds(125, 5, 102, 14);
			panel.add(lblNewLabel);
			
			textField = new JTextField();
			textField.setBounds(171, 71, 114, 20);
			panel.add(textField);
			textField.setColumns(10);
			
			JLabel lblNewLabel_3 = new JLabel("Force");
			lblNewLabel_3.setBounds(29, 108, 46, 14);
			panel.add(lblNewLabel_3);
			
			JLabel lblNewLabel_4 = new JLabel("Adresse");
			lblNewLabel_4.setBounds(29, 133, 46, 14);
			panel.add(lblNewLabel_4);
			
			JLabel lblNewLabel_5 = new JLabel("Resistance");
			lblNewLabel_5.setBounds(29, 158, 70, 14);
			panel.add(lblNewLabel_5);
			
			JRadioButton rdbtnNewRadioButton = new JRadioButton("Selectionner cette case pour creer un personnage et rejoindre");
			rdbtnNewRadioButton.setBounds(6, 179, 338, 23);
			panel.add(rdbtnNewRadioButton);
			
			JSpinner spinner = new JSpinner();
			spinner.setModel(new SpinnerNumberModel(1, 1, 16, 1));
			spinner.setBounds(171, 102, 114, 20);
			panel.add(spinner);
			
			JSpinner spinner_1 = new JSpinner();
			spinner_1.setModel(new SpinnerNumberModel(1, 1, 16, 1));
			spinner_1.setBounds(171, 130, 114, 20);
			panel.add(spinner_1);
			
			JSpinner spinner_2 = new JSpinner();
			spinner_2.setModel(new SpinnerNumberModel(1, 1, 16, 1));
			spinner_2.setBounds(171, 155, 114, 20);
			panel.add(spinner_2);
			
			JLabel lblNewLabel_7 = new JLabel("Attribuer 18 degrés à répartir entre les trois caractéristiques");
			lblNewLabel_7.setBounds(29, 33, 294, 17);
			panel.add(lblNewLabel_7);
			panPrincipal.setLayout(gl_panPrincipal);
		}
	private void fenetreJeu() {
		// le panneau est constituée de trois onglets le JEU, les MESSAGE et SAVE QUITTER
		panPrincipal = new JPanel(new BorderLayout());
		panPrincipal.setBorder(BorderFactory.createEtchedBorder());
		
		JTabbedPane tabOnglet= new JTabbedPane();
		tabOnglet.addTab("JEU", initJeu());
		tabOnglet.addTab("MESSAGE",panConversaton() );
		tabOnglet.addTab("SAUVEGARDER/ QUITTER", new JPanel());
		panPrincipal.add(tabOnglet, BorderLayout.CENTER);
		
		getContentPane().add(panPrincipal);
	}
	
	private JPanel initJeu() {
		JPanel panPrincipalJEU = new JPanel(new BorderLayout());
		panPrincipalJEU.add(panInfoPNJ(),BorderLayout.SOUTH);
		panPrincipalJEU.add(panInfoJoueur(),BorderLayout.NORTH);
		panPrincipalJEU.add(panCarte(),BorderLayout.CENTER);
		panPrincipalJEU.add(panAction(),BorderLayout.EAST);
		panPrincipalJEU.add(panItems(),BorderLayout.WEST);
		return panPrincipalJEU;
	}
	public void initEcouteursBienvenu() {
		this.rejoindre.addActionListener(new EcouteurBoutons(BOUTON_REJOINDRE));
	}
	public void initEcouteurs() {
		this.haut.addActionListener(new EcouteurBoutons(BOUTON_HAUT));
		this.bas.addActionListener(new EcouteurBoutons(BOUTON_BAS));
		this.droite.addActionListener(new EcouteurBoutons(BOUTON_DROITE));
		this.gauche.addActionListener(new EcouteurBoutons(BOUTON_GAUCHE));
		this.ramasser.addActionListener(new EcouteurBoutons(BOUTON_RAMASSER));
		this.equiper.addActionListener(new EcouteurBoutons(BOUTON_EQUIPER));
		this.echangerMain.addActionListener(new EcouteurBoutons(BOUTON_ECHANGER));
		this.deposer.addActionListener(new EcouteurBoutons(BOUTON_DEPOSER));
		this.attaquerD.addActionListener(new EcouteurBoutons(BOUTON_ATTAQUER_D));
		this.attaquerG.addActionListener(new EcouteurBoutons(BOUTON_ATTAQUER_G));
		this.utiliserD.addActionListener(new EcouteurBoutons(BOUTON_UTILISER_D));
		this.utiliserG.addActionListener(new EcouteurBoutons(BOUTON_UTILISER_G));
		this.boutonEnvoyer.addActionListener(new EcouteurBoutons(BOUTON_ENVOYER));
		
	}
	
	public JPanel panInfoJoueur() {
		
		JPanel pInfoG=new JPanel(new GridLayout(0,1));
		
		// Niveu et caractéristque
		JPanel pI2=new JPanel(new GridLayout(0,3));
		
		labelPA=new JLabel("Point d'action : ");
		pI2.add(labelPA);
		labelExp=new JLabel("Experiance");
		pI2.add(labelExp);
		labelVie=new JLabel("Niveau de Vie :");
		pI2.add(labelVie);
		
		labelADR=new JLabel("adr");
		pI2.add(labelADR);
		labelFRC=new JLabel("frc");
		pI2.add(labelFRC);
		labelRST=new JLabel("rst");
		pI2.add(labelRST);
		
		// capacité
		JPanel pInfoCap=new JPanel(new GridLayout(1,5));
		labelInitiative=new JLabel("initiative");
		labelAttaque=new JLabel("Attaque");
		labelDefense=new JLabel("Defense");
		labelEsquive=new JLabel("Esquive");
		labelDegat=new JLabel("Degat");
		pInfoCap.add(labelInitiative);
		pInfoCap.add(labelAttaque);
		pInfoCap.add(labelDefense);
		pInfoCap.add(labelEsquive);
		pInfoCap.add(labelDegat);

		pInfoG.add(pI2); //Niveau et caracteristique 
		pInfoG.add(pInfoCap); // capacité
		
		pInfoG.setBorder(BorderFactory.createEtchedBorder());
		pInfoG.setBackground(Color.GREEN);
		return pInfoG;
	}
	
	public JPanel panItems() {
		
		JPanel panItemG = new JPanel(new GridLayout(0,1));
		// main
		
		JPanel panMain = new JPanel(new GridLayout(0,2));
		labelMainDroite=new JLabel("null");labelMainGauche=new JLabel("null");
		panMain.add(new JLabel("Main gauche "));panMain.add(new JLabel("Main droite"));
		panMain.add(labelMainGauche);panMain.add(labelMainDroite);
		echangerMain= new JButton("Echanger ⇄");
		panMain.add(echangerMain);
		panItemG.add(panMain);
		
		panItemG.add(new JLabel("Votre Amure"));
		// il reste les equipements Armure
		JPanel panArmure = new JPanel(new GridLayout(3,2));
		
		panArmure.add(new JLabel("Casque : "));
		labelCasque = new JLabel("null");
		panArmure.add(labelCasque);
//		desequiperCasque= new JButton("X");
//		panArmure.add(desequiperCasque);
		panArmure.add(new JLabel("Vetement : "));
		labelVetement = new JLabel("null");
		panArmure.add(labelVetement);
//		desequiperVetement= new JButton("X");
//		panArmure.add(desequiperVetement);
		panArmure.add(new JLabel("Chaussure : "));
		labelChaussure= new JLabel("null");
		panArmure.add(labelChaussure);
//		desequiperChaussure= new JButton("X");
//		panArmure.add(desequiperChaussure);
		panItemG.add(panArmure);
		
		//le sac du joueur 
		JPanel panSac= new JPanel(new GridLayout(1,2));
		labelSac= new JLabel("Votre SSac");
		itemSac= new JComboBox<String>(itemSacString);
		panSac.add(labelSac);
		panSac.add(itemSac);
		panSac.setBorder(BorderFactory.createEtchedBorder());
		panSac.setVisible(true);
		panItemG.add(panSac);
		
		equiper= new JButton("Equiper/Desquiper");
		panItemG.add(equiper);
		panItemG.setBorder(BorderFactory.createEtchedBorder());
		return panItemG;
	}
	public JPanel panInfoPNJ() {
		JPanel pInfo=new JPanel(new GridLayout(0,3));
		labelADR1=new JLabel("adr1");
		pInfo.add(labelADR1);
		labelFRC1=new JLabel("frc1");
		pInfo.add(labelFRC1);
		labelRST1=new JLabel("rst2");
		pInfo.add(labelRST1);
		
		pInfo.setBorder(BorderFactory.createEtchedBorder());
		return pInfo;
	}
	
	public JPanel panCarte() {
		JPanel pCarte = new JPanel(new BorderLayout());
		
		caseCarte=new JTextArea(50,10);
		caseCarte.setEditable(false);
		
		pCarte.add(caseCarte,BorderLayout.CENTER);
		pCarte.setBorder(BorderFactory.createEtchedBorder());
		return pCarte;
		
	}
	
	public JPanel panConversaton() {
		JPanel pan = new JPanel(new BorderLayout());
		
		JScrollPane panTexteConversation=new JScrollPane();
		conversation = new JTextArea(5,25);
		conversation.setEditable(false);
		panTexteConversation.setViewportView(conversation);
		pan.setBorder(BorderFactory.createEtchedBorder());
		pan.add(panTexteConversation,BorderLayout.CENTER);
		
		JPanel panEnvoyer = new JPanel();
		envoyerTexte= new JTextField(17);
		panEnvoyer.add(envoyerTexte);
		boutonEnvoyer= new JButton("envoyer");
		panEnvoyer.add(boutonEnvoyer);
		panEnvoyer.setBorder(BorderFactory.createEtchedBorder());
		pan.add(panEnvoyer,BorderLayout.SOUTH);
		
		pan.setBorder(BorderFactory.createEtchedBorder()); 
		return pan;
	}
	public JPanel panAction() {
		JPanel pan = new JPanel(new GridLayout(0,1));
		
		JPanel panB = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		haut=new JButton("▲");
		c.fill = GridBagConstraints.PAGE_START;
		c.gridx = 1;
		c.gridy = 0;
		panB.add(haut, c);
		bas = new JButton("▼");
		c.fill = GridBagConstraints.PAGE_END;
		c.gridx = 1;
		c.gridy = 2;
		panB.add(bas, c);
		droite= new JButton("►");
		c.fill = GridBagConstraints.LINE_END;
		c.gridx = 2;
		c.gridy = 1;
		panB.add(droite, c);
		gauche = new JButton("◄");
		c.fill = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 1;
		
		panB.add(gauche, c);
		panB.setBorder(BorderFactory.createEtchedBorder()); 
		
		JPanel panA= new JPanel(new GridLayout(0,2));
		ramasser=new JButton("Ramasser");
		panA.add(ramasser);

		deposer=new JButton("Deposer");
		panA.add(deposer);
		attaquerG=new JButton("Attak G");
		panA.add(attaquerG);
		attaquerD=new JButton("Attak D");
		panA.add(attaquerD);
		utiliserG=new JButton("util G");
		panA.add(utiliserG);
		utiliserD=new JButton("util D");
		panA.add(utiliserD);
		
		pan.add(panA);
		pan.add(panB);
		
		pan.setBorder(BorderFactory.createEtchedBorder()); 
		return pan;
	}
		
		public void centrer(double d) {
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dim = tk.getScreenSize();
			int largeur = (int) (d * dim.width);
			int longueur = (int) (d * dim.height);
			this.setBounds((dim.width - largeur) / 2, (dim.height - longueur) / 2, largeur, longueur);
		}
		class EcouteurBoutons implements ActionListener {
			 
			private int sens;
	 
			public EcouteurBoutons(int sens) {
				this.sens = sens;
			}
	 
			public void actionPerformed(ActionEvent e) {
				switch (sens) {
				case BOUTON_REJOINDRE: {
					System.out.println("Bouton rejoindre " + BOUTON_REJOINDRE);
					Thread t = new Thread(moi);
				    t.start();
					break;
				}
				case BOUTON_HAUT: {
					System.out.println("Bouton HAUT " + BOUTON_HAUT);
				System.out.println("message à envoyer : "+HAUT);
				System.out.println(MESSAGE_DEPLACEMENT+"/"+HAUT);
				writer.println(MESSAGE_DEPLACEMENT+"/"+HAUT);
				writer.flush();
				break;
				}
				case BOUTON_BAS: {
					System.out.println("Bouton BAS " + BOUTON_BAS);
					System.out.println("message à envoyer : "+BAS);
					System.out.println(MESSAGE_DEPLACEMENT+"/"+BAS);
					writer.println(MESSAGE_DEPLACEMENT+"/"+BAS);
					writer.flush();
					break;
				}
				case BOUTON_DROITE:{
					System.out.println("Bouton DROITE " + BOUTON_DROITE);
					System.out.println("message à envoyer : "+DROITE);
					System.out.println(MESSAGE_DEPLACEMENT+"/"+DROITE);
					writer.println(MESSAGE_DEPLACEMENT+"/"+DROITE);
					writer.flush();
					break;
				}case BOUTON_GAUCHE:{
					System.out.println("Bouton GAUCHE " + BOUTON_GAUCHE);
					System.out.println("message à envoyer : "+GAUCHE);
					System.out.println(MESSAGE_DEPLACEMENT+"/"+GAUCHE);
					writer.println(MESSAGE_DEPLACEMENT+"/"+GAUCHE);
					writer.flush();
					break;
					}
				case BOUTON_RAMASSER:{
					System.out.println("Bouton RAMASSER " + BOUTON_RAMASSER);
					System.out.println("message à envoyer : "+RAMASSER);
					writer.println(MESSAGE_ACTION+"/"+RAMASSER);
					writer.flush();
					break;
					}
				
				case BOUTON_DEPOSER:{
					System.out.println("Bouton DEPOSER " + BOUTON_DEPOSER);
					System.out.println("message à envoyer : "+DEPOSER);
					System.out.println(MESSAGE_ACTION+"/"+DEPOSER);
					writer.println(MESSAGE_ACTION+"/"+DEPOSER);
					writer.flush();
					break;
					}
				case BOUTON_EQUIPER:{
					System.out.println("Bouton DEPOSER " + BOUTON_EQUIPER);
					System.out.println("message à envoyer : "+EQUIPER+"/0");
					System.out.println(MESSAGE_ACTION+"/"+EQUIPER);
					writer.println(MESSAGE_ACTION+"/"+EQUIPER+"/0");
					writer.flush();
					break;
					}
				case BOUTON_ECHANGER: {
					System.out.println("Bouton BAS " + BOUTON_ECHANGER);
					System.out.println("message à envoyer : "+ECHANGER);
					System.out.println(MESSAGE_ACTION+"/"+ECHANGER);
					writer.println(MESSAGE_ACTION+"/"+ECHANGER);
					writer.flush();
					break;
				}
				case BOUTON_ATTAQUER_D:{
					System.out.println("Bouton ATTAQUE " + BOUTON_ATTAQUER_D);
					System.out.println("message à envoyer : "+ATTAQUER);
					System.out.println(MESSAGE_ACTION+"/"+ATTAQUER+"/1");
					writer.println(MESSAGE_ACTION+"/"+ATTAQUER+"/1");
					writer.flush();
					break;
					}
				case BOUTON_UTILISER_D:{
					System.out.println("Bouton ATTAQUE " + BOUTON_UTILISER_D);
					System.out.println("message à envoyer : "+UTILISER);
					System.out.println(MESSAGE_ACTION+"/"+UTILISER+"/1");
					writer.println(MESSAGE_ACTION+"/"+UTILISER+"/1");
					writer.flush();
					break;
					}
				case BOUTON_UTILISER_G:{
					System.out.println("Bouton ATTAQUE " + BOUTON_UTILISER_G);
					System.out.println("message à envoyer : "+UTILISER);
					System.out.println(MESSAGE_ACTION+"/"+UTILISER+"/2");
					writer.println(MESSAGE_ACTION+"/"+UTILISER+"/2");
					writer.flush();
					break;
					}
				case BOUTON_ATTAQUER_G:{
					System.out.println("Bouton ATTAQUE " + BOUTON_ATTAQUER_G);
					System.out.println("message à envoyer : "+ATTAQUER);
					System.out.println(MESSAGE_ACTION+"/"+ATTAQUER+"/2");
					writer.println(MESSAGE_ACTION+"/"+ATTAQUER+"/2");
					writer.flush();
					break;
					}
				case BOUTON_ENVOYER :{
					System.out.println("Bouton ENVOYER " + BOUTON_ENVOYER);
					msg = envoyerTexte.getText();
					if (msg!=null && msg.length()!=0) {
						System.out.println("message à envoyer : "+msg);
						System.out.println(MESSAGE_CONVERSATION+"/"+msg);
						writer.println(MESSAGE_CONVERSATION+"/"+msg);
						writer.flush();
						// on modifie le texte de la conversation
						conversation.setText(conversation.getText()+"\nMoi : "+msg);
					}
					// le texte écrit dans la zone est effacé pour permettre d'envoyer un autre message
					envoyerTexte.setText("");
					break;
					}
				default:
					break;
				}
			}
		}
}
