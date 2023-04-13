// GameScreen.java: This class is used to create the game screen and implement the game GUI and logic.
///                 Also, implements the menu linking to the home screen, rules and odds screen.
///                 It also gives the use to change the background and sound/mute or exit the game.

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;


public class GameScreen extends HomeScreen {
    // GameScreen variables
    public BorderPane gameRoot;
    public static Scene gameScene;

    // Game mathematical variables
    public Text balanceText;
    public Label balanceLabel;
    public Text totalWinText;
    public Text totalLossText;
    public int balance = 500;
    public int matches = 0;
    public int totalWin = 0;
    public int totalLoss = 0;
    public int drawCount = 1;

    // Spots Variable
    public int[][] spotsNumArray = {{1, 4},{8, 10}};

    // Logical arrays for the game
    public  ArrayList<Integer> userSelectedNums = new ArrayList<Integer>(0);
    public  ArrayList<Integer> random20Nums = new ArrayList<Integer>(20);
    public  ArrayList<Integer> matchNumbers = new ArrayList<Integer>(20);
    public ArrayList<Integer> unMatchNumbers = new ArrayList<Integer>(20);

    // Menu Variables
    public MenuButton menuBar;
    public MenuItem homeButton;
    public MenuItem rulesButton;
    public MenuItem oddsButton;
    public MenuItem muteButton;
    public MenuItem exitButton;
    public Menu changeBackgroundButton;
    public MenuItem background1;
    public MenuItem background2;
    public MenuItem background3;
    public MenuItem sound1;
    public MenuItem sound2;
    public MenuItem sound3;
    public Menu changeSoundButton;

    // Music Variables
    public Media media;
    public MediaPlayer mediaPlayer;

    // Center Layout
    public GridPane numbersGridPane;
    public Label labelCard;

    // Arrays for buttons and spinner
    public  Button[][] buttonArray;
    public Spinner<Integer> spinner;

    // Red Buttons Spot Vars
    public  GridPane spotsGrid;
    public int selectedSpotNumber;
    public  Button[][] spotsButtonArray;
    public int spinnerValue;

    // Buttons Variables
    public Button autoPlayButton;
    public Button drawButton;
    public Button clearButton;
    public Button startAgainButton;


    // Method to get the background image
    public Background getBackgroundImg(String urlBackground) throws FileNotFoundException {
        // Set the background image
        Image kenoImage = new Image(new FileInputStream(urlBackground));

        // Get the bakcground image
        BackgroundSize size = new BackgroundSize(1300, 800, false, false, false, false);
        BackgroundImage backgroundImage1 = new BackgroundImage(kenoImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size);
        Background background = new Background(backgroundImage1);

        // Set the overall background in root
        return background;
    }

    public HBox menuBox() throws FileNotFoundException {
        // Menu Bar
        menuBar = new MenuButton("    MENU");
        homeButton = new MenuItem("Home");
        rulesButton = new MenuItem("Rules");
        muteButton = new MenuItem("Mute");
        oddsButton = new MenuItem("Odds of Winning");

        exitButton = new MenuItem("Exit");

        // Change Sound Button
        changeSoundButton = new Menu("Change Sound");
        sound1 = new MenuItem("Sound 1");
        sound2 = new MenuItem("Sound 2");
        sound3 = new MenuItem("Sound 3");
        changeSoundButton.getItems().addAll(sound1, sound2, sound3);

        // Change Background Button
        changeBackgroundButton = new Menu("New Look");
        background1 = new MenuItem("Background 1");
        background2 = new MenuItem("Background 2");
        background3 = new MenuItem("Background 3");
        changeBackgroundButton.getItems().addAll(background1, background2, background3);

        // Add all the menu items to the menu bar
        menuBar.getItems().addAll(homeButton, rulesButton, oddsButton, changeBackgroundButton, changeSoundButton, muteButton, exitButton);

        // Styling of menu box
        menuBar.setPrefHeight(50);
        menuBar.setPrefWidth(216);
        menuBar.setLayoutX(100);
        menuBar.setLayoutY(100);
        menuBar.setStyle("-fx-background-color: rgb(248,237,190); -fx-text-fill: rgb(248,237,190); -fx-font-weight: bold; -fx-font-size: 17pt;");

        // Add the menu icon
        FileInputStream input = new FileInputStream("src/res/menuIcon.png");
        Image menuIcon = new Image(input);
        ImageView imageView = new ImageView(menuIcon);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        menuBar.setGraphic(imageView);
        menuBar.setTranslateX(30);
        menuBar.setTranslateY(30);

        HBox menuBox1 = new HBox();
        // Add the menu bar to the menu box
        menuBox1.getChildren().add(menuBar);

        return menuBox1;
    }


