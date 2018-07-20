import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Satyajit
 */
public class LinkedQueue<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int n = 0;

    // inner class
    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() { return n == 0;}

    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last; // for first item inserted
        else
            oldLast.next = last;
        n++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue empty");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = null; //avoid loitering
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements  Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null;}

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    public static void main(String[] args) {
        LinkedQueue queue = new LinkedQueue();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty()) {
                StdOut.print(queue.dequeue() + " ");
            }
        }
    }
}
