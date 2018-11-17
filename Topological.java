/** Topological sort of a graph if it exists
 *  All edges point up
 *  reverse postorder of graph
 * @author Satyajit
 */
public class Topological {
    private Iterable<Integer> order;
    private int[] rank;

    public Topological(Digraph G) {
        DirectedCycle finder = new DirectedCycle(G);
        if (!finder.hasCycle()) {
            DepthFirstOrder dfsOrder = new DepthFirstOrder(G);
            order = dfsOrder.reversePost();
            rank = new int[G.V()];
            int i = 0;
            for (int v: order)
                rank[v] = i++;
        }
    }

    public Topological(EdgeWeightedDigraph G) {
        DirectedCycle finder = new DirectedCycle(G);
        if (!finder.hasCycle()) {
            DepthFirstOrder dfsOrder = new DepthFirstOrder(G);
            order = dfsOrder.reversePost();
            rank = new int[G.V()];
            int i = 0;
            for (int v: order)
                rank[v] = i++;
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean hasOrder() { return order != null;}

    public int rank(int v) {
        if (hasOrder())
            return rank[v];
        else
            return -1;
    }

    public static void main(String args[]) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        Topological topological = new Topological(G);
        if (topological.hasOrder()) {
            for (int v: topological.order()) {
                StdOut.println(v);
            }
        }
        else StdOut.println("no top order");

    }
}
