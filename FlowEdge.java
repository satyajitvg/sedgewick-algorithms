/**
 * @author Satyajit
 */
public class FlowEdge {
    private static final double FLOATING_POINT_EPSILON = 1E-10;
    private final int v;
    private final int w;
    private final double capacity;
    private double flow;

    public FlowEdge(int v, int w, double capacity) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = 0.0;
    }

    public int from() { return v;}

    public int to() { return w;}

    public double capacity() { return capacity;}

    public double flow() { return flow;}

    public int other(int vertex) {
        if (vertex == v) return w;
        else return v;
    }

    public double residualCapacityTo(int vertex) {
        if (vertex == v) return flow; //backward edge
        else return capacity-flow; // forward edge
    }

    public void addResidualFlowTo(int vertex, double delta) {
        if (vertex == v) flow -= delta; //backward edge
        else flow += delta; //forward edge

        //math roundoff
        if (Math.abs(flow) <= FLOATING_POINT_EPSILON)
            flow = 0;
        if (Math.abs(flow-capacity) <= FLOATING_POINT_EPSILON)
            flow = capacity;
    }

    public String toString() {
        return v + "->" + w + " " + flow + "/" + capacity;
    }

    public static void main(String[] args) {
        FlowEdge e = new FlowEdge(0, 1, 1.2);
        StdOut.println(e);
    }
}
