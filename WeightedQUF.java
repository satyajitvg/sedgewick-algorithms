/**
 * @author Satyajit
 */
/*
Avoid long skinny trees
When doing a union, always make the smaller tree a child of the larger tree
Proposition: depth of any node in tree can be at most lg N
Pf: depth of any node x increases by 1 when doing a union
    Size of tree containing x at least doubles
    can double at most lgN times
*/

public class WeightedQUF {
    private int[] parent; // keep track of parent of i
    private int[] sz; // keep track of size of subtree at i

    public WeightedQUF(int N) {
        parent = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i; // initially every node is its own parent
            sz[i] = 1;
        }
    }

    private int root(int i) {
        while (parent[i] != i) {
            // neat trick: path halving
            // make every other node point to its grandparent
            parent[i] = parent[parent[i]];
            i = parent[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (sz[rootQ] > sz[rootP]) {
            // subtree at rootQ is larger than subtree at rootP
            // make subtree at p a child of rootQ
            parent[rootP] = rootQ;
            sz[rootQ] += sz[rootP];
        }
        else {
            parent[rootQ] = rootP;
            sz[rootP] += sz[rootQ];
        }
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        WeightedQUF uf = new WeightedQUF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p,q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
    }
}
