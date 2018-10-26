import java.util.Comparator;

/**
 * Max Priority Queue
 * @author Satyajit
 */

public class MaxPQ<Key> {
    private Key[] pq; // 1-indexed array
    private int n;
    private Comparator<Key> comparator;

    //initialize with some capacity
    public MaxPQ(int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    // init an empty PQ
    public MaxPQ() {
        this(1);
    }

    // init with some capacity and a custom comparator
    public MaxPQ(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    // init empty PQ with comparator
    public MaxPQ(Comparator<Key> comparator) {
        this(1, comparator);
    }

    // init with a set of keys
    public MaxPQ(Key[] keys) {
        n = keys.length;
        pq = (Key[]) new Object[n + 1];
        for (int i = 0; i < n; i++) {
            pq[i+1] = keys[i];
        }
        for (int k = n/2; k >= 1; k--) {
            sink(k);
        }
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public void insert(Key x) {
        // resize if necessary
        if (n == pq.length -1) resize(2*pq.length);
        pq[++n] = x;
        swim(n);
    }

    public Key delMax() {
        Key max = pq[1];
        exch(1, n--);
        sink(1);
        pq[n+1] = null; //avoid loitering
        if ((n > 0) && (n == (pq.length-1)/4)) resize(pq.length/2);
        return max;
    }


    // resize array
    private void resize(int capacity) {
        assert capacity > n;
        Key[] temp = (Key []) new Object[capacity];
        for (int i = 1; i <=n ; i++) temp[i] = pq[i];
        pq = temp;
    }

    // fix if child is larger than parent
    // k here -> index of child
    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch(k/2, k);
            k = k/2;
        }

    }

    // fix if parent got smaller than child
    // k here -> index of parent
    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            //find out larger child
            if (j < n && less(j, j+1)) j++;
            if (less(j, k)) break; //nothing to do if parent is larger
            exch(k, j);
            k = j;
        }

    }

    private boolean less(int i, int j) {
        if (comparator == null) {
            // this is super ugly ...
            // sadly I dont know enough java to know if there is a better way
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
        }
        return comparator.compare(pq[i], pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    public static void main(String[] args) {
        MaxPQ<String> pq = new MaxPQ<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                pq.insert(item);
            }
            else if (!pq.isEmpty()) StdOut.print(pq.delMax() + " ");
        }
        StdOut.println("(" + pq.size() + "left on pq)");
    }

}