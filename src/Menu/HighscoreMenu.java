package Menu;

/**
 *
 * @author tbyor
 */
public class HighscoreMenu extends Menu {
    
    // Constants
    private final String TITLE = "Highscore";

    public HighscoreMenu(int id) {
        super(id);
        
        // Initialize Properties
        this.title.setText (this.TITLE);
        
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
