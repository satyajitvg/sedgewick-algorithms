/**
 * @author Satyajit
 */
public class DepthFirstOrder {
    private boolean[] marked;
    private int[] pre; // pre[v] = preorder number of v
    private int[] post; //post[v] = postorder number of v
    private LinkedQueue<Integer> preorder;
    private LinkedQueue<Integer> postorder;
    private int preCounter;
    private int postCounter;

    public DepthFirstOrder(Digraph G) {
        marked = new boolean[G.V()];
        pre = new int[G.V()];
        post = new int[G.V()];
        preorder = new LinkedQueue<>();
        postorder = new LinkedQueue<>();

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                dfs(G, v);
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        preorder.enqueue(v); //before dfs
        pre[v] = preCounter++;
        for (int w: G.adj(v)) {
            if (!marked[w])
                dfs(G, w);
        }
        postorder.enqueue(v); //after dfs
        post[v] = postCounter++;
    }

    public int pre(int v) {return pre[v];}

    public int post(int v) {return post[v];}

    public Iterable<Integer> pre() { return preorder; }

    public Iterable<Integer> post() { return postorder; }

    public Iterable<Integer> reversePost() {
        LinkedStack<Integer> reverse = new LinkedStack<>();
        for (int v: postorder)
            reverse.push(v);
        return reverse;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        DepthFirstOrder dfs = new DepthFirstOrder(G);
        StdOut.println("   v  pre post");
        StdOut.println("--------------");
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%4d %4d %4d\n", v, dfs.pre(v), dfs.post(v));
        }

        StdOut.print("Preorder:  ");
        for (int v : dfs.pre()) {
            StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.print("Postorder: ");
        for (int v : dfs.post()) {
            StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.print("Reverse postorder: ");
        for (int v : dfs.reversePost()) {
            StdOut.print(v + " ");
        }
        StdOut.println();


    }
}
