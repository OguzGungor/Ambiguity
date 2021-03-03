package Menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;


public class ChooseLobbyMenu extends Menu
{
   //constants
   private final String TITLE = "Versus Mode Lobby";
   
   //properties
   private String[] lobyList;
   
   //protected buttons;
   protected Button startButton;
   protected Button createLobbyButton;
   protected Button joinLobbyButton;
   
   // protected labels
   protected Label lobbyName; // = "Lobby Name";
   protected Label lobbySize; // = "Lobby Size";
   
   // textarea
   protected TextArea textField;
   protected TextArea playerNameArea;
   protected Button setPlayerNameButton;
  
   public ChooseLobbyMenu(int id)
   {
      super(id);
      
      //Initialize Properties
      this.lobyList = new String[1];
      this.title.setText(this.TITLE);
      this.startButton = new Button ("REFRESH");
      this.createLobbyButton = new Button ("CREATE LOBBY");
      this.joinLobbyButton = new Button ("JOIN");
    
      this.lobbyName = new Label("Lobby Name");
      this.lobbySize = new Label("Lobby Size");
      
      lobbyName.setStyle("-fx-text-fill: #02927A; -fx-font-size: 30px; -fx-font-weight: bold; -fx-font: Berlin Sans FB Demi;");
      lobbySize.setStyle("-fx-text-fill: #02927A; -fx-font-size: 30px; -fx-font-weight: bold; -fx-font: Berlin Sans FB Demi;");
      
      // Initialize Preferred Size
      this.title.setPadding(new Insets (0, 0, 0, 0));
      this.startButton.setPrefSize(200, 50);
      this.createLobbyButton.setPrefSize(200, 50);
      this.joinLobbyButton.setPrefSize(200, 50);
      
      this.setStyle("-fx-background-color: #0F4351;");
      startButton.setStyle("-fx-background-color: #37CABA; -fx-text-fill: #02927A; -fx-font-size: 20px; -fx-font-weight: bold; -fx-font: Berlin Sans FB Demi;");
      createLobbyButton.setStyle("-fx-background-color: #37CABA; -fx-text-fill: #02927A; -fx-font-size: 20px; -fx-font-weight: bold; -fx-font: Berlin Sans FB Demi;");
      joinLobbyButton.setStyle("-fx-background-color: #37CABA; -fx-text-fill: #02927A; -fx-font-size: 20px; -fx-font-weight: bold; -fx-font: Berlin Sans FB Demi;");
      
      //this.lobbyName.setStyle("-fx-font: 48 Berlin Sans FB Demi;");
      
      
      // Header Setup
      HBox header = new HBox ();
      header.setAlignment (Pos.CENTER);
      header.getChildren ().add (this.title);
      
      // create a panel for buttons and set it
      VBox buttonsPanel = new VBox ();
      buttonsPanel.setAlignment(Pos.CENTER);
      buttonsPanel.setPadding(new Insets (0, 200, 0, 0));   // (top , right , bottom, left )
      buttonsPanel.setSpacing (20);
      
      
      buttonsPanel.getChildren().add (this.startButton);
      buttonsPanel.getChildren().add (this.createLobbyButton);
      buttonsPanel.getChildren().add (this.joinLobbyButton);
      
      //Create a text area and set it
      this.textField = new TextArea();
      textField.setPrefHeight(400);
      textField.setPrefWidth(800);
      textField.setPrefRowCount(2);
      textField.setEditable(false);
      
      this.playerNameArea = new TextArea();
      this.playerNameArea.setText("Enter player name");
      this.playerNameArea.setPrefSize(200, 50);
      this.playerNameArea.setPrefRowCount(1);
      this.playerNameArea.setEditable(true);
      this.setPlayerNameButton = new Button("CHANGE NAME");
      this.setPlayerNameButton.setPrefSize(200, 50);
      
      buttonsPanel.getChildren().addAll(this.playerNameArea, this.setPlayerNameButton);
      //VBox playerNameBox = new VBox();
      //playerNameBox.getChildren().add(playerNameArea);
      //playerNameBox.getChildren().add(setPlayerNameButton);
      
      HBox hbox = new HBox();
      hbox.setPrefHeight(300);
      hbox.setPrefWidth(800);
      hbox.getChildren().add(textField);
      hbox.setPadding(new Insets(0,0,50,150));  // (top , right , bottom, left )
      ScrollPane scroll = new ScrollPane();
      scroll.setContent(textField);
      scroll.setFitToWidth(true);
      scroll.setFitToHeight(true);
      scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
      scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
      scroll.setPannable(true);
      hbox.getChildren().add(scroll);

// create a panel for header of textArea and set it
      HBox hb = new HBox();
      hb.setPrefHeight(10);
      hb.setPrefWidth(50);
      hb.setSpacing (200);
      hb.getChildren().add(lobbyName);
      hb.getChildren().add(lobbySize);
     // hb.setStyle("-fx-font: 60;");
      hb.setPadding(new Insets(80,0,10,200));   // (top , right , bottom, left )
    //  hb.setStyle("-fx-background-color: #37CABA;");
    //  hbox.getChildren().add(hb);
      
      //put themon the screen
      this.setRight(buttonsPanel);
      this.setLeft(hbox);
      this.setTop(hb);
      this.setBottom(this.footer);
   
   }
   
   protected void setLobyList(String[] lobyList) {
       this.lobyList = lobyList;
       this.displayLobies();
       
   }
   
   private void displayLobies() {
       String val = "";
       for(String loby : this.lobyList) {
           String[] row = loby.split(",");
           try {
            val += row[1] + "\t\t\t\t\t\t\t" + row[2]+ "\n";
           } catch (Exception e) {
               
           }
       }
       
       this.textField.setText(val);
   }
   
   protected int getSelectedLobbyID() {
       return 0;
   }
   
   
    @Override
    protected void initializeListeners() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
