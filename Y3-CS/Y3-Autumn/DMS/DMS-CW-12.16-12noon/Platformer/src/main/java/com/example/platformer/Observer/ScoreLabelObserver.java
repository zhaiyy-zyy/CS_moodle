package com.example.platformer.Observer;

import javafx.scene.control.Label;

/**
 * A concrete implementation of the {@link Observer} interface.
 * Updates a {@link Label} to display the current score when notified of changes.
 */
public class ScoreLabelObserver implements Observer {

    /**
     * The {@link Label} that displays the score.
     */
    private Label scoreLabel;

    /**
     * Constructs a {@code ScoreLabelObserver} with the specified score label.
     *
     * @param scoreLabel The {@link Label} that will be updated with the current score.
     */
    public ScoreLabelObserver(Label scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    /**
     * Updates the score label to display the latest score.
     * This method is called when the subject notifies its observers of a score change.
     *
     * @param score The updated score value.
     */
    @Override
    public void update(int score) {
        scoreLabel.setText("Score: " + score);
    }
}
