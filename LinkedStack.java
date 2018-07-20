import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author Satyajit
 */
public class LinkedStack<Item> implements Iterable<Item> {

    private int n; // size of stack
    private Node first = null; // top of stack

    // inner class
    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        n ++;
    }

    /**
     * Remove the item most recently added to stacl
     * @return item most recently added
     */
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack empty");
        Item item = first.item;
        first = first.next;
        n--;
        return item;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString();
    }
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw  new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        LinkedStack<String> stack = new LinkedStack<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                stack.push(item);
            }
            else if (!stack.isEmpty()) {
                StdOut.print(stack.pop() + " ");
            }
        }
    }


}
