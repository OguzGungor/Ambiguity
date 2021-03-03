package Menu;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
/**
 *
 * @author tbyor
 */
public class CreditsMenu extends Menu {
    
    // Constants
    private final String TITLE = "CREDITS";

    private String names = "Mustafa Oguz Gungor \n" + "Taylan Bartu Yoran \n" + "Shabnam Sadigova \n"+
       "Ali Can Zeybek \n" + "Asena Shahin";
       
    Label label;
    // Properties
  
    
    public CreditsMenu(int id) {
        super(id);
        // Initialize Properties
        this.title.setText (this.TITLE);
        
        label = new Label(names);
        label.setWrapText(true);
        
        this.title.setPadding(new Insets (50, 0, 0, 450)); // (top , right , bottom, left )
        // set style of labels and background, title
        this.setStyle("-fx-background-color: #0F4351;");
        label.setStyle("-fx-text-fill: #37CABA; -fx-font-size: 40px; -fx-font-weight: bold; -fx-font: Berlin Sans FB Demi;");
        title.setStyle("-fx-text-fill: #37CABA; -fx-font-size: 100px; -fx-font-weight: bold; -fx-font: Berlin Sans FB Demi;");
        // add label to centerPanel
        centerPanel.getChildren().add(label);
        // Root Setup
        this.setBottom(this.footer);
        this.setCenter(centerPanel);
        this.setTop(this.title);
    }

    @Override
    protected void initializeListeners() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}