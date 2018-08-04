
/**
 * @author Satyajit
 * Bottom Up Merge Sort
 * no recursion
 */
public class MergeSortBU {

    // merge a[lo..mid] and a[mid+1..hi]
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k<=hi; k++) {
            aux[k] = a[k];
        }
        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <=hi; k++) {
            if (i > mid) a[k] = aux[j++]; // left sub arr exhausted
            else if (j > hi) a[k] = aux[i++]; //right sub arr exhausted
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }


    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }


    public static void sort(Comparable[] a) {
        int n = a.length;
        Comparable[] aux = new Comparable[n];
        for(int sz=1; sz < n; sz=2*sz) { //sz = size of sub arr, doubles each time = lg N
            for (int lo = 0; lo < n-sz; lo=lo+2*sz) {
                int hi = Math.min(lo+(2*sz)-1, n-1);
                int mid = lo+sz-1;
                merge(a, aux, lo, mid, hi);
            }
        }
    }
    public static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        MergeSortBU.sort(a);
        MergeSortBU.show(a);
    }
}
