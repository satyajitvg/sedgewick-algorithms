

/** Shortest paths for directed weighted graphs
 * Can contain cycles
 * with NON negative edges weights
 * O(E LogV)
 * @author Satyajit
 */
public class DijkstraSP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e: G.adj(v))
                relax(e);
        }
    }

    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[v] + e.weight() < distTo[w]) {
            edgeTo[w] = e;
            distTo[w] = distTo[v] + e.weight();
            if (pq.contains(w))
                pq.decreaseKey(w, distTo[w]);
            else
                pq.insert(w, distTo[w]);
        }
    }

    public double distTo(int v) { return distTo[v];}

    public boolean hasPathTo(int v) { return distTo[v] < Double.POSITIVE_INFINITY;}

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        LinkedStack<DirectedEdge> path = new LinkedStack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt(args[1]);
        DijkstraSP sp = new DijkstraSP(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (sp.hasPathTo(v)) {
                StdOut.printf("%d to %d (%.2f) ", s, v, sp.distTo(v));
                for (DirectedEdge e: sp.pathTo(v)) {
                    StdOut.print(e + " ");
                }
                StdOut.println("\n");

            }
            else {
                StdOut.printf("%d to %d No path\n", s, v);
            }
        }

    }

}
