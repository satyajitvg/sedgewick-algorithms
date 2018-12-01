/**
 * @author Satyajit
 */
public class TrieST<Value> {
    private static final int R = 256 ; // radix
    private Node root;
    private int n;

    private class Node {
        private Object val;
        private Node[] next = (Node[]) new Object[R];
    }

    public TrieST() {}

    public Value get(String key) {
        if (key==null) throw new IllegalArgumentException("null key");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (key.length() == d)
            return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    public boolean contains(String key) {
        if (key==null) throw new IllegalArgumentException("null key");
        return get(key) != null;
    }

    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException("null key");
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (key.length() == d) {
            if (x.val == null) n++;
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1);
        return x;
    }

    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("null key");
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.val != null) n--;
            x.val = null;
        }
        else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }

        // remove subtrie rooted at x if it is completely empty
        if (x.val != null) return x;
        for (int c = 0; c < R; c++)
            if (x.next[c] != null)
                return x;
            return null;
    }

}
