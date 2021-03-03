
import java.io.IOException;
import java.util.UUID;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;
import org.eclipse.paho.client.mqttv3.*;

/**
 *
 * @author tbyor
 */
public class Client {

    // Singleton
    private static Client instance = null;

    // Constants
    private final ArrayList<String> TOPICS;
    private final ArrayList<String> COMMANDS_SERVER;
    private final ArrayList<String> COMMANDS_LOBBY;
    private final ArrayList<String> COMMANDS_GAME;
    private final String CLIENT_NAME = UUID.randomUUID().toString();
    private final int QOS = 2;
    private final int BROADCAST_PORT = 9876;

    // JSON File
    // Socket Properties
    DatagramSocket serverSocket;

    // MqttClient Properties
    private MqttClient client;
    private MqttConnectOptions options;
    private String broker;

    // Properties
    private String playerName = "defaultClient";
    private int lobbyID = -1;
    private boolean connected;

    private Client(MqttCallback callback) {
        // Initialize Constants
        this.TOPICS = new ArrayList<String>();
        this.COMMANDS_SERVER = new ArrayList<String>();
        this.COMMANDS_LOBBY = new ArrayList<String>();
        this.COMMANDS_GAME = new ArrayList<String>();

        // Initialize JSON File
        this.TOPICS.add("Ambiguity_Server");
        this.TOPICS.add("Ambiguity_Loby");
        this.TOPICS.add("Ambiguity_Game");
        this.TOPICS.add("Ambiguity_Response");
        this.COMMANDS_SERVER.add("createLoby");
        this.COMMANDS_SERVER.add("enterLoby");
        this.COMMANDS_SERVER.add("exitLoby");
        this.COMMANDS_SERVER.add("displayLoby");
        this.COMMANDS_SERVER.add("displayAll");
        this.COMMANDS_LOBBY.add("chat");
        this.COMMANDS_LOBBY.add("changeLobyName");
        this.COMMANDS_LOBBY.add("kickClient");
        this.COMMANDS_LOBBY.add("readyGame");
        this.COMMANDS_LOBBY.add("unreadyGame");
        this.COMMANDS_LOBBY.add("getClients");
        this.COMMANDS_GAME.add("move");
        this.COMMANDS_GAME.add("passTurn");
        this.COMMANDS_GAME.add("exit");
        this.COMMANDS_GAME.add("map");
        this.COMMANDS_GAME.add("gameTurn");
        this.COMMANDS_GAME.add("addEvent");

        // Initialize Socket
        this.broker = this.receiveServerIP();

        // Initialize MqttClient
        try {
            this.client = new MqttClient(this.broker, this.CLIENT_NAME);
            this.client.setCallback(callback);
            this.options = new MqttConnectOptions();
            this.options.setCleanSession(true);
        } catch (Exception e) {
            System.out.println("[Client] MqttException : constructor():" + e.getMessage());
        }

        this.connected = false;
    }

    public static Client getInstance(MqttCallback callback) {
        if (instance == null) {
            instance = new Client(callback);
        }

        return instance;
    }

    private String receiveServerIP() {
        String serverIP = "";
        try {
            this.serverSocket = new DatagramSocket(this.BROADCAST_PORT);
            byte[] receiveData = new byte[1024];

            do {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                for (int i = 0; i < receiveData.length; i++) {
                    if (receivePacket.getData()[i] != 0) {
                        serverIP += Character.toString((char) receiveData[i]);
                    } else {
                        break;
                    }
                }
                System.out.println("RECEIVED: [" + serverIP + "]");

                serverSocket.close();
            } while (serverIP.equals(""));
        } catch (IOException e) {
            System.out.println("[Client] Exception : receiveServerIP()");
        }
        return serverIP;
    }

