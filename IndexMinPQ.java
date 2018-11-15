import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Satyajit
 */

public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
    private Key[] keys; // keys[i] = key at index i, key signifies priority
    private int n;
    private int maxN;
    private int[] pq; //  1-indexed heap, pq[n] = position on heap -> index
    private int[] qp; // qp[i] = reverse of pq , index->position on heap

    public IndexMinPQ(int maxN) {
        this.maxN = maxN;
        n = 0;
        keys = (Key []) new Comparable[maxN + 1];
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }

    public boolean isEmpty() { return n == 0;}

    public boolean contains(int i) { return qp[i] != -1;}

    public int size() { return n;}

    /**
     * Associate key with index i
     * @param i index
     * @param key the key to associate with index
     */
    public void insert(int i, Key key) {
        n++;
        pq[n] = i;
        qp[i] = n;
        keys[i] = key;
        swim(n);
    }

    public int minIndex() { return pq[1];}

    public Key minKey() { return keys[pq[1]];}

    public int delMin() {
        int min = pq[1];
        exch(1, n--);
        sink(1);
        qp[min] = -1;
        keys[min] = null;
        pq[n+1] = -1;
        return min;
    }

    private void delete(int i) {
        int index = qp[i];
        exch(index, n--);
        swim(index);
        sink(index);
        keys[i] = null;
        qp[i] = -1;
    }

    public Key keyOf(int i) { return keys[i];}

    public void changeKey(int i, Key key) {
        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }

    public void decreaseKey(int i, Key key) {
        if (keys[i].compareTo(key) <= 0) throw new IllegalArgumentException("new key is not " +
                "lesser than old key");
        keys[i] = key;
        swim(qp[i]);
    }

    public void increaseKey(int i, Key key) {
        if (keys[i].compareTo(key) > 0) throw new IllegalArgumentException("old key bigger than " +
                "new key");
        keys[i] = key;
        sink(qp[i]);
    }

    // somehow child got smaller than parent
    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k/2, k);
            k = k/2;
        }
    }

    // somehow parent got larger than child
    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            // find out smaller child
            if (j < n && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }


    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    public Iterator<Integer> iterator() { return new HeapIterator();}

    private class HeapIterator implements Iterator<Integer> {
        // create a new pq
        private IndexMinPQ<Key> copy;

        public HeapIterator() {
            copy = new IndexMinPQ<Key>(pq.length -1);
            for (int i = 1; i <=n ; i++)
                copy.insert(pq[i], keys[pq[i]]);
        }

        public boolean hasNext() { return !copy.isEmpty();}

        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }

    public static void main(String[] args) {
        // insert a bunch of strings
        String[] strings = { "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst" };

        IndexMinPQ<String> pq = new IndexMinPQ<String>(strings.length);
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        // delete and print each key
        while (!pq.isEmpty()) {
            int i = pq.delMin();
            StdOut.println(i + " " + strings[i]);
        }
        StdOut.println();

        // reinsert the same strings
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        // print each key using the iterator
        for (int i : pq) {
            StdOut.println(i + " " + strings[i]);
        }
        while (!pq.isEmpty()) {
            pq.delMin();
        }

    }

}
