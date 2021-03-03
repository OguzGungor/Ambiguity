import org.eclipse.paho.client.mqttv3.*;

import java.util.ArrayList;
import java.util.UUID;

public class Subscriber {

    // Singleton
    private static Subscriber instance = null;

    // Constants
    private final String clientID = "Ambiguity_Subscriber"; // UUID.randomUUID().toString();
    private final String address = "tcp://localhost";
    private final String broker;
    private final int port;
    private final int qos = 2;

    // Properties
    private MqttClient client;
    private MqttConnectOptions options;
    private ArrayList<String> topics;

    private Subscriber() throws MqttException {
        this.port = GlobalVariables.getInstance().port;
        this.broker = this.address + ":" + this.port;
        this.client = new MqttClient(this.broker, this.clientID);
        System.out.println("[Subscriber] Initialized to connect the broker : " + this.broker);

        this.options = new MqttConnectOptions();
        this.options.setCleanSession(true);

        this.topics = new ArrayList<String>();
    }

    public static Subscriber getInstance() throws MqttException {
        if (instance == null) {
            instance = new Subscriber();
        }

        return instance;
    }

    public void start(MqttCallback callback) throws MqttException {
        this.client.connect(options);
        System.out.println("[Subscriber] Connected.");

        if (callback != null) {
            this.client.setCallback(callback);
            System.out.println("[Subscriber] Callback is setted.");
        }

        for (String topic : this.topics) {
            this.client.subscribe(topic, this.qos);
            System.out.println("[Subscriber] Subscribed to topic : " + topic);
        }
    }

    public void setTopic(String... topics) {
        for (String topic : topics) {
            this.topics.add(topic);
        }
    }

    public void sendMessage(String topic, String message) {
        try {
            this.client.publish(topic, new MqttMessage(message.getBytes()));
        } catch (MqttException e) {
            System.out.println("[Subscriber] MqttException :");
            System.out.println("\t\t" + e.getMessage());
        }
    }
}