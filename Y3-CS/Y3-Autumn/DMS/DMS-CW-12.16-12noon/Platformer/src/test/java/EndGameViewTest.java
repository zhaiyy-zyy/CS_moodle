import com.example.platformer.model.Game;
import com.example.platformer.view.EndView;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EndGameViewTest {

    private Game game;
    private EndView endView;

    @BeforeAll
    public static void initToolkit() throws Exception {
        if (!Platform.isFxApplicationThread()) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            latch.await();
        }
    }

    @BeforeEach
    public void setUp() {
        // Initialize the Game and EndView
        game = new Game(null);
        game.score = 100;
        game.currentLevel = 0;
        game.levels = new String[][]{{"Level1"}, {"Level2"}, {"Level3"}, {"Level4"}};
        game.uiRoot = new Pane();

        endView = new EndView();
    }

    @Test
    public void testEndGamePaneGameOver() {
        // Test the "Game Over" situation
        Map<String, Button> buttons = endView.endGamePane(false, game, 120);

        // Verify that the UI was correctly added to game.uiRoot
        assertNotNull(game.uiRoot.lookup("#endViewPane"), "End game pane should be initialized.");

        // Verify the title
        Label titleLabel = (Label) game.uiRoot.lookup("#endViewPane").lookup(".label");
        assertNotNull(titleLabel, "Title label should not be null.");
        assertEquals("Game Over", titleLabel.getText(), "The title label should display 'Game Over'.");

        //  Verify button
        assertNotNull(buttons.get("leaderboardButton"), "Leaderboard button should exist.");
        assertNotNull(buttons.get("restartButton"), "Restart button should exist.");
        assertNotNull(buttons.get("exitButton"), "Exit button should exist.");
        //Verify that the Next Level button should not exist (because it failed)
        Button nextButton = buttons.get("nextButton");
        assertEquals(null, nextButton, "Next Level button should not exist when game is lost.");
    }

    @Test
    public void testEndGamePaneWinWithNextLevel() {
        // Test if the player wins and has the next level
        game.currentLevel = 0; // current level
        Map<String, Button> buttons = endView.endGamePane(true, game, 120);

        // Verify that the "Next Level" button exists
        Button nextButton = buttons.get("nextButton");
        assertNotNull(nextButton, "Next Level button should exist when there is another level.");
        assertEquals("Next Level", nextButton.getText(), "Next button text should be 'Next Level'.");

        // Verify that other buttons exist
        assertNotNull(buttons.get("leaderboardButton"), "Leaderboard button should exist.");
        assertNotNull(buttons.get("restartButton"), "Restart button should exist.");
        assertNotNull(buttons.get("exitButton"), "Exit button should exist.");
    }

    @Test
    public void testEndGamePaneWinWithoutNextLevel() {
        // Test situations where the player wins but does not have the next level
        game.currentLevel = game.levels.length - 1; // final level
        Map<String, Button> buttons = endView.endGamePane(true, game, 300);

        // Verify that the "Next Level" button does not exist
        Button nextButton = buttons.get("nextButton");
        assertEquals(null, nextButton, "Next Level button should not exist when there are no more levels.");

        // Verify that other buttons exist
        assertNotNull(buttons.get("leaderboardButton"), "Leaderboard button should exist.");
        assertNotNull(buttons.get("restartButton"), "Restart button should exist.");
        assertNotNull(buttons.get("exitButton"), "Exit button should exist.");
    }
}