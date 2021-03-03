package Menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
/**
 *
 * @author shabnam
 */
public class MainMenu extends Menu {
    
    // Constants
    private final String TITLE = "AMBIGUITY";
    
    // Properties

    protected Button playButton;
    protected Button highscoreButton;
    protected Button settingsButton;
    protected Button helpButton;
    protected Button creditsButton;
    
    public MainMenu(int id) {
        super(id);
        
        // Initialize Properties
        this.title.setText (this.TITLE);
        this.playButton = new Button ("PLAY");
        this.highscoreButton = new Button ("HIGH SCORE");
        this.settingsButton = new Button ("SETTINGS");
        this.helpButton = new Button ("HELP");
        this.creditsButton = new Button ("CREDITS");
        
        // Initialize Preferred Size
        this.title.setPadding(new Insets (0, 0, 0, 0));
        this.playButton.setPrefSize(350, 75);
        this.highscoreButton.setPrefSize(350, 75);
        this.settingsButton.setPrefSize(350, 75);
        this.helpButton.setPrefSize(350, 75);
        this.creditsButton.setPrefSize(350, 75);
       
        this.setStyle("-fx-background-color: #0F4351;");
        playButton.setStyle("-fx-background-color: #37CABA;");
        highscoreButton.setStyle("-fx-background-color: #37CABA;");
        settingsButton.setStyle("-fx-background-color: #37CABA;");
        helpButton.setStyle("-fx-background-color: #37CABA;");
        creditsButton.setStyle("-fx-background-color: #37CABA;");
        
        this.title.setStyle("-fx-text-fill: #37CABA; -fx-font-size: 100px; -fx-font-weight: bold; -fx-font: Berlin Sans FB Demi;");
        
        
        // Center Panel Setup
        VBox menuPanel = new VBox ();
        menuPanel.setAlignment(Pos.CENTER);
        menuPanel.setPadding(new Insets (0, 0, 150, 0));  // (top , right , bottom, left )
        menuPanel.setSpacing (10);
        
        menuPanel.getChildren().add (this.playButton);
        menuPanel.getChildren().add (this.highscoreButton);
        menuPanel.getChildren().add (this.settingsButton);
        menuPanel.getChildren().add (this.helpButton);
        menuPanel.getChildren().add (this.creditsButton);
        
        // Root Setup
        this.setTop(this.header);
        this.setCenter(menuPanel);
        
      
    }
    
    @Override
    protected void initializeListeners() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString () {
        return "[MainMenu] " + this.getWidth() + ":" + this.getHeight();
    }
}