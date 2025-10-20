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


public class DoublyLinkedList<E> {
	private class Node<E> {
		private E element;
		private Node<E> next;
		private Node<E> prev;

		public Node(E element, Node<E> prev, Node<E> next) {
			this.element = element;
			this.prev = prev;
			this.next = next;
		}

		public E getElement() {
			return element;
		}

		public Node<E> getPrev() {
			return prev;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setPrev(Node<E> node) {
			prev = node;
		}

		public void setNext(Node<E> node) {
			next = node;
		}

		public String toString() {
			return element.toString();
		}
	}

	private Node<E> header;
	private Node<E> trailer;
	private int size = 0;

	public DoublyLinkedList() {
		header = new Node<E>(null, null, null);
		trailer = new Node<E>(null, header, null);
		header.setNext(trailer);
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E first() {
		if (isEmpty()) return null;
		return header.getNext().getElement();
	}

	public E last() {
		if (isEmpty()) return null;
		return trailer.getPrev().getElement();
	}

	public void addFirst(E e) {
		addBetween(e, header, header.getNext());
	}

	public void addLast(E e) {
		addBetween(e, trailer.getPrev(), trailer);
	}

	public E removeFirst() {
		if (isEmpty()) return null;
		return remove(header.getNext());
	}

	public E removeLast() {
		if (isEmpty()) return null;
		return remove(trailer.getPrev());
	}

	private void addBetween(E e, Node<E> prev, Node<E> next) {
		Node<E> newNode = new Node<>(e, prev, next);
		prev.setNext(newNode);
		next.setPrev(newNode);
		size++;
	}

	private E remove(Node<E> node) {
		Node<E> prevNode = node.getPrev();
		Node<E> nextNode = node.getNext();
		prevNode.setNext(nextNode);
		nextNode.setPrev(prevNode);
		size--;
		return node.getElement();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		Node<E> current = header.getNext();
		while (current != trailer) {
			sb.append(current.getElement().toString());
			if (current.getNext() != trailer) {
				sb.append(", ");
			}
			current = current.getNext();
		}
		sb.append("]");
		return sb.toString();
	}

	public static void main(String[] args) {
		DoublyLinkedList<String> dl = new DoublyLinkedList<String>();
		System.out.println(dl);
		dl.addFirst("First");
		System.out.println(dl);
		dl.addLast("Last");
		System.out.println(dl);
		dl.addFirst("First 2");
		System.out.println(dl);
		dl.addLast("Last 2");
		System.out.println(dl);
		dl.removeFirst();
		System.out.println(dl);
		dl.removeLast();
		System.out.println(dl);
	}
}