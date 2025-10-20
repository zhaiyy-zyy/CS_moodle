import com.example.platformer.controller.MainMenuController;
import com.example.platformer.model.Game;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;


@ExtendWith(ApplicationExtension.class)
public class MainMenuTestFx {

    private MainMenuController controller;

    @Start
    public void start(Stage stage) {
        // Initializes the controller or launches the home screen
        controller = new MainMenuController();
    }

    @BeforeEach
    public void setUp() {
        // Reset Game or other dependent objects
        controller.game = new Game(null);
    }


    @Test
    public void testStartButton(FxRobot robot) {
        // Call the Start method
        robot.interact(() -> controller.Start());
        // Verify that the game scene is initialized
        assertNotNull(controller.game);
    }

    @Test
    public void testOptionsButton(FxRobot robot) {
        // Options
        robot.interact(() -> controller.Options());

        // Verify that interface elements are visible
        verifyThat(".color-picker", isVisible());
        verifyThat(".button", isVisible());
        robot.sleep(1000);
        robot.clickOn(".button");
    }

    @Test
    public void testInfoButton(FxRobot robot) {
        //  Info
        robot.interact(() -> controller.Info());

        verifyThat(".label", isVisible());
        verifyThat(".button", isVisible());
        robot.clickOn(".button");
    }

    @Test
    public void testSelectLevelButton(FxRobot robot) {
        // SelectLevel
        robot.interact(() -> controller.SelectLevel());

        verifyThat(".button", isVisible());
        robot.clickOn(".button");
    }

    @Test
    public void testShowLeaderboardButton(FxRobot robot) {
        // ShowLeaderboard
        robot.interact(() -> controller.ShowLeaderboard());

        verifyThat(".button", isVisible());
        robot.clickOn(".button");
    }

    @AfterEach
    public void tearDown(FxRobot robot) {
        robot.sleep(2000);
    }
}
