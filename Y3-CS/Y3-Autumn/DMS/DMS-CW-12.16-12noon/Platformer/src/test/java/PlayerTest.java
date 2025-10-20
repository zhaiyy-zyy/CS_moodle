import com.example.platformer.Decorator.JumpingPlayer;
import com.example.platformer.Strategy.PlayerMovement;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.example.platformer.model.Player;
public class PlayerTest {

    private JumpingPlayer player;
    private Rectangle finishLine;

    @BeforeEach
    public void setup() {
        // Use Rectangle to simulate the Player's Node
        Rectangle playerNode = new Rectangle(40, 40);
        player =new JumpingPlayer( new Player(playerNode,new PlayerMovement()));

        // Initialize finish line (it could be a Rectangle with a defined position)
        finishLine = new Rectangle(60, 60);  // Assuming the finish line has width and height of 60
        finishLine.setTranslateX(1220); // Finish line positioned at x = 1200
        finishLine.setTranslateY(600); // Position the finish line at y = 600
    }

    @Test
    public void testJump_CanJump() {
        player.setCanJump(true);
        player.setVelocity(new Point2D(0, 0));
        player.jump();

        // Verify that the velocity is updated to the y-component of -30 after the jump
        assertEquals(new Point2D(0, -30), player.getVelocity());
        assertFalse(player.getCanJump()); // After jumping, set to No jumping
    }

    @Test
    public void testJump_CannotJump() {
        player.setCanJump(false);
        Point2D initialVelocity = new Point2D(0, 0);
        player.setVelocity(initialVelocity);
        player.jump();

        // Verify that the speed remains the same after the jump
        assertEquals(initialVelocity, player.getVelocity());
    }

    @Test
    public void testSetTranslateXAndY() {
        double x = 100.0;
        double y = 150.0;
        player.setTranslateX(x);
        player.setTranslateY(y);

        // Verify that the X and Y coordinates of the player node are set correctly
        assertEquals(x, player.getTranslateX());
        assertEquals(y, player.getTranslateY());
    }

    @Test
    public void testResetPosition() {
        double initialX = 50.0;
        double initialY = 60.0;
        player.setTranslateX(100);
        player.setTranslateY(120);

        player.setPosXY(initialX, initialY);

        // Verify that the player's position has been reset
        assertEquals(initialX, player.getTranslateX());
        assertEquals(initialY, player.getTranslateY());
    }

    @Test
    public void testSetAndGetVelocity() {
        Point2D velocity = new Point2D(5, -10);
        player.setVelocity(velocity);

        // Verify that the speed is set correctly
        assertEquals(velocity, player.getVelocity());
    }

    @Test
    public void testSetAndGetDead() {
        player.setDead(true);

        // Verify that the death status is set correctly
        assertTrue(player.getDead());
        assertTrue(player.getDead());

        player.setDead(false);
        assertFalse(player.getDead());
    }

    @Test
    public void testFinishLineCollision() {
        // Ensure the player is placed at the finish line position
        player.setTranslateX(finishLine.getTranslateX());  // Align player with finish line's X position
        player.setTranslateY(finishLine.getTranslateY());  // Align player with finish line's Y position

        // Check for collision
        boolean collisionDetected = player.checkFinishLineCollision(finishLine);

        // Verify that collision was detected
        assertTrue(collisionDetected, "The player should have collided with the finish line.");
    }
}