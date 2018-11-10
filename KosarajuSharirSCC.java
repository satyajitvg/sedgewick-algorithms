/** Strongly connected components in directed graph
 * @author Satyajit
 */
public class KosarajuSharirSCC {
    private boolean[] marked;
    private int[] id;
    private int count;


    public KosarajuSharirSCC(Digraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        DepthFirstOrder dfs = new DepthFirstOrder(G.reverse());
        for (int v: dfs.reversePost()) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }

        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w: G.adj(v)) {
            if (!marked[w])
                dfs(G, w);
        }
    }

    public int count() { return count;}

    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        KosarajuSharirSCC scc = new KosarajuSharirSCC(G);
        for (int v = 0; v < G.V();  v++) {
            StdOut.println("vertex: " + v + " component id: " + scc.id(v));
        }
    }
}
