package application.IoT_Application;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;
import java.util.Collections;
public class AStar {
	private final List<Node> open;
    private final List<Node> closed;
    private final List<Node> path;
   /* private final*/ int[][] maze;
    private Node now;
    private final int xstart;
    private final int ystart;
    private int xend, yend;
    private final boolean diag;
    static int[][] blop;
    static int angleX =0;
    static int angleY=0;
    static int angle=0;
    static int vitesse=0;
    static int position;
    static tabPositionMouvement []tabmouvement;
    static int compteNombre=1;
    static int compteSegment=1;
    static cube succes=new cube();
    static int parcours=1;
    static int parcourschangeant;
    static int success=0;
   static cube[] tabeCub;
   static int Supertotal=0;
   static int variableTransition=0;
   static int parcours_precendent=0;
   static int temps_reel=0;
   static int deroulage=0;
   static int variable_transition=0;
   static int test_=0;
   static int protection_=0;
   static int boucle=0;
   static int ieop=0;
   static int ieopp=0;
   static int ieoppp=0;
   static int ippp = 0;
   




 
    // Node class for convienience
    static class Node implements Comparable {
        public Node parent;
        public int x, y;
        public double g;
        public double h;
        
        Node(Node parent, int xpos, int ypos, double g, double h) {
            this.parent = parent;
            this.x = xpos;
            this.y = ypos;
            this.g = g;
            this.h = h;
       }
        
       // Compare by f value (g + h)
      
       public int compareTo(Object o) {
           Node that = (Node) o;
           return (int)((this.g + this.h) - (that.g + that.h));
       }
   }
    
    static class cube  {
        int x=0;
        int y=0;
        int valeur=0;
      
    }
    
    static class tabPositionMouvement{
    	int position_x=0;
        int position_y=0;
        int position_valeur=0;
        int position_parcours;
    }
    

  
    
    
    
   public static void createCube()
    {
	   

	   
	
		
    	 tabeCub= new cube [30];
    	 
    	
    	
    	 for (ippp = 0; ippp < tabeCub.length; ippp++) {
    		 
    		 
    		 
    			 tabeCub[ippp]= new cube();
    			 ieop=0;
    			 ieopp=0;
    			 ieoppp=0;
    			 String topic        = "c3-green/type";
    			 String topic2        = "c3-green/x";
    			 String topic3        = "c3-green/y";
    			 
    	         String content      = "tezfozsefrds";
    	         int qos             = 1;
    	         
    	       
    	         String broker       = "tcp://foundation.lyon.ece.licot.fr:2019";
    	  	        String clientId     = "B9V9UJO930T85F6";
    	  	        String password     = "E5RvTzzxm5KK4013Fj5q";
    	         MemoryPersistence persistence = new MemoryPersistence();

    	    	    try {
    	    	    	MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
    	    	           
    	                MqttConnectOptions connOpts = new MqttConnectOptions();
    	                connOpts.setCleanSession(true);
    	                connOpts.setUserName(clientId);
    	                connOpts.setPassword(password.toCharArray());
    	                System.out.println("Connecting to broker: "+broker);
    	                sampleClient.connect(connOpts);
    	                System.out.println("Connected");
    	                
    	                
    	    	        sampleClient.setCallback(new MqttCallback() {
    	    	            public void connectionLost(Throwable cause) {}

    	    	            public void messageArrived(String topic,
    	    	                    MqttMessage message)
    	    	                            throws Exception {
    	    	            	switch(topic)
    	    	                {
    	    	                    case "c3-green/type":
    	    	                    	System.out.println("VALEUR");
    	    	                    	
    	    	    	            	  tabeCub[ippp].valeur= Integer.parseInt(message.toString());
    	    	    	            	 
    	    	    	      				 ieop=1;
    	    	                    break;
    	    	                    case "c3-green/x":
    	    	                    	System.out.println("X");
    	    	                    	
    	    	    	            
    	    	    	     				tabeCub[ippp].x=Integer.parseInt(message.toString())/20;//récupération position
    	    	    	     				System.out.println(Integer.parseInt(message.toString())/20);
    	    	    	      				 ieopp=1;
    	    	                    break;
    	    	                    case "c3-green/y":
    	    	                    	System.out.println("Y");
    	    	                    	
    	    	    	            
    	    	    	     				tabeCub[ippp].y=Integer.parseInt(message.toString())/20;//récupération position
    	    	    	     				System.out.println(Integer.parseInt(message.toString())/20);
    	    	    	      				 ieoppp=1;
    	    	                    break;
    	    	                   
    	    	                    default:
    	    	                    	tabeCub[ippp]= new cube();
    	    	    	            	  tabeCub[ippp].valeur= 100;
    	    	    	     				tabeCub[ippp].x= 2;//récupération position
    	    	    	      				tabeCub[ippp].y= 2;//récupération position
    	    	                    break;
    	    	                }
    	    	              
    	    	              
    	    	            }

    	    	            public void deliveryComplete(IMqttDeliveryToken token) {}
    	    	        });
    	    	       
    	    	        while(ieop==0) {
    	    	        sampleClient.subscribe(topic);
    	    	        while(ieopp==0) {
    	    	        sampleClient.subscribe(topic2);
    	    	        while(ieoppp==0) {
        	    	        sampleClient.subscribe(topic3);
    	    	        }
    	    	        }
    	    	        
    	    	        }
    	    	        System.out.println("Fin de reception des messages");
    	    	        sampleClient.disconnect(); // Deconnexion du serveur MQTT
    	                System.out.println("Deconnexion subscriber");

    	    	    } catch (MqttException e) {
    	    	       e.printStackTrace();
    	    	       
    	    	    }
    			/*
    			 
    			if(ippp!=2 && ippp!=3 )	 {   	  
    		tabeCub[ippp]= new cube();
       	  tabeCub[ippp].valeur= 100;
				tabeCub[ippp].x= 9;//récupération position
 				tabeCub[ippp].y= 6;//récupération position  
    				               
    			}
    			else
    			{
    				 tabeCub[ippp]= new cube();
    		       	  tabeCub[ippp].valeur= 3;
    						tabeCub[ippp].x= 2;//récupération position
    		 				tabeCub[ippp].y= 2;//récupération position  
    			}
    				 
    			 if( ippp==3 )	 {   	  
     	    		tabeCub[ippp]= new cube();
     	       	  tabeCub[ippp].valeur= 100;
     					tabeCub[ippp].x= 1;//récupération position
     	 				tabeCub[ippp].y= 1;//récupération position  
     	    				               
     	    			}
    				  */
    				 
    			 
    			
    	 }
    	 
    	 
  }
  
public static cube[] returncube() {
	   return tabeCub;
   }
   
   
    