    // Method of event handlers for the menu bar
    public void menuLogic(Stage primaryStage) {
        // Lamda function for the menuBar
        menuBar.setOnMouseEntered(e->{
            gameScene.setCursor(Cursor.HAND);
        });

        menuBar.setOnMouseExited(e->{
            gameScene.setCursor(Cursor.DEFAULT);
        });

        // Lambda function for the homeScreen button
        HomeScreen homeScreen = new HomeScreen();
        homeButton.setOnAction(e->{
            gameScene.setCursor(Cursor.HAND);
            mediaPlayer.stop();
            try {
                mediaPlayer.stop();
                homeScreen.start(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Lambda Function for rules of Button
        rulesButton.setOnAction(e->{
            gameScene.setCursor(Cursor.HAND);
            RulesScreen rulesScreen = new RulesScreen();
            rulesScreen.setGameBack();
            try {
                rulesScreen.start(primaryStage);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });


        // Lambda Function for Odds of Winning
        oddsButton.setOnAction(e->{
            gameScene.setCursor(Cursor.HAND);
            OddsScreen oddsScreen = new OddsScreen();
            oddsScreen.setGameBack();
            try {
                oddsScreen.start(primaryStage);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Lamdba function for mute button
        muteButton.setOnAction(e->{
            gameScene.setCursor(Cursor.HAND);
            mediaPlayer.stop();
        });

        // Lambda Function for Exit Button
        exitButton.setOnAction(e->{
            primaryStage.close();
            System.exit(0);
            gameScene.setCursor(Cursor.HAND);
        });
    }


    // Method of handlers for the change background button
    public void changeBackground() {
        // Lambda function for the background1
        background1.setOnAction(e->{
            try {
                gameRoot.setBackground(getBackgroundImg("src/res/background1.jpg"));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Lambda function for the background2
        background2.setOnAction(e->{
            try {
                gameRoot.setBackground(getBackgroundImg("src/res/background2.jpg"));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Lambda function for the background3
        background3.setOnAction(e->{
            try {
                gameRoot.setBackground(getBackgroundImg("src/res/background3.jpg"));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    // Method of handlers for the change sound button
    public void changeMusic() {
        // Get the music file and change it properties
        media = new Media(new File("src/res/song4.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.20);
        mediaPlayer.play();

        // Event handler for the sound1
        sound1.setOnAction(e->{
            mediaPlayer.stop();
            String musicFileName = "src/res/song2.mp3";
            media = new Media(new File(musicFileName).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.setVolume(0.20);
            mediaPlayer.play();
        });

        // Event handler for the sound2
        sound2.setOnAction(e->{
            mediaPlayer.stop();
            String musicFileName = "src/res/song3.mp3";
            media = new Media(new File(musicFileName).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.setVolume(0.20);
            mediaPlayer.play();
        });

        // Event handler for the sound3
        sound3.setOnAction(e->{
            mediaPlayer.stop();
            String musicFileName = "src/res/song4.mp3";
            media = new Media(new File(musicFileName).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.setVolume(0.20);
            mediaPlayer.play();
        });
    }


    // Method to intialize the buttons
    public void initalizeButtons() {
        autoPlayButton = new Button("Auto Select");
        drawButton = new Button("Draw");
        clearButton = new Button("Clear");
        startAgainButton = new Button("Start Again");
        spinner = new Spinner<Integer>();
    }


    // Method to set the grtd design of the buttons 1-80
    public void setGridDesign() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                Button tempButton = new Button("" + (row * 10 + col + 1));
                tempButton.setStyle("-fx-padding: 10px; -fx-background-color: #b8bcc2; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 23px;");
                tempButton.setMinWidth(70);
                buttonArray[row][col] = tempButton;
                // 1.Disable all buttons from 1 to 80
                buttonArray[row][col].setDisable(true);
                numbersGridPane.add(buttonArray[row][col], col, row);

                // Event handler for the buttons
//                tempButton.setOnMouseEntered(e->{
//                    gameScene.setCursor(Cursor.HAND);
//                });
            }
        }
    }


    // Method to get the gridNumbers for the buttons 1-80
    public GridPane getGridNumbers() throws FileNotFoundException {
        // Instance new Grid Pane
        numbersGridPane = new GridPane();

        numbersGridPane.setPrefWidth(100);
        numbersGridPane.setPrefHeight(100);

        numbersGridPane.setTranslateX(-255);
        numbersGridPane.setTranslateY(35);

        numbersGridPane.setHgap(10);
        numbersGridPane.setVgap(10);
        numbersGridPane.setPadding(new Insets(10));

        // Set the background image for the grid of buttons from 1 to 80
        Image blueImage = new Image(new FileInputStream("src/res/blueBackground.jpg"));
        BackgroundSize size = new BackgroundSize(810, 525, false, false, false, false);
        BackgroundImage backgroundImage1 = new BackgroundImage(blueImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size);
        Background background = new Background(backgroundImage1);
        numbersGridPane.setBackground(background);

        // Create the buttons from 1 to 80
        buttonArray = new Button[8][10];
        setGridDesign();

        return numbersGridPane;
    }


    // Method to get the rectangle on left of screen to show the spots prizes
    public StackPane showCardRectangle() {
        // Create a new Stack Pane for the card rectangle on left to show prizes
        StackPane rectangle = new StackPane();

        rectangle.setPrefSize(380, 525);
        rectangle.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        rectangle.setTranslateX(30);
        rectangle.setTranslateY(35);

        return rectangle;
    }


    // Method to show the total win label and live amount change on top of screen
    public StackPane showTotalWin() {
        // Create a new Stack Pane for the total win label and live amount change
        StackPane totalWinPane = new StackPane();

        // Create a new rectangle for the total win label
        Rectangle totalWinBox = new Rectangle(100, 100, Color.rgb(204, 255, 204));

        // Create a new text for the total win label
        totalWinText = new Text("$" + totalWin);
        Label totalWinLabel = new Label("Total Win: ");

        // Style for the totalWinLabel
        totalWinLabel.setTextFill(Color.GREEN);
        totalWinLabel.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.7), new CornerRadii(5), Insets.EMPTY)));
        totalWinLabel.setTranslateX(-150);

        totalWinBox.setWidth(150);
        totalWinBox.setHeight(80);

        // Set the style for the total win label and live amount change
        totalWinPane.setStyle("-fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 30px;");
        totalWinPane.setTranslateX(400);
        totalWinPane.setTranslateY(15);

        // Add the total win label and live amount change to the total win stack pane
        totalWinPane.getChildren().addAll(totalWinLabel, totalWinBox, totalWinText);

        return totalWinPane;
    }


    // Method to show the total loss label and live amount change on top of screen
    public StackPane showTotalLoss() {
        // Create a new Stack Pane for the total loss label and live amount change
        StackPane totalLossPane = new StackPane();

        Rectangle totalLossBox = new Rectangle(100, 100, Color.rgb(255, 102, 102));
        totalLossText = new Text("$" + totalLoss);

        // Style for the totalLossLabel
        Label totalLossLabel = new Label("Total Loss: ");
        totalLossLabel.setTranslateX(-150);
        totalLossLabel.setTextFill(Color.RED);
        totalLossLabel.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.7), new CornerRadii(5), Insets.EMPTY)));

        totalLossBox.setWidth(150);
        totalLossBox.setHeight(80);

        // Set the style for the total loss label and live amount change
        totalLossPane.setStyle("-fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 30px;");
        totalLossPane.setTranslateX(620);
        totalLossPane.setTranslateY(15);

        // Add the total loss label and live amount change to the total loss stack pane
        totalLossPane.getChildren().addAll(totalLossLabel, totalLossBox, totalLossText);

        return totalLossPane;
    }


    // Method to let the use select their numbers automatically based upon the number of spots they want to play
    public void autoSelectNum(int buttonCount, int maxButtons, ArrayList<Integer> userSelectedNums, Button[][] buttonArray) {
        Random rand = new Random();
        while(buttonCount < maxButtons) {
            int rowRand = rand.nextInt(8);
            int colRand = rand.nextInt(10);

            Button randomGeneratedButton = buttonArray[rowRand][colRand];
            int randomGeneratedNumber = Integer.parseInt(randomGeneratedButton.getText());

            // Check if the random number is already selected
            if (!userSelectedNums.contains(randomGeneratedNumber)) {
                userSelectedNums.add(randomGeneratedNumber);
                buttonCount++;
                randomGeneratedButton.setDisable(true);
            }
        }
    }


    // Method for the style of auto selection if the user selects auto selection
    public void getAutoSelectionStyle() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                buttonArray[row][col].setDisable(true);
                buttonArray[row][col].setStyle("-fx-padding: 10px; -fx-background-color: Red; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 23px;");
            }
        }

        // If the user selected numbers matches to the numbers from 1 to 80 then change the style of the button
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                int buttonText = Integer.parseInt(buttonArray[row][col].getText());
                if(userSelectedNums.contains(buttonText)){
                    buttonArray[row][col].setStyle("-fx-padding: 10px; -fx-background: rgba(0, 0, 0, 0); -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 23px;");
                }
            }
        }
    }


    // Method to get the style of the user selected numbers
    public void getUserSelectionStyle(int buttonCount[], int maxButtons) {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                // Allowing user to click the buttons
                buttonArray[row][col].setStyle("-fx-padding: 10px; -fx-background-color: #b8bcc2; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 23px;");
                buttonArray[row][col].setTextFill(Color.BLACK);
                buttonArray[row][col].setDisable(false);
                Button tempButton = buttonArray[row][col];
                int buttonNumber = row * 10 + col + 1;

                buttonArray[row][col].setOnAction(event -> {
                    // Only allowing spot number amount of buttons to be clicked
                    if (buttonCount[0] < maxButtons){
                        // Disabling the button that was clicked
                        userSelectedNums.add(buttonNumber);
                        tempButton.setDisable(true);
                        buttonCount[0]++;
                    }
                });
            }
        }
    }


    // Method to enable the grid buttons to be clicked either by user or choose to auto play
    public void enableGirdButtons(int limitSpotNum, boolean isAutoPlay) {
        userSelectedNums = new ArrayList<Integer>(limitSpotNum);
        int maxButtons = limitSpotNum;
        final int[] buttonCount = {0};

        // If the user selects auto play then disable all the buttons and select the numbers automatically
        if(isAutoPlay){
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 10; col++) {
                    buttonArray[row][col].setDisable(false);
                    changeMouseCursor(buttonArray[row][col]);
                }
            }
            // Get the auto selection style
            autoSelectNum(buttonCount[0], maxButtons, userSelectedNums, buttonArray);
            getAutoSelectionStyle();
        }
        // If the user selects their own numbers
        else {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 10; col++) {
                    buttonArray[row][col].setDisable(false);
                    changeMouseCursor(buttonArray[row][col]);
                }
            }
            // Get the user selection style
            getUserSelectionStyle(buttonCount, maxButtons);
        }
    }


    // Method to disable the spots buttons
    public void disableSpotsButtons(Button tempButton, boolean startAgain) {
        for(int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                spotsButtonArray[row][col].setDisable(false);
            }
        }
        // If the user does not choose to start again, the spots buttons will be disabled
        if(!startAgain){
            tempButton.setDisable(true);
        }
    }


