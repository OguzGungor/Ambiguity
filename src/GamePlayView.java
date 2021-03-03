import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import java.awt.GridLayout;
import javafx.scene.layout.Region;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author tbyor
 */
public class GamePlayView extends Scene {

   private TilePane rightPanel;
   private int playerNo = 1;
   
    // Constants
    // Properties
    private BorderPane rootPanel;
    protected BorderPane header;
    protected TilePane mapView;
    private TilePane footer;

    // Map Properties
    private Block[][] map;
    private int rectSize;
    private int figureID;
    private int gameType;
    
    //for events
    private int eventID;

    // Preview Properties
    private Block[][] previewFigure;
    private int previewX;
    private int previewY;
    private int previewW;
    private int previewH;
    private int mouseX;
    private int mouseY;

    //for events
    private Block[][] previewEvent;
    
    // Header Components
    private Label scoreLabel;
    private Label timeLabel;
    private Label moveCountLabel;
    private Label playerTurnLabel;
    protected Button backButton;
    protected Button settingsButton;

    // MapView Components
    private JPanel mapPanel;
    private final SwingNode mapNode;

    // Footer Components
    private JPanel figurePanel;
    private final SwingNode figureNode;
    private Image[] figureImages;
    private int[] figureContainer;
  
    // Event components
    private JPanel eventPanel;
    private final SwingNode eventNode;
    private Image[] eventImages;
    private int[] eventContainer;
    
