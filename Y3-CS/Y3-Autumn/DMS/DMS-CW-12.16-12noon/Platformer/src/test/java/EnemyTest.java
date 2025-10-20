import com.example.platformer.Strategy.EnemyMovement;
import com.example.platformer.Strategy.PlayerMovement;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.example.platformer.model.Enemy;
import com.example.platformer.model.Player;
public class EnemyTest {

    private Enemy enemy;
    private Player player;

    @BeforeEach
    public void setup() {
        // Create enemy and player nodes
        Rectangle enemyNode = new Rectangle(40, 40);
        Rectangle playerNode = new Rectangle(40, 40);

        // Initialize enemies and players

        enemy = new Enemy(enemyNode, new EnemyMovement());
        player = new Player(playerNode, new PlayerMovement());
    }

    @Test
    public void testMoveX() {
        double initialX = enemy.getNode().getTranslateX();
        enemy.moveX(10,null);
        assertEquals(initialX + 10, enemy.getNode().getTranslateX(), 0.01);
    }

    @Test
    public void testMoveY() {
        double initialY = enemy.getNode().getTranslateY();
        enemy.setTranslateY(enemy.getTranslateY()+5);
        assertEquals(initialY + 5, enemy.getNode().getTranslateY(), 0.01);
    }

    @Test
    public void testSetVelocity() {
        Point2D newVelocity = new Point2D(2, 3);
        enemy.setVelocity(newVelocity);
        assertEquals(newVelocity, enemy.getVelocity());
    }

    @Test
    public void testCheckCollision_NoCollision() {
        // Set the initial position of the player and the enemy so that they do not overlap
        player.setTranslateX(100);
        player.setTranslateY(100);
        enemy.getNode().setTranslateX(200);
        enemy.getNode().setTranslateY(200);

        // Check no collision
        assertFalse(enemy.checkCollision(player));
    }

    @Test
    public void testCheckCollision_WithCollision() {
        // Place the player and the enemy in the same position to ensure that collisions occur
        double sameX = 100;
        double sameY = 100;
        player.setTranslateX(sameX);
        player.setTranslateY(sameY);
        enemy.getNode().setTranslateX(sameX);
        enemy.getNode().setTranslateY(sameY);

        // Check collision
        assertTrue(enemy.checkCollision(player));
    }
}