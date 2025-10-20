package com.example.platformer.model;
/**
 * Represents the score record of a player in the platformer game.
 * Contains information about the player's name, score, completion status, and elapsed time.
 */
public class PlayerScore {
    /**
     * The name of the player.
     */
    private String playerName;
    /**
     * The score achieved by the player.
     */
    private int score;
    /**
     * Indicates whether the player completed the game level.
     */
    private boolean isWin;
    /**
     * The time taken by the player to complete the level, in seconds.
     * If the player did not complete the level, this value is typically ignored.
     */
    private long elapsedTime;
    /**
     * Constructs a {@code PlayerScore} object with the specified player details.
     *
     * @param playerName  The name of the player.
     * @param score       The score achieved by the player.
     * @param isWin       {@code true} if the player completed the level; {@code false} otherwise.
     * @param elapsedTime The time taken by the player to complete the level, in seconds.
     */
    public PlayerScore(String playerName, int score, boolean isWin, long elapsedTime) {
        this.playerName = playerName;
        this.score = score;
        this.isWin = isWin;
        this.elapsedTime = elapsedTime;

    }
    /**
     * Gets the name of the player.
     *
     * @return The player's name.
     */
    public String getPlayerName() {
        return playerName;
    }
    /**
     * Sets the name of the player.
     *
     * @param playerName The new name of the player.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    /**
     * Gets the score achieved by the player.
     *
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }
    /**
     * Sets the score achieved by the player.
     *
     * @param score The new score to assign to the player.
     */
    public void setScore(int score) {
        this.score = score;
    }
    /**
     * Checks if the player completed the level.
     *
     * @return {@code true} if the player completed the level; {@code false} otherwise.
     */
    public boolean isWin() {
        return isWin;
    }
    /**
     * Sets the completion status of the player for the level.
     *
     * @param win {@code true} if the player completed the level; {@code false} otherwise.
     */
    public void setWin(boolean win) {
        isWin = win;
    }
    /**
     * Gets the time taken by the player to complete the level.
     *
     * @return The elapsed time in seconds.
     */
    public long getElapsedTime() {
        return elapsedTime;
    }
    /**
     * Sets the time taken by the player to complete the level.
     *
     * @param elapsedTime The new elapsed time in seconds.
     */
    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
