import java.util.Comparator;
import java.util.NoSuchElementException;

/** Min PQ
 * @author Satyajit
 */
public class MinPQ<Key> {
    private int n;
    private Key[] pq; // 1-indexed array
    Comparator<Key> comparator;

    // init with some capacity
    public MinPQ(int capacity) {
        pq = (Key []) new Object[capacity + 1];
        n = 0;
    }

    // init empty PQ
    public MinPQ() {
        this(1);
    }

    // init with some capacity and custom comparator
    public MinPQ(int capacity, Comparator<Key> comparator) {
        pq = (Key []) new Object[capacity + 1];
        this.comparator = comparator;
        n = 0;
    }

    public MinPQ(Key[] keys) {
        n = keys.length;
        pq = (Key []) new Object[n+1];
        for (int i = 0; i < n; i++)
            pq[i+1] = keys[i];
        for (int k = n/2; k >= 1; k--)
            sink(k);
    }

    // init empty PQ with custom comparator
    public MinPQ(Comparator<Key> comparator) {
        this(1, comparator);
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public Key delMin() {
        if (isEmpty()) throw new NoSuchElementException("empty PQ");
        Key min = pq[1];
        exch(1, n--);
        sink(1);
        pq[n+1] = null; //avoid loitering
        return min;
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("empty PQ");
        return pq[1];
    }

    public void insert(Key key) {
        if (pq.length -1  == n) resize(2*pq.length);
        pq[++n] = key;
        swim(n);
    }

    private void resize(int capacity) {
        Key[] temp = (Key []) new Object[capacity];
        for (int i = 1; i <= n; i++)
            temp[i] = pq[i];
        pq = temp;
    }


    // somehow child got smaller than parent, so fix min heap property, restore invariant
    // parent should be smaller than child
    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k/2, k);
            k = k/2;
        }
    }

    // somehow parent got larger than child, so fix min heap
    private void sink(int k) {
        while (2*k < n) {
            int j = 2*k;
            // find out smaller child
            if (j < n && greater(j, j+1)) j++;
            // parent smaller than smallest child, nothing to do
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    // compare
    private boolean greater(int i, int j) {
        if (comparator == null)
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
        else
            return comparator.compare(pq[i], pq[j]) > 0;
    }
    // exch i, j
    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    public static void main(String[] args) {
        MinPQ<String> pq = new MinPQ<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) pq.insert(item);
            else if (!pq.isEmpty()) StdOut.println(pq.delMin() + " ");
        }
        StdOut.println("(" + pq.size() + "left on pq)");
    }


}
