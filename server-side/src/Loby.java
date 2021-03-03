import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.paho.client.mqttv3.MqttException;

public class Loby implements Locatable {

    // Constants
    private final int LOBY_CLIENT_MAX_SIZE = 4;

    // Properties
    private GlobalVariables globalVariables;
    private Subscriber subscriber;
    private GameManager gameManager;
    private boolean[] readyStates;
    private String[] clients;
    private AI[] robots;
    private String lobyName;
    private int lobyID;
    private int size;
    private boolean isGameStarted;

    Loby(int lobyID) {
        // Initialize Properties
        this.globalVariables = GlobalVariables.getInstance();

        try {
            this.subscriber = Subscriber.getInstance();
        } catch (MqttException e) {
            System.out.println("[Loby] MqttException :");
            System.out.println("\t\t" + e.getMessage());
        }

        this.gameManager = new GameManager();
        this.gameManager.initialize(this.globalVariables.GAME_SPECS);
        this.readyStates = new boolean[this.LOBY_CLIENT_MAX_SIZE];
        this.clients = new String[this.LOBY_CLIENT_MAX_SIZE];
        for (int i = 0; i < this.LOBY_CLIENT_MAX_SIZE; i++) {
            this.clients[i] = "";
        }
        this.robots = new AI[this.LOBY_CLIENT_MAX_SIZE];
        this.lobyName = "Loby #" + lobyID;
        this.lobyID = lobyID;
        this.size = 0;
        this.isGameStarted = false;
    }

    public boolean addClient(String name) {
        if (this.isGameStarted) {
            return false;
        }

        if (this.size >= this.LOBY_CLIENT_MAX_SIZE) {
            return false;
        }

        for (String client : clients) {
            if (client.equals(name)) {
                return false;
            }
        }

        this.clients[this.size++] = name;
        return true;
    }

    public boolean removeClient(String name) {
        if (this.size <= 0) {
            return false;
        }

        for (int i = 0; i < this.size; i++) {
            if (clients[i].equals(name)) {
                if (this.isGameStarted) {
                    this.robots[i] = new AI(i);
                    this.clients[i] = this.robots[i].getName();
                    this.size--;
                    // If a player leaves the game on ıts own turn
                    if(i == this.gameManager.getTurn()) {
                        String topic = this.globalVariables.topicResponse + this.globalVariables.TOPIC_RESPONSE_TAG + this.lobyID;
                        this.moveByAI(this.gameManager.getTurn(), topic);
                    }
                    
                    return true;
                }

                for (int k = i; k < this.size - 1; k++) {
                    clients[k] = clients[k + 1];
                }
                clients[--this.size] = "";
                return true;
            }
        }

        return false;
    }

    public int getSize() {
        return this.size;
    }

    public int getLobyID() {
        return this.lobyID;
    }

    public boolean gameStarted() {
        return this.isGameStarted;
    }

