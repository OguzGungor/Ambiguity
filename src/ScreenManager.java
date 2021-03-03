
import Menu.MenuHolder;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.event.MouseInputAdapter;
import org.eclipse.paho.client.mqttv3.*;

/**
 *
 * @author tbyor
 */
public class ScreenManager extends Application implements Locatable {

    // Thread
    private static ConcurrentLinkedQueue<Thread> threads = new ConcurrentLinkedQueue<Thread>();
    private static String[][] parsedMessage;

    private boolean move = false;
    private boolean event = false;
    // Constants
    private final GraphicsDevice GD = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private final int WINDOW_WIDTH = GD.getDisplayMode().getWidth() * 86 / 100;
    private final int WINDOW_HEIGHT = GD.getDisplayMode().getHeight() * 86 / 100;
    private final String TITLE = "AMBIGUITY";
    //   private final String CSS = this.getClass().getResource("./css/app.css").toExternalForm();

    // Properties
    private GameManager gameManager;
    private Timeline timeline;

    // Scene
    private Stage primaryStage;
    private Scene scene;
    private static MenuHolder menuHolder;
    private GamePlayView gamePlayView;
    private SplashScene splash;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize Properties
        this.gameManager = new GameManager();

        this.primaryStage = primaryStage;
        this.menuHolder = new MenuHolder(this.WINDOW_WIDTH, this.WINDOW_HEIGHT);
        this.gamePlayView = new GamePlayView(this.WINDOW_WIDTH, this.WINDOW_HEIGHT);
        this.splash = new SplashScene(this.WINDOW_WIDTH, this.WINDOW_HEIGHT);

        // CSS Setup
        //     this.menuHolder.getStylesheets().add(this.CSS);
        //      this.gamePlayView.getStylesheets().add(this.CSS);
        //      this.splash.getStylesheets().add(this.CSS);
        // Initialize Listeners
        this.initializeListeners();

        // Scene Setup
        this.scene = this.splash;

        // Frame Setup
        this.primaryStage.setTitle(this.TITLE);
        this.primaryStage.setScene(this.scene);
        this.primaryStage.setResizable(false);
        this.primaryStage.show();

