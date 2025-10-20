package com.example.platformer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing the subject in the Observer design pattern.
 * Manages a list of observers, allowing them to subscribe for updates and notifying them when changes occur.
 */
public class Subject {

    /**
     * A list of observers subscribed to this subject.
     */
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Adds an observer to the subject's list of observers.
     * The observer will receive notifications when the subject's state changes.
     *
     * @param observer The {@link Observer} to be added.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the subject's list of observers.
     * The observer will no longer receive notifications.
     *
     * @param observer The {@link Observer} to be removed.
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all subscribed observers of a change in the subject's state.
     * Passes the updated score to each observer.
     *
     * @param score The updated score value to be sent to the observers.
     */
    public void notifyObservers(int score) {
        for (Observer observer : observers) {
            observer.update(score);
        }
    }
}