    public boolean hasSpace() {
        return this.size != this.LOBY_CLIENT_MAX_SIZE;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void setLobyName(String name) {
        this.lobyName = name;
    }

    private void readyGame(String clientName, String topic) {
        if (this.isGameStarted) {
            return;
        }

        for (int i = 0; i < this.size; i++) {
            if (clients[i].equals(clientName)) {
                System.out.println("[Loby] Client is ready. : " + clientName);
                this.readyStates[i] = true;
                this.subscriber.sendMessage(topic, "readyGame;" + this.lobyID + ";" + clientName + ";true");

                if (this.size != this.LOBY_CLIENT_MAX_SIZE) {
                    // return;
                    // for AI debug
                    for(int k = 0; k < this.LOBY_CLIENT_MAX_SIZE; k++) {
                        if (this.clients[k].equals("")) {
                            this.robots[k] = new AI(k);
                            this.clients[k] = this.robots[k].getName();
                        }
                    }
                    //
                }

                boolean everyClientIsReady = true;
                for (boolean ready : this.readyStates) {
                    if (!ready) {
                        // everyClientIsReady = false;
                        break;
                    }
                }

                if (everyClientIsReady) {
                    this.isGameStarted = true;
                    this.gameManager.startGame();
                    System.out.println("[Loby] Game is started!");
                    this.subscriber.sendMessage(topic, "map;" + this.parseMap(this.gameManager.getCurrentMap()));
                    // to do (send message to client about gameturn)
                }
                //
            }
        }
    }

    public void unreadyGame(String clientName, String topic) {
        if(this.isGameStarted) {
            return;
        }

        for (int i = 0; i < this.size; i++) {
            if(this.clients[i].equals(clientName)) {
                System.out.println("[Loby] Client is unready. : " + clientName);
                this.readyStates[i] = false;
                // send message to chat
            }
        }
    }

    public void runCommand(String opcode, String[] params) {
        System.out.println("________________________");
        System.out.println("[Loby] #" + this.lobyID + " runCommand : " + opcode);
        String topic = this.globalVariables.topicResponse + this.globalVariables.TOPIC_RESPONSE_TAG + this.lobyID;
        if (!this.isGameStarted) {
            // Clients are at the LOBY STAGE
            if (this.globalVariables.COMMANDS_LOBY.get(0).equals(opcode)) {
                // Chat
                this.subscriber.sendMessage(topic, opcode + ";" + params[0] + ";" + params[1]);
            } else if (this.globalVariables.COMMANDS_LOBY.get(1).equals(opcode)) {
                // Change Loby Name
                this.lobyName = params[1];
                this.subscriber.sendMessage(topic, opcode + ";" + params[0] + ";" + params[1]);
            } else if (this.globalVariables.COMMANDS_LOBY.get(2).equals(opcode)) {
                // Kick
            } else if (this.globalVariables.COMMANDS_LOBY.get(3).equals(opcode)) {
                // Ready
                this.readyGame(params[0], topic);
            } else if (this.globalVariables.COMMANDS_LOBY.get(4).equals(opcode)) {
                // Unready
                this.unreadyGame(params[0], topic);
            } else if (this.globalVariables.COMMANDS_LOBY.get(5).equals(opcode)) {
                this.subscriber.sendMessage(topic, opcode + ";" + this.lobyID + ";" + Arrays.toString(clients));
            }
        } else {
            // Clients are at the GAME STAGE
            if (this.globalVariables.COMMANDS_GAME.get(0).equals(opcode)) {
                // Move
                System.out.println("205");
                boolean success = this.moveByClient(params);
                System.out.println("207");
                String messageToClients = "";
                if (success) {
                    messageToClients += "map;";
                    messageToClients += this.parseMap(this.gameManager.getCurrentMap());
                } else {
                    messageToClients = opcode + ";" + params[0] + ";false";
                }
                System.out.println("215");
                this.subscriber.sendMessage(topic, messageToClients);
                // to do (send message to clients about game turn)

                // If turn is on AI, play
                this.moveByAI(this.gameManager.getTurn(), topic);

            } else if (this.globalVariables.COMMANDS_GAME.get(1).equals(opcode)) {
                // Pass Turn
                if(this.clients[this.gameManager.getTurn()].equals(params[0])) {
                    this.gameManager.passTurn();
                }
                // to do (send message to clients about game turn)
            } else if (this.globalVariables.COMMANDS_GAME.get(5).equals(opcode)) {
                // Add Event

                if (this.clients[this.gameManager.getTurn()].equals(params[0])) {
                    this.gameManager.setEventID(Integer.parseInt(params[1]));
                    String[] eventPosString = params[2].substring(1, params[2].length() - 1).split(",");
                    int[] eventPos = {Integer.parseInt(eventPosString[0]), Integer.parseInt(eventPosString[1])};
                    locatable.setLocation(eventPos[0], eventPos[1]);
                    boolean success = this.gameManager.setEvent();

                    String messageToClients = "";
                    if (success) {
                        messageToClients += "map;";
                        messageToClients += this.parseMap(this.gameManager.getCurrentMap());
                    } else {
                        messageToClients = opcode + ";" + params[0] + ";false";
                    }

                    this.subscriber.sendMessage(topic, messageToClients);
                }
            }
        }
        System.out.println("________________________");
    }

    private String parseMap(Block[][] map) {
        String mapParse = "";

        for (int i = 0; i < map.length; i++) {
            for (int k = 0; k < map[0].length; k++) {
                mapParse += map[i][k].getPlayerNo() + "-";
                mapParse += map[i][k].getEvent() + "-";
                mapParse += map[i][k].getEmbeddedPlayerID();

                if (k < map[0].length - 1) {
                    mapParse += ",";
                }
            }

            if (i < map.length - 1) {
                mapParse += ";";
            }
        }
        return mapParse;
    }

    private boolean parseMove(String[] params) {
        // params[0] = figureID -> int
        int figureID = Integer.parseInt(params[0]);

        // params[1] = figurePos -> [x, y]
        String[] figurePosString = params[1].substring(1, params[1].length() - 1).split(",");
        int[] figurePos = { Integer.parseInt(figurePosString[0]), Integer.parseInt(figurePosString[1]) };

        // params[2] = rotateSymetry -> [1, 0, 0] -> [int...]
        String[] rotateSymetryString = params[2].substring(1, params[2].length() - 1).split(",");
        int[] rotateSymetry = new int[rotateSymetryString.length];
        for (int i = 0; i < rotateSymetry.length; i++) {
            rotateSymetry[i] = Integer.parseInt(rotateSymetryString[i]);
        }

        // params[3] = eventID -> int
        int eventID = Integer.parseInt(params[3]);

        // params[4] = eventPos -> [x, y]
        String[] eventPosString = params[4].substring(1, params[4].length() - 1).split(",");
        int[] eventPos = { Integer.parseInt(eventPosString[0]), Integer.parseInt(eventPosString[1]) };

        // Move
        locatable.setFigureID(figureID);
        locatable.setLocation(figurePos[0], figurePos[1]);
        this.gameManager.setFigure(figureID);
        for (int element : rotateSymetry) {
            if (element != -1) {
                locatable.getFigure().rotate(element != 1, element == 1);
            }
        }
        // to do for events
        System.out.println("306");
        return this.gameManager.move();
    }

    private boolean moveByClient(String[] params) {
        // params[0] -> clientName
        // params[1 to END] -> other params

        if (this.clients[this.gameManager.getTurn()].equals(params[0])) {
            String[] extendedParams = new String[params.length - 1];
            for (int i = 1; i < params.length; i++) {
                extendedParams[i - 1] = params[i];
            }
            System.out.println("318");
            return parseMove(extendedParams);
        } else {
            System.out.println("[Loby] Client cannot move in this turn. Turn: " + this.gameManager.getTurn());
            return false;
        }
    }

    private boolean moveByAI(int id, String topic) {
        if (this.size <= 0) {
            return false;
        }

        AI robot = this.robots[id];

        if (robot != null) {
            // Move by AI
            System.out.println("Move by " + robot.getName());
            boolean isProperMove = false; // this.gamemanager.move();

            if (!isProperMove) {
                this.gameManager.passTurn();
            }

            String messageToClients = "";
            if (isProperMove) {
                messageToClients += "map;";
                messageToClients += this.parseMap(this.gameManager.getCurrentMap());
            } else {
                messageToClients = this.globalVariables.COMMANDS_GAME.get(4) + ";" + this.gameManager.getTurn();
            }

            this.subscriber.sendMessage(topic, messageToClients);
            
            if (isProperMove) {
                messageToClients = this.globalVariables.COMMANDS_GAME.get(4) + ";" + this.gameManager.getTurn();
                this.subscriber.sendMessage(topic, messageToClients);
            }

            moveByAI(this.gameManager.getTurn(), topic);
            return true;
        }

        return false;
    }

    public String toString() {
        String val = "";
        val += this.lobyID + ",";
        val += this.lobyName + ",";
        val += this.size + "/" + this.LOBY_CLIENT_MAX_SIZE + ",";
        val += this.isGameStarted;
        return val;
    }
}