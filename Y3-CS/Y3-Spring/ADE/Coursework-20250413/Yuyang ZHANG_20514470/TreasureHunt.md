# Treasure Hunt Game

## Project Introduction
"Treasure Hunt" is a 2D grid-based treasure hunting game. Players use the up, down, left, and right keys to control the character to find three hidden treasures on the map and avoid obstacles. The game map is randomly generated at each startup to ensure that each game is unique. Players gradually find treasures by moving on the map while avoiding obstacles.

## How to Run the Game

### System Requirements
- Java version: JDK 23 version is required.
- IDE: IntelliJ IDEA is recommended.
- Maven: Maven 3.9.x or a higher version.

### Running on macOS and Windows
Due to the lack of IntelliJ IDEA software on the computers in MB306 classroom, I attempted to install it but was unsuccessful. However, the game runs successfully on both macOS and Windows systems using Java 23 version, and also on IntelliJ IDEA in the IAMET406 classroom.

## Game Features
- Players start with 100 points, and each move consumes 1 point.
- **Map Generation**: The game map is a 20x20 two-dimensional grid containing obstacles, empty paths and treasures. The map is randomly generated in each game.
- **Player Movement**: Players can move up, down, left, and right through up, down, left, right on the keyboard. When the player move past a grid, flowers will show up to remind the player of their previous path.
- **Treasure**: The player's goal is to find three treasures on the map. For each treasure found, the player will receive a 20-point bonus.
- **Obstacle**: The player must avoid hitting obstacles, otherwise they will lose 10 points each time. Each obstacle occupies a single grid on the map.
- **Hint function**: When the player's score is less than 60 points, the game will remind the player to use the hint function. Each use of the hint function will consume 3 points. Players can use hints to see the next step to reach the treasure.
- **Add points function**: When the player's score is less than 10 points, the system will allow the player to click the energy bottle and add a 20-point bonus to help the player continue the game.
- **Ranking**: The game can record the top 5 history play which is displayed in the ranking page.
- **Developer mode**: Game developers can click the space bar to view the shortest path to the treasure and test the effectiveness of the algorithm.
- **Exit game**: In the game, players can select the exit button to end the current game.
- **Restart the game**: During the game, players can select the restart button to restart the game.