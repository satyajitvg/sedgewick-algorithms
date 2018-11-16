/** Eager prim mst
 * @author Satyajit
 */
public class PrimMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;
    private double[] distTo;
    private Edge[] edgeTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;

    public PrimMST(EdgeWeightedGraph G) {
        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                prim(G, v);
        }
    }

    private void prim(EdgeWeightedGraph G, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        scan(G, s);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(G, v);
        }
    }

    // scan edges adjacent to vertex
    // if other endpoint vertex has not been scanned before
    // add vertex to pq if shorter edge found
    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e: G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;
            if (e.weight() < distTo[w]) {
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else pq.insert(w, distTo[w]);
            }
        }
    }

    public Iterable<Edge> edges() {
        LinkedQueue<Edge> mst = new LinkedQueue<Edge>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null)
                mst.enqueue(e);
        }
        return mst;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e: edges()) weight += e.weight();
        return weight;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        PrimMST mst = new PrimMST(G);
        for (Edge e: mst.edges())
            StdOut.println(e);
        StdOut.printf("mst weight: %.5f\n", mst.weight());
    }



}
