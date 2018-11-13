/** Grow mst tree by adding the next best edge.
 * Next best edge is the edge with min weight from all
 * the edges with one vertex in the mst tree
 *
 * @author Satyajit
 */
public class LazyPrimMST {
    private boolean[] marked;
    private LinkedQueue<Edge> mst;
    private double weight;
    private MinPQ<Edge> pq;

    public LazyPrimMST(EdgeWeightedGraph G) {
        marked = new boolean[G.V()];
        mst = new LinkedQueue<Edge>();
        pq = new MinPQ<Edge>();
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) prim(G, v);
    }

    // run prim's algorithm
    private void prim(EdgeWeightedGraph G, int s) {
        scan(G, s);
        while (!pq.isEmpty()) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            // if both endpoints already scanned then skip this edge
            if (marked[v] && marked[w]) continue;
            mst.enqueue(e);
            weight += e.weight();
            if (!marked[v]) scan(G, v);
            if (!marked[w]) scan(G, w);
        }
    }

    // add all edges incident on v onto pq if the other endpoint has not yet been scanned
    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e: G.adj(v)) {
            int w = e.other(v);
            if (!marked[w]) pq.insert(e);
        }

    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        LazyPrimMST mst = new LazyPrimMST(G);
        for (Edge e: mst.edges())
            StdOut.println(e);
        StdOut.print("weight: " + mst.weight() + "\n");
    }

}
