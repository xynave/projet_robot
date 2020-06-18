package application.IoT_Application;


import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import application.IoT_Application.AStar.Node;
import application.IoT_Application.AStar.tabPositionMouvement;



/**
 *
 * @author 
 */
public class App {
	
 
	
    public static void main(String[] args) throws MqttException {
 
    	//AStar a= new AStar(null, 0, 0, false);
    	//a.ok(args);
    	
      // PublishClient publieur = new PublishClient();
       SubClient subscriber = new SubClient();
    	
    	
    	
    	
    	 
    }
    
 
}
