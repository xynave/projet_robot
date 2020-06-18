/*package application.IoT_Application;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
 
 

public class PublishClient implements MqttCallback{
 
    MqttClient client; // Creation de l'objet client
    MqttConnectOptions connOpt; // Creation de l'objet connOpt
 
   
 
    public PublishClient() throws MqttException{
    String broker = "tcp://localhost:1883"; // Adresse du serveur MQTT Je ne sais pas si je peux la mettre ici sur le forum, ca ne craint pas ?
    String clientId = "lens_DTLSVaSxNkdZwhys9T2aHEp45UH"; // Nom du clientID
    String topic1f = "ZCC"; // Topic simulateur
    int QoSserveur = 0;
    String password="azerty";
 
 
 
 
            try{
                
 
                MemoryPersistence persistence = new MemoryPersistence();   
 
                // Creation des 2 objets : client et connOpt
                MqttClient mqttClient = new MqttClient(broker,clientId);
              //Mqtt ConnectOptions is used to set the additional features to mqtt message

                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setUserName(clientId);
                connOpts.setPassword(password.toCharArray());
                  connOpts.setCleanSession(true); //no persistent session 
                  connOpts.setKeepAliveInterval(1000);


              MqttMessage message = new MqttMessage("Ed Sheeran".getBytes());
              //here ed sheeran is a message
                  message.setQos(QoSserveur);     //sets qos level 1
                  message.setRetained(true); //sets retained message 

              MqttTopic topic2 = mqttClient.getTopic(topic1f);

                  mqttClient.connect(connOpts); //connects the broker with connect options
                  topic2.publish(message);    // publishes the message to the topic(test/topic)
            }catch(MqttException e){
                e.printStackTrace();
            }
 
    }
 
 
    public void connectionLost(Throwable thrwbl) {}
 
 
    public void messageArrived(String string, MqttMessage mm) throws Exception {}
 
    public void deliveryComplete(IMqttDeliveryToken imdt) {
    System.out.println("Message delivre au broker");
    }
 
}
*/

package application.IoT_Application;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;




public class PublishClient 
{ 
	public int i;
    public static void main(String[] args) {

        String topic        = "c3-green/start-of-the-game";
        String topic2        = "c3-green/end-of-the-game";
        String content      = "je";
        String content2      = "tu";
        
        int qos             = 1;
        String broker       = "tcp://foundation.lyon.ece.licot.fr:2019"/*"tcp://foundation.lyon.ece.licot.fr:2019"*/;
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
            System.out.println("Publishing message: "+content);
            //MqttMessage message = new MqttMessage(content.getBytes());
            //message.setQos(qos);
            sampleClient.publish(topic, content.getBytes(),qos,true);
            sampleClient.publish(topic2, content2.getBytes(),qos,true);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
}