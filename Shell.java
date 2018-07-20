/**
 * Shell sort
 * Repeatedly h-sort an array
 * h-sort: insertion sort with stride h
 * Reduce the number of exchanges
 * @author Satyajit
 */

public class Shell {
    public static void sort(Comparable[] a) {
        int h = 1;
        int n = a.length;
        while (h < n/3) h = 3*h + 1;
        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a[j], a[j-h]); j-=h) {
                    exch(a, j, j-h);
                }
            }
            h = h/3;
        }

    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Shell.sort(a);
        Shell.show(a);
    }
}
