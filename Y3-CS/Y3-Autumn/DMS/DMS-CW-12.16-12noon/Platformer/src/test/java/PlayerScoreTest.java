import com.example.platformer.model.PlayerScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerScoreTest {

    private PlayerScore playerScore;

    @BeforeEach
    public void setup() {
        // Initializes the test object
        playerScore = new PlayerScore("TestPlayer", 100, true, 5000);
    }

    @Test
    public void testGetPlayerName() {
        //Verifies the player’s name is retrieved correctly.
        assertEquals("TestPlayer", playerScore.getPlayerName());
    }

    @Test
    public void testSetPlayerName() {
        //Verifies that the player’s name can be updated correctly.
        playerScore.setPlayerName("NewPlayer");
        assertEquals("NewPlayer", playerScore.getPlayerName());
    }

    @Test
    public void testGetScore() {
        //Verifies that the player’s score is retrieved correctly.
        assertEquals(100, playerScore.getScore());
    }

    @Test
    public void testSetScore() {
        //Verifies that the player’s score can be updated correctly.
        playerScore.setScore(200);
        assertEquals(200, playerScore.getScore());
    }

    @Test
    public void testIsWin() {
        //Verifies the win status of the player.
        assertTrue(playerScore.isWin());
    }

    @Test
    public void testSetWin() {
        //Verifies that the win status can be updated correctly.
        playerScore.setWin(false);
        assertFalse(playerScore.isWin());
    }

    @Test
    public void testGetElapsedTime() {
        //Verifies that the elapsed time is retrieved correctly.
        assertEquals(5000, playerScore.getElapsedTime());
    }

    @Test
    public void testSetElapsedTime() {
        //Verifies that the elapsed time can be updated correctly.
        playerScore.setElapsedTime(10000);
        assertEquals(10000, playerScore.getElapsedTime());
    }

    @Test
    public void testConstructorValues() {
        //Verifies that the constructor initializes the player’s attributes correctly.
        PlayerScore newPlayerScore = new PlayerScore("ConstructorPlayer", 300, false, 7000);
        assertEquals("ConstructorPlayer", newPlayerScore.getPlayerName());
        assertEquals(300, newPlayerScore.getScore());
        assertFalse(newPlayerScore.isWin());
        assertEquals(7000, newPlayerScore.getElapsedTime());
    }
}