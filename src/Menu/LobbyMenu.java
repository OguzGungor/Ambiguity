package Menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class LobbyMenu extends Menu {
    // buttons on the screen

    String chatString = "";
    Button readyButton;
    Button submitButton;
    TextArea textArea;
    TextArea messageArea;

    Label player1;
    Label player2;
    Label player3;
    Label player4;

    public LobbyMenu(int id) {
        super(id);

        this.setStyle("-fx-background-color: #0F4351;");

        this.readyButton = new Button("READY");
        this.submitButton = new Button("SUBMIT");
        this.backButton = new Button("BACK");

        this.player1 = new Label("Player #1");
        this.player2 = new Label("Player #2");
        this.player3 = new Label("Player #3");
        this.player4 = new Label("Player #4");

        player1.setStyle("-fx-text-fill: #02927A; -fx-font-size: 40px; -fx-font-weight: bold; -fx-font: Berlin Sans FB Demi;");
        player2.setStyle("-fx-text-fill: #02927A; -fx-font-size: 40px; -fx-font-weight: bold; -fx-font: Berlin Sans FB Demi;");
        player3.setStyle("-fx-text-fill: #02927A; -fx-font-size: 40px; -fx-font-weight: bold; -fx-font: Berlin Sans FB Demi;");
        player4.setStyle("-fx-text-fill: #02927A; -fx-font-size: 40px; -fx-font-weight: bold; -fx-font: Berlin Sans FB Demi;");

        HBox hb = new HBox();
        textArea = new TextArea();
        textArea.setPrefHeight(200);
        textArea.setPrefWidth(800);
        textArea.setEditable(false);

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(textArea);
        scroll.setFitToWidth(false);
        scroll.setFitToHeight(true);
        scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scroll.setPannable(true);

        hb.getChildren().addAll(textArea, scroll);
        hb.setPadding(new Insets(10, 0, 20, 200));  // (top , right , bottom, left )

        HBox box = new HBox();
        box.setPrefHeight(200);
        box.setAlignment(Pos.TOP_RIGHT);
        this.readyButton.setPrefSize(200, 50);
        readyButton.setStyle(" -fx-background-radius: 20em;  "
                + "-fx-min-width: 75px; "
                + "-fx-min-height: 75px; "
                + "-fx-max-width: 75px; "
                + "-fx-max-height: 75px; "
                + //  "-fx-fill: #37CABA" +
                //     "-fx-stroke: #37CABA"+
                "-fx-background-insets: 0px; "
                + "-fx-padding: 0px;"
                + "-fx-text-fill: #02927A; "
                + "-fx-font-size: 20px;"
                + "-fx-font-weight: bold;"
                + "-fx-font: Berlin Sans FB Demi;"); //"-fx-background-color: #37CABA; -fx-text-fill: #02927A; -fx-font-size: 20px; -fx-font-weight: bold; -fx-font: Berlin Sans FB Demi;");

        box.setPadding(new Insets(30, 135, 0, 0));  // (top , right , bottom, left )
        box.getChildren().addAll(readyButton);

        HBox hbox = new HBox();
        messageArea = new TextArea();
        messageArea.setPrefHeight(30);
        messageArea.setPrefWidth(800);
        messageArea.setEditable(true);

        this.submitButton.setPrefSize(200, 50);
        submitButton.setStyle("-fx-background-color: #37CABA; -fx-text-fill: #02927A; -fx-font-size: 20px; -fx-font-weight: bold; -fx-font: Berlin Sans FB Demi;");
        hbox.setPadding(new Insets(0, 0, 20, 200));  // (top , right , bottom, left )
        hbox.getChildren().addAll(messageArea, submitButton, backButton);
        hbox.setSpacing(100);

        VBox labelBox = new VBox();
        labelBox.setPrefHeight(80);
        labelBox.setPrefWidth(400);
        labelBox.setSpacing(10);
        labelBox.getChildren().addAll(player1, player2, player3, player4);
        labelBox.setAlignment(Pos.CENTER);
        labelBox.setPadding(new Insets(40, 0, 80, 40));  // (top , right , bottom, left )
        hb.getChildren().add(labelBox);

        // setting 
        // this.footer.getChildren().add(hbox);
        this.setTop(box);
        this.setCenter(hb);
        this.setBottom(hbox);

    }

    public void addTextToChat(String val) {
        this.chatString += val + "\n";
        this.textArea.setText(this.chatString);
    }

    public String message() {
        return messageArea.getText();
    }

    @Override
    protected void initializeListeners() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setClients(String clients) {
        clients = clients.replaceAll("\\[", "");
        clients = clients.replaceAll("\\]", "");
        String[] clientNames = clients.split(",");

        try {
            this.player1.setText(clientNames[0]);
        } catch (Exception e) {

        }
        try {
            this.player2.setText(clientNames[1]);
        } catch (Exception e) {

        }
        try {
            this.player3.setText(clientNames[2]);
        } catch (Exception e) {

        }
        try {
            this.player4.setText(clientNames[3]);
        } catch (Exception e) {

        }
        this.player1.setTextFill(Color.CYAN);
        this.player2.setTextFill(Color.YELLOW);
        this.player3.setTextFill(Color.ORANGE);
        this.player4.setTextFill(Color.MAGENTA);
    }
}
