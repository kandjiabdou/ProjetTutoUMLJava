package serveur;

import java.io.*;
import java.net.*;
import java.util.*;

import carte.Carte;
import personnage.Monstre;
import personnage.PersonnageJoueur;
import personnage.PersonnageNonJoueur;

public class Serveur {

   //On initialise des valeurs par défaut
   private int port = 63333;
   private String host = "127.0.0.1";
   private ServerSocket server = null;
   private boolean isRunning = true;
   private int nombreDeClient=0;
   private Carte carte;
   private Socket[] listeClient=new Socket[2];
   public Serveur(String pHost, int pPort){
      host = pHost;
      port = pPort;
      try {
     	 System.out.println("############  Démaarage du SEVEUR ##############");
         server = new ServerSocket(port, 100, InetAddress.getByName(host));
         System.out.println("Serveur initialisé : "+server);
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
		System.out.println("PARRRRRFAIT !!!");
      this.carte=new Carte();
   }
   
   
   //On lance notre serveur
   public void ouvrir(){
      //Toujours dans un thread à part vu qu'il est dans une boucle infinie
      Thread t = new Thread(new Runnable(){
         public void run(){
        	System.out.println("SERVEUR OUVERT : "+server);
        	System.out.println("En attentte d'un client .....");
            while(isRunning == true){
	       try {
				//On attend une connexion d'un client
				Socket client = server.accept();
				listeClient[nombreDeClient]=client;
				nombreDeClient++;
			  
				ClientProcessor cp=new ClientProcessor(client,nombreDeClient);
			  
				/*PrintWriter envoieVersClient = new PrintWriter(client.getOutputStream(), true);
				envoieVersClient.println("####################################");
				envoieVersClient.println("####        Bievenu au serveur du JEU        ####");
				envoieVersClient.println("####   Vous etes le joueur "+nombreDeClient+" du serveur   ####");
				envoieVersClient.println("####################################");
				envoieVersClient.println(carte.afficherFen());
				envoieVersClient.flush(); */
				String nom = nombreDeClient==1 ? "Moussa : +221 76 333 33 33" : "Fatou +221 77 999 99 99";
				PrintWriter envoieVersClient = new PrintWriter(client.getOutputStream(), true);
				envoieVersClient.println("####################################");
				envoieVersClient.println("#       Espace discussion en pv");
				envoieVersClient.println("#       "+nom);
				envoieVersClient.println("####################################");
				//envoieVersClient.println(carte.afficherFen());
				envoieVersClient.flush();
				
				//Une fois reçue, on la traite dans un thread séparé
				//System.out.println("Client n° "+nombreDeClient+" est connecté au SERVEUR");
				System.out.println("Client : "+nom+" est connecté au SERVEUR");
				
				new Thread(cp).start();
				if (nombreDeClient==2){
					isRunning=false;
				};  
				
	       } catch (IOException e) {
	          e.printStackTrace();
	       }
            }
            
            try {
            	System.out.println(" ###### FERMETURE DU SERVEUR ###########");
            	server.close();
            } catch (IOException e) {
               e.printStackTrace();
               server = null;
            }
         }
      });
      
      t.start();
   }
   
   public void close(){
      isRunning = false;
   }
   

	public Socket[] getListeClient() {
		return listeClient;
	}
	
	
	public void setListeClient(Socket[] listeClient) {
		this.listeClient = listeClient;
	}
	
	public static void main(String[] args) {
	      new Serveur("localhost", 63334).ouvrir();;
	}
	
	public class ClientProcessor implements Runnable{

		private Socket clientConnecte;
		private PrintWriter envoyeurDonneeVersClient = null;
		private BufferedReader lecteurDonneeClient = null;
		private int numero;
		
		private static final String ATTAQUER = "attaquer";
		private static final String RAMASSER = "ramasser";
		private static final String DEPOSER = "deposer";
		private static final String UTILISER = "utiliser";
		private static final String HAUT = "haut";
		private static final String BAS = "bas";
		private static final String DROITE = "droite";
		private static final String GAUCHE = "gauche";
		private static final String MESSAGE_CLOSE = "close";
		private static final String MESSAGE_CONVERSATION = "message";
		private static final String MESSAGE_CARTE = "catre";
		private static final String MESSAGE_ETAT = "etat";
		private static final String MESSAGE_ARMURE = "armure";
		private static final String MESSAGE_SAC = "sac";
		private static final String MESSAGE_DEPLACEMENT = "deplacement";
		private static final String MESSAGE_ACTION = "action";
		private static final String MESSAGE_MAIN = "main";
		private static final String MESSAGE_CARACTERISTIQUE = "caracteristique";
		private static final String MESSAGE_CAPACITE = "capacite";
		private static final String EQUIPER = "equiper";
		private static final String ECHANGER = "echanger";
		   
		public ClientProcessor(Socket pSock,int num){
			clientConnecte = pSock;
			setNumero(num);
			Timer tm = new Timer();
		      tm.schedule(new TimerTask() {
		    	  public void run() {
		    		  for(Monstre m : carte.monstres) {
		    			  m.deplacer();
		    		  }
		    		  for(PersonnageNonJoueur p : carte.pnjs) {
		    			  p.deplacer();
		    		  }
		    		  for(Socket c : listeClient) {
		    			  // la carte est actualisée pour les deux joueurs
		    			  if(c!=null) {
		    				  try {
		    					  envoyeurDonneeVersClient = new PrintWriter(c.getOutputStream());
		    					  //System.out.println(MESSAGE_CARTE+"/"+carte.afficherFen());
		    					  envoyeurDonneeVersClient.println(MESSAGE_CARTE+"/"+carte.afficherFen());
		    					  envoyeurDonneeVersClient.flush();
		    					  //System.err.println("Envoyé au client n°"+(numero)+" actualisation carte");
		    				  } catch (IOException e) {
		    					  e.printStackTrace();
		    				  }
		    			  }
		    		  }
		    		  // mais les des spécifications pour chaque jour comme ses informations
		    		  for(int i=0;i<2;i++) {
		    			  if(listeClient[i]!=null) {
		    				  try {
		    					  PersonnageJoueur joueur = i==0 ? carte.joueur1 : carte.joueur2;
			    				  envoyeurDonneeVersClient = new PrintWriter(listeClient[i].getOutputStream());
			    				  if(!joueur.isDeplacemet()) {
			    					  envoyeurDonneeVersClient.println(MESSAGE_CLOSE);
			    					  listeClient[i]=null;
			    					  ouvrir();
		    					  }
				    			  envoyeurDonneeVersClient.println(MESSAGE_ETAT+"/"+joueur.infoEtat());
				    			  envoyeurDonneeVersClient.flush();
				    			  envoyeurDonneeVersClient.println(MESSAGE_MAIN+"/"+joueur.infoMain());
				    			  envoyeurDonneeVersClient.flush();
				    			  envoyeurDonneeVersClient.println(MESSAGE_ARMURE+"/"+joueur.armureEquipe());
				    			  envoyeurDonneeVersClient.flush();
				    			  envoyeurDonneeVersClient.println(MESSAGE_CAPACITE+"/"+joueur.infoCapacite());
				    			  envoyeurDonneeVersClient.flush();
				    			  envoyeurDonneeVersClient.println(MESSAGE_CARACTERISTIQUE+"/"+joueur.infoCaracteristique());
				    			  envoyeurDonneeVersClient.flush();
				    			 
			    			  } catch (IOException e) {
			    				  e.printStackTrace();
			    			  }
		    			  }
		    		  }
		    	  }
		      },1, 1000);
		}
		   
		//Le traitement lancé dans un thread séparé
		public void run(){
			System.out.println("Lancement du traitement de la connexion cliente");
			
			//Ici, nous n'utilisons pas les mêmes objets que précédemment
			//Je vous expliquerai pourquoi ensuite
			try {
				envoyeurDonneeVersClient = new PrintWriter(clientConnecte.getOutputStream());
				lecteurDonneeClient = new BufferedReader(new InputStreamReader(clientConnecte.getInputStream()));
//				sender= new DataOutputStream(clientConnecte.getOutputStream());
//				receveur= new DataInputStream(clientConnecte.getInputStream());
				boolean closeConnexion = false;
				//tant que la connexion est active, on traite les demandes
				
				while(!clientConnecte.isClosed()){
			
					String messageClient = lecteurDonneeClient.readLine();
					String reponse="";
					
					String debug = "--> Commande reçue : " + messageClient + "\n";
					System.out.println(debug);
					
					@SuppressWarnings("resource")
					Scanner sc = new Scanner(messageClient);
					sc.useDelimiter("/");
					String m= sc.next();
					switch(m) {
						case MESSAGE_DEPLACEMENT: {
							String d = sc.next();
							switch (d) {
								case HAUT: {
									//
									if (this.numero==1)
										carte.joueur1.deplacer(1);
									else
										carte.joueur2.deplacer(1);
									break;
								}
								case BAS: {
									//
									if (this.numero==1)
										carte.joueur1.deplacer(2);
									else
										carte.joueur2.deplacer(2);
									break;
								}
								case DROITE: {
									//
									if (this.numero==1)
										carte.joueur1.deplacer(3);
									else
										carte.joueur2.deplacer(3);
									break;
								}
								case GAUCHE: {
									//
									if (this.numero==1)
										carte.joueur1.deplacer(4);
									else
										carte.joueur2.deplacer(4);
									break;
								}
							}
							// reponse
							break;
						}
						case MESSAGE_CONVERSATION: {
							reponse=sc.next();
							System.out.println(reponse);
							String tel= "telephone";
							if(reponse.contains(tel)) {
								System.err.println("Envoie de PUB télépnone à Moussa ");
								System.err.println("Envoie de PUB accessoir telephone à Moussa ");
							}
							if(listeClient[(this.numero)%2]!=null && messageClient!=null && messageClient.length()>0) {
								envoyeurDonneeVersClient = new PrintWriter(listeClient[(this.numero)%2].getOutputStream());
								String user = (this.numero)%2 == 0 ? "Moussa" : "Fatou" ;
								System.out.println(MESSAGE_CONVERSATION+"/"+reponse);
								envoyeurDonneeVersClient.println(MESSAGE_CONVERSATION+"/--> "+user+":"+reponse);
								envoyeurDonneeVersClient.flush();
								//System.err.println("Envoyé au client n°"+(this.numero%2+1)+" message de l'autre");
							}
							break;
						}
						case MESSAGE_ACTION: {
							PersonnageJoueur joueur = numero==1 ? carte.joueur1 : carte.joueur2;
							String a = sc.next();
							switch (a) {
								case ATTAQUER: {
										joueur.attaquer(Integer.valueOf(sc.next()));
									break;
								}
								case UTILISER: {
									joueur.utiliser(Integer.valueOf(sc.next()));
								break;
							}
								case DEPOSER: { 
										joueur.deposerSac(0);
										joueur.infoSac();
									break;
								}
								case RAMASSER: {
										joueur.ramasserSac();
										joueur.infoSac();
									break;
								}
								case ECHANGER: {
									joueur.enchangerMain();
								break;
								}
								case EQUIPER: {
									joueur.equiper(Integer.valueOf(sc.next()));;
									joueur.infoSac();
								break;
								}
							}
		    				envoyeurDonneeVersClient = new PrintWriter(listeClient[numero-1].getOutputStream());
							envoyeurDonneeVersClient.println(MESSAGE_ETAT+"/"+joueur.infoEtat());
							envoyeurDonneeVersClient.flush();
							envoyeurDonneeVersClient.println(MESSAGE_SAC+joueur.infoSac());
			    			envoyeurDonneeVersClient.flush();
							break;
						}
						case MESSAGE_CLOSE: {
							reponse = "Communication terminée avec le serveur Merci Au revoir !!!!"; 
							closeConnexion = true;
							break;
						}
					}
					if (m!=MESSAGE_CONVERSATION) {
						for(Socket c : listeClient) {
							if (c!=null && !closeConnexion) {
								envoyeurDonneeVersClient = new PrintWriter(c.getOutputStream());
								//System.out.println(MESSAGE_CARTE+"/"+carte.afficherFen());
								envoyeurDonneeVersClient.println(MESSAGE_CARTE+"/"+carte.afficherFen());
								envoyeurDonneeVersClient.flush();
								//System.err.println("Envoyé au client n°"+(this.numero%2+1)+" message carte");
							}
						}
					}
					if(closeConnexion){
						System.err.println("COMMANDE CLOSE DETECTEE ! ");
						envoyeurDonneeVersClient = null;
						envoyeurDonneeVersClient = null;
						clientConnecte.close();
						int i= numero==1 ? 0:1;
						listeClient[i]=null;
						break;
					}
				}
			}catch(SocketException e){
				System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
				//break;
			} catch (IOException e) {
				System.err.println("RIEN N4EST ENVOY2 ! ");
				e.printStackTrace();
			}
		}
		public Socket getClientConnecte() {
			return clientConnecte;
		}

		public void setClientConnecte(Socket clientConnecte) {
			this.clientConnecte = clientConnecte;
		}

		public PrintWriter getEnvoyeurDonneeVersClient() {
			return envoyeurDonneeVersClient;
		}

		public void setEnvoyeurDonneeVersClient(PrintWriter writer) {
			this.envoyeurDonneeVersClient = writer;
		}
		
		public BufferedReader getLecteurDonneeClient() {
			return lecteurDonneeClient;
		}

		public void setLecteurDonneeClient(BufferedReader reader) {
			this.lecteurDonneeClient = reader;
		}
		public int getNumero() {
			return numero;
		}
			
		public void setNumero(int numero) {
			this.numero = numero;
		}
	}
}
