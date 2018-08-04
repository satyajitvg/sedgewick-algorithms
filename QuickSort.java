

/**
 * @author Satyajit
 */
public class QuickSort {
    // a[lo..j-1] <= a[j] <= a[j+1..hi]
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi+1;
        while (true) {
            while (less(a[++i], a[lo])) {
                if (i==hi) break;
            }

            while (less(a[lo], a[--j])) {
                if (j==lo) break;
            }

            if (i >=j) break; // check if pointers cross
            exch(a, i, j);
        }
        exch(a, j, lo); // put partitioning elem in its place
        return j;
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }

    public static void sort(Comparable[] a) {
        edu.princeton.cs.algs4.StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        QuickSort.sort(a);
        QuickSort.show(a);
    }
}
