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

import main.utils.DefaultComparator;
import main.utils.Queue;
import main.utils.LinkedQueue;

import java.util.Arrays;
import java.util.Comparator;

class MergeSort {

    //-------- support for top-down merge-sort of arrays --------

    /**
     * Merge contents of arrays S1 and S2 into properly sized array S.
     */
    public static <K> void merge(K[] S1, K[] S2, K[] S, Comparator<K> comp) {
        // TODO
    }

    /**
     * Merge-sort contents of array S.
     */
    public static <K> void mergeSort(K[] S, Comparator<K> comp) {
        // TODO
    }

    //-------- support for top-down merge-sort of queues --------

    /**
     * Merge contents of sorted queues S1 and S2 into empty queue S.
     */
    public static <K> void merge(Queue<K> S1, Queue<K> S2, Queue<K> S,
                                 Comparator<K> comp) {
        // TODO
    }

    /**
     * Merge-sort contents of queue.
     */
    public static <K> void mergeSort(Queue<K> S, Comparator<K> comp) {
        // TODO
    }

    //-------- support for bottom-up merge-sort of arrays --------

    /**
     * Merges in[start..start+inc-1] and in[start+inc..start+2*inc-1] into out.
     */
    public static <K> void merge(K[] in, K[] out, Comparator<K> comp,
                                 int start, int inc) {
        // TODO
    }

    @SuppressWarnings({"unchecked"})
    /** Merge-sort contents of data array. */
    public static <K> void mergeSortBottomUp(K[] orig, Comparator<K> comp) {
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
        mergeSort(queue, new DefaultComparator<>());
        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }
}
