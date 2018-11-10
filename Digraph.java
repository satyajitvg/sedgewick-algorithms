/**
 * @author Satyajit
 */
public class Digraph {
    private int V;
    private int E;
    private Bag<Integer>[] adj;
    private int[] indegree;


    // init an empty digraph with V vertices
    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        indegree = new int[V];
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    public Digraph(In in) {
        this.V = in.readInt();
        indegree = new int[V];
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
        int E = in.readInt();
        for (int e = 0; e < E; e++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    private void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
        indegree[w]++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int indegree(int v) {
        return indegree[v];
    }

    public int outdegree(int v) {
        return adj[v].size();
    }

    public int V() {return V;}

    public int E() {return E;}

    public Digraph reverse() {
        Digraph reverse = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w: adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + "vertices, " + E + " edges\n");
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w: adj(v)) {
                s.append(String.format("%d ", w));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        StdOut.println(G);
    }
}
