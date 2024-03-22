package gre.lab1.groupX;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.GenericAlgorithm;
import gre.lab1.graph.GraphCondensation;
import gre.lab1.graph.GraphScc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContractionAlgorithm implements GenericAlgorithm<GraphCondensation> {
  @Override
  public GraphCondensation compute(DirectedGraph graph) {
    //Pas obligé d'appeler TarjanAlgorithm apparemment (petite subtilité)(je sais pas)
    GraphScc scc = new TarjanAlgorithm().compute(graph);
    DirectedGraph condensation = new DirectedGraph(scc.count()); //count = nombre de composantes fortement connexes
    List<List<Integer>> mapping = new LinkedList<>();

    for (int i = 0; i < scc.count(); i++) {
      mapping.add(new LinkedList<>());
    }

    for (int i = 0; i < graph.getNVertices(); i++) {
      int sccIndex = scc.componentOf(i);
      mapping.get(sccIndex-1).add(i);
    }

    int[] listCfc = new int[scc.count()];

    for (int i = 0; i < graph.getNVertices(); i++) {
      int sccIndexI = scc.componentOf(i) - 1;
      for (int j : graph.getSuccessorList(i)) {
        int sccIndexJ = scc.componentOf(j) - 1;
        if(sccIndexI != sccIndexJ && listCfc[sccIndexJ] != sccIndexI){
          listCfc[sccIndexJ] = sccIndexI;
          condensation.addEdge(sccIndexI, sccIndexJ);
        }
      }
    }

    return new GraphCondensation(graph,condensation, mapping);
  }
}

//    for (int i = 0; i < graph.getNVertices(); i++) {
//        for (int j : graph.getSuccessorList(i)) {
//int sccIndexI = scc.componentOf(i);
//int sccIndexJ = scc.componentOf(j);
//        if (sccIndexI != sccIndexJ) {
//        condensation.addEdge(sccIndexI, sccIndexJ);
//        }
//                }
//                }