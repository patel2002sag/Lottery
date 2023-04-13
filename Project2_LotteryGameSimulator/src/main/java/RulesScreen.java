// RulesScreen.java: This class is used to create the rules screen and display the rules of the game.

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import static javafx.scene.paint.Color.rgb;


public class RulesScreen extends HomeScreen {
    // RulesScreen variables
    private Boolean gameScreen = false;
    Button goBackButton;
    Scene rulesScene;

    // Method to get the background image
    public Background getBackGroundImage() throws FileNotFoundException {
        // Background Image
        Image kenoImage = new Image(new FileInputStream("src/res/kenobackground.png"));

        // Setting the background image
        BackgroundSize size = new BackgroundSize(1300, 800, false, false, false, false);
        BackgroundImage backgroundImage1 = new BackgroundImage(kenoImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size);
        Background background = new Background(backgroundImage1);

        return background;
    }


    // Method to get the title of the game
    public HBox getTitleGame() {
        // Container for the title
        HBox hBox = new HBox();
        hBox.setPrefHeight(800);
        hBox.setPrefWidth(1300);
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.setPadding(new Insets(30, 30, 30, 30));

        // Title for the Screen
        Label titleLabel = new Label("Rules of the Game");
        titleLabel.setFont(new Font("Arial", 50));
        titleLabel.setPadding(new Insets(70, 30, 30, 30));

        // Add the title to the container
        hBox.getChildren().addAll(titleLabel);

        return hBox;
    }


    // Method to go back to homescreen from rules screen
    public VBox getGoBackButton(Button buttonTemp) throws FileNotFoundException {
        // Back Button Design, Layout, and Style
        buttonTemp = new Button();
        buttonTemp.setText("     BACK");
        buttonTemp.setPrefHeight(50);
        buttonTemp.setPrefWidth(216);
        buttonTemp.setLayoutX(100);
        buttonTemp.setLayoutY(100);
        buttonTemp.setStyle("-fx-background-color: rgb(248,237,190); -fx-text-fill: rgb(0,0,0); -fx-font-weight: bold; -fx-font-size: 17pt;");

        // Back Button Icon
        FileInputStream input1 = new FileInputStream("src/res/backbutton.png");
        Image menuIcon1 = new Image(input1);
        ImageView imageView1 = new ImageView(menuIcon1);
        imageView1.setFitHeight(30);
        imageView1.setFitWidth(30);

        // Place the icon inside the button
        buttonTemp.setGraphic(imageView1);
        buttonTemp.setContentDisplay(ContentDisplay.LEFT);
        buttonTemp.setAlignment(Pos.CENTER_LEFT);

        // Create a vBox to place the button inside
        VBox vBoxButton = new VBox();
        vBoxButton.setPrefHeight(800);
        vBoxButton.setPrefWidth(1300);
        vBoxButton.setSpacing(1000);
        vBoxButton.setPadding(new Insets(30, 30, 30, 30));

        // Place the button inside the vBox
        goBackButton = buttonTemp;
        vBoxButton.getChildren().addAll(goBackButton);

        return vBoxButton;
    }


    // Method to get the background container
    public VBox getBackgroundContainer() {
        // Container for the rules
        VBox vBoxRec = new VBox();
        vBoxRec.setPrefHeight(600);
        vBoxRec.setPrefWidth(900);
        vBoxRec.setSpacing(1000);
        vBoxRec.setPadding(new Insets(200, 30, 50, 200));

        // Create a rectangle to hold the rules
        Rectangle rulesArea = new Rectangle();
        rulesArea.setHeight(600);
        rulesArea.setWidth(900);
        rulesArea.setArcWidth(40);
        rulesArea.setArcHeight(30);
        rulesArea.setFill(rgb(248,237,190));

        // Add the rules to VBox
        vBoxRec.getChildren().add(rulesArea);

        return vBoxRec;
    }


    /// Method to get the container for the rules
    public VBox getRules() {
        VBox rulesTextBox = new VBox();
        rulesTextBox.setPrefHeight(500 );
        rulesTextBox.setPrefWidth(500);

        // Label for Rules for the game
        Label ruleOne = new Label("⚫ Balance: The player has an initial balance set for the game of $500. \n\n" +
                "⚫ Choose your numbers:  The player must choose the spot or let computer \n decide, typically ranging from 1, 4, 8, 10 spots (numbers). This can not \n change once  the drawings begin.\n\n" +
                "⚫ You can choose (minimum of 1 and maximum of 4) drawings. This can \n not change once the  drawings begin. \n\n" +
                "⚫ You cannot select duplicate numbers or select more spots than you \n decided originally.\n");

        ruleOne.setTextAlignment(TextAlignment.JUSTIFY);
        ruleOne.setTranslateX(25);
        ruleOne.setTranslateY(25);
        rulesTextBox.setPadding(new Insets(200, 0, 50, 210));
        ruleOne.setTextAlignment(TextAlignment.JUSTIFY);
        ruleOne.setMaxHeight(2000);
        ruleOne.setMaxWidth(900);
        ruleOne.setFont(new Font("Arial", 25));

        // Add the rules to the container
        rulesTextBox.getChildren().add(ruleOne);

        return rulesTextBox;
    }


    // Method to get the event handler for the back button
    public void getEventHandlers(Stage stage) {

        goBackButton.setOnMouseEntered(event->{
            rulesScene.setCursor(Cursor.HAND);
        });

        goBackButton.setOnMouseExited(event->{
            rulesScene.setCursor(Cursor.DEFAULT);
        });

        goBackButton.setOnAction(e->{
            // If not on the game screen, go back to the home screen
            if(!gameScreen){
                stage.setScene(HomeScreen.getScene());
            } else{
                stage.setScene(GameScreen.getScene());
            }
        });
    }


    // Method to set the game screen
    public void setGameBack() {
        gameScreen = true;
    }


    public void start(Stage stage) throws FileNotFoundException {
        // Create a root
        StackPane ruleRoot = new StackPane();

        // Set the overall background in root
        ruleRoot.setBackground(getBackGroundImage());

        // Add the title to the root
        ruleRoot.getChildren().add(getTitleGame());

        // Add the background container to the root
        ruleRoot.getChildren().add(getBackgroundContainer());

        // Add the rules to the root
        ruleRoot.getChildren().add(getRules());

        // Add the back button to the root
        ruleRoot.getChildren().add(getGoBackButton(goBackButton));

        // Get the event handlers for the back button
        getEventHandlers(stage);

        // Show the Stage
        rulesScene = new Scene(ruleRoot, 1300, 800);
        stage.setScene(rulesScene);

        // Display the stage
        stage.show();
    }
}