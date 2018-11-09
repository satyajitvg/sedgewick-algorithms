/**
 * @author Satyajit
 */
public class NonrecursiveDFS {
    private boolean[] marked;
    private int[] edgeTo;
    private int s;

    public NonrecursiveDFS(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    // vertices can be on the stack more than once
    // https://github.com/kevin-wayne/algs4/pull/62
    private void dfs(Graph G, int s) {
        LinkedStack<Integer> stack = new LinkedStack<>();
        stack.push(s);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            marked[v] = true;
            for (int w: G.adj(v)) {
                if (!marked[w]) {
                    stack.push(w);
                    edgeTo[w] = v;
                }
            }
        }
    }

    public boolean marked(int v) { return marked[v];}

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        LinkedStack<Integer> path = new LinkedStack<Integer>();
        int x = v;
        while (x != s) {
            path.push(x);
            x = edgeTo[x];
        }
        path.push(s);
        return path;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = Integer.parseInt(args[1]);
        NonrecursiveDFS dfs = new NonrecursiveDFS(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                StdOut.printf("%d to %d: ", s, v);
                for (int x: dfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else StdOut.print("-" + x);
                }
                StdOut.println();
            }
        }

    }

}
