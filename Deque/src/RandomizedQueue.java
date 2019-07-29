import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a; // array of item
    private int n; // number of elements on queue

    public RandomizedQueue(){
        // construct an empty randomized queue
        a = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty(){
        // is the randomized queue empty?
        return n==0;
    }

    public int size() {
        // return the number of items on the randomized queue
        return n;
    }

    //resize  the underlying array holding the elements
    private void resize(int capacity){
        assert capacity>=n;

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++){
            temp[i]=a[i];
        }
        a = temp;
    }

    public void enqueue(Item item) {
        // add the item
        if (n == a.length) resize(2*a.length); //double size of array if necessary
        a[n++] = item;

    }

    public Item dequeue() {
        // remove and return a random item
        if (isEmpty()) throw new NoSuchElementException("queue underflow");
        int r = StdRandom.uniform(n);
        Item item = a[r];
        a[r] = a[n-1];
        a[n-1] = null;
        n--;
        // shrink size
        if (n>0 && n == a.length/4) resize(a.length/2);
        return item;
    }

    public Item sample() {
        // return a random item (but do not remove it)
        if (isEmpty()) throw new NoSuchElementException("queue underflow");
        int r = StdRandom.uniform(n);
        Item item = a[r];
        return item;
    }

    public Iterator<Item> iterator(){
        // return an independent iterator over items in random order
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item>{

        private int i;
        public ArrayIterator(){
            i = n;
        }
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int r = StdRandom.uniform(i);
            Item item = a[r];
            a[r] = a[i-1];
            i--;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();

        }

    }
    public static void main(String[] args) {
        RandomizedQueue<String> queue= new RandomizedQueue<>();
        /*while (!StdIn.isEmpty()){
            String item=StdIn.readString();
            if (!item.equals("-"))  queue.enqueue(item);
            else if (!queue.isEmpty()) StdOut.print(queue.dequeue()+" ");
        }*/
        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");
        queue.enqueue("4");
        queue.enqueue("5");
        queue.enqueue("6");
        queue.enqueue("7");
        queue.enqueue("8");
        queue.enqueue("9");
        queue.enqueue("10");
        queue.enqueue("11");
        queue.enqueue("12");
        for (String s1 : queue) {
            for (String s2 : queue) {
                System.out.println("s1: " + s1 + " s2: " + s2);
            }
        }
        //for (String s : queue) {
        //    System.out.println(s);
        //}

    } // unit testing (optional)
}






