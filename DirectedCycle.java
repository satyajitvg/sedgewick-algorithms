/** Find cycle in directed graph
 * DFS on graph -> before dfs starts, put v on stack, before exit remove from stack
 * If one of the adjacent vertices being explored is on stack, cycle detected
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

    public DirectedCycle(EdgeWeightedDigraph G) {
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

    private void dfs(EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        onStack[v] = true;
        for (DirectedEdge e: G.adj(v)) {
            int w = e.to();
            if (cycle != null) return; // short circuit if cycle found
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
