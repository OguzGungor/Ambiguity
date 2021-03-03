package Menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

public class GameModeMenu extends Menu {
    
    // Constants
    private final String TITLE = "Game Mode";
    
    // Properties
    protected Button casualModeButton;
    protected Button timeModeButton;
    protected Button versusModeButton;
    
    public GameModeMenu(int id) {
        super(id);
        
        // Initialize Properties
        this.title.setText (this.TITLE);
        this.casualModeButton = new Button ("CASUAL MODE");
        this.timeModeButton = new Button ("LIMITED TIME MODE");
        this.versusModeButton = new Button ("VERSUS MODE");
        
        // Initialize Preferred Size
        this.casualModeButton.setPrefSize(350, 75);
        this.timeModeButton.setPrefSize(350, 75);
        this.versusModeButton.setPrefSize(350, 75);
        this.setStyle("-fx-background-color: #0F4351;");
        casualModeButton.setStyle("-fx-background-color: #37CABA;");
        timeModeButton.setStyle("-fx-background-color: #37CABA;");
        versusModeButton.setStyle("-fx-background-color: #37CABA;");
      
        this.title.setStyle("-fx-font: 32 arial;");
        

// Initialize CSS ID
        //this.title.setId("title");
        
         // Center Panel Setup
        VBox buttonPanel = new VBox ();
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.setPadding(new Insets (0, 0, 0, 15));
        buttonPanel.setSpacing (10);
        // Center Panel Setup
        buttonPanel.getChildren ().add (this.casualModeButton);
        buttonPanel.getChildren ().add (this.timeModeButton);
        buttonPanel.getChildren ().add (this.versusModeButton);
        
        
        // Root Setup
        this.setBottom(this.footer);
        this.setCenter(buttonPanel);
    }
    
    @Override
    protected void initializeListeners() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}