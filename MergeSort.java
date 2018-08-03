/**
 * @author Satyajit
 */
public class MergeSort {

    // merge a[lo .. mid] with a[mid+1 ..hi using aux array
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k<=hi; k++) {
            aux[k] = a[k];
        }
        int i = lo;
        int j = mid + 1;

        for (int k = lo; k<=hi; k++) {
            if (i > mid) a[k] = aux[j++]; // left sub arr exhausted
            else if (j > hi) a[k] = aux[i++]; // right sub arr exhausted
            else if (less(aux[j], aux[i])) a[k] = aux[j++]; // elem in right sub arr smaller
            else a[k] = aux[i++]; // note: preserve stability, move from left sub arr if equal
        }
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (lo >= hi) return;
        int mid = (lo + hi)/2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
        assert  isSorted(a, 0, a.length - 1);
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static  boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo; i<hi; i++) {
            if (!(less(a[i], a[i+1]))) return false;
        }
        return true;
    }

    public static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        MergeSort.sort(a);
        MergeSort.show(a);
    }
}
