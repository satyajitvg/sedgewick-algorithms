/**
 * @author Satyajit
 */
public class DirectedCycle {
    private int[] edgeTo;
    private boolean[] marked;
    private boolean[] onStack;
    private LinkedStack<Integer> cycle;

    public DirectedCycle(Digraph G) {
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        onStack = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v] && cycle == null)
                dfs(G, v);
        }
    }

    private void dfs(Digraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w: G.adj(v)) {

            // short circuit if cycle found
            if (cycle != null) return;

            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }

            else if (onStack[w]) {
                cycle = new LinkedStack<Integer>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }
        }

        onStack[v] = false;
    }

    public boolean hasCycle() {return cycle != null;}

    public Iterable<Integer> cycle() {return cycle;}

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        DirectedCycle finder = new DirectedCycle(G);
        if (finder.hasCycle()) {
            StdOut.println("Cycle found");
            for (int v: finder.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("No cycle");
        }
    }
}
