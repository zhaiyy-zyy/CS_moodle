import com.example.platformer.Decorator.JumpingPlayer;
import com.example.platformer.Strategy.PlayerMovement;
import com.example.platformer.controller.GameController;
import com.example.platformer.model.Game;
import com.example.platformer.model.Player;
import com.example.platformer.view.RespawnView;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class RespawnViewTest {

    private Game game;
    private RespawnView respawnView;

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
        // Initializes the Game and related components
        game = new Game(null);
        game.score = 100; // Set initial score
        game.uiRoot = new Pane();
        game.gameRoot = new Pane();
        game.scoreLabel = new Label("Score: 100");
        game.uiRoot.getChildren().add(game.scoreLabel);
        game.gameController = new GameController();
        game.gameController.getMusicPlayerController().initialize();
        // Initialize Player
        Pane playerNode = new Pane();
        // Initialize Player's position
        playerNode.setTranslateX(50);
        playerNode.setTranslateY(300);
        game.player = new JumpingPlayer(new Player(playerNode, new PlayerMovement()));

        respawnView = new RespawnView();
    }

    @Test
    public void testRespawnPaneStructure() throws Exception {
        Platform.runLater(() -> {
            respawnView.respawnPane(game);

            //Verify that a respawnPane exists
            Pane respawnPane = (Pane) game.uiRoot.getChildren().get(1);
            assertNotNull(respawnPane, "Respawn pane should exist in the UI.");

            // Verify that the title is correct
            Label messageLabel = (Label) respawnPane.getChildren().get(1);
            assertNotNull(messageLabel, "Message label should exist in the respawn pane.");
            assertEquals("Use 10 points to respawn?", messageLabel.getText(), "Message label text is incorrect.");

            // Verify that the button exists
            Button yesButton = (Button) respawnPane.getChildren().get(2);
            Button noButton = (Button) respawnPane.getChildren().get(3);
            assertNotNull(yesButton, "Yes button should exist.");
            assertNotNull(noButton, "No button should exist.");
            assertEquals("Yes", yesButton.getText(), "Yes button text is incorrect.");
            assertEquals("No", noButton.getText(), "No button text is incorrect.");
        });

        waitForJavaFX();
    }

    @Test
    public void testYesButtonFunctionality() throws Exception {
        Platform.runLater(() -> {
            respawnView.respawnPane(game);

            Pane respawnPane = (Pane) game.uiRoot.getChildren().get(1);
            Button yesButton = (Button) respawnPane.getChildren().get(2);

            yesButton.fire();

            // Verification score reduction
            assertEquals(90, game.score, "Score should decrease by 10 after respawn.");

            // Verify that the player position is reset
            assertEquals(0, game.player.getNode().getTranslateX(), "Player X position should reset to 0.");
            assertEquals(600, game.player.getNode().getTranslateY(), "Player Y position should reset to 600.");
            assertEquals(new Point2D(0, 0), game.player.getVelocity(), "Player velocity should reset to (0, 0).");

            // Verify player status
            assertTrue(game.player.getCanJump(), "Player should be able to jump after respawn.");
            assertFalse(game.player.getDead(), "Player should be alive after respawn.");

            assertFalse(game.isPaused, "Game should resume after respawn.");

            // Verify that the respawnPane is removed
            assertEquals(1, game.uiRoot.getChildren().size(), "Respawn pane should be removed after clicking Yes.");
        });

        waitForJavaFX();
    }

    @Test
    public void testNoButtonFunctionality() throws Exception {
        // Mock GameProcessing
        GameController mockGameProcessing = Mockito.mock(GameController.class);

        Platform.runLater(() -> {
            respawnView.respawnPane(game);

            Pane respawnPane = (Pane) game.uiRoot.getChildren().get(1);
            Button noButton = (Button) respawnPane.getChildren().get(3);

            noButton.fire();
            // Verify that the game has entered the end state
            Mockito.verify(mockGameProcessing).endGame(false);
        });

        waitForJavaFX();

        // Verify that the respawnPane is removed
        assertEquals(1, game.uiRoot.getChildren().size(), "Respawn pane should be removed after clicking No.");
    }

    private void waitForJavaFX() {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(latch::countDown);
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}