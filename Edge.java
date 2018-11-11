/**
 * Undirected Edge with weight
 * @author Satyajit
 */
public class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int either() { return v;}

    public int other(int vertex) {
        if (vertex == v) return w;
        else return v;
    }

    public double weight() { return weight;}

    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    public String toString() {
        return String.format("%d-%d %.5f", v, w, weight);
    }

    public static void main(String[] args) {
        Edge e = new Edge(1,2, 3.5);
        StdOut.println(e);
    }
}
