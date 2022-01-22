public class WeightedGraph {
    private class Edge {
        // v1 -> v2 d[v1,v2] = weight
        // v1 = source, v2 = destination.
        int v1, v2, weight;
        Edge() {
            v1 = v2 = weight = 0;
        }
    }

    int V, E;
    Edge edges[];

    // Creates a graph with V vertices and E edges
    public WeightedGraph(int v, int e) {
        V = v;
        E = e;
        // edges list
        edges = new Edge[e];
        for (int i = 0; i < e; ++i)
            edges[i] = new Edge();
    }
/***********************************************************************************************/
    /**
     * Bellman ford algorithm, relax based.
     * @param G = the graph
     * @param source = the single source
     */
    static void bellmanFord(WeightedGraph G, int source) {

        int E = G.E;
        int V = G.V;

        // d[i] = distance from source to vertex i
        int[] d = new int[V];

        // initialization - INF distances
        for (int i = 0; i < G.V; i++) {
            d[i] = Integer.MAX_VALUE; // inf
        }
        //distance to src = 0;
        d[source] = 0;

        // the algorithm
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < E; j++) {
                if (d[G.edges[j].v1] != Integer.MAX_VALUE) {
                    d[G.edges[j].v2] = relax(G, d, j);
                }
            }
        }

        // if the Graph contains negative circles - do not print!
        if (!isValidGraph(G, d)) return;
         printSSPS(d);

    }

    /**
     * Improve distances.
     * if the current distance to vertex v2 is longer than: the current distance to vertex v1 + the edge (v1, v2):
     * the new shortest distance to vertex 2 will decrease to v1 + edge (v1,v2).
     * @param G = the graph
     * @param d = smallest distance array
     * @param edge = current edge to relax
     * @return the new relaxed edge weight
     */
    static int relax(WeightedGraph G, int[] d, int edge) {
        int v = G.edges[edge].v1;
        int u = G.edges[edge].v2;
        int weight = G.edges[edge].weight;

        return Math.min(d[u], d[v] + weight);
    }

    /**
     * Print all the shortest paths from source d[i] to all the other vertex.
     * @param d = the shortest distances
     */
    private static void printSSPS(int[] d) {
        int source = d[0];
        for (int i = 0; i < d.length; i++) {
            System.out.println("Shortest path from " + source + " to vertex " +
                    i + " is: " + d[i] + ".");
        }
    }
    /**
     * Valid graph = does not contains negative circles.
     * if after V - 1 iterations the distances keep decreasing - that means the graph contains neg weight cycle.
     *
     * @param G = the graph
     * @param d = distances array
     * @return False if there is negative cycles, o.w - true
     */
    static boolean isValidGraph (WeightedGraph G, int[] d) {
        for (int j = 0; j < G.E; ++j) {
            int u = G.edges[j].v1;
            int v = G.edges[j].v2;
            int weight = G.edges[j].weight;
            if (d[u] != Integer.MAX_VALUE && d[u] + weight < d[v]) {
                System.out.println("the Graph contains negative weight cycle");
                return false;
            }
        }
        return true;
    }
/***********************************************************************************************/



    /**
     * driver main of BELLMAN FORD - from Geeks4Geeks
     * @param args
     */
    public static void main(String[] args) {
        int V = 5; // Number of vertices in graph
        int E = 8; // Number of edges in graph

        WeightedGraph graph = new WeightedGraph(V, E);

        // add edge 0-1 (or A-B in above figure)
        graph.edges[0].v1 = 0;
        graph.edges[0].v2 = 1;
        graph.edges[0].weight = -1;

        // add edge 0-2 (or A-C in above figure)
        graph.edges[1].v1 = 0;
        graph.edges[1].v2 = 2;
        graph.edges[1].weight = 4;

        // add edge 1-2 (or B-C in above figure)
        graph.edges[2].v1 = 1;
        graph.edges[2].v2 = 2;
        graph.edges[2].weight = 3;

        // add edge 1-3 (or B-D in above figure)
        graph.edges[3].v1 = 1;
        graph.edges[3].v2 = 3;
        graph.edges[3].weight = 2;

        // add edge 1-4 (or B-E in above figure)
        graph.edges[4].v1 = 1;
        graph.edges[4].v2 = 4;
        graph.edges[4].weight = 2;

        // add edge 3-2 (or D-C in above figure)
        graph.edges[5].v1 = 3;
        graph.edges[5].v2 = 2;
        graph.edges[5].weight = 5;

        // add edge 3-1 (or D-B in above figure)
        graph.edges[6].v1 = 3;
        graph.edges[6].v2 = 1;
        graph.edges[6].weight = 1;

        // add edge 4-3 (or E-D in above figure)
        graph.edges[7].v1 = 4;
        graph.edges[7].v2 = 3;
        graph.edges[7].weight = -3;

        graph.bellmanFord(graph, 0);

    }
}
