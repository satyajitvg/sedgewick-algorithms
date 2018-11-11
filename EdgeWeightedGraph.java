/** Edge Weighted undirected graph
 * @author Satyajit
 */
public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private Bag<Edge>[] adj;

    // init an empty graph with V vertices
    public EdgeWeightedGraph(int V) {
        this.V = V;
        E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Edge>();
        }
    }

    public EdgeWeightedGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public int V() {return V;}

    public int E() {return E;}

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public int degree(int v) {
        return adj[v].size();
    }

    // all edges in graph
    // edges should be counted only once
    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e: adj(v)) {
                // add edge only once, arbitrarily pick the one where v > w
                if ( v > e.other(v))
                    list.add(e);
                // self loop. add only once, self loops will be consecutive
                else if (v == e.other(v)) {
                    if (selfLoops%2 == 0)
                        list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + "\n");
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge e : adj[v]) {
                s.append(e + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        StdOut.println(G);
    }
}
