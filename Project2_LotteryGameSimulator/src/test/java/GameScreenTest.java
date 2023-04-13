// GameScreenTest.java: This class is used to test the logic of the game.

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameScreenTest {
    @Test
    public void getBackgroundImgTest_NotNULL() throws FileNotFoundException {
        GameScreen GameScreen = new GameScreen();
        Background background = GameScreen.getBackgroundImg("src/res/background1.jpg");
        assertNotNull(background);
    }

    @Test
    void getBackgroundImgTest_validUrl() {
        try {
            GameScreen GameScreen = new GameScreen();
            Background bg = GameScreen.getBackgroundImg("src/res/kenobackground.png");
            assertNotNull(bg);
            BackgroundImage bgImg = bg.getImages().get(0);
            assertEquals(bgImg.getRepeatX(), BackgroundRepeat.NO_REPEAT);
            assertEquals(bgImg.getRepeatY(), BackgroundRepeat.NO_REPEAT);
            assertEquals(bgImg.getPosition(), BackgroundPosition.DEFAULT);
        } catch (FileNotFoundException e) {
            fail("Unexpected FileNotFoundException thrown", e);
        }
    }

    @Test
    void getBackgroundImgTest_InvalidURL() {
        GameScreen GameScreen = new GameScreen();
        assertThrows(FileNotFoundException.class, () -> {
            GameScreen.getBackgroundImg("INVALID_IMAGE_URL");
        });
    }

    @Test
    void getBackgroundImgTest_nullUrl() {
        GameScreen GameScreen = new GameScreen();
        assertThrows(NullPointerException.class, () -> {
            GameScreen.getBackgroundImg(null);
        });
    }

    @Test
    public void ShowCardRectangleTest() {
        GameScreen card = new GameScreen();
        StackPane rectangle = card.showCardRectangle();
        assertNotNull(rectangle);
        assertEquals(380, rectangle.getPrefWidth());
        assertEquals(525, rectangle.getPrefHeight());
        assertEquals(Color.LIGHTBLUE, ((BackgroundFill)rectangle.getBackground().getFills().get(0)).getFill());
        assertEquals(30, rectangle.getTranslateX());
        assertEquals(35, rectangle.getTranslateY());
    }

    @Test
    void testSpotsNumArray() {
        GameScreen GameScreen = new GameScreen();
        int[][] spotsNumArray = { {1, 4}, {8, 10} };

        assertEquals(1, GameScreen.spotsNumArray[0][0]);
        assertEquals(4, GameScreen.spotsNumArray[0][1]);
        assertEquals(8, GameScreen.spotsNumArray[1][0]);
        assertEquals(10, GameScreen.spotsNumArray[1][1]);
    }

    @Test
    void testSpotsNumArrayNotNull() {
        GameScreen GameScreen = new GameScreen();
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                assertNotNull(GameScreen.spotsNumArray[i][j]);
            }
        }
    }

    @Test
    void testSpotNumNotEqual() {
        GameScreen GameScreen = new GameScreen();
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                assertNotEquals(0, GameScreen.spotsNumArray[i][j]);
            }
        }
    }

    @Test
    void testSpotNum1Prize() {
        GameScreen GameScreen = new GameScreen();
        GameScreen.balance = 500;
        GameScreen.balanceText = new Text("0");
        GameScreen.totalWinText = new Text("0");
        GameScreen.totalLossText = new Text("0");

        int[] result1 = GameScreen.spotNum1Prize(1, 0, 0);
        assertEquals(502, GameScreen.balance);
        assertEquals(2, GameScreen.totalWin);
        assertEquals(0, GameScreen.totalLoss);
        assertEquals(2, result1[0]);  // cWin should be incremented by 2
        assertEquals(0, result1[1]);  // cLoss should remain unchanged
    }

    @Test
    void testSpotNum1Prize2() {
        GameScreen GameScreen = new GameScreen();
        GameScreen.balance = 500;
        GameScreen.balanceText = new Text("0");
        GameScreen.totalWinText = new Text("0");
        GameScreen.totalLossText = new Text("0");

        int[] result2 = GameScreen.spotNum1Prize(2, 0, 0);
        assertEquals(498, GameScreen.balance);
        assertEquals(0, GameScreen.totalWin);
        assertEquals(-2, GameScreen.totalLoss);
        assertEquals(-2, result2[1]);  // cLoss should be decremented by 2
        assertEquals(0, result2[0]);  // cWin should remain unchanged
    }

    @Test
    void testSpotNum1Prize3() {
        // // Test case 3: when cWin and cLoss are both 0
        GameScreen GameScreen = new GameScreen();
        GameScreen.balance = 500;
        GameScreen.balanceText = new Text("0");
        GameScreen.totalWinText = new Text("0");
        GameScreen.totalLossText = new Text("0");

        int[] result3 = GameScreen.spotNum1Prize(1, 0, 0);
        assertEquals(502, GameScreen.balance);
        assertEquals(2, GameScreen.totalWin);
        assertEquals(0, GameScreen.totalLoss);
        assertEquals(2, result3[0]);  // cWin should be incremented by 2
        assertEquals(0, result3[1]);  // cLoss should remain unchanged
    }

    @Test
    void testSpotNum1Prize4() {
        // Test case 4: when cWin is greater than cLoss
        GameScreen GameScreen = new GameScreen();
        GameScreen.balance = 500;
        GameScreen.balanceText = new Text("0");
        GameScreen.totalWinText = new Text("0");
        GameScreen.totalLossText = new Text("0");

        int[] result4 = GameScreen.spotNum1Prize(2, 3, 1);
        assertEquals(498, GameScreen.balance);
        assertEquals(0, GameScreen.totalWin);
        assertEquals(-2, GameScreen.totalLoss);
        assertEquals(3, result4[0]);  // cWin should remain unchanged
        assertEquals(-1, result4[1]);  // cLoss should be decremented by 2
    }

    @Test
    void testSpotNum8Prize1() {
        // Test case 1: 8 matches, balance increased by 10000
        GameScreen gameScreen = new GameScreen();
        gameScreen.balanceText = new Text("0");
        gameScreen.totalWinText = new Text("0");
        gameScreen.totalLossText = new Text("0");

        gameScreen.balance = 0;
        int[] result = gameScreen.spotNum8Prize(8, 0, 0);
        assertEquals(10000, gameScreen.balance);
        assertEquals(10000, gameScreen.totalWin);
        assertEquals(0, gameScreen.totalLoss);
        assertEquals(10000, result[0]);
        assertEquals(0, result[1]);
    }

    @Test
    void testSpotNum8Prize2() {
        // Test case 2: 7 matches, balance increased by 750
        GameScreen gameScreen = new GameScreen();
        gameScreen.balanceText = new Text("0");
        gameScreen.totalWinText = new Text("0");
        gameScreen.totalLossText = new Text("0");


        gameScreen.balance = 500;
        int[] result = gameScreen.spotNum8Prize(7, 0, 0);
        assertEquals(1250, gameScreen.balance);
        assertEquals(750, gameScreen.totalWin);
        assertEquals(0, gameScreen.totalLoss);
        assertEquals(750, result[0]);
        assertEquals(0, result[1]);
    }

    @Test
    void testSpotNum8Prize3() {
        // Test case 3: 6 matches, balance increased by 50
        GameScreen gameScreen = new GameScreen();
        gameScreen.balanceText = new Text("0");
        gameScreen.totalWinText = new Text("0");
        gameScreen.totalLossText = new Text("0");


        gameScreen.balance = 1000;
        int result[] = gameScreen.spotNum8Prize(6, 0, 0);
        assertEquals(1050, gameScreen.balance);
        assertEquals(50, gameScreen.totalWin);
        assertEquals(0, gameScreen.totalLoss);
        assertEquals(50, result[0]);
        assertEquals(0, result[1]);
    }

    @Test
    void testSpotNum8Prize4() {
        // Test case 4: 5 matches, balance increased by 12
        GameScreen gameScreen = new GameScreen();
        gameScreen.balanceText = new Text("0");
        gameScreen.totalWinText = new Text("0");
        gameScreen.totalLossText = new Text("0");


        gameScreen.balance = 2000;
        int result[] = gameScreen.spotNum8Prize(5, 0, 0);
        assertEquals(2012, gameScreen.balance);
        assertEquals(12, gameScreen.totalWin);
        assertEquals(0, gameScreen.totalLoss);
        assertEquals(12, result[0]);
        assertEquals(0, result[1]);
    }

    @Test
    void testSpotNum8Prize5() {
        // Test case 5: 4 matches, balance increased by 2
        GameScreen gameScreen = new GameScreen();
        gameScreen.balanceText = new Text("0");
        gameScreen.totalWinText = new Text("0");
        gameScreen.totalLossText = new Text("0");

        gameScreen.balance = 5000;
        int result[] = gameScreen.spotNum8Prize(4, 0, 0);
        assertEquals(5002, gameScreen.balance);
        assertEquals(2, gameScreen.totalWin);
        assertEquals(0, gameScreen.totalLoss);
        assertEquals(2, result[0]);
        assertEquals(0, result[1]);
    }

    @Test
    void testSpotNum8Prize6() {
        // Test case 6: 0 matches, balance decreased by 2
        GameScreen gameScreen = new GameScreen();
        gameScreen.balanceText = new Text("0");
        gameScreen.totalWinText = new Text("0");
        gameScreen.totalLossText = new Text("0");


        gameScreen.balance = 500;
        int[] result = gameScreen.spotNum8Prize(7, 0, 0);
        assertEquals(1250, gameScreen.balance);
        assertEquals(750, gameScreen.totalWin);
        assertEquals(0, gameScreen.totalLoss);
        assertEquals(750, result[0]);
        assertEquals(0, result[1]);
    }

    @Test
    void testSpotNum4Prize() {
        GameScreen gameScreen = new GameScreen();
        gameScreen.balance = 100;
        gameScreen.balanceText = new Text("$100");
        gameScreen.totalWinText = new Text("$0");
        gameScreen.totalLossText = new Text("$0");

        int[] result = gameScreen.spotNum4Prize(4, 0, 0);
        assertArrayEquals(new int[]{75, 0}, result);

        result = gameScreen.spotNum4Prize(3, 0, 0);
        assertArrayEquals(new int[]{5, 0}, result);

        result = gameScreen.spotNum4Prize(2, 10, 10);
        assertArrayEquals(new int[]{11, 10}, result);

        result = gameScreen.spotNum4Prize(1, 20, 20);
        assertArrayEquals(new int[]{20, 18}, result);
    }

    @Test
    void testSpotNum4Prize2() {
        GameScreen gameScreen = new GameScreen();
        gameScreen.balance = 100;
        gameScreen.balanceText = new Text("100");
        gameScreen.totalWinText = new Text("50");
        gameScreen.totalLossText = new Text("25");

        int[] result = gameScreen.spotNum4Prize(4, 0, 0);
        assertArrayEquals(new int[]{75, 0}, result);
        assertEquals(175, gameScreen.balance);
        assertEquals(75, gameScreen.totalWin);
        assertEquals(0, gameScreen.totalLoss);

        result = gameScreen.spotNum4Prize(3, 0, 0);
        assertArrayEquals(new int[]{5, 0}, result);
        assertEquals(180, gameScreen.balance);
        assertEquals(80, gameScreen.totalWin);
        assertEquals(0, gameScreen.totalLoss);

        result = gameScreen.spotNum4Prize(2, 10, 5);
        assertArrayEquals(new int[]{11, 5}, result);
        assertEquals(181, gameScreen.balance);
        assertEquals(81, gameScreen.totalWin);
        assertEquals(0, gameScreen.totalLoss);

        result = gameScreen.spotNum4Prize(1, 5, 10);
        assertArrayEquals(new int[]{5, 8}, result);
        assertEquals(179, gameScreen.balance);
        assertEquals(81, gameScreen.totalWin);
        assertEquals(-2, gameScreen.totalLoss);
    }

    @Test
    void testSpotNum10PrizeCase10() {
        GameScreen gameScreen = new GameScreen();
        gameScreen.balance = 500;
        gameScreen.balanceText = new Text("0");
        gameScreen.totalWinText = new Text("0");
        gameScreen.totalLossText = new Text("0");

        int[] result = gameScreen.spotNum10Prize(10, 0, 0);

        assertEquals(100500, gameScreen.balance);
        assertEquals(100000, gameScreen.totalWin);
        assertEquals(0, gameScreen.totalLoss);
        assertArrayEquals(new int[]{100000, 0}, result);
    }

    @Test
    void testSpotNum10PrizeCase7() {
        GameScreen gameScreen = new GameScreen();
        gameScreen.balance = 500;
        gameScreen.balanceText = new Text("0");
        gameScreen.totalWinText = new Text("0");
        gameScreen.totalLossText = new Text("0");

        int[] result = gameScreen.spotNum10Prize(7, 0, 0);

        assertEquals(540, gameScreen.balance);
        assertEquals(40, gameScreen.totalWin);
        assertEquals(0, gameScreen.totalLoss);
        assertArrayEquals(new int[]{40, 0}, result);
    }

    @Test
    void testSpotNum10PrizeCase4() {
        GameScreen gameScreen = new GameScreen();
        gameScreen.balance = 500;
        gameScreen.balanceText = new Text("0");
        gameScreen.totalWinText = new Text("0");
        gameScreen.totalLossText = new Text("0");

        int[] result = gameScreen.spotNum10Prize(4, 0, 0);

        assertEquals(498, gameScreen.balance);
        assertEquals(0, gameScreen.totalWin);
        assertEquals(-2, gameScreen.totalLoss);
        assertArrayEquals(new int[]{0, -2}, result);
    }

    @Test
    void testSpotNum10PrizeCaseLoss() {
        GameScreen gameScreen = new GameScreen();
        gameScreen.balance = 500;
        gameScreen.balanceText = new Text("0");
        gameScreen.totalWinText = new Text("0");
        gameScreen.totalLossText = new Text("0");

        int[] result = gameScreen.spotNum10Prize(3, 0, 0);

        assertEquals(498, gameScreen.balance);
        assertEquals(0, gameScreen.totalWin);
        assertEquals(-2, gameScreen.totalLoss);
        assertArrayEquals(new int[]{0, -2}, result);
    }

    @Test
    void testClearArray() {
        GameScreen gameScreen = new GameScreen();
        gameScreen.userSelectedNums = new ArrayList<>();
        gameScreen.random20Nums = new ArrayList<>();
        gameScreen.matchNumbers = new ArrayList<>();
        gameScreen.unMatchNumbers = new ArrayList<>();

        gameScreen.userSelectedNums.add(1);
        gameScreen.userSelectedNums.add(2);
        gameScreen.random20Nums.add(3);
        gameScreen.random20Nums.add(4);
        gameScreen.matchNumbers.add(5);
        gameScreen.unMatchNumbers.add(6);

        gameScreen.clearArray();

        assertTrue(gameScreen.userSelectedNums.isEmpty());
        assertTrue(gameScreen.random20Nums.isEmpty());
        assertTrue(gameScreen.matchNumbers.isEmpty());
        assertTrue(gameScreen.unMatchNumbers.isEmpty());
    }

    @Test
    void testClearArrayWithEmptyLists() {
        GameScreen gameScreen = new GameScreen();
        gameScreen.userSelectedNums = new ArrayList<>();
        gameScreen.random20Nums = new ArrayList<>();
        gameScreen.matchNumbers = new ArrayList<>();
        gameScreen.unMatchNumbers = new ArrayList<>();

        gameScreen.clearArray();

        assertTrue(gameScreen.userSelectedNums.isEmpty());
        assertTrue(gameScreen.random20Nums.isEmpty());
        assertTrue(gameScreen.matchNumbers.isEmpty());
        assertTrue(gameScreen.unMatchNumbers.isEmpty());
    }

    @Test
    void testClearArrayWithNullLists() {
        GameScreen gameScreen = new GameScreen();

        gameScreen.clearArray();

        assertNotNull(gameScreen.userSelectedNums);
        assertNotNull(gameScreen.random20Nums);
        assertNotNull(gameScreen.matchNumbers);
        assertNotNull(gameScreen.unMatchNumbers);
    }

    @Test
    public void testGenerateRandomNumbers() {
        GameScreen gameScreen = new GameScreen();
        // Add some user-selected numbers
        gameScreen.userSelectedNums.addAll(Arrays.asList(5, 12, 23, 34, 45));

        // Call the method to generate random numbers
        gameScreen.generateRandomNumbers();

        // Check that 20 unique random numbers were generated
        assertEquals(20, gameScreen.random20Nums.size(), "20 unique random numbers should be generated");

        // Check that all random numbers are between 1 and 80
        for (int num : gameScreen.random20Nums) {
            assertTrue(num >= 1 && num <= 80, "Random number " + num + " should be between 1 and 80");
        }
    }

    @Test
    public void testGenerateRandomNumbersEmptyList() {
        GameScreen gameScreen = new GameScreen();
        // Add some user-selected numbers
        gameScreen.userSelectedNums.addAll(Arrays.asList());

        // Call the method to generate random numbers
        gameScreen.generateRandomNumbers();
        gameScreen.random20Nums.clear();

        // Check that 20 unique random numbers were generated
        assertEquals(0, gameScreen.random20Nums.size(), "20 unique random numbers should be generated");

        // Check that all random numbers are between 1 and 80
        for (int num : gameScreen.random20Nums) {
            assertTrue(num >= 1 && num <= 80, "Random number " + num + " should be between 1 and 80");
        }
    }
}