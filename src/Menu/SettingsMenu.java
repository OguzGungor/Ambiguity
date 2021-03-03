package Menu;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author tbyor
 */
public class SettingsMenu extends Menu {
    
    // Constants
    private final String TITLE = "Settings";
    
    // Properties
    private CheckBox soundCheckBox;
    private CheckBox fxCheckBox;
    
    public SettingsMenu(int id) {
        super(id);
        
        // Initialize Properties
        this.title.setText (this.TITLE);
        this.soundCheckBox = new CheckBox ("Background Sound");
        this.fxCheckBox = new CheckBox ("FX Sounds");
        
        // Set Default Values
        this.soundCheckBox.setSelected(true);
        this.fxCheckBox.setSelected(true);
        
        // Center Panel Setup
        VBox menuPanel = new VBox ();
        menuPanel.setAlignment(Pos.CENTER);
        menuPanel.setSpacing (10);
        menuPanel.getChildren ().add (this.soundCheckBox);
        menuPanel.getChildren ().add (this.fxCheckBox);
        
        // Root Setup
        this.setTop(this.header);
        this.setCenter(menuPanel);
    }

    @Override
    protected void initializeListeners() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