        // Debug
        //this.debug();
    }

    private void debug() {
        this.menuHolder.setGameMode(0);
        this.finalizeActions();
        this.changeScreen();
        this.changeScreen();
        this.updateGamePlayView();
        this.initializeTimerTask();
        this.gameManager.startGame();
    }

    private void initializeListeners() {
        this.initializeActionListeners();
        this.initializeMouseListener();
        this.initializeKeyListener();
    }

    private void initializeActionListeners() {
        // [GameModeMenu] CASUAL BUTTON
        EventHandler<ActionEvent> casualHandler = (ActionEvent event) -> {
            this.menuHolder.setGameMode(1);
            this.finalizeActions();
            this.changeScreen();
            this.updateGamePlayView();
            this.gameManager.startGame();
        };

        // [GameModeMenu] VERSUS BUTTON
        EventHandler<ActionEvent> versusHandler = (ActionEvent event) -> {
            // Open Loading Screen
            this.gameManager.initializeClient(new Callback());
            this.gameManager.startClient();
            this.gameManager.askLobyList();
            // Close Loading Screen
            this.menuHolder.setGameMode(2);
            this.menuHolder.changeMenu(this.menuHolder.ID_CHOOSE_LOBY_MENU);
        };

        // [Loby Menu] READY BUTTON
        EventHandler<ActionEvent> readyHandler = (ActionEvent event) -> {
            this.gameManager.readyLoby();
        };

        // [TimeLimitMenu] SEC 45 BUTTON
        EventHandler<ActionEvent> sec45Handler = (ActionEvent event) -> {
            this.menuHolder.setSpecTime(0);
            this.finalizeActions();
            this.changeScreen();
            this.initializeTimerTask();
            this.updateGamePlayView();
            this.gameManager.startGame();
            System.out.println("[ScreenManager] BLUR : ");
        };

        // [TimeLimitMenu] SEC 60 BUTTON
        EventHandler<ActionEvent> sec60Handler = (ActionEvent event) -> {
            this.menuHolder.setSpecTime(1);
            this.finalizeActions();
            this.initializeTimerTask();
            this.changeScreen();
            this.updateGamePlayView();
            this.gameManager.startGame();
            System.out.println("[ScreenManager] BLUR : ");
        };

        // [TimeLimitMenu] SEC 90 BUTTON
        EventHandler<ActionEvent> sec90Handler = (ActionEvent event) -> {
            this.menuHolder.setSpecTime(2);
            this.finalizeActions();
            this.initializeTimerTask();
            this.changeScreen();
            this.updateGamePlayView();
            this.gameManager.startGame();
            System.out.println("[ScreenManager] BLUR : ");
        };

        this.menuHolder.initializePlayListeners(casualHandler, versusHandler, readyHandler, sec45Handler, sec60Handler, sec90Handler);

        // [ChooseLobbyMenu] SUBMIT BUTTON
        EventHandler<ActionEvent> submitHandler = (ActionEvent event) -> {
            this.gameManager.sendMessage(this.menuHolder.getMessage());
        };

        // [ChooseLobbyMenu] REFRESH BUTTON
        EventHandler<ActionEvent> refreshHandler = (ActionEvent event) -> {
            this.gameManager.askLobyList();
        };

        // [ChooseLobbyMenu] CREATE LOBBY BUTTON
        EventHandler<ActionEvent> createLobbyHandler = (ActionEvent event) -> {
            this.gameManager.createLobby();
        };

        // [ChooseLobbyMenu] JOIN LOBBY BUTTON
        EventHandler<ActionEvent> joinLobyHandler = (ActionEvent event) -> {
            this.gameManager.enterLoby(this.menuHolder.getSelectedLobbyID());
        };
        
        // [ChooseLobbyMenu] CHANGE NAME BUTTON
        EventHandler<ActionEvent> changeNameHandler = (ActionEvent event) -> {
            this.gameManager.setPlayerName(this.menuHolder.getPlayerName());
        };
        
        // [LobbyMenu] BACK BUTTON BUTTON
        EventHandler<ActionEvent> backButtonLobyHandler = (ActionEvent event) -> {
            this.gameManager.exitLobby();
            this.menuHolder.changeMenu(this.menuHolder.ID_CHOOSE_LOBY_MENU);
        };

        this.menuHolder.initializeChooseLobbyMenuInitializers(submitHandler, createLobbyHandler, joinLobyHandler, refreshHandler, backButtonLobyHandler, changeNameHandler);
        //

        // [GamePlayView] BACK BUTTON
        this.gamePlayView.backButton.setOnAction((ActionEvent event) -> {
            executeTimerTask();
            if (menuHolder.getAction()[0] == 2) {
                this.gameManager.exitGame();
            }
            menuHolder.changeMenu(menuHolder.MAIN_MENU_ID);
            changeScreen();
        });

        // [GamePlayView] SETTINGS BUTTON
        this.gamePlayView.settingsButton.setOnAction((ActionEvent event) -> {
            menuHolder.changeMenu(2);
            menuHolder.setGameScreenOn(true);
            changeScreen();
        });

        // [SETTINGS] BACK BUTTON
        this.menuHolder.settingsMenu.backButton.setOnAction((ActionEvent event) -> {
            if (menuHolder.getGameScreenOn()) {
                menuHolder.setGameScreenOn(false);
                changeScreen();
            } else {
                menuHolder.changeMenu(0);
            }
        });
    }

    private void initializeMouseListener() {
        MouseInputAdapter mouseMotionAdapter = new MouseInputAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                Platform.runLater(() -> {
                    double x = e.getX(); // 27
                    double y = e.getY(); //56
                    gamePlayView.setPreviewPosition(x, y);
                });
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                Platform.runLater(() -> {
                    int[] pos = gamePlayView.releasePreviewPosition(e.getX(), e.getY());

                    if (pos[0] == -1 && pos[1] == -1) {
                        return;
                    }

                    locatable.setLocation(pos[0], pos[1]);
                    boolean success = false;
                    if (move) {
                        success = gameManager.move();
                    } else if (event) {
                        success = gameManager.setEvent();
                    }

                    updateGamePlayView();

                    if (success) {
                        System.out.println("[ScreenManager] EndGame : true");
                    }
                    event = false;
                    move = false;
                    //
                });
            }
        };

        MouseInputAdapter mouseMapAdapter = new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Platform.runLater(() -> {
                    if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                        int x = e.getX();
                        int y = e.getY();
                        int[] pos = gamePlayView.transformPosition(x, y);
                        gameManager.remove(pos[0], pos[1]);
                        updateGamePlayView();
                    }
                });
            }
        };

        MouseInputAdapter mouseFigureAdapter = new MouseInputAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                Platform.runLater(() -> {
                    int figureID = gamePlayView.getFigureID(e.getX(), e.getY());
                    if (figureID == -1) {
                        return;
                    }
                    locatable.setFigureID(figureID);
                    gameManager.setFigure(figureID);
                    gamePlayView.setPreviewFigure(gameManager.getFigure(figureID));
                    move = true;
                });
            }
        };

        MouseInputAdapter mouseEventAdapter = new MouseInputAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                Platform.runLater(() -> {
                    //gamePlayView.getEventID(e.getX(), e.getY());
                    gameManager.setEventID(gamePlayView.getEventID(e.getX(), e.getY()));
                    event = true;
                });
            }
            /* @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                Platform.runLater(() -> {
                    // burada bıraktığı resmin event mi yoksa figure mü olduğunu anlayıp ona göre işlem yapması lazım
                    
                    // figure için işlem
                    int[] pos = gamePlayView.releasePreviewPosition(e.getX(), e.getY());
                    if (pos[0] == -1 && pos[1] == -1) {
                        return;
                    }

                    locatable.setLocation(pos[0], pos[1]);
                    boolean success = gameManager.setEvent();
                    updateGamePlayView();

                    if (success) {
                        System.out.println("[ScreenManager] EndGame : false");
                    }
                    //
                });
            }*/
        };

        this.gamePlayView.initializeListeners(mouseMotionAdapter, mouseMapAdapter, mouseFigureAdapter, mouseEventAdapter);
    }

    private void initializeKeyListener() {
        // [SplashScene] PressAnyKeyToContinue
        this.splash.setOnKeyPressed((KeyEvent event) -> {
            if (this.scene == this.splash) {
                this.changeScreen();
            }
        });

        // [GamePlayView] Rotate & Symmetry
        this.gamePlayView.setOnKeyTyped((KeyEvent event) -> {
            if (this.scene == this.gamePlayView) {
                if (event.getCharacter().equals("R") || event.getCharacter().equals("r")) {
                    gameManager.rotate(true, false);
                } else if (event.getCharacter().equals(" ")) {
                    gameManager.rotate(false, true);
                }
                gamePlayView.recalculatePreviewFigure();
                gamePlayView.update();
            }
        });
    }

    private void initializeTimerTask() {
        System.out.println("[ScreenManager] initializeTimerTask : " + this.gameManager.getTime());
        if (this.gameManager.getTime() <= 0) {
            return;
        }

        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            this.gamePlayView.setTime(gameManager.getTime());
        }));
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    private void executeTimerTask() {
        if (this.timeline != null) {
            this.timeline.stop();
        }
    }

    private void changeScreen() {
        if (this.scene == this.menuHolder) {
            this.scene = this.gamePlayView;
            gamePlayView.setGameType(menuHolder.getAction()[0]);
        } else if (this.scene == this.gamePlayView) {
            this.scene = this.menuHolder;
        } else {
            this.scene = this.menuHolder;
        }

        this.primaryStage.setScene(this.scene);
    }

    private void updateGamePlayView() {
        this.gamePlayView.setPlayerNo(gameManager.getTurnPlayer());
        this.gamePlayView.drawMap(this.gameManager.getCurrentMap());
        this.gamePlayView.setFigureContainer(this.gameManager.getFigureContainer());
        this.gamePlayView.setData(this.gameManager.getScore(), this.gameManager.getMoveCount());
        this.gamePlayView.setTime(this.gameManager.getTime());
        this.gamePlayView.setEventContainer(this.gameManager.getEventContainer());
    }

    private void finalizeActions() {
        this.gameManager.initialize(this.menuHolder.getAction());
        this.gamePlayView.setFigureImages(this.gameManager.getFigureImages());
        this.gamePlayView.setFigureContainer(this.gameManager.getFigureContainer());
        this.gamePlayView.setEventImages(this.gameManager.getEventImages());
        this.gamePlayView.setEventContainer(this.gameManager.getEventContainer());

    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Thread thread = (Thread) threads.poll();

                    if (thread == null) {
                        do {
                            synchronized (threads) {
                                try {
                                    threads.wait();
                                } catch (InterruptedException e) {
                                    System.out.println("[Screen] InterruptedException : ");
                                    System.out.println("\t\t" + e.getMessage());
                                }
                            }

                            thread = (Thread) threads.poll();
                        } while (thread == null);
                    }
                    thread.start();
                }
            }
        }).start();
        launch(args);
    }

    private void runCommand(String topic, String message) {
        System.out.println("topic: " + topic);
        System.out.println("message: " + message);
        String[][] parsedMessage = gameManager.runCommand(topic, message);
        System.out.println("parsed: " + Arrays.toString(parsedMessage[1]));

        if (parsedMessage[0][0].equals("createLoby")) {
            // Create Loby
            this.gameManager.askLobyList();
        } else if (parsedMessage[0][0].equals("enterLoby")) {
            // Enter Loby
            if (parsedMessage[1][0].equals("true")) {
                this.menuHolder.changeMenu(this.menuHolder.ID_LOBY_MENU);
            }
        } else if (parsedMessage[0][0].equals("exitLoby")) {
            // Exit Loby
            if (parsedMessage[1][0].equals("true")) {
                this.menuHolder.changeMenu(this.menuHolder.ID_CHOOSE_LOBY_MENU);
            }
        } else if (parsedMessage[0][0].equals("displayLoby")) {
            // Display Loby
        } else if (parsedMessage[0][0].equals("displayAll")) {
            // Display All
            menuHolder.setLobyList(parsedMessage[1]);
        } else if (parsedMessage[0][0].equals("readyGame")) {
            // Ready Game
            if (parsedMessage[1][0].equals("true")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        finalizeActions();
                        changeScreen();
                        updateGamePlayView();
                        gameManager.startGame();
                    }
                });
            }
        } else if (parsedMessage[0][0].equals("move")) {
            // Probably move is false
        } else if (parsedMessage[0][0].equals("map")) {
            System.out.println("parsed::: " + parsedMessage[1][0]);
            Block[][] map = new Block[parsedMessage[1].length][parsedMessage[1][0].split(",").length];
            // Initialize map
            for (int i = 0; i < parsedMessage[1].length; i++) {
                String[] row = parsedMessage[1][i].split(",");

                for (int k = 0; k < row.length; k++) {
                    String[] blockString = row[k].split("-");
                    int playerID = Integer.parseInt(blockString[0]);
                    int eventNo = Integer.parseInt(blockString[1]);
                    int embededPlayerID = Integer.parseInt(blockString[2]);
                    Block block = new Block();
                    block.setPlayer(playerID);
                    block.setEvent(eventNo);
                    block.setEmbeddedPlayerID(embededPlayerID);
                    map[i][k] = block;
                }
            }
            this.gameManager.setCurrentMap(map);
            this.updateGamePlayView();
            System.out.println("[ScreenManager] Server map is sent to GameManager");
        } else if (parsedMessage[0][0].equals("gameTurn")) {
            // Set Game Turn
            this.gameManager.setTurn(Integer.parseInt(parsedMessage[1][0]));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateGamePlayView();
                }
            });
        } else if (parsedMessage[0][0].equals("chat")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    menuHolder.setChat(parsedMessage[1][0] + ": " + parsedMessage[1][1]);
                }
            });
        } else if (parsedMessage[0][0].equals("getClients")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    menuHolder.setClients(parsedMessage[1][1]);
                }
            });
        }
    }

    private class Callback implements MqttCallback {

        @Override
        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
            String message = new String(mqttMessage.getPayload());

            // Add New Thread
            threads.add(new Thread() {
                @Override
                public void start() {
                    runCommand(s, message);
                }
            });

            synchronized (threads) {
                threads.notifyAll();
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            // System.out.println("[Callback] Delivery is completed!");
        }

        @Override
        public void connectionLost(Throwable throwable) {
            System.out.println("---\t---\t---");
            System.out.println("[Callback] Connection to broker lost!");
            System.out.println("---\t---\t---");
        }
    }
}
