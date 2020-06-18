package application.IoT_Application;

import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SubClient implements MqttCallback  {
 
	MqttClient client; // Creation de l'objet client
    MqttConnectOptions connOpt; // Creation de l'objet connOpt
 
 
    public SubClient() throws MqttException{
    	 String broker = "tcp://foundation.lyon.ece.licot.fr:2019"; // Adresse du serveur MQTT Je ne sais pas si je peux la mettre ici sur le forum, ca ne craint pas ?
    	    String clientId = "B9V9UJO930T85F6"; // Nom du clientID
    	    String topic1f = "c3-green/type"; // Topic simulateur
    	    int QoSserveur = 1;
    	    String password="E5RvTzzxm5KK4013Fj5q";
    	    /*
    	 String topic1f = "test"; // Topic simulateur
 	    int QoSserveur = 2;
 	    String broker       = "tcp://localhost:1883"/*"tcp://foundation.lyon.ece.licot.fr:2019"*/;
 	    	  /*    String clientId     = "lens_oHOSBuT4TDiIsRoJBOFb9oD6P9f";
	        String password     = "azerty";
	       */
    	    Scanner sc = new Scanner(System.in);
    	    int i =0;
    try{
 
    	
    	 MqttClient client = new MqttClient(broker,clientId);
         client.setCallback(this);
         
    MqttConnectOptions mqOptions=new MqttConnectOptions();
    mqOptions.setUserName(clientId);
    mqOptions.setPassword(password.toCharArray());
         mqOptions.setCleanSession(true);
         client.connect(mqOptions);      //connecting to broker 
         
            client.subscribe(topic1f); //subscribing to the topic name  test/topic
           while(i!=9) {
        	   i= sc.nextInt();
           	
               System.out.println("Fin de reception des messages");
               client.disconnect(); // Deconnexion du serveur MQTT
               System.out.println("Deconnexion subscriber");
           }
           
                }catch(MqttException e){
                e.printStackTrace();
            }
 
    }
 
 
  
    public void connectionLost(Throwable thrwbl) {
    System.out.println("Connexion perdue");
    }
 
   
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("message is : "+message);
        
    	
		
       
    }
 
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        System.out.println("Message arrive");
    }



	
}