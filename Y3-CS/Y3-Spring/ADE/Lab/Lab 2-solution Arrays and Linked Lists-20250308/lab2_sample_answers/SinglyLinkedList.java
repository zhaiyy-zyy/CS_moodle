package arrays;

public class SinglyLinkedList<E> {
	private class Node<E> {
		private E element;
		private Node<E> next;
		public Node(E element, Node<E> next) {
			this.element = element;
			this.next =next;
		}
		public E getElement() {return element ;}
		public Node<E> getNext(){return next;}
		public void setNext(Node<E> node) {
			next=node;
		}
		public String toString() {
			return element.toString();
		}
	}
	
	private Node<E> head=null;
	private Node<E> tail =null;
	private int size =0;
	public SinglyLinkedList() {}
	
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
		return head.getElement();
	}
	
	public E last() {
		if(isEmpty()) {
			return null;
		}
		return tail.getElement();
	}
	
	public void addFirst(E e) {
		head = new Node<E>(e, head);
		if (size==0) {
			tail=head;
		}
		size++;
	}
	
	public void addLast(E e) {
		Node<E> newtail = new Node<E>(e, null);
		if(isEmpty()) {
			head=newtail;
		}else {
			tail.setNext(newtail);
		}
		tail=newtail;
		size++;
	}
	
	public E removeFirst() {
		if(isEmpty()) {
			return null;
		}
		E heade= head.getElement();
		head = head.getNext();
		size--;
		if(size==0) {
			tail=null;
		}
		return heade;
	}
	
	
	 
	  public String toString() {
	    StringBuilder sb = new StringBuilder("[");
	    Node<E> current = head;
	    
	    while(!isEmpty()&& (current!=null)) {
	    	sb.append(current.toString());
	    	if(current.next !=null) {
	    		sb.append(", ");     
	    	}
	    	
	    	current = current.next;	
	    }
	    sb.append("]");
	    return sb.toString();
	  }
	
	public static void main(String[] args) {
		SinglyLinkedList<String> sl = new SinglyLinkedList<String>();
		System.out.println(sl);
		sl.addFirst("First");
		System.out.println(sl);
		sl.addLast("Last");
		System.out.println(sl);
		sl.addFirst("First 2");
		System.out.println(sl);
		sl.addLast("Last 2");
		System.out.println(sl);
		sl.removeFirst();
		System.out.println(sl);
	}
}
