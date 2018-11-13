/** MST using Kruskal's algorithm
 * Add next best (min wt)  edge as long as its does not create a cycle
 * Cycle is detected by keeping vertices in a UnionFind data structure.
 * If both vertices in UF then that edge will create a cycle
 * @author Satyajit
 */
public class KruskalMST {
    private double weight;
    private LinkedQueue<Edge> mst;

    public KruskalMST(EdgeWeightedGraph G) {
        MinPQ<Edge> pq = new MinPQ<Edge>();
        for (Edge e: G.edges()) {
            pq.insert(e);
        }
        mst = new LinkedQueue<Edge>();

        WeightedQUF uf = new WeightedQUF(G.V());
        while (!pq.isEmpty() || mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) {
                mst.enqueue(e);
                uf.union(v, w);
                weight += e.weight();
            }
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {return weight;}

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        KruskalMST mst = new KruskalMST(G);
        for (Edge e: mst.edges())
            StdOut.println(e);
        StdOut.print("weight: " + mst.weight() + "\n");
    }
}
