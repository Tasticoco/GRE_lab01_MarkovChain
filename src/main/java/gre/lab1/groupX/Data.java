package gre.lab1.groupX;

import gre.lab1.graph.DirectedGraph;

import java.util.Stack;

public class Data {
    public Stack<Integer> stack = new Stack<>();
    public int numDfsnum = 0;
    public int numCompos = 0;
    public int nbVertices;
    public int[] dfsnum;
    public int[] scc;
    public int[] low;
    public Data(DirectedGraph graph){
        nbVertices = graph.getNVertices();
        dfsnum = new int[nbVertices];
        scc = new int[nbVertices];
        low = new int[nbVertices];
    }
}
