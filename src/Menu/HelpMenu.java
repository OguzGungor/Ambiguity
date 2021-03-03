package Menu;

import javafx.scene.control.Label;

/**
 *
 * @author tbyor
 */
public class HelpMenu extends Menu {
    
    // Constants
    private final String TITLE = "Help";
    private final String INFO = "How to Play IQ Puzzler PRO?";
    
    // Properties
    private Label helpLabel;
    
    public HelpMenu(int id) {
        super(id);
        
        // Initialize Properties
        this.title.setText (this.TITLE);
        this.helpLabel = new Label (this.INFO);
        
        // Initialize CSS ID
        this.helpLabel.setId("credits");
        
        // Center Panel Setup
        this.centerPanel.getChildren ().add (this.helpLabel);
        
        // Root Setup
        this.setTop(this.header);
        this.setCenter(this.centerPanel);
        this.setBottom(this.footer);
    }

    @Override
    protected void initializeListeners() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