    // Method to get the design of the spots buttons
    public void getSpotButtonDesign(int row, int col) {
        // Design of the button
        Button tempButton = new Button ("" + spotsNumArray[row][col]);
        tempButton.setStyle("-fx-padding: 5px; -fx-background-color: red; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 23px;");
        tempButton.setMinWidth(50);
        spotsButtonArray[row][col] = tempButton; // Place button inside an array
        spotsGrid.add(spotsButtonArray[row][col], col, row); // add insie gridpane

        // Tooltip for the spots button for extra information
        Tooltip tt= new Tooltip("1. Select # of spots!");
        tt.setStyle("-fx-padding: 2px; -fx-background-color: grey; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 15px;");
        tt.setShowDelay(Duration.seconds(0));
        spotsButtonArray[row][col].setTooltip(tt);

        // Disabling not required buttons
        autoPlayButton.setDisable(true);
        drawButton.setDisable(true);
        clearButton.setDisable(true);
        startAgainButton.setDisable(true);
        spinner.setDisable(true);
    }


    // Method to change the mouse cursor when hovering over the buttons
    public void changeMouseCursor(Button tempButton){
        tempButton.setOnMouseEntered(event->{
            gameScene.setCursor(Cursor.HAND);
        });

        tempButton.setOnMouseExited(event->{
            gameScene.setCursor(Cursor.DEFAULT);
        });
    }


