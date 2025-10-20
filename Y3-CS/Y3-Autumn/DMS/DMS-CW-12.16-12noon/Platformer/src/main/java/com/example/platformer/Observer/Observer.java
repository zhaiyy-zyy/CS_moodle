package com.example.platformer.Observer;

/**
 * An interface representing an observer in the Observer design pattern.
 * Classes that implement this interface can receive updates about changes in the subject's state.
 */
public interface Observer {

    /**
     * Called to update the observer with a new score.
     * This method is triggered when the subject's state changes.
     *
     * @param score The updated score value.
     */
    void update(int score);
}
