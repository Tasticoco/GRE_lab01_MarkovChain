package gre.lab1.groupX;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.GraphScc;
import gre.lab1.graph.SccAlgorithm;


public final class TarjanAlgorithm implements SccAlgorithm {

  private DirectedGraph graph;

  @Override
  public GraphScc compute(DirectedGraph graph) {

    this.graph = graph;

    Data data = new Data(graph);

    for(int i = 0; i < data.nbVertices; ++i){
      if(data.scc[i] == 0){
        sccProcedure(i,data);
      }
    }

    return new GraphScc(graph, data.numCompos, data.scc);
  }

  private void sccProcedure(int v, Data d){
    d.dfsnum[v] = ++d.numDfsnum;
    d.low[v] = d.numDfsnum;

    d.stack.push(v);
    for(int i : graph.getSuccessorList(v)){
      if(d.dfsnum[i] == 0) sccProcedure(i, d);
      if(d.scc[i] == 0) d.low[v] = Math.min(d.low[i], d.low[v]);
    }
    if(d.low[v] == d.dfsnum[v]){
      d.numCompos++; //Commence à numéroter à partir de 1
      int top;
      do{
        top = d.stack.pop();
        d.scc[top] = d.numCompos;
      } while (top != v);
    }
  }

}

//private void sccProcedure(int v, Data d){
//  d.dfsnum[v] = ++d.numDfsnum;
//  d.low[v] = d.numDfsnum;
//
//  d.stack.push(v);
//  for(int w : graph.getSuccessorList(v)){
//    if(d.dfsnum[w] == 0) {
//      sccProcedure(w, d);
//      d.low[v] = Math.min(d.low[v], d.low[w]);
//    } else if (d.scc[w] == 0) {
//      d.low[v] = Math.min(d.low[v], d.dfsnum[w]);
//    }
//  }
//  if(d.low[v] == d.dfsnum[v]){
//    d.numCompos++;
//    int top;
//    do{
//      top = d.stack.pop();
//      d.scc[top] = d.numCompos;
//    } while (top != v);
//  }
//}