package WordNet;

import edu.princeton.cs.algs4.*;

public class SAP {
    private Digraph graph;


    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        graph = G;

        if(!isDAG()) System.out.println("");
    }

    // is the digraph a directed acyclic graph?
    public boolean isDAG(){
        DirectedCycle d = new DirectedCycle(graph);
        return !d.hasCycle();
    }

    // is the digraph a rooted DAG?
    public boolean isRootedDAG(){
        for(int i = 0; i < graph.V(); i++){
            if(graph.indegree(i) == 0) return true;
        }
        return false;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){

        BreadthFirstDirectedPaths searchV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths searchW = new BreadthFirstDirectedPaths(graph, w);

        int lengthOfPath = -1;


        for( int s = 0; s < graph.V(); s++) {
            if (searchV.hasPathTo(s) && searchW.hasPathTo(s)) {
                int distance = searchV.distTo(s) + searchW.distTo(s);
                if (lengthOfPath == -1 || distance < lengthOfPath) {
                    lengthOfPath = distance;

                }
            }

        }
        return lengthOfPath;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){

        BreadthFirstDirectedPaths searchV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths searchW = new BreadthFirstDirectedPaths(graph, w);

        int lengthOfPath = Integer.MAX_VALUE;
        int ancestor = -1;


        for( int s = 0; s < graph.V(); s++) {
            if (searchV.hasPathTo(s) && searchW.hasPathTo(s)) {
                int distance = searchV.distTo(s) + searchW.distTo(s);
                if (distance < lengthOfPath) {
                    lengthOfPath = distance;
                    ancestor = s;
                }
            }

        }
        return ancestor;
    }


/*
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){

    }
*/

    // do unit testing of this class
    public static void main(String[] args){

        Digraph gAcyclic = new Digraph(6);
        gAcyclic.addEdge(0, 1);
        gAcyclic.addEdge(1,2);
        gAcyclic.addEdge(0,4);
        gAcyclic.addEdge(4, 5);

        Digraph gCyclic = new Digraph(3);
        gCyclic.addEdge(0, 1);
        gCyclic.addEdge(1,2);
        gCyclic.addEdge(2, 0);
        SAP sapA = new SAP(gAcyclic);
        SAP sapC = new SAP(gCyclic);

        //test is not cyclic
        System.out.print("Test graph is DAG, should be true: ");
        System.out.println(sapA.isDAG());

        //test is cyclic
        System.out.print("Test graph is cyclic, should be false: ");
        System.out.println(sapC.isDAG());

        //test is rooted
        System.out.print("Test graph is rooted, should be true: ");
        System.out.println(sapA.isRootedDAG());

        //test is not rooted
        System.out.print("Test graph is not rooted, should be false: ");
        System.out.println(sapC.isRootedDAG());

        //test has ancestor and length
        System.out.print("Test two known vertices have ancestor and length, should be \nAncestor: 0\nLength: 5\n\n");
        System.out.println("Ancestor: " + sapA.ancestor(1, 4));
        System.out.println("Length: " + sapA.length(1, 4));

        System.out.println("Test does not have ancestor or length, should be \nAncestor: -1\nLength: -1\n");
        System.out.println("Ancestor: " + sapA.ancestor(3, 5));
        System.out.println("Length: " + sapA.length(3, 5));

    }
}