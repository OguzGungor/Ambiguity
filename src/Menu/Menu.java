package Menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author tbyor
 */
public abstract class Menu extends BorderPane {
    
    // UI Components
    protected BorderPane header;
    protected HBox centerPanel;
    protected BorderPane footer;
    protected Label title;
    public Button backButton;
    
    // Properties
    protected int id;
    
    Menu (int id) {
        super ();
        
        // Initialize Properties
        this.id = id;
        this.header = new BorderPane ();
        this.footer = new BorderPane();
        this.centerPanel = new HBox ();
        this.title = new Label ();
        this.backButton = new Button ("Back");
        
        // Initialize Preferred Size
        this.title.setPadding(new Insets (0, 100, 0, 0));
        this.backButton.setPrefSize(50, 30);
        
        // Initialize CSS ID
        this.setId("Menu");
        this.title.setId("title");
        this.backButton.setId("primary");
        
        this.setStyle("-fx-background-color: #0F4351;");
        backButton.setStyle("-fx-background-color: #37CABA;");
        
        // Header Setup
        this.footer.setPadding(new Insets (15, 15, 15, 15));
        this.footer.setLeft (this.backButton);
        this.header.setPadding(new Insets (100, 100, 100, 100));
        this.header.setCenter (this.title);
        
        // Center Panel Setup
        this.centerPanel.setAlignment (Pos.CENTER);
        this.centerPanel.setSpacing (20);
    }
    
    public int getID () {
        return this.id;
    }
    
    public void setBackButtonListener (EventHandler<ActionEvent> handler) {
        this.backButton.setOnAction(handler);
    }
    
    protected abstract void initializeListeners ();
}