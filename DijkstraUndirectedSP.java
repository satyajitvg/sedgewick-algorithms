/**
 * @author Satyajit
 */
public class DijkstraUndirectedSP {
    private double[] distTo;
    private Edge[] edgeTo;
    private IndexMinPQ<Double> pq;

    public DijkstraUndirectedSP(EdgeWeightedGraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];
        pq = new IndexMinPQ<>(G.V());

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);

        while(!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e: G.adj(v))
                relax(e, v);
        }
    }

    private void relax(Edge e, int v) {
        int w = e.other(v);
        if (distTo[v] + e.weight() < distTo[w]) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w))
                pq.decreaseKey(w, distTo[w]);
            else
                pq.insert(w, distTo[w]);
        }
    }

    public double distTo(int v) { return distTo[v];}

    public boolean hasPathTo(int v) { return distTo[v] < Double.POSITIVE_INFINITY;}

    public Iterable<Edge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        LinkedStack<Edge> path = new LinkedStack<Edge>();
        int x = v;
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
            path.push(e);
            x = e.other(x);
        }
        return path;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        int s = Integer.parseInt(args[1]);
        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (sp.hasPathTo(v)) {
                StdOut.printf("%d to %d (%.2f) ", s, v, sp.distTo(v));
                for (Edge e: sp.pathTo(v)) {
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
