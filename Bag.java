import java.util.Iterator;
import java.util.NoSuchElementException;

/** Bag of items, supports insertion and iteration in a random order
 * @author Satyajit
 */
public class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int n;

    // helper inner class for linked list
    private static class Node<Item> {
        private Item item;
        private Node next;
    }

    public Bag() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void add(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        n++;
    }


    public Iterator<Item> iterator() {
        return new ListIterator(first);

    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Bag<String> bag = new Bag<String>();
        while (!StdIn.isEmpty()) {
            bag.add(StdIn.readString());
        }
        StdOut.println("Size of bag: " + bag.size());
        for (String s: bag) {
            StdOut.println(s);
        }
    }

}
