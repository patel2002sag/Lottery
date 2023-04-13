// OddsScreen.java: This class is used to create the odds screen and display the odds of the game.

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import static javafx.scene.paint.Color.rgb;


public class OddsScreen extends HomeScreen {
    // OddsScreen variables
    public Boolean gameScreen = false;
    public Button goBackButton;
    public Scene oddsScene;


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
        hBox.setPrefHeight(500);
        hBox.setPrefWidth(500);
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.setPadding(new Insets(30, 30, 30, 30));

        // Title for the Screen
        Label titleLabel = new Label("\uD83D\uDCB5 Odds of Winning \uD83D\uDCB5");
        titleLabel.setFont(new Font("Arial", 50));
        titleLabel.setPadding(new Insets(70, 30, 30, 30));

        // Add the title to the container
        hBox.getChildren().addAll(titleLabel);

        return hBox;
    }


    public VBox getTenAndOneOdds() {
        // Container for spot1
        VBox spotGame1 = new VBox();
        spotGame1.setPrefHeight(300);
        spotGame1.setPrefWidth(800);

        // Image for odds of winning 1
        Image odds1Img = null;
        try {
            odds1Img = new Image(new FileInputStream("src/res/oddsWin1.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView odds1View = new ImageView(odds1Img);
        odds1View.setFitHeight(210);
        odds1View.setFitWidth(300);

        // Image for odds of winning 10
        Image odds10Img = null;
        try {
            odds10Img = new Image(new FileInputStream("src/res/oddsWin10.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView odds10View = new ImageView(odds10Img);
        odds10View.setFitHeight(340);
        odds10View.setFitWidth(320);

        // Design for spotGame1 and spotGame10 odds image
        spotGame1.setPadding(new Insets(190, 0, 50, 220));
        spotGame1.setSpacing(20);

        // Add the odds to the container
        spotGame1.getChildren().addAll(odds10View, odds1View);

        return spotGame1;
    }


    public VBox getEightAndFourOdds() {
        // Container for spot2
        VBox spotGame2 = new VBox();
        spotGame2.setPrefHeight(300);
        spotGame2.setPrefWidth(800);

        // Image for odds of winning 4
        Image odds4Img = null;
        try {
            odds4Img = new Image(new FileInputStream("src/res/oddsWin4.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView odds4View = new ImageView(odds4Img);
        odds4View.setFitHeight(230);
        odds4View.setFitWidth(270);

        // Image for odds of winning 8
        Image odds8Img = null;
        try {
            odds8Img = new Image(new FileInputStream("src/res/oddsWin8.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView odds8View = new ImageView(odds8Img);
        odds8View.setFitHeight(340);
        odds8View.setFitWidth(320);

        // Design for spotGame2
        spotGame2.setPadding(new Insets(230, 0, 70, 730));
        spotGame2.setSpacing(20);

        // Add the odds to the container
        spotGame2.getChildren().addAll(odds8View, odds4View);

        return spotGame2;
    }


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


    // Method to go back to the home screen
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


    // Method to get the event handler for the back button
    public void getEventHandlers(Stage stage) {

        goBackButton.setOnMouseEntered(event->{
            oddsScene.setCursor(Cursor.HAND);
        });

        goBackButton.setOnMouseExited(event->{
            oddsScene.setCursor(Cursor.DEFAULT);
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


    // Method to set the game screen to true
    public void setGameBack() {
        gameScreen = true;
    }


    public void start(Stage stage) throws FileNotFoundException {
        // Create a root
        StackPane ruleRoot = new StackPane();

        // Set the background image
        ruleRoot.setBackground(getBackGroundImage());

        // Add the title of the game to root
        ruleRoot.getChildren().add(getTitleGame());

        // Add the VBox to the root
        ruleRoot.getChildren().add(getBackgroundContainer());

        // Add the odds to the root
        ruleRoot.getChildren().add(getTenAndOneOdds());
        ruleRoot.getChildren().add(getEightAndFourOdds());

        // Add the back button to the root
        ruleRoot.getChildren().add(getGoBackButton(goBackButton));

        // Get the event handler for the back button
        getEventHandlers(stage);

        // Create a scene
        oddsScene = new Scene(ruleRoot, 1300, 800);
        stage.setScene(oddsScene);

        // Display the stage
        stage.show();
    }
}