    // Method to handle the spots buttons if clicked
    public void spotsButtonHandler(int row, int col) {
        Button tempButton = spotsButtonArray[row][col];
        int buttonNumber = spotsNumArray[row][col];

        changeMouseCursor(spotsButtonArray[row][col]);

        // Enable all the buttons that were disabled
        spotsButtonArray[row][col].setOnAction(event ->{
            // If user clicks on any spot number, enable the 1 to 80 buttons
            selectedSpotNumber = buttonNumber;
            enableGirdButtons(selectedSpotNumber, false);

            // Enable the buttons that were disabled
            autoPlayButton.setDisable(false);
            drawButton.setDisable(false);
            clearButton.setDisable(false);
            startAgainButton.setDisable(false);
            spinner.setDisable(false);

            // Disable the button user clicked and enable the rest spots buttons
            disableSpotsButtons(tempButton, false);


        });





    }


    // Solution: Created two functions: spotsButtonHandler && getSpotButtonDesign
    public GridPane addSpotNumbers() {

        // Grid Design for Spots
        spotsGrid = new GridPane();
        spotsGrid.setPrefWidth(100);
        spotsGrid.setPrefHeight(100);
        spotsGrid.setTranslateX(300);
        spotsGrid.setTranslateY(-50);
        spotsGrid.setHgap(5);
        spotsGrid.setVgap(5);
        spotsGrid.setPadding(new Insets(5));

        // Spots Arrays
        spotsButtonArray = new Button[2][2];
        int[][] spotsNumArray = { {1, 4}, {8, 10} };

        for(int row = 0; row < 2; row++){
            for(int col = 0; col < 2; col++){
                // Make and Design the Red-Buttons
                getSpotButtonDesign(row, col);

                // Handles events for Spots Selection
                spotsButtonHandler(row, col);
            }
        }
        return spotsGrid;
    }


    // Method to get and set the win and loss prize accordingly to the number of matches for spots 1
    public int[] spotNum1Prize(int matches, int cWin, int cLoss) {
        switch(matches){
            case 1: balance += 2; totalWin += 2; cWin += 2; break;
            default: balance -= 2; totalLoss -= 2; cLoss -= 2; break;
        }

        balanceText.setText("$" + balance);
        totalWinText.setText("$" + totalWin);
        totalLossText.setText("$" + totalLoss);

        return new int[]{cWin, cLoss};
    }


