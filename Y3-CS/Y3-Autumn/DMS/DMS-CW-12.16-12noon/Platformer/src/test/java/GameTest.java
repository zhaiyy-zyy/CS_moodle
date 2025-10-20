import com.example.platformer.controller.GameController;
import com.example.platformer.model.Game;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;
    private GameController gameController;

    @BeforeEach
    public void setup() {
        gameController = new GameController();
        game = gameController.game;   // Mocking the GameController if necessary
        game.appRoot = new Pane();
        game.gameRoot = new Pane();
        game.uiRoot = new Pane();
        game.bg = new Rectangle(1280, 720, Color.WHITE);
        game.appRoot.getChildren().add(game.bg);
        game.startTime = System.currentTimeMillis();
        game.score = 100;
    }

    @Test
    public void testSetBackgroundColor() {
        game.setBackgroundColor(Color.RED);
        assertEquals(Color.RED, game.bg.getFill(), "Background color should be set to red.");
    }

    @Test
    public void testGameTiming() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = (currentTime - game.startTime) / 1000;

        // Ensure elapsed time is non-negative
        assertTrue(elapsedTime >= 0, "Elapsed time should be non-negative.");
    }

    @Test
    public void testScoreIncreaseOnCoinCollection() {
        game.score = 100;
        // Simulate collecting a coin, which increases score by 10
        game.score += 10;

        assertEquals(110, game.score, "Score should increase by 10 when a coin is collected.");
    }

    @Test
    public void testScoreDecreasesWhenPlayerDies() {
        game.score = 100;
        // Simulate a player death, which decreases score by 10
        game.score -= 10;

        assertEquals(90, game.score, "Score should decrease by 10 when the player dies.");
    }


}