   private static  int[][] createTab(int [][]tab)
   {
   	tab= new int [10][15];
   	//taille du plan
   	cube []tabCube;
  	 tabCube= new cube [30];
  	
  	 tabCube=tabeCub;
   	
   	for (int l = 0; l < tab.length; l++) {

			for(int p = 0; p < tab[l].length; p++) {

				tab[l][p]= 0;
				for(int k=0; k<30; k++) {
				if(tabCube[k].x==p && tabCube[k].y==l)
				{
					tab[l][p]= tabCube[k].valeur;
				}
							
			}
			}
			
   	}

   	
   
   	
		return tab;
   }
    
    
    
    AStar(int[][] maze, int xstart, int ystart, boolean diag) {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.path = new ArrayList<>();
        this.maze = maze;
        this.now = new Node(null, xstart, ystart, 0, 0);
        this.xstart = xstart;
        this.ystart = ystart;
        this.diag = diag;
    }
    /*
    ** Finds path to xend/yend or returns null
    **
    ** @param (int) xend coordinates of the target position
    ** @param (int) yend
    ** @return (List<Node> | null) the path
    */
    public List<Node> findPathTo(int xend, int yend) {
        this.xend = xend;
        this.yend = yend;
        this.closed.add(this.now);
        addNeigborsToOpenList();
        while (this.now.x != this.xend || this.now.y != this.yend) {
            if (this.open.isEmpty()) { // Nothing to examine
                return null;
            }
            this.now = this.open.get(0); // get first node (lowest f score)
            this.open.remove(0); // remove it
            this.closed.add(this.now); // and add to the closed
            addNeigborsToOpenList();
        }
        this.path.add(0, this.now);
        while (this.now.x != this.xstart || this.now.y != this.ystart) {
            this.now = this.now.parent;
            this.path.add(0, this.now);
        }
        return this.path;
    }
    /*
    ** Looks in a given List<> for a node
    **
    ** @return (bool) NeightborInListFound
    */
    private static boolean findNeighborInList(List<Node> array, Node node) {
        return array.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
    }
    /*
    ** Calulate distance between this.now and xend/yend
    **
    ** @return (int) distance
    */
    private double distance(int dx, int dy) {
        if (this.diag) { // if diagonal movement is alloweed
            return Math.hypot(this.now.x + dx - this.xend, this.now.y + dy - this.yend); // return hypothenuse
        } else {
            return Math.abs(this.now.x + dx - this.xend) + Math.abs(this.now.y + dy - this.yend); // else return "Manhattan distance"
        }
    }
    private void addNeigborsToOpenList() {
        Node node;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (!this.diag && x != 0 && y != 0) {
                    continue; // skip if diagonal movement is not allowed
                }
                node = new Node(this.now, this.now.x + x, this.now.y + y, this.now.g, this.distance(x, y));
                if ((x != 0 || y != 0) // not this.now
                    && this.now.x + x >= 0 && this.now.x + x < this.maze[0].length // check maze boundaries
                    && this.now.y + y >= 0 && this.now.y + y < this.maze.length
                    && this.maze[this.now.y + y][this.now.x + x] != -1 // check if square is walkable
                    && !findNeighborInList(this.open, node) && !findNeighborInList(this.closed, node)) { // if not already done
                        node.g = node.parent.g + 1.; // Horizontal/vertical cost = 1.0
                        node.g += maze[this.now.y + y][this.now.x + x]; // add movement cost for this square
 
                        // diagonal cost = sqrt(hor_cost² + vert_cost²)
                        // in this example the cost would be 12.2 instead of 11
                        /*
                        if (diag && x != 0 && y != 0) {
                            node.g += .4;	// Diagonal movement cost = 1.4
                        }
                        */
                        this.open.add(node);
                }
            }
        }
        Collections.sort(this.open);
    }
 
    public static void ok(String[] args) {
    	
    	
   	 Timer chrono =new Timer();
   	 // -1 = blocked
	        // 0+ = additional movement cost
	    	createCube();
	    	
		chrono.schedule(new TimerTask() {
			int tempslimite=120;
			int centimetre=0;
			int longueur_total=0;
			@Override
			public void run() {
				
			
   	 
   				if(centimetre<=0) {
   					deroulage=0;
   				compteNombre=0;
   				//mettre la boucle ici
   		    	blop=createTab(blop);
   		    	// REMPLISSAGE AUTO
   		        int[][] maze = blop;
   		       
   		       tabeCub = returncube(); 
   		       parcours=1;
   		      //cherche the cube the more near of the position of robot
   		       success=0;
   		      while(success==0)
   		      {
   		    	 parcourschangeant=0;
   		    	 while(  success==0)
   		    	 {
   		    		 for(int i=0; i<11;i++)
   		    		 { 
   		    			 if((-parcours+10)==tabeCub[i].x && (8+parcourschangeant)==tabeCub[i].y && success==0 && 3==tabeCub[i].valeur && maze[-parcours+10][parcourschangeant+8]!=100)
   		    			 {
   		    				
   		    				 succes.x=10-parcours;
   		    				 succes.y=8+parcourschangeant;
   		    				 tabeCub[i].valeur=0;
   		    				 
   		       				success=2;
   		    				 System.out.print("c'est trouvé a");
   		    				 break;
   		    			 }
   		    		 }
   		    		 
   		    		
   		    		 if(parcourschangeant==-parcours && success==0)
   			    	 {
   			    		 success=1;
   			    	 }
   		    		 else {
   		    			 parcourschangeant--;
   		    		 }
   		    		
   		    	 }
   		    	 if(success==1)
   		    	 {
   		    		 success=0;
   		    	 }
   		    	
   		    	 
   		    	 parcourschangeant=-parcours;
   		    	  while(  success==0)
   		    	 {
   		    		 for(int i=0; i<11;i++)
   		    		 {
   		    			 if(8-parcours==tabeCub[i].y && 10+parcourschangeant==tabeCub[i].x  && success==0 && 3==tabeCub[i].valeur)
   		    			 {
   		    				
   		    				 succes.x=10+parcourschangeant;
   		    				 succes.y=8-parcours;
   		    				 tabeCub[i].valeur=0;
   		    				 
   		       				success=2;
   		    				 System.out.print("c'est trouvé b");
   		    				 break;
   		    			 }
   		    		 }
   		    		 
   		    		
   		    		 if(parcourschangeant==parcours && success==0)
   			    	 {
   			    		 success=1;
   			    	 }
   		    		 else {
   		    			 parcourschangeant++;
   		    		 }
   		    		
   		    	 }
   		    	 if(success==1)
   		    	 {
   		    		 success=0;
   		    	 }
   		    	
   		    	 parcourschangeant=-parcours;
   		    	 
   		    	  while(  success==0)
   		    	 {
   		    		 for(int i=0; i<11;i++)
   		    		 {
   		    			 if(parcours+10==tabeCub[i].x && 8+parcourschangeant==tabeCub[i].y  && success==0 && 3==tabeCub[i].valeur)
   		    			 {
   		    				
   		    				 succes.x=10+parcours;
   		    				 succes.y=8+parcourschangeant;
   		    				 tabeCub[i].valeur=0;
   		    				 
   		       				success=2;
   		    				 System.out.print("c'est trouvé c");
   		    				 break;
   		    			 }
   		    		 }
   		    		 
   		    		
   		    		 if(parcourschangeant==parcours && success==0)
   			    	 {
   			    		 success=1;
   			    	 }
   		    		 else {
   		    			 parcourschangeant++;
   		    		 }
   		    		
   		    	 }
   		    	 if(success==1)
   		    	 {
   		    		 success=0;
   		    	 }
   		    	 
   		    	      	 parcourschangeant=parcours;
   		    	      	 
   		    	      	  while(  success==0)
   		    	 {
   		    		 for(int i=0; i<11;i++)
   		    		 {

   		    			 if(8+parcours==tabeCub[i].y && 10+parcourschangeant==tabeCub[i].x && success==0 && 3==tabeCub[i].valeur)
   		    			 {
   		    				
   		    				 succes.x=10+parcourschangeant;
   		    				 succes.y=8+parcours;
   		    				 tabeCub[i].valeur=0;
   		    				 
   		       				success=2;
   		    				 System.out.print("c'est trouvé d");
   		    				 break;
   		    			 }
   		    			 }
   		    		 
   		    		 if(parcourschangeant==-parcours && success==0)
   			    	 {
   			    		 success=1;
   			    	 }
   		    		 else {
   		    			 parcourschangeant--;
   		    		 }
   		    		
   		    	 }
   		    	 if(success==1)
   		    	 {
   		    		 success=0;
   		    	 }
   		    	 
   		    	
   		    	 parcourschangeant=parcours;
   		    	 
   		    	 
   		    	  while(  success==0)
   		    	 {
   		    		 for(int i=0; i<11;i++)
   		    		 {
   		    			 if(10+parcours==tabeCub[i].x && 8+parcours==tabeCub[i].x  && success==0 && 3==tabeCub[i].valeur)
   		    			 {
   		    				
   		    				 succes.x=10+parcours;
   		    				 succes.y=8+parcourschangeant;
   		    				 tabeCub[i].valeur=0;
   		    				 
   		       				success=2;
   		    				 System.out.print("c'est trouvé e");
   		    				 break;
   		    			 }
   		    		 }
   		    		 
   		    		
   		    		 if(parcourschangeant==0 && success==0)
   			    	 {
   			    		 success=1;
   			    	 }
   		    		 else {
   		    			 parcourschangeant--;
   		    		 }
   		    		
   		    	 }
   		    	 if(success==1)
   		    	 {
   		    		 success=0;
   		    	 }
   		    	 
   		    	  
   		    	 
   		    	 parcours++;
   		      }
   		      
   		        
   		        //Put start
   		        AStar as = new AStar(maze, 11, 8, true);
   		        //put end
   		        List<Node> path = as.findPathTo(succes.x, succes.y);
   		        if (path != null) {
   		        	
   		        	 tabmouvement=new tabPositionMouvement[300];
   		            path.forEach((n) -> {if(n.x!=0 &&n.y!=0) {
   		        	}
   		            	//calcul when the robot need turn and how many
   		            
   		            if (angleX==n.x && angleY<n.y)
   		        	{
   		            	
   		        			if(angleX==0 && angleY==0)
   		        			{
   		        				 tabmouvement[compteNombre]= new tabPositionMouvement();
   	     		        			tabmouvement[compteNombre].position_x=0;
   	     		        			tabmouvement[compteNombre].position_y=0;
   	     		        			tabmouvement[compteNombre].position_valeur=1;
   	     		        			parcours_precendent=parcours_precendent+tabmouvement[compteNombre].position_y;
   	     		        			tabmouvement[compteNombre].position_parcours=parcours_precendent;
   	     		        			compteNombre++;
   	     		        			
   	     		        			
   		        			}
   		        			if (position!=1)
        		        		{	
   		        				 
   		        			tabmouvement[compteNombre]= new tabPositionMouvement();
   		        			tabmouvement[compteNombre].position_x=20*(n.x);
   		        			tabmouvement[compteNombre].position_y=20*(n.y);
   		        			tabmouvement[compteNombre].position_valeur=1;
    		        			tabmouvement[compteNombre].position_parcours=parcours_precendent;
   		        			compteNombre++;
        		        		}
   		        			
   		        			
   		        		
   		        		position=1;	
   		        	}
   		        	else if (angleX==n.x && angleY>n.y)
   		        	{		if(angleX==0 && angleY==0) 
   		        			{
		    		        		 tabmouvement[compteNombre]= new tabPositionMouvement();
		  		        			tabmouvement[compteNombre].position_x=0;
		  		        			tabmouvement[compteNombre].position_y=0;
		  		        			tabmouvement[compteNombre].position_valeur=5;
	     		        			parcours_precendent=parcours_precendent-tabmouvement[compteNombre].position_y;
	     		        			tabmouvement[compteNombre].position_parcours=parcours_precendent;
		  		        			compteNombre++;
   		        			}
   		        	if (position!=5)
		        		{	
   		        		
		            			
   		        			tabmouvement[compteNombre]= new tabPositionMouvement();
   		        			tabmouvement[compteNombre].position_x=20*(n.x);
   		        			tabmouvement[compteNombre].position_y=20*(n.y);
   		        			tabmouvement[compteNombre].position_valeur=5;
   		        			compteNombre++;
		        		}
   		        	position=5;
   		        			
   		        			
   		        	}
   		    	 else if (angleX<n.x && angleY==n.y)
   		        	{		if(angleX==0 && angleY==0) 
	        					{
		    		        		 tabmouvement[compteNombre]= new tabPositionMouvement();
		  		        			tabmouvement[compteNombre].position_x=0;
		  		        			tabmouvement[compteNombre].position_y=0;
		  		        			tabmouvement[compteNombre].position_valeur=7;
		  		        			compteNombre++;
		  		        			
		  		        			
	        					}
   		        		
   		        	if (position!=7)
		        		{	
   		        			tabmouvement[compteNombre]= new tabPositionMouvement();
   		        			tabmouvement[compteNombre].position_x=20*(n.x);
   		        			tabmouvement[compteNombre].position_y=20*(n.y);
   		        			tabmouvement[compteNombre].position_valeur=7;
   		        			compteNombre++;
		        		}
   		        			
   		        		position=7;
   		        	}
   		        	 else if (angleX>n.x && angleY==n.y)
   		        	{
   		        		 if(angleX==0 && angleY==0) 
   		        		 {
   		        			 tabmouvement[compteNombre]= new tabPositionMouvement();
     		        			tabmouvement[compteNombre].position_x=0;
     		        			tabmouvement[compteNombre].position_y=0;
     		        			tabmouvement[compteNombre].position_valeur=3;
     		        			compteNombre++;
     		        			
     		        			
   		        		 }
   		        		 if (position!=3)
     		        		{	
   		        			 
   		        			tabmouvement[compteNombre]= new tabPositionMouvement();
   		        			tabmouvement[compteNombre].position_x=20*(n.x);
   		        			tabmouvement[compteNombre].position_y=20*(n.y);
   		        			tabmouvement[compteNombre].position_valeur=3;
   		        			compteNombre++;
     		        		}
   		        			
   		        		
   		        		position=3;
   		        	}
   		        	 else if (angleX>n.x && angleY>n.y)
   		        	{
   		        		if(angleX==0 && angleY==0) 
   		        		 {
   		        			 tabmouvement[compteNombre]= new tabPositionMouvement();
     		        			tabmouvement[compteNombre].position_x=0;
     		        			tabmouvement[compteNombre].position_y=0;
     		        			tabmouvement[compteNombre].position_valeur=8;
     		        			compteNombre++;
     		        			
     		        		
   		        		 }
   		        		 if (position!=8)
     		        		{	 
   		        			
     		                	
   		        			
   		        			tabmouvement[compteNombre]= new tabPositionMouvement();
   		        			tabmouvement[compteNombre].position_x=20*(n.x);
   		        			tabmouvement[compteNombre].position_y=20*(n.y);
   		        			tabmouvement[compteNombre].position_valeur=8;
   		        			compteNombre++;
     		        		}
   		        			
   		        		position=8;
   		        	}
   		        	 else if (angleX<n.x && angleY>n.y)
   		        	{
   		        		 if(angleX==0 && angleY==0) 
   		        		 {
   		        			 tabmouvement[compteNombre]= new tabPositionMouvement();
     		        			tabmouvement[compteNombre].position_x=0;
     		        			tabmouvement[compteNombre].position_y=0;
     		        			tabmouvement[compteNombre].position_valeur=2;
     		        			compteNombre++;
     		        			
     		        			
   		        		 }
   		        		 if (position!=2)
     		        		{	
   		        			 
   		        			tabmouvement[compteNombre]= new tabPositionMouvement();
   		        			tabmouvement[compteNombre].position_x=20*(n.x);
   		        			tabmouvement[compteNombre].position_y=20*(n.y);
   		        			tabmouvement[compteNombre].position_valeur=2;
   		        			compteNombre++;
     		        		}
   		        			
   		        			
   		        		
   		        		position=2;
   		        	}
   		        	 else if (angleX<n.x && angleY<n.y)
   		        	{
   		        		 if(angleX==0 && angleY==0) 
   		        		 {
   		        			 tabmouvement[compteNombre]= new tabPositionMouvement();
    		        			tabmouvement[compteNombre].position_x=0;
    		        			tabmouvement[compteNombre].position_y=0;
    		        			tabmouvement[compteNombre].position_valeur=4;
    		        			compteNombre++;
    		        			
    		        			
   		        		 }
   		        		 if (position!=4)
     		        		{	
   		        			 
   		        			tabmouvement[compteNombre]= new tabPositionMouvement();
   		        			tabmouvement[compteNombre].position_x=20*(n.x);
   		        			tabmouvement[compteNombre].position_y=20*(n.y);
   		        			tabmouvement[compteNombre].position_valeur=4;
   		        			compteNombre++;
     		        		}
   		        			
   		        		
   		        		position=4;
   		        	}
   		        	 else if (angleX>n.x && angleY<n.y)
   		        	{
   		        		 if(angleX==0 && angleY==0) 
   		        		 {
   		        			 tabmouvement[compteNombre]= new tabPositionMouvement();
     		        			tabmouvement[compteNombre].position_x=0;
     		        			tabmouvement[compteNombre].position_y=0;
     		        			tabmouvement[compteNombre].position_valeur=6;
     		        			compteNombre++;
     		        			
     		        			
   		        		 }
   		        		 
    		        		if (position!=6)
    		        		{	
    		        			
   		        			tabmouvement[compteNombre]= new tabPositionMouvement();
   		        			tabmouvement[compteNombre].position_x=20*(n.x);
   		        			tabmouvement[compteNombre].position_y=20*(n.y);
   		        			tabmouvement[compteNombre].position_valeur=6;
   		        			compteNombre++;	
   		        		}
   		        		position=6;
   		        		
   		        	}
   		        	 
   		    		
   		    		angleX=n.x;
   		            angleY=n.y;
   		                System.out.print("[" + n.x + ", " + n.y + "] ");
   		               
   		                maze[n.y][n.x] = -1;
   		                
   		                
   		                
   		            });
   		            
   		            	tabmouvement[compteNombre]= new tabPositionMouvement();
	            			tabmouvement[compteNombre].position_x=20*angleX;
	            			tabmouvement[compteNombre].position_y=20*angleY;
	            			tabmouvement[compteNombre].position_valeur=position;
	            			compteNombre++;
   		            
   		            System.out.printf("\nTotal cost: %.02f\n", (path.get(path.size() - 1).g)*20);
   		            
   		            
       				variableTransition=compteNombre-1;
       				
       					for(int i=1;i<=variableTransition;i++)
           				{
           					if(tabmouvement[variableTransition-i].position_valeur==2)
           					{
           						tabmouvement[compteNombre]= new tabPositionMouvement();
       		        			tabmouvement[compteNombre].position_x=tabmouvement[variableTransition-i].position_x;
       		        			tabmouvement[compteNombre].position_y=tabmouvement[variableTransition-i].position_y;
       		        			tabmouvement[compteNombre].position_valeur=6;
       		        			compteNombre++;
           					}
           					else if(tabmouvement[variableTransition-i].position_valeur==7)
           					{
           						tabmouvement[compteNombre]= new tabPositionMouvement();
       		        			tabmouvement[compteNombre].position_x=tabmouvement[variableTransition-i].position_x;
       		        			tabmouvement[compteNombre].position_y=tabmouvement[variableTransition-i].position_y;
       		        			tabmouvement[compteNombre].position_valeur=3;
       		        			compteNombre++;
           					}
           					else if(tabmouvement[variableTransition-i].position_valeur==4)
           					{
           						tabmouvement[compteNombre]= new tabPositionMouvement();
       		        			tabmouvement[compteNombre].position_x=tabmouvement[variableTransition-i].position_x;
       		        			tabmouvement[compteNombre].position_y=tabmouvement[variableTransition-i].position_y;
       		        			tabmouvement[compteNombre].position_valeur=8;
       		        			compteNombre++;
           					}
           					else if(tabmouvement[variableTransition-i].position_valeur==1)
           					{
           						tabmouvement[compteNombre]= new tabPositionMouvement();
       		        			tabmouvement[compteNombre].position_x=tabmouvement[variableTransition-i].position_x;
       		        			tabmouvement[compteNombre].position_y=tabmouvement[variableTransition-i].position_y;
       		        			tabmouvement[compteNombre].position_valeur=5;
       		        			compteNombre++;
           					}
           					else if(tabmouvement[variableTransition-i].position_valeur==6)
           					{
           						tabmouvement[compteNombre]= new tabPositionMouvement();
       		        			tabmouvement[compteNombre].position_x=tabmouvement[variableTransition-i].position_x;
       		        			tabmouvement[compteNombre].position_y=tabmouvement[variableTransition-i].position_y;
       		        			tabmouvement[compteNombre].position_valeur=2;
       		        			compteNombre++;
           					}
           					else if(tabmouvement[variableTransition-i].position_valeur==3)
           					{
           						tabmouvement[compteNombre]= new tabPositionMouvement();
       		        			tabmouvement[compteNombre].position_x=tabmouvement[variableTransition-i].position_x;
       		        			tabmouvement[compteNombre].position_y=tabmouvement[variableTransition-i].position_y;
       		        			tabmouvement[compteNombre].position_valeur=7;
       		        			compteNombre++;
           					}
           					else if(tabmouvement[variableTransition-i].position_valeur==8)
           					{
           						tabmouvement[compteNombre]= new tabPositionMouvement();
       		        			tabmouvement[compteNombre].position_x=tabmouvement[variableTransition-i].position_x;
       		        			tabmouvement[compteNombre].position_y=tabmouvement[variableTransition-i].position_y;
       		        			tabmouvement[compteNombre].position_valeur=4;
       		        			compteNombre++;
           					}
           					else if(tabmouvement[variableTransition-i].position_valeur==5)
           					{
           						tabmouvement[compteNombre]= new tabPositionMouvement();
       		        			tabmouvement[compteNombre].position_x=tabmouvement[variableTransition-i].position_x;
       		        			tabmouvement[compteNombre].position_y=tabmouvement[variableTransition-i].position_y;
       		        			tabmouvement[compteNombre].position_valeur=1;
       		        			compteNombre++;
           					}
           					
           				}
       				
       				
       					for (int l=0;l<compteNombre;l++)
       					{
       						if (l==0)
       						{
       							tabmouvement[l].position_parcours=temps_reel+0;
       						}
       						else if(tabmouvement[l].position_valeur==1) {
       							temps_reel+=(tabmouvement[l].position_y-tabmouvement[l-1].position_y);
       							tabmouvement[l].position_parcours=temps_reel;
       						}
       						else if(tabmouvement[l].position_valeur==5) {
       							temps_reel+=(tabmouvement[l-1].position_y-tabmouvement[l].position_y);
       							tabmouvement[l].position_parcours=temps_reel;
       						}
       						else if(tabmouvement[l].position_valeur==7) {
       							temps_reel+=(tabmouvement[l].position_x-tabmouvement[l-1].position_x);
       							tabmouvement[l].position_parcours=temps_reel;
       						}
       						else if(tabmouvement[l].position_valeur==3) {
       							temps_reel+=(tabmouvement[l-1].position_x-tabmouvement[l].position_x);
       							tabmouvement[l].position_parcours=temps_reel;
       						}
       						
       						else if(tabmouvement[l].position_valeur==8) {
       							temps_reel+=Math.sqrt(((tabmouvement[l-1].position_y-tabmouvement[l].position_y)*(tabmouvement[l-1].position_y-tabmouvement[l].position_y))+((tabmouvement[l-1].position_x-tabmouvement[l].position_x)*(tabmouvement[l-1].position_x-tabmouvement[l].position_x)));
       							tabmouvement[l].position_parcours=temps_reel;
       						}
       						else if(tabmouvement[l].position_valeur==6) {
       							temps_reel+=Math.sqrt(((tabmouvement[l].position_y-tabmouvement[l-1].position_y)*(tabmouvement[l].position_y-tabmouvement[l-1].position_y))+((tabmouvement[l-1].position_x-tabmouvement[l].position_x)*(tabmouvement[l-1].position_x-tabmouvement[l].position_x)));
       							tabmouvement[l].position_parcours=temps_reel;
       						}
       						else if(tabmouvement[l].position_valeur==4) {
       							temps_reel+=Math.sqrt(((tabmouvement[l].position_y-tabmouvement[l-1].position_y)*(tabmouvement[l].position_y-tabmouvement[l-1].position_y))+((tabmouvement[l].position_x-tabmouvement[l-1].position_x)*(tabmouvement[l].position_x-tabmouvement[l-1].position_x)));
       							tabmouvement[l].position_parcours=temps_reel;
       						}
       						else if(tabmouvement[l].position_valeur==2) {
       							temps_reel+=Math.sqrt(((tabmouvement[l-1].position_y-tabmouvement[l].position_y)*(tabmouvement[l-1].position_y-tabmouvement[l].position_y))+((tabmouvement[l].position_x-tabmouvement[l-1].position_x)*(tabmouvement[l].position_x-tabmouvement[l].position_x)));
       							tabmouvement[l].position_parcours=temps_reel;
       						}
       					}
       					centimetre=temps_reel-tabmouvement[0].position_parcours;
       					Supertotal=+centimetre;
   		            for (int[] maze_row : maze) {
   		                for (int maze_entry : maze_row) {
   		                    switch (maze_entry) {
   		                        case 0:
   		                            System.out.print("_");
   		                            break;
   		                        case -1:
   		                            System.out.print("*");
   		                            break;
   		                        case 3:
   		                            System.out.print("%");
   		                            break;
   		                        default:
   		                            System.out.print("#");
   		                    }
   		                }
   		                System.out.println();
   		            	
   		                
   		               
   		            }
   		           
   		            for(int i=0; i<compteNombre;i++) {
   		            	System.out.print(tabmouvement[i].position_x);
   		            	System.out.print("/");
   		            	System.out.print(tabmouvement[i].position_y);
   		            	System.out.print("/");
   		            	System.out.print(tabmouvement[i].position_valeur);
   		            	System.out.print("/");
   		            	System.out.print(tabmouvement[i].position_parcours);
   		            	System.out.println(" ");
   		            	
   		                }  
   		         
   			    
   		           
   		        }
   	    	    	    	}	
   				
   				String aString1= "le temps en seconde est de : ";
   				String aString = Integer.toString(120-tempslimite);
   				String aString2= aString1+ aString;
   				System.out.print(aString2);
   				
   				String aString3= " on a parcouru : ";
   				String aString4 = Integer.toString((120-tempslimite)*5);
   				
   				String aString6= aString3+ aString4;
   				aString6+=" cm";
   				System.out.println(aString6);
   			 
   				
					
   				
   				
   				String contentangle=" " ;
   				String contentx=" " ;
   				String contenty=" " ;
   				float okezfe;
   			
   				if(protection_<test_ )
   				{deroulage+=1;
   				boucle=0;
   				}
   				if(deroulage+1<compteNombre) {
   					
   				if (tabmouvement[deroulage+1].position_parcours>=test_)
       				{
       					if(tabmouvement[deroulage+1].position_valeur==1) {
       						System.out.print(" on est orienté en: ");
       						 contentangle      = " on est orienté en: "+Integer.toString(tabmouvement[deroulage].position_valeur);
       			   	        
       	    				System.out.print(tabmouvement[deroulage].position_valeur);
       						System.out.print(" et on est en: ");
       	    				System.out.print(tabmouvement[deroulage].position_x);
       	    				contentx=Integer.toString(tabmouvement[deroulage].position_x);
       	    				System.out.print("x");
       	    				System.out.print(", ");
       	    				System.out.print(tabmouvement[deroulage].position_y+(5*boucle));
       	    				contenty=Integer.toString(tabmouvement[deroulage].position_y+(5*boucle));
       	    				System.out.println("y");
       	    				
   						}
   						else if(tabmouvement[deroulage+1].position_valeur==5) {
   							System.out.print(" on est orienté en: ");
       	    				System.out.print(tabmouvement[deroulage].position_valeur);
       	    				contentangle      = " on est orienté en: "+Integer.toString(tabmouvement[deroulage].position_valeur);
   							System.out.print(" et on est en: ");
       	    				System.out.print(tabmouvement[deroulage].position_x);
       	    				System.out.print("x");
       	    				contentx=Integer.toString(tabmouvement[deroulage].position_x);
       	    				System.out.print(", ");
       	    				System.out.print(tabmouvement[deroulage].position_y-(5*boucle));
       	    				contenty=Integer.toString(tabmouvement[deroulage].position_y-(5*boucle));
       	    				System.out.println("y");
       	    				
   						}
   						else if(tabmouvement[deroulage+1].position_valeur==7) {
   							System.out.print(" on est orienté en: ");
       	    				System.out.print(tabmouvement[deroulage].position_valeur);
       	    				 contentangle      = " on est orienté en: "+Integer.toString(tabmouvement[deroulage].position_valeur);
   							System.out.print(" et on est en: ");
       	    				System.out.print(tabmouvement[deroulage].position_x+(5*boucle));
       	    				System.out.print("x");
       	    				contentx=Integer.toString(tabmouvement[deroulage].position_x+(5*boucle));
       	    				System.out.print(", ");
       	    				System.out.print(tabmouvement[deroulage].position_y);
       	    				contenty=Integer.toString(tabmouvement[deroulage].position_y);
       	    				System.out.println("y");
       	    				
   						}
   						else if(tabmouvement[deroulage+1].position_valeur==3) {
   							System.out.print(" on est orienté en: ");
       	    				System.out.print(tabmouvement[deroulage].position_valeur);
       	    				contentangle      = " on est orienté en: "+Integer.toString(tabmouvement[deroulage].position_valeur);
   							System.out.print(" et on est en: ");
       	    				System.out.print(tabmouvement[deroulage].position_x-(5*boucle));
       	    				System.out.print("x");
       	    				contentx=Integer.toString(tabmouvement[deroulage].position_x-(5*boucle));
       	    				System.out.print(", ");
       	    				System.out.print(tabmouvement[deroulage].position_y);
       	    				contenty=Integer.toString(tabmouvement[deroulage].position_y);
       	    				System.out.println("y");
       	    				
   						}
   						
   						else if(tabmouvement[deroulage+1].position_valeur==8) {
   							System.out.print(" on est orienté en: ");
       	    				System.out.print(tabmouvement[deroulage].position_valeur);
       	    				 contentangle      = " on est orienté en: "+Integer.toString(tabmouvement[deroulage].position_valeur);
   							System.out.print(" et on est en: ");
       	    				System.out.print(tabmouvement[deroulage].position_x-(2.5*boucle));
       	    				System.out.print("x");
       	    				okezfe=(float) (tabmouvement[deroulage].position_x-(2.5*boucle));
       	    				contentx= Float.toString(okezfe);
       	    				
       	    				System.out.print(", ");
       	    				okezfe=(float) (tabmouvement[deroulage].position_y-(2.5*boucle));
       	    				contenty= Float.toString(okezfe);
       	    				System.out.print(tabmouvement[deroulage].position_y-(2.5*boucle));
       	    				System.out.println("y");
       	    				
   						}
   						else if(tabmouvement[deroulage+1].position_valeur==6) {
   							System.out.print(" on est orienté en: ");
       	    				System.out.print(tabmouvement[deroulage].position_valeur);
       	    				 contentangle      = " on est orienté en: "+Integer.toString(tabmouvement[deroulage].position_valeur);
   							System.out.print(" et on est en: ");
       	    				System.out.print(tabmouvement[deroulage].position_x-(2.5*boucle));
       	    				System.out.print("x");
       	    				okezfe=(float) (tabmouvement[deroulage].position_x-(2.5*boucle));
       	    				contentx= Float.toString(okezfe);
       	    				
       	    				System.out.print(", ");
       	    				okezfe=(float) (tabmouvement[deroulage].position_y+(2.5*boucle));
       	    				contenty= Float.toString(okezfe);
       	    				System.out.print(tabmouvement[deroulage].position_y+(2.5*boucle));
       	    				System.out.println("y");
       	    				
   						}
   						else if(tabmouvement[deroulage+1].position_valeur==4) {
   							System.out.print(" on est orienté en: ");
       	    				System.out.print(tabmouvement[deroulage].position_valeur);
       	    				 contentangle      = " on est orienté en: "+Integer.toString(tabmouvement[deroulage].position_valeur);
   							System.out.print(" et on est en: ");
       	    				System.out.print(tabmouvement[deroulage].position_x+(2.5*boucle));
       	    				System.out.print("x");
       	    				okezfe=(float) (tabmouvement[deroulage].position_x+(2.5*boucle));
       	    				contentx= Float.toString(okezfe);
       	    				System.out.print(tabmouvement[deroulage].position_y+(2.5*boucle));
       	    				okezfe=(float) (tabmouvement[deroulage].position_y+(2.5*boucle));
       	    				contenty= Float.toString(okezfe);
       	    				System.out.println("y");
       	    				
   						}
   						else if(tabmouvement[deroulage+1].position_valeur==2) {
   							System.out.print(" on est orienté en: ");
       	    				System.out.print(tabmouvement[deroulage].position_valeur);
       	    				contentangle      = " on est orienté en: "+Integer.toString(tabmouvement[deroulage].position_valeur);
   							
   							System.out.print("  on est en: ");
       	    				System.out.print(tabmouvement[deroulage].position_x+(2.5*boucle));
       	    				System.out.print("x");
       	    				okezfe=(float) (tabmouvement[deroulage].position_x+(2.5*boucle));
       	    				contentx= Float.toString(okezfe);
       	    				System.out.print(", ");
       	    				System.out.print(tabmouvement[deroulage].position_y-(2.5*boucle));
       	    				okezfe=(float) (tabmouvement[deroulage].position_y-(2.5*boucle));
       	    				contenty= Float.toString(okezfe);
       	    				System.out.println("y");
       	    				System.out.print(", ");
       	    				System.out.print(tabmouvement[deroulage+1].position_parcours);
       	    				System.out.println(" position");
       	    				System.out.print(" et ");
       	    				System.out.print(deroulage);
       	    				System.out.println(" deroulage");
       	    				
   						}
       					boucle+=1;
       				}
   					
   					
   				protection_=tabmouvement[deroulage+1].position_parcours;
   				}
   				
   			 String topic        = "temps";
   			 String topic2        = "parcours";
   			 String topic3        = "x";
   			 String topic4        = "y";
   			 String topic5        = "angle";
   			 
   	        String content      = aString2;
   	        String content2     = aString6;
   	        int qos             = 2;
   	        String broker       = "tcp://localhost:1883"/*"tcp://foundation.lyon.ece.licot.fr:2019"*/;
   	        String clientId     = "lens_oHOSBuT4TDiIsRoJBOFb9oD6P9f";
   	        String password     = "azerty";
   	        MemoryPersistence persistence = new MemoryPersistence();

   	        try {
   	            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
   	           
   	            MqttConnectOptions connOpts = new MqttConnectOptions();
   	            connOpts.setCleanSession(true);
   	            connOpts.setUserName(clientId);
   	            connOpts.setPassword(password.toCharArray());
   	            System.out.println("Connecting to broker: "+broker);
   	            sampleClient.connect(connOpts);
   	            System.out.println("Connected");
   	            System.out.println("Publishing message: "+content);
   	            //MqttMessage message = new MqttMessage(content.getBytes());
   	            //message.setQos(qos);
   	            sampleClient.publish(topic, content.getBytes(),qos,true);
   	         sampleClient.publish(topic2, content2.getBytes(),qos,true);
   	      sampleClient.publish(topic5, contentangle.getBytes(),qos,true);
   	   sampleClient.publish(topic3, contentx.getBytes(),qos,true);
	      sampleClient.publish(topic4, contenty.getBytes(),qos,true);
   	         
   	            System.out.println("Message published");
   	            sampleClient.disconnect();
   	            System.out.println("Disconnected");
   	          //  System.exit(0);
   	        } catch(MqttException me) {
   	            System.out.println("reason "+me.getReasonCode());
   	            System.out.println("msg "+me.getMessage());
   	            System.out.println("loc "+me.getLocalizedMessage());
   	            System.out.println("cause "+me.getCause());
   	            System.out.println("excep "+me);
   	            me.printStackTrace();
   	        }
   				
   				
   	     
   				
   	    		centimetre-=5;
   	    		tempslimite--;
   	    		test_+=5;
   				if (tempslimite==0)
   				{
   					cancel();
   					
   				}
   	    			}
   	    		},0,1000);
   	    
		System.out.println("ok");

   }

	private static void SubClients() {
		// TODO Auto-generated method stub
		
	}
   

  
  
}