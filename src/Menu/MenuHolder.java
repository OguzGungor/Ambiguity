package Menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class MenuHolder extends Scene {
    
    // Constants
    public final int MAIN_MENU_ID = 0;
    public final int ID_CHOOSE_LOBY_MENU = 7;
    public final int ID_LOBY_MENU = 8;
    
    // Properties
    private boolean isGameScreenOn;
    private int[] specs;
    
    // Menu
    private Menu rootPanel;
    private MainMenu mainMenu;
    private HighscoreMenu highscoreMenu;
    public GameSettingsMenu settingsMenu;
    private HelpMenu helpMenu;
    private CreditsMenu creditsMenu;
    private ChooseLobbyMenu chooseLobbyMenu;
    private LobbyMenu lobbyMenu;
    //private StageMenu levelMenu;
// Play Menu
    private GameModeMenu gameModeMenu;
    private TimeLimitMenu timeLimitMenu;
    
    public MenuHolder (final double WIDTH, final double HEIGHT) {
        super(new Pane (), WIDTH, HEIGHT);
        
        // Initialize Properties
        this.isGameScreenOn = false;
        this.specs = new int [5];
        this.mainMenu = new MainMenu (0);
        this.highscoreMenu = new HighscoreMenu (1);
        this.settingsMenu = new GameSettingsMenu (2);
        this.helpMenu = new HelpMenu (3);
        this.creditsMenu = new CreditsMenu (4);
        this.gameModeMenu = new GameModeMenu (5);
        this.timeLimitMenu = new TimeLimitMenu (6);
        this.chooseLobbyMenu = new ChooseLobbyMenu(7);
        this.lobbyMenu = new LobbyMenu(8);
        //this.levelMenu = new StageMenu(9);
         
        this.rootPanel = this.mainMenu;
       
        // Default Values
        this.specs[0] = 1;
        
        // Initialize Listeners
        this.initializeListeners ();
        
        // Root Setup
        this.rootPanel.setMinSize (WIDTH, HEIGHT);
        this.setRoot (this.rootPanel);
    }
    
    private void initializeListeners () {
        this.initializeBackButtonListeners ();
        
        // [MainMenu] PLAY BUTTON
        this.mainMenu.playButton.setOnAction((ActionEvent event) -> {
            changeMenu (gameModeMenu.getID ());
        });
        
        // [MainMenu] HIGHSCORE BUTTON
        this.mainMenu.highscoreButton.setOnAction((ActionEvent event) -> {
            changeMenu (highscoreMenu.getID ());
        });
        
        // [MainMenu] SETTINGS BUTTON
        this.mainMenu.settingsButton.setOnAction((ActionEvent event) -> {
            changeMenu (settingsMenu.getID ());
        });
        
        // [MainMenu] HELP BUTTON
        this.mainMenu.helpButton.setOnAction((ActionEvent event) -> {
            changeMenu (helpMenu.getID ());
        });
        
        // [MainMenu] CREDITS BUTTON
        this.mainMenu.creditsButton.setOnAction((ActionEvent event) -> {
            changeMenu (creditsMenu.getID ());
        });
    }
    
    public void initializePlayListeners (EventHandler<ActionEvent> casualHandler, EventHandler<ActionEvent> versusHandler, EventHandler<ActionEvent> readyHandler,  EventHandler<ActionEvent> sec45Handler, EventHandler<ActionEvent> sec60Handler, EventHandler<ActionEvent> sec90Handler) {
        // [GameModeMenu] CASUAL MODE BUTTON
        this.gameModeMenu.casualModeButton.setOnAction(casualHandler);
        
        // [GameModeMenu] TIME MODE BUTTON
        this.gameModeMenu.timeModeButton.setOnAction((ActionEvent event) -> {
            specs [0] = 0;
            changeMenu (timeLimitMenu.getID ());
        });
        
        // [GameModeMenu] VERSUS MODE BUTTON
      this.gameModeMenu.versusModeButton.setOnAction(versusHandler);
      
      this.lobbyMenu.readyButton.setOnAction(readyHandler);
        
        // [TimeLimitMenu] SEC 45 BUTTON
      this.timeLimitMenu.sec45Button.setOnAction(sec45Handler);
        
        // [TimeLimitMenu] SEC 60 BUTTON
      this.timeLimitMenu.sec60Button.setOnAction(sec60Handler);
        
        // [TimeLimitMenu] SEC 90 BUTTON
      this.timeLimitMenu.sec90Button.setOnAction(sec90Handler);
      
//       this.timeLimitMenu.sec45Button.setOnAction((ActionEvent event) -> {
//            specs [0] = 0;
//            changeMenu (levelMenu.getID ());
//        });
//       
//       this.timeLimitMenu.sec60Button.setOnAction((ActionEvent event) -> {
//          specs [0] = 0;
//          changeMenu (levelMenu.getID ());
//       });
//       
//       this.timeLimitMenu.sec90Button.setOnAction((ActionEvent event) -> {
//          specs [0] = 0;
//          changeMenu (levelMenu.getID ());
//       });

      // this.levelMenu.button1.setOnAction(sec90Handler);
    }
    
    private void initializeBackButtonListeners () {
        // Initialize Listener
        EventHandler<ActionEvent> handler = (ActionEvent event) -> {
            if (rootPanel.getID() > gameModeMenu.getID ()) {
                changeMenu (rootPanel.getID() - 1);
            } else {
                changeMenu (mainMenu.getID());
            }
        };
        
        // [HighscoreMenu] BACK BUTTON
        this.highscoreMenu.setBackButtonListener (handler);
        
        // [SettingsMenu] BACK BUTTON
        this.settingsMenu.setBackButtonListener (handler);
        
        // [HelpMenu] BACK BUTTON
        this.helpMenu.setBackButtonListener (handler);
        
        // [CreditsMenu] BACK BUTTON
        this.creditsMenu.setBackButtonListener (handler);
        
        // [GameModeMenu] BACK BUTTON
        this.gameModeMenu.setBackButtonListener (handler);
        
        // [TimeLimitMenu] BACK BUTTON
        this.timeLimitMenu.setBackButtonListener (handler);
        
        // [ChooseLobbyMenu] BACK BUTTON
        this.chooseLobbyMenu.setBackButtonListener(handler);
        
        //[LobbyMenu] BACK BUTTON
        this.lobbyMenu.setBackButtonListener(handler);
        
        //
      //  this.casualModeMenu.setBackButtonListener(handler);
    }
    
    public void changeMenu (int index) {
        System.out.println ("[MenuHolder] changeMenu : " + index);
        switch (index) {
         //   case 0: this.rootPanel = this.mainMenu; break;
           case 1: this.rootPanel = this.highscoreMenu; break;
           case 2: this.rootPanel = this.settingsMenu; break;
           case 3: this.rootPanel = this.helpMenu; break;
           case 4: this.rootPanel = this.creditsMenu; break;
           case 5: this.rootPanel = this.gameModeMenu; break;
           case 6: this.rootPanel = this.timeLimitMenu; break;
           case 7: this.rootPanel = this.chooseLobbyMenu; break;
           case 8: this.rootPanel = this.lobbyMenu; break;
           //case 9: this.rootPanel = this.levelMenu; break;
           default: this.rootPanel = this.mainMenu; break;
        }
        
        this.setRoot (this.rootPanel);
    }
    
    public void setGameScreenOn (boolean val) {
        this.isGameScreenOn = val;
    }
    
    public void setGameMode (int action) {
        this.specs[0] = action;
    }
    
    public void setSpecTime (int action) {
        this.specs [1] = action;
    }
    
    public boolean getGameScreenOn () {
        return this.isGameScreenOn;
    }
    
    public int[] getAction () {
        return this.specs;
    }
    
    public void setLobyList(String[] lobyList) {
        this.chooseLobbyMenu.setLobyList(lobyList);
    }
    
    public void initializeChooseLobbyMenuInitializers(EventHandler<ActionEvent> submitHandler, EventHandler<ActionEvent> createLobbyHandler, EventHandler<ActionEvent> joinLobyHandler, EventHandler<ActionEvent> refreshHandler, EventHandler<ActionEvent> backButtonLobbyHandler, EventHandler<ActionEvent> changeNameHandler) {
        this.lobbyMenu.submitButton.setOnAction(submitHandler);
        this.chooseLobbyMenu.createLobbyButton.setOnAction(createLobbyHandler);
        this.chooseLobbyMenu.joinLobbyButton.setOnAction(joinLobyHandler);
        this.chooseLobbyMenu.startButton.setOnAction(refreshHandler); // change name of the button
        this.lobbyMenu.backButton.setOnAction(backButtonLobbyHandler);
        this.chooseLobbyMenu.setPlayerNameButton.setOnAction(changeNameHandler);
    }
    
    public String getPlayerName() {
        return this.chooseLobbyMenu.playerNameArea.getText();
    }
    
    public int getSelectedLobbyID() {
        return this.chooseLobbyMenu.getSelectedLobbyID();
    }
    
    public String getMessage() {
        return this.lobbyMenu.message();
    }
    
    public void setChat(String val) {
        this.lobbyMenu.addTextToChat(val);
    }
    
    public void setClients(String clientNames) {
        this.lobbyMenu.setClients(clientNames);
    }
}
