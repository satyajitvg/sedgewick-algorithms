/**
 *  Heap sort using binary heap
 * @author Satyajit
 */

public class Heap {

    public static void sort(Comparable[] pq) {
        int n = pq.length;
        // make heap: ensure every parent larger than its children
        for (int i = n/2; i >= 1; i--) {
            sink(pq, i, n);
        }
        while (n > 1) {
            exch(pq, 1, n--);
            sink(pq, 1, n);
        }
    }
    private static void sink(Comparable[] pq, int k, int n) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(pq, j,j+1)) j++; //find larger child
            if (less(pq, j, k)) break; // nothing to do if largest child is smaller than parent
            exch(pq, k, j);
            k = j;
        }
    }


    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i-1].compareTo(pq[j-1]) < 0;
    }

    private static void exch(Object[] pq, int i, int j) {
        Object temp = pq[i-1];
        pq[i-1] = pq[j-1];
        pq[j-1] = temp;
    }

    public static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Heap.sort(a);
        Heap.show(a);
    }
}