    public void start() {
        try {
            this.client.connect(this.options);
            System.out.println("[Client] Client is connected.");
            this.client.subscribe(this.TOPICS.get(3), this.QOS);
            System.out.println("[Client] Client is subscribed to topic.");
            this.connected = true;
        } catch (MqttException e) {
            System.out.println("[Client] MqttException : start():" + e.getMessage());
        }
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean getConnected() {
        return this.connected;
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

    public String[][] runCommand(String topic, String message) {
        String[][] parsedMessage = parseMessage(message);
        String opcode = parsedMessage[0][0];
        String[] params = parsedMessage[1];

        if (this.COMMANDS_SERVER.get(0).equals(opcode)) {
            // Create Lobby
            return parsedMessage;
        } else if (this.COMMANDS_SERVER.get(4).equals(opcode)) {
            // Display All
            return parsedMessage;
        } else if (this.COMMANDS_SERVER.get(1).equals(opcode)) {
            // Enter Lobby
            if (params[params.length - 1].equals("true")) {
                this.lobbyID = Integer.parseInt(params[0]);
                try {
                    this.client.subscribe(this.TOPICS.get(3) + "_Loby_" + this.lobbyID);
                } catch (MqttException e) {
                    System.out.println("[Client] MqttException : runCommand()");
                }
            } else {
                this.lobbyID = -1;
            }
        } else if (this.COMMANDS_GAME.get(3).equals(opcode)) {
            // Map
            return parsedMessage;
        } else if (this.COMMANDS_GAME.get(4).equals(opcode)) {
            // Game Turn
            return parsedMessage;
        } else if (this.COMMANDS_SERVER.get(2).equals(opcode)) {
            try {
                this.client.unsubscribe(this.TOPICS.get(3) + "_Loby_" + this.lobbyID);
            } catch (MqttException ex) {
                System.out.println("[Client] MqttException : runCommand()");
            }
        } else if (this.COMMANDS_LOBBY.get(0).equals(opcode)) {
            // Chat
            return parsedMessage;
        } else if (this.COMMANDS_LOBBY.get(5).equals(opcode)) {
            // Get Client Names
            return parsedMessage;
        } else if (this.COMMANDS_LOBBY.get(3).equals(opcode)) {
            // Ready Game
            boolean success = params[params.length - 1].equals("true");
            String[][] readyGameMessage = new String[2][1];
            readyGameMessage[0][0] = opcode;
            readyGameMessage[1][0] = params[params.length - 1];
            return readyGameMessage;
        }

        String[][] val = new String[2][1];
        val[0][0] = opcode;
        if (params[1].equals(this.playerName)) {
            val[1][0] = params[params.length - 1];
        } else {
            val[1][0] = "non";
        }
        return val;
    }

    private void sendMessage(String topic, String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(this.QOS);
            this.client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            System.out.println("[Client] MqttException : sendMessage()");
            System.out.println("\t\t" + topic + ":" + message);
        }
    }

    public void createLobby() {
        this.sendMessage(this.TOPICS.get(0), this.COMMANDS_SERVER.get(0));
    }

    public void enterLobby(int index) {
        this.sendMessage(this.TOPICS.get(0), this.COMMANDS_SERVER.get(1) + ";" + index + ";" + this.playerName);
        this.lobbyID = index;
        this.getClientNames();
    }

    public void exitLobby() {
        this.sendMessage(this.TOPICS.get(0), this.COMMANDS_SERVER.get(2) + ";" + this.lobbyID + ";" + this.playerName);
        this.lobbyID = -1;
    }

    public void askLobyList() {
        this.sendMessage(this.TOPICS.get(0), this.COMMANDS_SERVER.get(4));
    }

    public void readyLobby() {
        this.sendMessage(this.TOPICS.get(1), this.COMMANDS_LOBBY.get(3) + ";" + this.lobbyID + ";" + this.playerName);
    }

    public void unreadyLobby() {
        this.sendMessage(this.TOPICS.get(1), this.COMMANDS_LOBBY.get(4) + ";" + this.lobbyID + ";" + this.playerName);
    }

    public void getClientNames() {
        this.sendMessage(this.TOPICS.get(1), this.COMMANDS_LOBBY.get(5) + ";" + this.lobbyID/* + ";" + this.playerName*/);
    }

    public void exit() {
        try {
            this.client.unsubscribe(this.TOPICS.get(3) + "_Loby_" + this.lobbyID);
        } catch (MqttException ex) {
            System.out.println("[Client] MqttException : exit()");
        }
        this.sendMessage(this.TOPICS.get(2), this.COMMANDS_GAME.get(2) + ";" + this.lobbyID + ";" + this.playerName);
    }

    public void sendChat(String message) {
        this.sendMessage(this.TOPICS.get(1), this.COMMANDS_LOBBY.get(0) + ";" + this.lobbyID + ";" + this.playerName + ";" + message);
    }

    public void move(int figureID, int[] figurePos, int[] rotateSymetry, int eventID, int[] eventPos) {
        String message = this.COMMANDS_GAME.get(0) + ";" + this.lobbyID + ";" + this.playerName + ";";
        message += figureID + ";";
        message += "[" + figurePos[0] + "," + figurePos[1] + "];";
        message += "[";
        for (int i = 0; i < rotateSymetry.length; i++) {
            message += rotateSymetry[i];

            if (i < rotateSymetry.length - 1) {
                message += ",";
            }
        }
        message += "];";
        message += eventID + ";";
        message += "[" + eventPos[0] + "," + eventPos[1] + "]";

        this.sendMessage(this.TOPICS.get(2), message);
    }

    public void addEvent(int eventID, int[] eventPos) {
        String message = this.COMMANDS_GAME.get(5) + ";" + this.lobbyID + ";" + this.playerName + ";";
        message += eventID + ";";
        message += "[" + eventPos[0] + "," + eventPos[1] + "]";

        this.sendMessage(this.TOPICS.get(2), message);
    }
}
