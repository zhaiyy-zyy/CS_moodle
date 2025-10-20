
public class DoublyLinkedList<E>{
	private class Node<E> {
		private E element;
		private Node<E> next;
		private Node<E> prev;
		public Node(E element, Node<E> prev, Node<E> next) {
			this.element = element;
			this.prev= prev;
			this.next =next;
		}
		public E getElement() {return element ;}
		public Node<E> getPrev(){return prev;}
		public Node<E> getNext(){return next;}
		public void setPrev(Node<E> node) {
			prev=node;
		}
		public void setNext(Node<E> node) {
			next=node;
		}
		public String toString() {
			return element.toString();
		}
	}
	
	private Node<E> header;
	private Node<E> trailer;
	private int size=0;
	
	public DoublyLinkedList() {
		header = new Node<E>(null, null, null);
		trailer = new Node<E>(null, header, null);
		header.setNext(trailer);
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public E first() {
		if(isEmpty()) {
			return null;
		}
		return header.getNext().getElement();
	}
	
	public E last() {
		if(isEmpty()) {
			return null;
		}
		return trailer.getPrev().getElement();
	}
	
	public void addFirst(E e) {
		addBetween(e, header, header.getNext());
	}
	
	public void addLast(E e) {
		addBetween(e, trailer.getPrev(), trailer);
	}
	
	public E removeFirst() {
		if(isEmpty()) {
			return null;
		}
		
		return remove(header.getNext()) ;
	}
	
	public E removeLast() {
		if(isEmpty()) {
			return null;
		}
		
		return remove(trailer.getPrev()) ;
		
	}
	private void addBetween(E e, Node<E> prev, Node<E> next) {
		Node<E> newnode = new Node<E>(e, prev, next);
		prev.setNext(newnode);
		next.setPrev(newnode);
		size++;
	}
	
	private E remove(Node<E> node) {
		Node<E> prev = node.getPrev();
		Node<E> next = node.getNext();
		prev.setNext(next);
		next.setPrev(prev);
		size--;
		return node.getElement();
	}
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
	    Node<E> current = header.next;
	   
	    while(!isEmpty()&& (current.getElement()!=null)) {
	    	sb.append(current.getElement().toString());
	    	if(current.next !=trailer) {
	    		sb.append(", ");     
	    	}
	    	
	    	current = current.next;	
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
