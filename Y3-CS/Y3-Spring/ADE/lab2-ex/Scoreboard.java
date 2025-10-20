package template;



/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


/** Class for storing high scores in an array in nondecreasing order. */
public class Scoreboard {
  private int numEntries = 0;              // number of actual entries
  private GameEntry[] board;               // array of game entries (names & scores)
  /** Constructs an empty scoreboard with the given capacity for storing entries. */
  public Scoreboard(int capacity) {
    board = new GameEntry[capacity];
  }

  /** Attempt to add a new score to the collection (if it is high enough) */
  public void add(GameEntry e) {
    if (numEntries < board.length) {
      // If there's room in the board, add the new entry.
      board[numEntries] = e;
      numEntries++;
    } else {
      // If the board is full, find the lowest score and replace it if necessary
      int minIndex = 0;
      for (int i = 1; i < numEntries; i++) {
        if (board[i].getScore() < board[minIndex].getScore()) {
          minIndex = i;
        }
      }
      if (e.getScore() > board[minIndex].getScore()) {
        board[minIndex] = e;
      }
    }

    // Sort the board in descending order based on the scores
    sortBoard();
  }

  /** Remove and return the high score at index i. */
  public GameEntry remove(int i) throws IndexOutOfBoundsException {
    if (i < 0 || i >= numEntries) {
      throw new IndexOutOfBoundsException("Invalid index.");
    }

    // Get the removed game entry
    GameEntry removed = board[i];

    // Shift the remaining entries to the left
    for (int j = i; j < numEntries - 1; j++) {
      board[j] = board[j + 1];
    }
    board[numEntries - 1] = null; // Clear the last entry
    numEntries--; // Decrement the number of entries

    // Return the removed entry
    return removed;
  }

  /** Sort the board array in descending order based on scores. */
  private void sortBoard() {
    for (int i = 0; i < numEntries - 1; i++) {
      for (int j = i + 1; j < numEntries; j++) {
        if (board[i].getScore() < board[j].getScore()) {
          // Swap the entries
          GameEntry temp = board[i];
          board[i] = board[j];
          board[j] = temp;
        }
      }
    }
  }

  /** Returns a string representation of the high scores list. */
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    for (int j = 0; j < numEntries; j++) {
      if (j > 0)
        sb.append(", ");                   // separate entries by commas
      sb.append(board[j]);
    }
    sb.append("]");
    return sb.toString();
  }

  public static void main(String[] args) {
    // The main method
    Scoreboard highscores = new Scoreboard(5);
    String[] names = {"Rob", "Mike", "Rose", "Jill", "Jack", "Anna", "Paul", "Bob"};
    int[] scores = {750, 1105, 590, 740, 510, 660, 720, 400};

    for (int i=0; i < names.length; i++) {
      GameEntry gE = new GameEntry(names[i], scores[i]);
      System.out.println("Adding " + gE);
      highscores.add(gE);
      System.out.println(" Scoreboard: " + highscores);
    }
    System.out.println("Removing score at index " + 3);
    highscores.remove(3);
    System.out.println(highscores);
    System.out.println("Removing score at index " + 0);
    highscores.remove(0);
    System.out.println(highscores);
    System.out.println("Removing score at index " + 1);
    highscores.remove(1);
    System.out.println(highscores);
    System.out.println("Removing score at index " + 1);
    highscores.remove(1);
    System.out.println(highscores);
    System.out.println("Removing score at index " + 0);
    highscores.remove(0);
    System.out.println(highscores);
  }
}