    public GamePlayView(final double WIDTH, final double HEIGHT) {
        super(new Pane(), WIDTH, HEIGHT);
        
        
        rightPanel = new TilePane();
        rightPanel.setStyle("-fx-background-color: #0F4551;");
        rightPanel.setPrefHeight(20);
        rightPanel.setPrefWidth(150);

        // Initialize Properties
        this.rootPanel = new BorderPane();
        this.header = new BorderPane();
        this.mapView = new TilePane();
        this.footer = new TilePane();

        this.map = new Block[1][1];
        this.rectSize = -1;
        this.figureID = -1;
        this.eventID = -1;

        this.previewFigure = new Block[1][1];
        
        //for event
        this.previewEvent = new Block[1][1];
        
        this.previewX = -1;
        this.previewY = -1;
        this.previewW = -1;
        this.previewH = -1;
        this.mouseX = -1;
        this.mouseY = -1;

        this.scoreLabel = new Label("Score: 000");
        this.timeLabel = new Label("Time: 000");
        this.moveCountLabel = new Label("Move Count: 000");
        this.playerTurnLabel = new Label("Player " + playerNo + "'s turn");
        this.backButton = new Button("Back");
        this.settingsButton = new Button("Settings");

        this.mapPanel = null;
        this.mapNode = new SwingNode();

        this.figurePanel = null;
        this.figureNode = new SwingNode();
        this.figureImages = new Image[1];
        this.figureContainer = new int[1];

        // for events
        this.eventPanel = null;
        this.eventNode = new SwingNode();
        this.eventImages = new Image[1];
        this.eventContainer = new int[1];
        
        // Initialize Preferred Size
        this.backButton.setPrefSize(100, 50);
        this.settingsButton.setPrefSize(150, 50);

//        // Initialize CSS ID
//        this.rootPanel.setId("GamePlayView");
//        this.header.setId("header");
//        this.mapView.setId("mapView");
//        this.footer.setId("footer");
//        this.backButton.setId("secondary");
//        this.settingsButton.setId("secondary");

        // Header Setup
        HBox infoBox = new HBox();
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setSpacing(25);
        infoBox.getChildren().add(this.scoreLabel);
        infoBox.getChildren().add(this.timeLabel);
        infoBox.getChildren().add(this.moveCountLabel);
        infoBox.getChildren().add(this.playerTurnLabel);
        this.header.setLeft(this.backButton);
        this.header.setRight(this.settingsButton);
        this.header.setCenter(infoBox);

        // MapView Setup
        this.mapView.setPadding(Insets.EMPTY);
        this.mapView.setAlignment(Pos.CENTER);
        this.mapPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Draw Map
                for (int i = 0; i < map.length; i++) {
                    for (int k = 0; k < map[0].length; k++) {
                        java.awt.Color color;
                        if(gameType != 2){
                        switch (map[i][k].getFigureID()) {
                            case 0:
                                color = java.awt.Color.WHITE;
                                break;
                            case 1:
                                color = java.awt.Color.GREEN;
                                break;
                            case 2:
                                color = java.awt.Color.YELLOW;
                                break;
                            case 3:
                                color = java.awt.Color.PINK;
                                break;
                            case 4:
                                color = java.awt.Color.BLUE;
                                break;
                            case 5:
                                color = java.awt.Color.RED;
                                break;
                            case 6:
                                color = java.awt.Color.CYAN;
                                break;
                            case 7:
                                color = java.awt.Color.MAGENTA;
                                break;
                            case 8:
                                color = java.awt.Color.ORANGE;
                                break;
                            case 9:
                                color = java.awt.Color.GRAY;
                                break;
                            case 10:
                                color = java.awt.Color.LIGHT_GRAY;
                                break;
                            case 11:
                                color = java.awt.Color.DARK_GRAY;
                                break;
                            case 12:
                                color = java.awt.Color.BLACK;
                                break;
                            default:
                                color = java.awt.Color.WHITE;
                                break;
                        }
                        g.setColor(color);
                            g.fillRect(i * rectSize, k * rectSize, rectSize, rectSize);
                            g.setColor(java.awt.Color.BLACK);
                            g.drawRect(i * rectSize, k * rectSize, rectSize, rectSize);
                        }   else{
                        
                       //color = Color.WHITE;
//                    switch (map[i][k].getPathNo()) {
//                      case 0: color = Color.BLACK; break;
//                      case 1: color = Color.GREEN; break;
//                      case 2: color = Color.YELLOW; break;
//                      case 3: color = Color.PINK; break;
//                      case 4: color = Color.BLUE; break;
//                      case 5: color = Color.RED; break;
//                      case 6: color = Color.CYAN; break;
//                    }
                    switch (map[i][k].getPlayerNo()) {
                      case 1: color = Color.CYAN; break;
                      case 2: color = Color.YELLOW; break;
                      case 3: color = Color.ORANGE; break;
                      case 4: color = Color.MAGENTA; break;   
                      case 5: color = Color.BLACK; break;  
                      default : color = new Color(219,219,219, 100); break;
                    }                    
              
                            g.setColor(color);
                            g.fillRect(i * rectSize, k * rectSize, rectSize, rectSize);
                            
                            
                            
                             if(map[i][k].getEvent() != 0){
                                // to do
                                g.drawImage(eventImages[map[i][k].getEvent()-1],i *rectSize,k*rectSize,rectSize,rectSize,this);
                             }
                             
                             g.setColor(java.awt.Color.BLACK);
                            g.drawRect(i * rectSize, k * rectSize, rectSize, rectSize);
                        }
                    }
                }

                // Draw Preview
                if (previewX >= 0 && previewY >= 0 && previewX < map.length && previewY < map[0].length) {
                    g.setColor(new Color(238, 66, 244, 75));
                    for (int t = 0; t < previewFigure.length; t++) {
                        for (int m = 0; m < previewFigure[0].length; m++) {
                            if (previewFigure[t][m].getFigureID() != 0) {
                                g.setColor(new Color(238, 66, 244, 75));
                                if (figureID == -1) {
                                    g.fillRect((previewX + t) * rectSize, (previewY + m) * rectSize, rectSize, rectSize);
                                } else {
                                    g.fillRect((previewX + t - previewW) * rectSize, (previewY + m - previewH) * rectSize, rectSize, rectSize);
                                }
                            }
                        }
                    }
                 
                }

                // Draw Selected Figure Image
                if (figureID >= 0 && figureID < figureContainer.length) {
                    g.drawImage(figureImages[figureID], mouseX, mouseY, this);
                }
                
                
                //Draw selected event image
                if(eventID >= 0 && eventID < eventContainer.length)
                   g.drawImage(eventImages[eventID], mouseX, mouseY, rectSize, rectSize, this);
            }
        };
        this.mapPanel.setOpaque(true);

        // Footer Setup
        this.footer.setPadding(Insets.EMPTY);
        this.figurePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (int i = 0; i < figureImages.length; i++) {
                    if (figureImages[i] == null) {
                        return;
                    }

                    if (figureContainer[i] != 0) {
                        g.drawImage(figureImages[i], i * 75, 0, this);
                    }
                }
                
               
            }
        };
        this.figurePanel.setBackground(new Color(80, 80, 80));

    
        this.rightPanel.setPadding(Insets.EMPTY);
        eventPanel = new JPanel()
        {
           @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (int i = 0; i < eventImages.length; i++) {
                    if (eventImages[i] == null) {
                        return;
                    }
                    if(i < 4 && i > 0){
                        if (eventContainer[i] != 0) {
                            g.drawImage(eventImages[i], 0, (i-1) * 75, this);
                        }
                    
                    }
                }
                
           }
        };
        this.eventPanel.setBackground(new Color(80,80,80));
       
     //   figurePanel.setLayout(new GridLayout(100,10,100,300));
        
        // Root Setup
        this.rootPanel.setMinSize(WIDTH, HEIGHT);
        this.rootPanel.setTop(this.header);
        this.rootPanel.setBottom(this.footer);
        this.rootPanel.setCenter(this.mapView);
        this.setRoot(this.rootPanel);
    }

    public void initializeListeners(MouseInputAdapter mouseMotionAdapter, MouseInputAdapter mouseMapAdapter, MouseInputAdapter mouseFigureAdapter, MouseInputAdapter mouseEventAdapter) {
        this.mapPanel.addMouseListener(mouseMapAdapter);
        this.mapPanel.addMouseListener(mouseMotionAdapter);
        this.figurePanel.addMouseListener(mouseFigureAdapter);
        this.figurePanel.addMouseListener(mouseMotionAdapter);
        
        //for event 
        this.eventPanel.addMouseListener(mouseEventAdapter);
        this.eventPanel.addMouseListener(mouseMotionAdapter);

        this.mapPanel.addMouseMotionListener(mouseMotionAdapter);
        this.figurePanel.addMouseMotionListener(mouseMotionAdapter);
        
        //for event
        this.eventPanel.addMouseMotionListener(mouseMotionAdapter);
    }
    
    EventHandler<MouseEvent> mouseEntered = new EventHandler<MouseEvent>() {
       @Override
       public void handle(MouseEvent e) {
          rightPanel.setStyle("-fx-background-color: black");
       }
    };
    EventHandler<MouseEvent> mouseExited = new EventHandler<MouseEvent>() {
       @Override
       public void handle(MouseEvent e) {
          rightPanel.setStyle("-fx-background-color:green");
       }
    };
   
   
  public void drawMap(Block[][] map) {
        if (this.map.length == map.length && this.map[0].length == map[0].length) {
            this.map = map;
            return;
        }

        this.map = map;
        int size = Math.min((int) mapView.getWidth() / map.length, (int) mapView.getHeight() / map[0].length);

        Dimension dim = new Dimension(map.length * size + 1, map[0].length * size + 1);
        this.mapPanel.setSize(dim);
        this.mapPanel.setPreferredSize(dim);
        this.mapPanel.setMinimumSize(dim);
        this.mapNode.setContent(this.mapPanel);
        try {
            this.mapView.getChildren().add(this.mapNode);
        } catch (Exception e) {
            
        }

        this.rectSize = size;
        this.update();
    }

    public void setFigureImages(Image[] figureImages) {
        this.figureImages = figureImages;
        this.figureContainer = new int[figureImages.length];

        Dimension dim = new Dimension(75 * figureImages.length, 75);
        this.figurePanel.setSize(dim);
        this.figurePanel.setPreferredSize(dim);
        this.figurePanel.setMinimumSize(dim);
        this.figureNode.setContent(this.figurePanel);
        try {
            this.footer.getChildren().add(this.figureNode);
        } catch (Exception e) {
            
        }
        this.update();
    }

    public void setFigureContainer(int[] figureContainer) {
        this.figureContainer = figureContainer;
        this.update();
    }
    
    // setEventImages() for events
    public void setEventImages(Image[] eventImages)
    {
       this.eventImages = eventImages;
       this.eventContainer = new int[eventImages.length];
       System.out.println(eventImages.length + " aha la ");
       Dimension dm = new Dimension(75, 75*eventImages.length);
       this.eventPanel.setSize(dm);
        this.eventPanel.setPreferredSize(dm);
        this.eventPanel.setMinimumSize(dm);
        this.eventNode.setContent(this.eventPanel);
        try {
            this.rightPanel.getChildren().add(this.eventNode);
        } catch (Exception e) {
            
        }
        this.update();
    }
    
