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
package main;

import java.util.Comparator;

import main.utils.DefaultComparator;
import main.utils.Queue;
import main.utils.LinkedQueue;

class QuickSort {

    //-------- support for top-down quick-sort of queues --------

    /**
     * Quick-sort contents of a queue.
     */
    public static <K> void quickSort(Queue<K> S, Comparator<K> comp) {
        // TODO
    }

    //-------- support for in-place quick-sort of an array --------

    /**
     * Quick-sort contents of a queue.
     */
    public static <K> void quickSortInPlace(K[] S, Comparator<K> comp) {
        quickSortInPlace(S, comp, 0, S.length - 1);
    }

    /**
     * Sort the subarray S[a..b] inclusive.
     */
    private static <K> void quickSortInPlace(K[] S, Comparator<K> comp,
                                             int a, int b) {
        // TODO
    }

    public static void main(String[] args) {
        LinkedQueue<Character> queue = new LinkedQueue<>();
        queue.enqueue('C');
        queue.enqueue('E');
        queue.enqueue('B');
        queue.enqueue('D');
        queue.enqueue('A');
        queue.enqueue('I');
        queue.enqueue('J');
        queue.enqueue('L');
        queue.enqueue('K');
        queue.enqueue('H');
        queue.enqueue('G');
        queue.enqueue('F');
        quickSort(queue, new DefaultComparator<>());
        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }
}
