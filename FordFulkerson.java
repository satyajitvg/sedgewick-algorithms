/**
 * @author Satyajit
 */
public class FordFulkerson {
    private static final double FLOATING_POINT_EPSILON = 1E-11;
    private final int V;
    private boolean[] marked; // marked[v] == true if s->v path in residual graph;
    private FlowEdge[] edgeTo; // edgeTo[v] = last edge on shortest residual s->v path
    private double value; // current value of maxflow

    public FordFulkerson(FlowNetwork G, int s, int t) {
        V = G.V();
        while (hasAugmentingPath(G, s, t)) {

            // compute bottleneck capacity
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }

            // augment flow
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle);
            }

            value += bottle;
        }
    }

    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];

        //bfs
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        queue.enqueue(s);
        marked[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (FlowEdge e: G.adj(v)) {
                int w = e.other(v);

                // if residual capacity between v->w
                if (e.residualCapacityTo(w) > 0) {
                    if (!marked[w]) {
                        queue.enqueue(w);
                        edgeTo[w] = e;
                        marked[w] = true;
                    }
                }
            }
        }
        return marked[t];
    }

    public double value() { return value;}

    public boolean inCut(int v) { return marked[v];}

    public static void main(String[] args) {
        In in = new In(args[0]);
        FlowNetwork G = new FlowNetwork(in);
        FordFulkerson maxflow = new FordFulkerson(G, 0, 4);
        StdOut.println("maxflow: " + maxflow.value);
    }

}
