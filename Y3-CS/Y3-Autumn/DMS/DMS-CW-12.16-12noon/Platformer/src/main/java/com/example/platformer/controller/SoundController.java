package com.example.platformer.controller;

import com.example.platformer.model.Game;
import com.example.platformer.model.LevelData;
import com.example.platformer.util.Util;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import java.io.File;
/**
 * Utility class for managing sound playback and setting up background elements in the platformer game.
 * Provides static methods for playing music and configuring the game's background settings.
 */
public class SoundController {
    /**
     * Plays a music file from the specified file path.
     * Creates a new {@link MediaPlayer} instance to play the sound.
     *
     * @param filepath The path to the music file to be played.
     *                 The file must be a valid media file (e.g., MP3, WAV) accessible from the file system.
     */
    public static void playMusic(String filepath){
        Media sound = new Media(new File(filepath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    /**
     * Sets up the background for the game by creating a rectangle representing the game background
     * and initializing the level width based on the first level's data.
     *
     * This method performs the following tasks:
     * Creates a {@link Rectangle} of size 1280x720 to represent the background.
     * Fills the rectangle with the background color defined in {@link Util#backgroundColor}.
     * Calculates the level width based on the first level's data in {@link LevelData#Level1}.
     * Assigns the created background and level width to the {@link Game} object.
     *
     * @param game The {@link Game} object to which the background and level width will be assigned.
     */
    public static void setupBackground(Game game) {
        Rectangle bg = new Rectangle(1280, 720);
        bg.setFill(Util.backgroundColor);
        game.bg = bg;
        game.levelWidth = LevelData.Level1[0].length() * 60;
    }
}
