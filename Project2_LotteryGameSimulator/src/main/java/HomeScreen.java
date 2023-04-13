/// Author: Kathan Parikh, Sagar Patel
/// NetID: kpari3, spate596
/// Email: kpari3@uic.edu, spate596@uic.edu
/// Course: CS342
/// Date: 3/19/2023
/// Project 2: Lottery Game Simulation
///            Implementing own version of lottery game Keno through
///            GUI development using JavaFX and event driving programming.
///            The game will have a home screen, rules screen, odds screen,
///            and a game screen. Each screen is implemented in a separate
///            class, which are interconnected. The bet card, spot selection
///            and drawing are implemented in the game screen.

/// HomeScreen.java: This class is used to create the home screen of the game.
///                  which implements the menu linking to the rules and odds screen.
///                  Also, give the way to start the game or exit the game.


import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class HomeScreen extends Application {
    // HomeScreen variables
    public static Scene homeScene;
    public Media newMedia;
    public MediaPlayer mediaPlayer;
    public MenuButton menuBox;
    public MenuItem rulesButton;
    public MenuItem oddsButton;
    public MenuItem exitButton;
    public StackPane root;
    public ImageView playImgView;


    // Main method to launch the game
    public static void main(String[] args) {
        launch(args);
    }


    // Method to get the title of the game
    public Label getGameTitle() {
        Label titleLabel = new Label("Keno Gambling");

        // Adding animation to the title
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), titleLabel);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();


        titleLabel.setFont(Font.font("Robus", FontWeight.BOLD, 100));

        return titleLabel;
    }


    // Method to get the play button in home screen
    public ImageView getPlayDesign(ImageView playImgViewTemp) {
        // Design of play button
        playImgViewTemp.setFitWidth(300);
        playImgViewTemp.setFitHeight(300);
        playImgViewTemp.setTranslateY(0);

        // Adding animation to the play button
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), playImgViewTemp);
        scaleTransition.setByX(0.2);
        scaleTransition.setByY(0.2);
        scaleTransition.setCycleCount(1000000000);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();

        return playImgViewTemp;
    }


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


    // Method to get the menu for the home screen
    public VBox getMenu() throws FileNotFoundException {
        // Menu box
        menuBox = new MenuButton();
        menuBox.setText("    MENU");

        // Menu items
        rulesButton = new MenuItem("Rules");
        oddsButton = new MenuItem("Odds of Winning");
        exitButton = new MenuItem("Exit");

        // Adding menu items to menu box
        menuBox.getItems().addAll(rulesButton, oddsButton, exitButton);

        // Styling of menu box
        menuBox.setPrefHeight(50);
        menuBox.setPrefWidth(216);
        menuBox.setLayoutX(100);
        menuBox.setLayoutY(100);
        menuBox.setStyle("-fx-background-color: rgb(248,237,190); -fx-text-fill: rgb(248,237,190); -fx-font-weight: bold; -fx-font-size: 17pt;");

        // Adding image to menu box
        FileInputStream input = new FileInputStream("src/res/menuIcon.png");
        Image menuIcon = new Image(input);
        ImageView imageView = new ImageView(menuIcon);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        menuBox.setGraphic(imageView);

        // Create a new vBox for menu
        VBox vMenuBox = new VBox();
        vMenuBox.setPrefHeight(50);
        vMenuBox.setPrefWidth(215);
        vMenuBox.setSpacing(200);
        vMenuBox.setPadding(new Insets(30, 30, 30, 30));
        vMenuBox.getChildren().addAll(menuBox);

        return vMenuBox;
    }


    // Method of event handlers for buttons in home screen
    public void getEventHandlers(Stage primaryStage) {
        // Lambda function event handler for menu box
        menuBox.setOnMouseEntered(e->{
            homeScene.setCursor(Cursor.HAND);
        });

        menuBox.setOnMouseExited(e->{
            homeScene.setCursor(Cursor.DEFAULT);
        });

        // Lambda function event handler for rules button
        rulesButton.setOnAction(e->{
            homeScene.setCursor(Cursor.HAND);
            RulesScreen rulesScreen = new RulesScreen();
            try {
                rulesScreen.start(primaryStage);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Lambda function event handler for odds of winning button
        oddsButton.setOnAction(e->{
            homeScene.setCursor(Cursor.HAND);
            OddsScreen oddsScreen = new OddsScreen();
            try {
                oddsScreen.start(primaryStage);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Lambda function event handler for exit button
        exitButton.setOnAction(e->{
            homeScene.setCursor(Cursor.HAND);
            primaryStage.close();
        });

        playImgView.setOnMouseEntered(e->{
            homeScene.setCursor(Cursor.HAND);
        });

        playImgView.setOnMouseExited(e->{
            homeScene.setCursor(Cursor.DEFAULT);
        });

        // Lambda function event handler for play button
        playImgView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event->{
                homeScene.setCursor(Cursor.HAND);
                GameScreen gameScreen = new GameScreen();
                try{
                    mediaPlayer.stop();
                    gameScreen.start(primaryStage);
                } catch (FileNotFoundException ex){
                    throw new RuntimeException(ex);
                }
            }
        );
    }


    // Method to get the transition and music
    public void getTransitionAndMusic() {
        // Transition to fade in the scene
        FadeTransition fadeInScene = new FadeTransition(Duration.seconds(2), root);
        fadeInScene.setFromValue(0);
        fadeInScene.setToValue(1);
        fadeInScene.play();

        // Music
        String musicFileName = "src/res/song1.mp3";
        newMedia = new Media(new File(musicFileName).toURI().toString());
        mediaPlayer = new MediaPlayer(newMedia);
        mediaPlayer.setVolume(0.20);
        mediaPlayer.play();

    }


    // Method to get the scene
    public static Scene getScene() {
        return homeScene;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub

        // Setting the title of the game
        primaryStage.setTitle("Keno Gambling");
        root = new StackPane();
        VBox titleBox = new VBox();
        titleBox.setPrefHeight(800);
        titleBox.setPrefWidth(1300);
        titleBox.setPadding(new Insets(130, 30, 30, 320));

        // Adding the title to the title box and adding the title box to the root
        titleBox.getChildren().add(getGameTitle());
        root.getChildren().add(titleBox);

        // Adding the background image to the root
        root.setBackground(getBackGroundImage());

        // Adding the menu to the root
        root.getChildren().addAll(getMenu());

        // Adding the play button to the root
        Image playImage = new Image(new FileInputStream("src/res/playButton.png"));
        playImgView = new ImageView(playImage);
        playImgView = getPlayDesign(playImgView);
        root.getChildren().add(playImgView);

        // Adding the event handlers for the home screen
        getEventHandlers(primaryStage);

        // Create the scene
        homeScene = new Scene(root, 1300,800);

        // Adding the transition to the scene
        getTransitionAndMusic();

        // Setting the scene
        primaryStage.setResizable(false);
        primaryStage.setScene(homeScene);

        // Display the stage
        primaryStage.show();
    }
}