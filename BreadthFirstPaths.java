import java.util.Queue;
import java.util.Stack;

/** Breadth First Paths
 * @author Satyajit
 */
public class BreadthFirstPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;
    private int[] edgeTo; //
    private int[] distTo; // number of edges, shortest path from s to v


    public BreadthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        bfs(G, s);
    }

    // bfs from source vertex
    private void bfs(Graph G, int s) {

        // init distances
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;

        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();

        // add s to q
        marked[s] = true;
        distTo[s] = 0;
        queue.enqueue(s);

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w: G.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    queue.enqueue(w);
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                }
            }

        }

    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public int distTo(int v) {
        return distTo[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        LinkedStack<Integer> path = new LinkedStack<Integer>();
        int x ;
        for (x = v; distTo[x] != 0; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(x);
        return path;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = Integer.parseInt(args[1]);
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%d to %d: ", s, v);
                for (int x: bfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else StdOut.print("-" + x);
                }
                StdOut.println();
            }
        }

    }
}