// setEventContainer() method for events
    public void setEventContainer(int[] eventContainer)
    {
       this.eventContainer = eventContainer;
       this.update();
    }
    
    
    
    public int[] transformPosition(double x, double y) {
        int[] pos = {-1, -1};
        if (x < 0 || y < 0 || x > this.rectSize * this.map.length || y > this.rectSize * this.map[0].length) {
            return pos;
        }

        pos[0] = (int) (x / this.rectSize);
        pos[1] = (int) (y / this.rectSize);
        return pos;
    }

    public int getFigureID(double x, double y) {
        int id = (int) (x / 75);
        if (id >= 0 && id < figureImages.length) {
            if (this.figureContainer[id] != 0) {
                System.out.println("[GamePlayView] getFigureID () : " + (id + 1));
                this.figureID = id;
                return (id + 1);
            }
        }

        this.figureID = -1;
        return -1;
    }
    
    public int getEventID(double x, double y) {
        int id = (int) (y / 75) +1;
        if (id >= 0 && id < eventImages.length) {
            if (this.eventContainer[id] != 0) {
                System.out.println("[GamePlayView] getEventID () : " + (id ));
                this.eventID = id;
                this.previewFigure = new Block[1][1];
                this.previewFigure[0][0] = new Block();
                this.previewFigure[0][0].setFigureID(1);
                return (id + 1);
            }
        }

        this.eventID = -1;
        return -1;
    }
    
    public void setPreviewPosition(double x, double y) {
        if (figureID == -1) {
            if (eventID != -1) {
                this.mouseX = (int) (this.mapPanel.getWidth() + x + 3);
                this.mouseY = (int) (y - this.header.getHeight() - 27);
                int[] pos = transformPosition(mouseX, mouseY);
                this.previewX = pos[0];
                this.previewY = pos[1];
                System.out.println(pos[0] + "::" + pos[1]);
                this.update();
            }
            return;
        }
       
        this.mouseX = (int) x - 2; // fix double
        this.mouseY = (int) (this.rootPanel.getHeight() - (Math.abs(y) + this.footer.getHeight()) - this.header.getHeight() - 75); // fix double

        int[] pos = transformPosition(mouseX, mouseY);
        this.previewX = pos[0];
        this.previewY = pos[1];
        this.update();
    }

    
    public int[] releasePreviewPosition(double x, double y) {
        if (this.figureID == -1 ) {
            if (this.eventID != -1) {
                int[] posEvent = {this.previewX, this.previewY}; //transformPosition(this.mouseX, this.mouseY);
                System.out.println(posEvent[0] + "::" + posEvent[1]);
                this.eventID = -1;
                this.mouseX = -1;
                this.mouseY = -1;
                this.previewX = -1;
                this.previewY = -1;
                this.update();
                return posEvent;
            } 
            int[] pos = {-1, -1};
            return pos;
            
        }
        this.figureID = -1;
       
        int[] pos = {this.previewX, this.previewY};
        this.mouseX = -1;
        this.mouseY = -1;
        this.previewX = -1;
        this.previewY = -1;
        this.update();
        return pos;
    }

        
    public void setPreviewFigure(Block[][] figure) {
        this.previewFigure = figure;
        this.previewW = 0;
        this.previewH = 0;
        boolean found = true;
        for (int i = 0; i < figure.length; i++) {
            for (int j = 0; j < figure[0].length; j++) {
                if (figure[i][j].getFigureID() != 0 && found) {
                    this.previewH = j;
                    this.previewW = i;
                    found = false;
                }
            }
        }
    }

