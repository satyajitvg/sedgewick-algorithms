import java.util.NoSuchElementException;

/** Undirected Graph of vertices
 * Adjacency list representation
 * @author Satyajit
 */
public class Graph {

    private final int V;
    private int E;
    private Bag<Integer>[] adj; // array of Bags

    /**
     * Init an empty graph with V vertices and 0 edges
     * @param V
     */
    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("V must be > 0");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    public Graph(In in) {
        try {
            this.V = in.readInt();
            int E = in.readInt();
            if (V < 0 || E < 0) throw new IllegalArgumentException("must be non negative");
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            }
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                addEdge(v, w);
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("bad args to constructor", e);
        }
    }

    // init graph that is a deepcopy of G
    public Graph(Graph G) {
        this(G.V()); //constructor overloading, call G(int V) constructor
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            for ( int w: G.adj[v]) {
                adj[v].add(w);
            }
        }

    }

    public int V() {return V;}
    public int E() {return E;}

    private void validateVertex(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException("bad vertex: " + v);
    }

    private void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    /**
     * Vertices adjacent to v
     * @param v
     * @return
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges\n");
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w: adj[v]) {
                s.append(w + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        StdOut.println(G);
        Graph G2 = new Graph(G);
        StdOut.println(G2);
    }


}