    // Method to get and set the win and loss prize accordingly to the number of matches for spots 4
    public int[] spotNum4Prize(int matches, int cWin1, int CLoss1) {
        switch (matches) {
            case 4: balance += 75; totalWin += 75; cWin1 += 75; break;
            case 3: balance += 5; totalWin += 5; cWin1 += 5; break;
            case 2: balance += 1; totalWin += 1; cWin1 += 1;break;
            default: balance -= 2; totalLoss -= 2; CLoss1 -= 2;break;
        }
        balanceText.setText("$" + balance);
        totalWinText.setText("$" + totalWin);
        totalLossText.setText("$" + totalLoss);

        return new int[]{cWin1, CLoss1};
    }


    // Method to get and set the win and loss prize accordingly to the number of matches for spots 8
    public int[] spotNum8Prize (int matches, int cWin2, int cLoss2) {
        switch (matches) {
            case 8: balance += 10000; totalWin += 10000; cWin2 += 10000; break;
            case 7: balance += 750; totalWin += 750;cWin2 += 750; break;
            case 6: balance += 50; totalWin += 50;cWin2 += 50; break;
            case 5: balance += 12; totalWin += 12;cWin2 += 12; break;
            case 4: balance += 2; totalWin += 2;cWin2 += 2; break;
            default: balance -=2; totalLoss -= 2;cLoss2 -= 2; break;
        }
        balanceText.setText("$" + balance);
        totalWinText.setText("$" + totalWin);
        totalLossText.setText("$" + totalLoss);

        return new int[]{cWin2, cLoss2};
    }


    // Method to get and set the win and loss prize accordingly to the number of matches for spots 10
    public int[] spotNum10Prize(int matches, int cWin3, int cLoss3) {
        switch(matches) {
            case 10: balance += 100000; totalWin += 100000;  cWin3 += 100000; break;
            case 9: balance += 4250; totalWin += 4250; cWin3 += 4250;break;
            case 8: balance += 450; totalWin += 450; cWin3 += 450;break;
            case 7: balance += 40; totalWin += 40; cWin3 += 40;break;
            case 6: balance += 15; totalWin += 15; cWin3 += 15;break;
            case 5: balance += 2; totalWin += 2; cWin3 += 2;break;
            case 0:
                if(userSelectedNums.size() < 10) {
                    balance -=2; totalLoss -= 2; cLoss3 -= 2; break;
                }else{
                    balance += 5; totalWin += 5; cWin3 += 5; break;
                }
            default: balance -=2; totalLoss -= 2; cLoss3 -= 2; break;
        }
        balanceText.setText("$" + balance);
        totalWinText.setText("$" + totalWin);
        totalLossText.setText("$" + totalLoss);

        return new int[]{cWin3, cLoss3};
    }