//     public void setPreviewEvent(Block[][] event) {
//        this.previewEvent = event;
//        this.previewW = 0;
//        this.previewH = 0;
//        boolean found = true;
//        for (int i = 0; i < event.length; i++) {
//            for (int j = 0; j < event[0].length; j++) {
//                if (event[i][j].getEvent() != 0 && found) {
//                    this.previewH = j;
//                    this.previewW = i;
//                    found = false;
//                }
//            }
//        }
//    }
    
    public void recalculatePreviewFigure() {
        boolean found = true;
        for (int i = 0; i < this.previewFigure.length; i++) {
            for (int j = 0; j < this.previewFigure[0].length; j++) {
                if (this.previewFigure[i][j].getFigureID() != 0 && found) {
                    this.previewH = j;
                    this.previewW = i;
                    found = false;
                }
            }
        }
    }

    
//     public void recalculatePreviewEvent() {
//        boolean found = true;
//        for (int i = 0; i < this.previewEvent.length; i++) {
//            for (int j = 0; j < this.previewEvent[0].length; j++) {
//                if (this.previewEvent[i][j].getEvent() != 0 && found) {
//                    this.previewH = j;
//                    this.previewW = i;
//                    found = false;
//                }
//            }
//        }
//    }
    
    public void setTime(int time) {
        if (time == -1) {
            this.timeLabel.setText("");
        } else {
            this.timeLabel.setText("Time: " + time);
        }
    }

    public void setData(int score, int moveCount) {
        this.scoreLabel.setText("Score: " + score);
        this.moveCountLabel.setText("Move Count: " + moveCount);
        
    }
    public void setPlayerNo(int no){
        playerNo = no;
    
    }

    public void update() {
        this.playerTurnLabel.setText("Player " + playerNo + "'s turn");
        this.mapPanel.revalidate();
        this.mapPanel.repaint();
        this.figurePanel.revalidate();
        this.figurePanel.repaint();
        this.eventPanel.revalidate();
       this.eventPanel.repaint();
    }
      public void setGameType(int game){
        gameType = game;
        if (gameType == 2) {
            this.rootPanel.setRight(rightPanel);
        } else {
            this.rootPanel.setRight(null);
        }
   }
    
}