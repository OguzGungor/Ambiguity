package Menu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author tbyor
 */
public class GameSettingsMenu extends Menu {
    
    // Constants
    private final String TITLE = "SETTINGS";
    
    // Properties
    private CheckBox soundCheckBox;
    private CheckBox fxCheckBox;
    
    public GameSettingsMenu(int id) {
        super(id);
        
        // Initialize Properties
        this.title.setText (this.TITLE);
        this.soundCheckBox = new CheckBox ("Background Sound");
        this.fxCheckBox = new CheckBox ("FX Sounds");
        
        // Set Default Values
        this.soundCheckBox.setSelected(true);
        this.fxCheckBox.setSelected(true);
       this.setStyle("-fx-background-color: #0F4351;");
        // Center Panel Setup
        VBox menuPanel = new VBox ();
        menuPanel.setAlignment(Pos.CENTER);
        menuPanel.setSpacing (10);
        menuPanel.getChildren ().add (this.soundCheckBox);
        menuPanel.getChildren ().add (this.fxCheckBox);
        
        // Root Setup
        this.setBottom(this.footer);
        this.setCenter(menuPanel);
    }

    @Override
    protected void initializeListeners() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}