    // Method to get the colors for buttons that matches and doesn't matches user selections
    public void getMatchingButtonTransitions(Timeline timeline, int delay) {
        for(int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                buttonArray[row][col].setDisable(true);
                // Make the buttons to their default colors
                Button temp80Button = buttonArray[row][col];
                temp80Button.setStyle("-fx-padding: 10px; -fx-background-color: #b8bcc2; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 23px;");
                temp80Button.setTextFill(Color.BLACK);
                int temp80Text = Integer.parseInt(temp80Button.getText());

                // Change all user selected numbers to yellow
                if (userSelectedNums.contains(temp80Text)) {
                    temp80Button.setStyle("-fx-padding: 10px; -fx-background-color: YELLOW; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 23px;");
                    temp80Button.setTextFill(Color.valueOf(String.valueOf(Color.BLACK)));
                }

                // Make the buttons that matches the random selection to green
                if (matchNumbers.contains(temp80Text)) {
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(delay), event -> {
                        temp80Button.setStyle("-fx-padding: 10px; -fx-background-color: darkgreen; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 23px;");
                        temp80Button.setTextFill(Color.valueOf("#C0ECB1"));

                        // Make the buttons to scale up and down when they matches the random selection
                        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), temp80Button);
                        scaleTransition.setToX(1.5);
                        scaleTransition.setToY(1.5);
                        scaleTransition.setAutoReverse(true);
                        scaleTransition.setCycleCount(2);
                        // Play the scale transition
                        scaleTransition.play();
                    });
                    // Add the keyframe to the timeline and increase the delay for next button
                    timeline.getKeyFrames().add(keyFrame);
                    delay += 250;
                } else if (unMatchNumbers.contains(temp80Text)) {
                    // // Make the buttons that doesn't matches the random selection to red
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(delay), event -> {
                        temp80Button.setStyle("-fx-padding: 10px; -fx-background-color: red; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 23px;");
                        temp80Button.setTextFill(Color.valueOf("#C0ECB1"));
                    });
                    // Add the keyframe to the timeline and increase the delay for next button
                    timeline.getKeyFrames().add(keyFrame);
                    delay += 250;
                }
            }
        }
    }


    // Method to change the colors of the buttons that matches and doesn't matches the random selection
    public void changeMatchingButtonColors() {
        Timeline timeline = new Timeline();
        // Delay between color changes in milliseconds
        int delay = 500;
        // Set start and draw to disable during transitions
        startAgainButton.setDisable(true);
        drawButton.setDisable(true);

        getMatchingButtonTransitions(timeline, delay);

        // Enable the startAgainButton after the timeline finishes
        timeline.setOnFinished(event -> {
            if(spinnerValue != (drawCount - 1)){
                drawButton.setDisable(false);
            }else{
                startAgainButton.setDisable(false);
            }
        });
        // Play the timeline
        timeline.play();
    }

    // Method to generate random 20 numbers from 1 to 90 and check if they matches the user selections
    public void generateRandomNumbers() {
        random20Nums.clear();
        matchNumbers.clear();
        unMatchNumbers.clear();
        int count = 0;
        Random rand = new Random();
        // Generate 20 random numbers from 1 to 80
        while(count < 20){
            int randomNum = rand.nextInt(80) + 1;
            if(!random20Nums.contains(randomNum)){
                random20Nums.add(randomNum);
                count++;
            }
        }

        // Check if the random numbers matches the user selections`
        for(int i = 0; i < random20Nums.size(); i++){
            if(userSelectedNums.contains(random20Nums.get(i))){
                matchNumbers.add(random20Nums.get(i));
            }
            else{
                unMatchNumbers.add(random20Nums.get(i));
            }
        }
    }


    // Get the draw spinner for user to selected # of draws from 1 to 4
    public Spinner getDrawSpinner() {
        // Create option from 1 to 4
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1);

        valueFactory.setValue(1);
        spinner.setValueFactory(valueFactory);
        spinnerValue = spinner.getValue();

        // Set the tooltip for the spinner for extra information
        Tooltip tt= new Tooltip("2. Select # of Draws");
        tt.setStyle("-fx-padding: 2px; -fx-background-color: grey; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 15px;");
        tt.setShowDelay(Duration.seconds(0));
        spinner.setTooltip(tt);

        // Set the spinner style
        spinner.setStyle("-fx-background-color: #9966ff; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 20pt;");
        spinner.setPrefWidth(120);
        spinner.setPrefHeight(60);
        spinner.setTranslateX(590);
        spinner.setTranslateY(-35);
        spinner.setMinWidth(120);
        spinner.setMinHeight(60);

        spinner.setOnMouseEntered(event->{
            gameScene.setCursor(Cursor.HAND);
        });

        spinner.setOnMouseExited(event->{
            gameScene.setCursor(Cursor.DEFAULT);
        });

        // Adding a listener to the spinner value getting changed
        spinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
                spinnerValue = spinner.getValue();
            }
        });

        return spinner;
    }


    // Method to clear the arrays
    public void clearArray() {
        userSelectedNums.clear();
        random20Nums.clear();
        matchNumbers.clear();
        unMatchNumbers.clear();
    }


    // Method to clear the arrays if the user clicks on the clear button
    public Button getClearButton() {
        // Style the clear button
        clearButton.setTranslateX(-260);
        clearButton.setTranslateY(-35);
        clearButton.setMinHeight(80);
        clearButton.setMinWidth(150);
        clearButton.setStyle("-fx-padding: 10px; -fx-background-color:#ff9866; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 30px;");

        // Set the tooltip for the clear button for extra information
        Tooltip tt= new Tooltip("Clear Selected #(s)");
        tt.setStyle("-fx-padding: 2px; -fx-background-color: grey; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 15px;");
        tt.setShowDelay(Duration.seconds(0));
        clearButton.setTooltip(tt);

        changeMouseCursor(clearButton);

        // Add an action listener to the clear button
        clearButton.setOnAction(e->{

            enableGirdButtons(selectedSpotNumber, false);
            clearArray();
        });

        return clearButton;
    }


    // Method to show the current balance of the game by showing the label and changing the text
    public StackPane showBalance() {
        // Creating stackpane and rectangle to show the balance
        StackPane balancePane = new StackPane();
        Rectangle balanceBox = new Rectangle(100, 100, Color.rgb(255, 204, 153));

        // Creating the label and text to show the balance
        balanceText = new Text("$" + balance);
        balanceLabel = new Label("Balance");
        balanceLabel.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.7), new CornerRadii(5), Insets.EMPTY)));
        balanceLabel.setTranslateY(65);

        // Set the style of the balance box
        balanceBox.setWidth(150);
        balanceBox.setHeight(80);
        balancePane.setStyle("-fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 30px;");
        balancePane.setTranslateX(-650);
        balancePane.setTranslateY(-40);

        // Add the label, text and rectangle to the stackpane
        balancePane.getChildren().addAll(balanceBox, balanceText, balanceLabel);

        return balancePane;
    }


    // Method to enable the grid buttons from 1 to 80 again and disable other non required buttons
    public Button getAutoPlayButton() {
        // Style the auto play button
        autoPlayButton.setTranslateX(370);
        autoPlayButton.setTranslateY(-35);
        autoPlayButton.setMinHeight(80);
        autoPlayButton.setMinWidth(150);
        autoPlayButton.setStyle("-fx-padding: 20px; -fx-background-color:#ff9999; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 30px;");

        // Set the tooltip for the auto play button for extra information
        Tooltip tt= new Tooltip("Choose Random #s");
        tt.setStyle("-fx-padding: 2px; -fx-background-color: grey; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 15px;");
        tt.setShowDelay(Duration.seconds(0));
        autoPlayButton.setTooltip(tt);

        changeMouseCursor(autoPlayButton);

        // Add an action listener to the auto play button
        autoPlayButton.setOnAction(e->{

            enableGirdButtons(selectedSpotNumber, true);
        });

        return autoPlayButton;
    }


    // Method to get the start again button and set the style and tooltip
    public Button getStartAgainButton() {
        // Style the start again button
        startAgainButton.setTranslateX(520);
        startAgainButton.setTranslateY(-35);
        startAgainButton.setMinHeight(80);
        startAgainButton.setMinWidth(150);

        // Set the tooltip for the start again button for extra information
        startAgainButton.setStyle("-fx-padding: 20px; -fx-background-color: #ffccff; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 30px;");
        Tooltip tt= new Tooltip("4. Play Again!");
        tt.setStyle("-fx-padding: 2px; -fx-background-color: grey; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 15px;");
        tt.setShowDelay(Duration.seconds(0));
        startAgainButton.setTooltip(tt);

        changeMouseCursor(startAgainButton);

        // Add an action listener to the start again button and disable the non required buttons
        startAgainButton.setOnAction(e-> {
            selectedSpotNumber = 0;
            enableGirdButtons(selectedSpotNumber, false);
            Button tempButton = new Button("");
            disableSpotsButtons(tempButton, true);
            clearArray();
            spotsGrid.setDisable(false);
            startAgainButton.setDisable(true);
            spinner.setDisable(true);
            autoPlayButton.setDisable(true);
            clearButton.setDisable(true);
            drawCount = 1;
            drawButton.setDisable(true);
        });

        return startAgainButton;
    }


    public void LabelAnimator(){
        String labelText = labelCard.getText();
        labelCard.setText("");
        // create a Timeline animation to gradually update the label text
        Timeline timeline = new Timeline();
        for (int i = 0; i < labelText.length(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(10 * i), event -> {
                labelCard.setText(labelText.substring(0, index + 1));
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    public void showCardPrizes(int selectedSpotNumber){
        switch (selectedSpotNumber){
            case 1:
                int cWin1 = 0; int cLoss1 = 0;
                int [] cWinLoss1 = spotNum1Prize(matches, cWin1, cLoss1);
                labelCard.setText("         \uD83D\uDCB5 1 Spot Game \uD83D\uDCB5       \n" +
                        "     --------------------------- \n" +
                        "      Match                 Prize\n" +
                        "        1                          $2\n"+
                        "     ---------------------------\n"+
                        "           》Draw Win: $"+ cWinLoss1[0] +
                        "\n           》Draw Loss: $" + cWinLoss1[1] + "\n");
                LabelAnimator();

                break;
            case 4:
                int cWin2 = 0; int cLoss2 = 0;
                int [] cWinLoss2 = spotNum4Prize(matches, cWin2, cLoss2);
                labelCard.setText("        \uD83D\uDCB5 4 Spot Game \uD83D\uDCB5       \n" +
                        "    --------------------------- \n" +
                        "     Match                 Prize\n" +
                        "       4                         $75\n" +
                        "       3                           $5\n"+
                        "       2                           $1\n"+
                        "     ---------------------------\n"+
                        "           》Draw Win: $"+ cWinLoss2[0] +
                        "\n           》Draw Loss: $" + cWinLoss2[1] + "\n");
                LabelAnimator();
                break;
            case 8:
                int cWin3 = 0; int cLoss3 = 0;
                int [] cWinLoss3 = spotNum8Prize(matches, cWin3, cLoss3);
                labelCard.setText("        \uD83D\uDCB5 8 Spot Game \uD83D\uDCB5       \n" +
                        "    --------------------------- \n" +
                        "    Match                    Prize\n" +
                        "       8                      $10,000*\n" +
                        "       7                           $750\n"+
                        "       6                             $50\n"+
                        "       5                             $12\n"+
                        "       4                               $2\n" +
                        "     ---------------------------\n"+
                        "           》Draw Win: $"+ cWinLoss3[0] +
                        "\n           》Draw Loss: $" + cWinLoss3[1] + "\n");

                LabelAnimator();
                break;
            case 10:
                int cWin4 = 0; int cLoss4 = 0;
                int [] cWinLoss4 = spotNum10Prize(matches, cWin4, cLoss4);
                labelCard.setText("        \uD83D\uDCB5 10 Spot Game \uD83D\uDCB5       \n" +
                        "    --------------------------- \n" +
                        "    Match                    Prize\n" +
                        "      10                  $100,000*\n" +
                        "       9                       $4,250\n"+
                        "       8                          $450\n"+
                        "       7                            $40\n"+
                        "       6                            $15\n"+
                        "       5                              $2\n"+
                        "       0                              $5\n"+
                        "     ---------------------------\n"+
                        "           》Draw Win: $"+ cWinLoss4[0] +
                        "\n           》Draw Loss: $" + cWinLoss4[1] + "\n");
                LabelAnimator();
                break;

        }

    }


    // Method to get the draw button and set the style and tooltip
    public Button getDrawButton() {
        // Draw Button Style and Formatting
        drawButton.setTranslateX(470);
        drawButton.setTranslateY(35);
        drawButton.setPrefHeight(60);
        drawButton.setPrefWidth(120);
        drawButton.setMinHeight(0);
        drawButton.setMinWidth(100);
        drawButton.setStyle("-fx-padding: 10px; -fx-background-color: #9966ff; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 18pt;");

        // Draw Button Tooltip
        Tooltip tt= new Tooltip("3. Click Draw to play!");
        tt.setStyle("-fx-padding: 2px; -fx-background-color: grey; -fx-border-radius: 5px; -fx-font-weight: 900; -fx-font-size: 15px;");
        tt.setShowDelay(Duration.seconds(0));
        drawButton.setTooltip(tt);

        changeMouseCursor(drawButton);

        // Event Handler for Draw Button
        drawButton.setOnAction(e-> {

            spotsGrid.setDisable(true);
            clearButton.setDisable(true);
            startAgainButton.setDisable(true);
            spinner.setDisable(true);
            autoPlayButton.setDisable(true);

            // Display the label card on the left
            labelCard.setTranslateX(-330);
            labelCard.setTranslateY(60);

            // Generate Random Numbers when Draw Button is clicked
            generateRandomNumbers();
            changeMatchingButtonColors();
            matches = matchNumbers.size();

            // Display Card through function
            showCardPrizes(selectedSpotNumber);

            // If the
            if(drawCount == spinnerValue){
                drawButton.setDisable(true);
                clearButton.setDisable(true);
                autoPlayButton.setDisable(true);
            }
            drawCount++;

        });

        return drawButton;
    }


    // Method to get the spots label and set the style
    public Label getSpotsLabel(){
        // Spots Label Style and Formatting
        Label spotsLabel = new Label("Spots");
        spotsLabel.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.7), new CornerRadii(5), Insets.EMPTY)));
        spotsLabel.setStyle("-fx-font-weight: 900; -fx-font-size: 30px;");
        spotsLabel.setTranslateX(-590);
        spotsLabel.setTranslateY(55);

        return spotsLabel;
    }

    // Method to get the scene
    public static Scene getScene(){
        return gameScene;
    }

    public void start(Stage stage)  throws FileNotFoundException{
        // Get the root for the GameScreen
        gameRoot = new BorderPane();
        gameRoot.setBackground(getBackgroundImg("src/res/background3.jpg"));

        // Intialize Buttons
        initalizeButtons();

        // Add the menu to the top of the game screen
        HBox menuBox = menuBox();
        menuBox.getChildren().addAll(showTotalWin(), showTotalLoss());
        gameRoot.setTop(menuBox);

        // Logic for menu, background, and music
        menuLogic(stage);
        changeBackground();
        changeMusic();

        // Setting the layout for left
        VBox leftLayout = new VBox();
        leftLayout.getChildren().addAll(showCardRectangle());
        gameRoot.setLeft(leftLayout); // get Grid Numbers

        // Label Card on the left side
        labelCard = new Label("");
        labelCard.setMinWidth(350);
        labelCard.setStyle("-fx-font-weight: 900; -fx-font-size: 25px;");

        // Center Layout Grid 1-80
        HBox centerLayout = new HBox(labelCard, getGridNumbers());
        gameRoot.setCenter(centerLayout); // call grid number

        // Bottom Layout
        HBox bottomLayout = new HBox();

        // Add the buttons to the bottom layout
        bottomLayout.getChildren().addAll(addSpotNumbers(), getAutoPlayButton(),  getDrawSpinner(), getDrawButton(), getStartAgainButton(),  showBalance(), getSpotsLabel(), getClearButton());
        gameRoot.setBottom(bottomLayout);

        // Fade in the scene when the game starts
        FadeTransition fadeInScene = new FadeTransition(Duration.seconds(3), gameRoot);
        fadeInScene.setFromValue(0);
        fadeInScene.setToValue(1);
        fadeInScene.play();

        // Scene
        gameScene = new Scene(gameRoot, 1300, 800);
        stage.setScene(gameScene);

        // Display the stage
        stage.show();
    }
}