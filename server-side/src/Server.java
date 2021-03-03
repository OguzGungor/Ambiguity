import org.eclipse.paho.client.mqttv3.*;
import org.json.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {

    // Thread
    private ConcurrentLinkedQueue<Thread> threads;

    // Constants
    private final LobyManager lobyManager;
    private String SERVER_IP;

    // Properties
    private GlobalVariables globalVariables;
    private DatagramSocket clientSocket;
    private InetAddress IPAddress;
    private Subscriber subscriber;
    private JSONObject config;
    private String topicServer;
    private boolean errorOnBroadcast = false;

    Server() {
        // Initialize Thread
        this.threads = new ConcurrentLinkedQueue<Thread>();

        // Initialize Properties
        this.globalVariables = GlobalVariables.getInstance();
        this.lobyManager = new LobyManager();
        try {
            this.SERVER_IP = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            this.SERVER_IP = "non";
            this.errorOnBroadcast = true;
            System.out.println("[Server] Exception: " + e.getMessage());
        }

        this.clientSocket = null;
        this.IPAddress = null;
        try {
            this.clientSocket = new DatagramSocket();
            String broadcastAddress = "";
            String[] broadcastAddressArr = this.SERVER_IP.split("\\.");
            for(int i = 0;  i < broadcastAddressArr.length; i++) {
                if (i < broadcastAddressArr.length - 1) {
                    broadcastAddress += broadcastAddressArr[i] + ".";
                } else {
                    break;
                }
            }
            broadcastAddress += "255";

            if (!broadcastAddressArr[0].equals("192")) {
                broadcastAddress = "255.255.255.255";
                broadcastAddress = "192.168.137.255";
            }
            System.out.println("[Server] Broadcast Address: " + broadcastAddress);
            this.IPAddress = InetAddress.getByName(broadcastAddress);
        } catch (Exception e) {
            this.errorOnBroadcast = true;
            System.out.println("[Server] Exception : ");
            System.out.println("\t\t" + e.getMessage());
        }

        // Read JSON File
        try {
            JSONTokener jsonTokener = new JSONTokener(new FileReader(this.globalVariables.JSON_CONFIG_PATH));
            this.config = new JSONObject(jsonTokener);
            this.topicServer = this.config.getString(this.globalVariables.JSON_SERVER_TOPIC_KEY);
            this.globalVariables.port = this.config.getInt(this.globalVariables.JSON_PORT_KEY);
            this.globalVariables.topicLoby = this.config.getString(this.globalVariables.JSON_LOBY_TOPIC_KEY);
            this.globalVariables.topicGame = this.config.getString(this.globalVariables.JSON_GAME_TOPIC_KEY);
            this.globalVariables.topicResponse = this.config.getString(this.globalVariables.JSON_RESPONSE_TOPIC_KEY);
            JSONObject commandsObject = this.config.getJSONObject(this.globalVariables.JSON_COMMANDS_KEY);
            JSONArray serverCommandsArray = commandsObject.getJSONArray(this.globalVariables.JSON_SERVER_COMMANDS_KEY);
            JSONArray lobyCommandsArray = commandsObject.getJSONArray(this.globalVariables.JSON_LOBY_COMMANDS_KEY);
            JSONArray gameCommandsArray = commandsObject.getJSONArray(this.globalVariables.JSON_GAME_COMMANDS_KEY);

            // Initialize COMMANDS
            for (int i = 0; i < serverCommandsArray.length(); i++) {
                this.globalVariables.COMMANDS.add(serverCommandsArray.getString(i));
            }

            for (int i = 0; i < lobyCommandsArray.length(); i++) {
                this.globalVariables.COMMANDS_LOBY.add(lobyCommandsArray.getString(i));
            }

            for (int i = 0; i < gameCommandsArray.length(); i++) {
                this.globalVariables.COMMANDS_GAME.add(gameCommandsArray.getString(i));
            }
        } catch (Exception e) {
            System.out.println("[Server] Exception : ");
            System.out.println("\t\t" + e.getMessage());
        }

        // Subscribe to Game Management Topic
        try {
            this.subscriber = Subscriber.getInstance();
            this.subscriber.setTopic(this.topicServer, this.globalVariables.topicLoby, this.globalVariables.topicGame);
        } catch (MqttException e) {
            System.out.println("[Server] MqttException :");
            System.out.println("\t\t" + e.getMessage());
        }
    }

    private void broadcastServerAddress() {
        if(this.clientSocket == null || this.IPAddress == null || this.errorOnBroadcast) {
            this.errorOnBroadcast = true;
            return;
        }
        // System.out.print("Broadcasting");
        try {
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            String sentence = "tcp://" + this.SERVER_IP + ":" + this.globalVariables.port;
            sendData = sentence.getBytes();
            // System.out.print(".");
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, this.IPAddress,
                    this.globalVariables.broadcastPort);
            // System.out.print(".");
            this.clientSocket.send(sendPacket);
            // System.out.print(".");
            //this.clientSocket.close();
        } catch (Exception e) {
            // System.out.println();
            System.out.println("[Server] Broadcast Exception :");
            System.out.println("\t\t" + e.getMessage());
            this.errorOnBroadcast = true;
            this.clientSocket.close();
        }
        // System.out.println();
    }

    public void start() {
        try {
            this.subscriber.start(new Callback());
        } catch (MqttException e) {
            System.out.println("[Server] MqttException :");
            System.out.println("\t\t" + e.getMessage());
        }

        while (true) {
            // this.broadcastServerAddress();

            Thread thread = this.threads.poll();
            if (thread == null) {
                do {
                    if (!this.errorOnBroadcast) {
                        this.broadcastServerAddress();
                    }
                    /*
                     * synchronized (this.threads) { try { this.threads.wait(); } catch
                     * (InterruptedException e) {
                     * System.out.println("[Server] InterruptedException : ");
                     * System.out.println("\t\t" + e.getMessage()); } }
                     */
                    thread = this.threads.poll();
                } while (thread == null);
            }
            thread.start();
        }
    }

    private void runCommand(String topic, String message) {
        // Parse Message
        String[][] parsedMessage = this.parseMessage(message);
        String opcode = parsedMessage[0][0];
        String[] params = parsedMessage[1];

        // Print Information
        System.out.println("________________________");
        System.out.println("opcode : " + opcode);
        if (params != null) {
            System.out.println("params : ");
            for (String param : params) {
                System.out.println("\t" + param);
            }
            System.out.println();
        }

        if (topic.equals(this.globalVariables.topicLoby) || topic.equals(this.globalVariables.topicGame)) {
            this.lobyManager.runCommand(opcode, params);
            System.out.println("________________________");
            return;
        }

        // fix sendMessage parameters

        // COMMANDS
        boolean success;
        if (this.globalVariables.COMMANDS.get(0).equals(opcode)) {
            // createLoby
            success = lobyManager.createNewLoby();
            this.subscriber.sendMessage(this.globalVariables.topicResponse, opcode + ";" + success);

            if (success) {
                System.out.println("[Server] Loby is created.");
            } else {
                System.out.println("[Server] Loby IS NOT created.");
            }
            //
        } else if (this.globalVariables.COMMANDS.get(1).equals(opcode)) {
            // enterLoby
            if (params.length >= 2) {
                success = lobyManager.addClientToLoby(Integer.parseInt(params[0]), params[1]);
                this.subscriber.sendMessage(this.globalVariables.topicResponse,
                        opcode + ";" + params[0] + ";" + params[1] + ";" + success);
            } else {
                success = lobyManager.addClientToLoby(params[0]);
                this.subscriber.sendMessage(this.globalVariables.topicResponse,
                        opcode + ";" + params[0] + ";" + success);
            }

            if (success) {
                System.out.println("[Server] Client entered to loby.");
            } else {
                System.out.println("[Server] Client COULD NOT entered to loby.");
            }
            //
        } else if (this.globalVariables.COMMANDS.get(2).equals(opcode)) {
            // exitLoby
            int lobyID = Integer.parseInt(params[0]);
            success = lobyManager.removeClientFromLoby(lobyID, params[1]);
            this.subscriber.sendMessage(this.globalVariables.topicResponse, opcode + ";" + params[0] + ";" + success);

            if (success) {
                System.out.println("[Server] Client exited from loby.");
            } else {
                System.out.println("[Server] Client COULD NOT exited from loby.");
            }
            //
        } else if (this.globalVariables.COMMANDS.get(3).equals(opcode)) {
            // displayLoby
            this.subscriber.sendMessage(this.globalVariables.topicResponse,
                    opcode + ";" + params[0] + ";" + this.lobyManager.getLoby(Integer.parseInt(params[0])));

            System.out.println("[Server] Loby is displayed.");
            System.out.println(this.lobyManager.getLoby(Integer.parseInt(params[0])));
            //
        } else if (this.globalVariables.COMMANDS.get(4).equals(opcode)) {
            // displayAll
            this.subscriber.sendMessage(this.globalVariables.topicResponse, opcode + ";" + this.lobyManager.toString());

            System.out.println("[Server] LobyManager is displayed.");
            System.out.println(this.lobyManager.toString());
            //
        } else {
            System.out.println("[Server] Invalid command!");
            System.out.println("[Server] COMMANDS : " + this.globalVariables.COMMANDS);
            System.out.println("________________________");
            return;
        }
        System.out.println("________________________");
    }

    private String[][] parseMessage(String message) {
        String opcode = message.split(";")[0];
        String[] params = null;
        try {
            params = message.substring(message.indexOf(opcode) + opcode.length() + 1).split(";");
        } catch (Exception e) {
            // System.out.println("[Callback] parseMessage : ");
            // System.out.println("\t\t" + e.getMessage());
        }
        String[][] parsedMessage;
        if (params != null && params.length != 0) {
            parsedMessage = new String[2][params.length];
        } else {
            parsedMessage = new String[2][1];
        }

        parsedMessage[0][0] = opcode;
        parsedMessage[1] = params;

        return parsedMessage;
    }

    private class Callback implements MqttCallback {
        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
            String message = new String(mqttMessage.getPayload());

            // Add New Thread
            threads.add(new Thread() {
                @Override
                public void start() {
                    runCommand(s, message);
                }
            });

            synchronized (threads) {
                threads.notifyAll();
            }
        }

        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            // System.out.println("---\t---\t---");
            // System.out.println("[Callback] Delivery is completed!");
            // System.out.println(" : " + iMqttDeliveryToken);
            // System.out.println("---\t---\t---");
        }

        public void connectionLost(Throwable throwable) {
            System.out.println("---\t---\t---");
            System.out.println("[Callback] Connection to broker lost!");
            System.out.println("[Callback] Trying reconnect to broker.");
            try {
                subscriber.start(new Callback());
            } catch (MqttException e) {
                System.out.println("[Callback] MqttException :");
                System.out.println("\t\t" + e.getMessage());
            }
            System.out.println("---\t---\t---");
        }
    }
}
