import java.util.Random;

/**
 * Knuth Shuffle
 * @author Satyajit
 */
public class Shuffle {
    public static void shuffle(Object[] a) {
        Random rand = new Random();
        for (int i = 0; i < a.length; i++) {
            int r = rand.nextInt(i + 1); // between 0 and i
            exch(a, i, r);
        }
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
        Shuffle.shuffle(a);
        Shuffle.show(a);
    }

}
