import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        Item val;
        Node prev;
        Node next;

        public Node (Item item) {
            val = item;
            prev = null;
            next = null;
        }
    }

    public Deque() {
        // construct an empty deque
        size = 0;
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        // is the deque empty?
        return size == 0;
    }

    public int size() {
        // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) {
        // add the item to the front
        if (item == null)
            throw new java.lang.IllegalArgumentException();
        if (head == null) {
            // empty deque
            head = new Node(item);
            tail = head;
        } else {
            Node temp = head;
            head = new Node(item);
            head.next = temp;
            temp.prev = head;
        }
        size++;
    }

    public void addLast(Item item) {
        // add the item to the end
        if (item == null)
            throw new java.lang.IllegalArgumentException();
        if (tail == null){
            //empty
            head = tail = new Node(item);
        } else {
            Node temp = tail;
            tail = new Node(item);
            tail.prev = temp;
            temp.next = tail;
        }
        size++;
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if (head == null)
            throw new NoSuchElementException();
        Node temp = head;
        if (head.next == null){
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return temp.val;
    }

    public Item removeLast() {
        // remove and return the item from the end
        if (tail == null)
            throw new NoSuchElementException();
        Node temp = tail;
        if (tail.prev == null) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return temp.val;
    }

    public Iterator<Item> iterator(){
        // return an iterator over items in order from front to end
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = head;
        @Override
        public boolean hasNext() {
            return  current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node temp = current;
            current = current.next;
            return temp.val;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> q = new Deque<>();
        q.addFirst("first");
        q.addLast("last");
        q.addFirst("very first");
        for (String s:q) {
            System.out.println(s);
        }
        System.out.println(q.size());
        System.out.println(q.removeLast());
        System.out.println(q.removeLast());
        System.out.println(q.removeLast());
        System.out.println(q.removeFirst());
        //System.out.println(q.removeFirst());
    } // unit testing (optional)

}