package com.example.platformer.controller;
import com.example.platformer.State.MusicPausedState;
import com.example.platformer.model.Game;
import com.example.platformer.view.GameView;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import com.example.platformer.State.MusicPlayerState;

/**
 * Controller class for managing the background music in the platformer game.
 * This class provides functionality to play, pause, stop, and toggle the background music.
 */
public class MusicPlayerController {
    /**
     * The current state of the music player, represented as a {@link MusicPlayerState}.
     */
    private MusicPlayerState state;
    /**
     * The {@link MediaPlayer} instance used to play the background music.
     */
    private MediaPlayer mediaPlayer;
    /**
     * The toggle button for controlling music playback.
     */
    private Button musicToggleButton;
    /**
     * The {@link Game} object representing the current game state.
     */
    private Game game;
    /**
     * Constructs a {@code MusicPlayerController} with a reference to the game model.
     * Initializes the music player with the background music provided in the game model.
     *
     * @param game The {@link Game} object representing the current game state.
     */
    public MusicPlayerController(Game game) {
        this.game = game;
        this.mediaPlayer = game.backgroundMusicPlayer;
    }
    /**
     * Initializes the music player with the default background music.
     * Sets the music to loop indefinitely and starts with a paused state.
     */
    public void initialize() {
        String musicFile = getClass().getResource("/static/music.mp3").toExternalForm();
        Media backgroundMusic = new Media(musicFile);
        mediaPlayer = new MediaPlayer(backgroundMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.5);
        this.state = new MusicPausedState();
        this.state.handle(this);
    }
    /**
     * Sets up the music toggle button in the game's UI.
     * The button allows users to toggle between playing and pausing the music.
     */
    public void setupMusicToggleButton(){
        GameView gameView = new GameView();
        musicToggleButton = gameView.musicToggleButton();
        musicToggleButton.setOnAction(event -> {
            if (mediaPlayer!= null) {
                pressButton();
            }
        });
        this.game.uiRoot.getChildren().add(musicToggleButton);
    }
    /**
     * Simulates a button press for toggling the music state.
     * Delegates the action to the current {@link MusicPlayerState}.
     */
    public void pressButton() {
        state.handle(this);
    }
    /**
     * Sets the current state of the music player.
     *
     * @param state The new {@link MusicPlayerState}.
     */
    public void setState(MusicPlayerState state) {
        this.state = state;
    }
    /**
     * Gets the current {@link MediaPlayer} instance.
     *
     * @return The media player for the background music.
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
    /**
     * Gets the music toggle button.
     *
     * @return The button for toggling music playback.
     */
    public Button getMusicToggleButton() {
        return musicToggleButton;
    }
    /**
     * Sets the music toggle button.
     *
     * @param musicToggleButton The button for toggling music playback.
     */
    public void setMusicToggleButton(Button musicToggleButton) {
        this.musicToggleButton = musicToggleButton;
    }
    /**
     * Updates the text displayed on the music toggle button.
     *
     * @param text The new text to display on the button.
     */
    public void updateButtonText(String text) {
        musicToggleButton.setText(text);
    }
    /**
     * Stops the background music playback.
     */
    public void stopMusic(){
        mediaPlayer.stop();
    }
    /**
     * Starts or resumes the background music playback.
     */
    public void playMusic(){
        mediaPlayer.play();
    }

    public void pauseMusic() {
        mediaPlayer.pause();
    }
}
