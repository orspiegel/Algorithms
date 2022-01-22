import jdk.jshell.spi.ExecutionControl;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Graph {

    private static int[] topSort;
    //int[] topSort;
    int topSortLocation;

    // todo - connected components

    List<Integer>[] adjacentList; // can be boolean adjacent matrix also. Size of AdjacentList is V
    int V;
    public Graph(int v) {
        adjacentList = new LinkedList[v]; // array of V linked lists
        this.V = v;
        topSort = new int[v];
        topSortLocation = V;
        for (int i = 0; i < v; i++) {
            adjacentList[i] = new LinkedList<Integer>(); // adjacentList[i] = The neighbors of node i kept in adjacentList[i].
        }
    }
    void addNeighbor(int v, int u) {
        this.adjacentList[v].add(u);
    }

    public void DFS(int source) {
        boolean visited[] = new boolean[this.V];
        // for unconnected graphs:
        for (int i = 0; i < adjacentList.length; i++) { // for every node
            if (!visited[i])
                dfsRec(source, visited);
        }

    }


    public void dfsRec(int source, boolean visited[]) {
        visited[source] = true;
        System.out.println(source); // visit(source)

         for (int j = 0; j < adjacentList[source].size(); j++) { // for all that node neighbors
                int a = adjacentList[source].get(j);
                if (!visited[a])
                    dfsRec(a, visited);
            }
        }
    /**
     * Only if DAG
     */
    public void topSort(int v) throws ExecutionControl.NotImplementedException {

        // throw new ExecutionControl.NotImplementedException("Not implemented");

        boolean visited[] = new boolean[this.V];
        Random random = new Random();
        // starting from random source
        //int source = random.nextInt(v-1 - 0 + 1) + 0;
        int source = 0;
        int a;

        // for unconnected graphs:
        for (int i = 0; i < adjacentList.length; i++) { // for every node
            if (!visited[i])
                topSortRec(source, visited);
        }

    }
    public void topSortRec(int source, boolean visited[]) throws  ExecutionControl.NotImplementedException {
        //throw new ExecutionControl.NotImplementedException("Not implemented");
        visited[source] = true;
        int a = 0;
       // System.out.println(source); // visit(source)
        for (int j = 0; j < adjacentList[source].size(); j++) { // for all that node neighbors
            a = adjacentList[source].get(j);
            if (!visited[a]) {
                topSortRec(a, visited);
            }
        }
        topSort[topSortLocation - 1] = source;
        topSortLocation--;
        //return this.topSort;
    }
    static public void getTopSort() {
        for (int n : topSort) {
            System.out.print(n);
        }

    }

    public void BFS(int source) {
        boolean[] visited = new boolean[this.V];
        LinkedList<Integer> Q = new LinkedList<Integer>();
        Q.add(source);
        visited[source] = true;
        int curr;
        while (Q.size()!=0) {
            source = Q.poll();
            System.out.println(source);
            for (int i = 0; i < adjacentList[source].size(); i++) {
                curr = adjacentList[source].get(i);
                if (!visited[curr]) {
                    visited[curr] = true;
                    // add that node neighbors only if they haven't been visited yet.
                    Q.add(curr);
                }
            }
        }
    }


    public static void main(String[] args) throws ExecutionControl.NotImplementedException {

        /**
              0
            /   \
           1     2
          / \
         3---4

         neighbors of 0 = 1,2
         neighbors of 1 = 3,4
         neighbors of 2 = []
         neighbors of 3 = 4
         neighbors of 4 = []

         **/
        Graph G = new Graph(5);
        G.addNeighbor(0,1);
        G.addNeighbor(0,2);
        G.addNeighbor(1,3);
        G.addNeighbor(1,4);
        G.addNeighbor(3,4);

//        G.DFS(0);
  //      G.BFS(0);
        G.topSort(5);
        getTopSort();




    }

}
