import java.util.ArrayList;

public class GlobalVariables {

    // Singleton
    private static GlobalVariables instance = null;

    // Constants
    public final ArrayList<String> COMMANDS;
    public final ArrayList<String> COMMANDS_LOBY;
    public final ArrayList<String> COMMANDS_GAME;
    public final int[] GAME_SPECS = {2, 0, 0, 0, 0};
    public final String JSON_CONFIG_PATH = "./src/config/config.JSON";
    public final String JSON_PORT_KEY = "port";
    public final String JSON_SERVER_TOPIC_KEY = "TOPIC_SERVER";
    public final String JSON_LOBY_TOPIC_KEY = "TOPIC_LOBY";
    public final String JSON_GAME_TOPIC_KEY = "TOPIC_GAME";
    public final String JSON_RESPONSE_TOPIC_KEY = "TOPIC_RESPONSE";
    public final String JSON_COMMANDS_KEY = "commands";
    public final String JSON_SERVER_COMMANDS_KEY = "serverCommands";
    public final String JSON_LOBY_COMMANDS_KEY = "lobyCommands";
    public final String JSON_GAME_COMMANDS_KEY = "gameCommands";
    public final String TOPIC_RESPONSE_TAG = "_Loby_";
    
    // Properties
    public String topicLoby;
    public String topicGame;
    public String topicResponse;
    public int port = 1883;
    public int broadcastPort = 9876;

    private GlobalVariables() {
        this.COMMANDS = new ArrayList<String>();
        this.COMMANDS_LOBY = new ArrayList<String>();
        this.COMMANDS_GAME = new ArrayList<String>();
    }

    public static GlobalVariables getInstance() {
        if (instance == null) {
            instance = new GlobalVariables();
        }

        return instance;
    }
}