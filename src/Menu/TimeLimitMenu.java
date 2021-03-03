package Menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author tbyor
 */
public class TimeLimitMenu extends Menu {
    
    // Constants
    private final String TITLE = "Time Limit";
    
    // Properties
    protected Button sec45Button;
    protected Button sec60Button;
    protected Button sec90Button;

    public TimeLimitMenu(int id) {
        super(id);
        
        // Initialize Properties
        this.title.setText (this.TITLE);
        this.sec45Button = new Button ("45 SEC.");
        this.sec60Button = new Button ("60 SEC.");
        this.sec90Button = new Button ("90 SEC.");
        
        // Initialize Preferred Size
        this.sec45Button.setPrefSize(300, 300);
        this.sec60Button.setPrefSize(300, 300);
        this.sec90Button.setPrefSize(300, 300);
        
        // Initialize CSS ID
        //this.title.setId("title");
        
        // Center Panel Setup
        this.centerPanel.getChildren ().add (this.sec45Button);
        this.centerPanel.getChildren ().add (this.sec60Button);
        this.centerPanel.getChildren ().add (this.sec90Button);
        
        // Root Setup
        this.setTop(this.header);
        this.setCenter(this.centerPanel);
    }

    @Override
    protected void initializeListeners() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
