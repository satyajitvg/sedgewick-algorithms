/**
 * @author Satyajit
 */
public class AcyclicSP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;

    public AcyclicSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo= new DirectedEdge[G.V()];

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        Topological topological = new Topological(G);
        if (!topological.hasOrder()) throw  new IllegalArgumentException("Bad stuff");
        for (int v: topological.order()) {
            for (DirectedEdge e: G.adj(v)) {
                relax(e);
            }
        }
    }

    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[v] + e.weight() < distTo[w]) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    public double distTo(int v) { return distTo[v]; }

    public boolean hasPathTo(int v) { return distTo[v] < Double.POSITIVE_INFINITY;}

    public Iterable<DirectedEdge> pathTo(int v) {
        LinkedStack<DirectedEdge> path = new LinkedStack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt(args[1]);
        AcyclicSP sp = new AcyclicSP(G, s);
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
