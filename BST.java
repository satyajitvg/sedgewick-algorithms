import java.util.NoSuchElementException;

/**
 * Binary search tree
 * Ordered symbol table implementation
 * @author Satyajit
 */
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    // inner class
    private class Node {
        private Key key;
        private Value val;
        private Node right, left;
        private int size;

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    // init an empty BST
    public BST() { }

    public boolean isEmpty() { return root == null;}

    public int size() { return size(root); }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("null node");
        return get(key) != null;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    // get val
    private Value get(Node x, Key key) {
        if (key == null) throw new IllegalArgumentException("key is null");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);

        if (cmp < 0) return get(x.left, key); // search left subtree
        else if (cmp > 0) return get(x.right, key); // search right subtree
        else return x.val; // yay ! we finds it
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    //insert key,val in BST
    private Node put(Node x, Key key, Value val) {
        if (x == null)
            return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.size = size(x.right) + size(x.left) + 1;
        return x;
    }

    //delete min key from BST
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("empty BST");
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    //delete max key in BST
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("empty BST");
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Delete node from BST using Hibbard deletetion
     * @return
     */
    public void delete(Key key) {
        if (isEmpty()) throw new IllegalArgumentException("delete called on empty tree");
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left =  delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.left == null) return  x.right;
            if (x.right == null) return  x.left;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }


    //smallest key in the BST
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("min() called with empty BST");
        return min(root).key;
    }

    private Node min(Node x) {
        while (x.left != null)
            x = x.left;
        return x;
    }

    // largest key in the BST
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("max() called with empty BST");
        return max(root).key;

    }

    private Node max(Node x) {
        if (x.right == null)
            return x;
        else
            return max(x.right);
    }

    // largest key <= than given key
    public Key floor(Key key) {
        if (isEmpty()) throw new NoSuchElementException("floor() called with empty BST");
        Node x = floor(root, key);
        if (x == null) return null;
        else return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        else if (cmp < 0) return floor(x.left, key);
        else  {
            Node t = floor(x.right, key);
            if (t == null) return x;
            else return t;
        }
    }

    // smallest key >= given key
    public Key cieling(Key key) {
        if (isEmpty()) throw new NoSuchElementException("cieling() called on empty BST");
        Node x = cieling(root, key);
        if (x == null) return null;
        else return x.key;
    }

    private Node cieling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        else if (cmp > 0) return cieling(x.right, key);
        else {
            Node t = cieling(x.left, key);
            if (t == null) return x;
            else return t;
        }
    }

    // k'th smallest key in the BST
    public Key select(int k) {
        if ( k < 0 || k >= size()) {
            throw new IllegalArgumentException("argument to select is invalid: " + k);
        }
        Node x = select(root, k);
        return x.key;

    }

    private Node select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k-t-1);
        else return x;
    }

    // rank of given key
    public int rank(Key key) {
        if (key == null) throw  new IllegalArgumentException("argument to rank is null");
        return rank(root, key);

    }

    private int rank(Node x, Key key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(x.left, key);
        else if (cmp > 0) return 1 + size(x.left) + rank(x.right, key);
        else return size(x.left);
    }


    // inorder traversal of keys
    public Iterable<Key> keys() {
        if (isEmpty()) return new LinkedQueue<Key>();
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("lo is null");
        if (hi == null) throw new IllegalArgumentException("hi is null");
        LinkedQueue<Key> queue = new LinkedQueue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, LinkedQueue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        else return Math.max(height(x.left), height(x.right)) + 1;
    }

    // is valid BST ?
    private boolean isBST() {
        return isBST(root, null, null);

    }

    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        // when checking right subtree min is key of x's parent
        if (min != null && x.key.compareTo(min) <= 0) return false;
        // when checking left subtree, max is key of x's parent
        if (max != null && x.key.compareTo(max) >= 0) return false;
        // check left subtree and right subtree
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);

    }

    public static void main(String[] args) {
        BST<String, Integer> st = new BST<String, Integer>();
        st.put("b", 2);
        st.put("a", 1);
        st.put("c", 3);
        st.put("d", 4);
        st.put("e", 5);
        StdOut.println(st.size());
        StdOut.println(st.max());
        StdOut.println(st.min());
        st.delete("a");
        StdOut.println(st.min());
     }

}
