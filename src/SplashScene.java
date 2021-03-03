import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 * @author tbyor
 */
public class SplashScene extends Scene {
    
    // Constants
    private final String COMPANY_NAME = "AMBIGUITY";
    private final String PRESS_BUTTON = "Press Any Key to Continue";
    
    // Properties
    private final BorderPane rootPanel;
    private final Label compName;
    private final Label pressToCont;
    private final Glow glow;
    private Timer timer;
    private TimerTask timerTaskGlow;
    private TimerTask timerTaskColor;
    private TimerTask timerTaskOpacity;
    private double glowLevel;
    private double opacityLevel;
    
    public SplashScene(final double WIDTH, final double HEIGHT) {
        super(new Pane(), WIDTH, HEIGHT);
        
        // Initialize Properties
        this.rootPanel = new BorderPane();
        this.compName = new Label (this.COMPANY_NAME);
        this.pressToCont = new Label (this.PRESS_BUTTON);
        this.glow = new Glow ();
        this.timer = new Timer ();
        this.glowLevel = 0;
        this.opacityLevel = 1;
        
        // CSS Setup
        this.rootPanel.setStyle("-fx-background-color: #0F4351;-fx-text-fill: white;");
        this.compName.setStyle("#ffffff;-fx-font-size:64;");
        this.pressToCont.setStyle("-fx-text-fill: white;-fx-text-align: center;");
        this.rootPanel.setId("SplashScene");
        this.compName.setId("compName");
        this.pressToCont.setId("pressToCont");
        
        // Glow Setup
        this.compName.setEffect(this.glow);
        
        // Timer Task Setup
        this.timerTaskGlow = new TimerTask () {
            
            private boolean reverse = false;
            
            @Override
            public void run() {
                if (glowLevel < 0)
                    reverse = false;
                else if (glowLevel > 1)
                    reverse = true;
                
                if (reverse)
                    glowLevel -= 0.1;
                else
                    glowLevel += 0.1;
                
                glow.setLevel(glowLevel);
            }
        };
        
        this.timerTaskColor = new TimerTask() {
            
            private int r = 255;
            private int g = 255;
            private int b = 255;
            private boolean reverse = false;
            
            @Override
            public void run() {
                if (r <= 120) {
                    reverse = true;
                } else if (r >= 255 && g >= 255) {
                    reverse = false;
                }
                
                if (reverse) {
                    r += 3;
                    g += 1;
                } else {
                    r -= 3;
                    g -= 1;
                }
                
                compName.setTextFill(Color.rgb (r, g, b));
            }
        };
        
        this.timerTaskOpacity = new TimerTask () {
            
            private boolean reverse = false;
            
            @Override
            public void run() {
                if (opacityLevel < 0)
                    reverse = true;
                else if (opacityLevel > 1)
                    reverse = false;
                
                if (reverse)
                    opacityLevel += 0.05;
                else
                    opacityLevel -= 0.05;
                
                pressToCont.setOpacity(opacityLevel);
            }
        };
        
        this.timer.schedule(timerTaskGlow, 0, 40);
        this.timer.schedule(timerTaskColor, 0, 40);
        this.timer.schedule(timerTaskOpacity, 0, 55);
        
        // Root Setup
        VBox title = new VBox();
        title.getChildren().add(this.compName);
        title.getChildren().add(this.pressToCont);
        this.rootPanel.setCenter(title);
        
        this.setRoot(this.rootPanel);
    }
    
    protected void executeTimer() {
        
    }
}
