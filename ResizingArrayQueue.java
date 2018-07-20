import java.util.Iterator;
import java.util.NoSuchElementException;

/** Wrap around queue
 * @author Satyajit
 */
public class ResizingArrayQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int first;
    private int last;
    private int n;

    public ResizingArrayQueue() {
        q = (Item[]) new Object[2];
        first = 0;
        last = 0;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first + i)%q.length];
        }
        q = temp;
        first = 0;
        last = n;
    }

    public void enqueue(Item item) {
        if (n == q.length) resize(2*q.length);
        q[last++] = item;
        if (last == q.length) last = 0; // wrap around
        n++ ;

    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue empty");
        Item item = q[first];
        q[first] = null; //avoid loitering
        n--;
        first++;
        if (first == q.length) first = 0; //wrap around
        if (n > 0 && n == q.length/4) resize(q.length/2);
        return item;
    }

    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue empty");
        return q[first];
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext() { return i < n;}

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[(first + i)%q.length];
            i++;
            return item;
        }
    }

    public static void main(String[] args) {
        ResizingArrayQueue queue = new ResizingArrayQueue();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty()) {
                StdOut.print(queue.dequeue() + " ");
            }
        }
    }


}
