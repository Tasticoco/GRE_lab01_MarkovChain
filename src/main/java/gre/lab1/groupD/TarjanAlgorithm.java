package gre.lab1.groupD;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.GraphScc;
import gre.lab1.graph.SccAlgorithm;


public final class TarjanAlgorithm implements SccAlgorithm {

  private DirectedGraph graph;

  /**
   * Applique l'algorithme de Tarjan sur un graphe et permet de récupérer le résultat
   *
   * @param graph Le graphe sur lequel on applique Tarjan
   * @return Un nouveau graphe qui connait les différentes composantes fortement connexes du graphe donné
   */
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

  /**
   * Procédure récursive pour trouver les composantes fortement connexes
   * @param v le numéro du sommet sur lequel on travaille
   * @param d les informations du graphe
   */
  private void sccProcedure(int v, DataTarjan d){
    d.dfsnum[v] = ++d.numDfsnum; //On incrémente l'ordre de passage et on l'assigne au sommet 'v'
    d.low[v]    = d.numDfsnum;   // Au début le plus vieil ancêtre accessible d'un sommet ne peut être que lui-même

    //On rajoute le sommet sur la pile
    d.stack.push(v);

    //Pour chaque sommets successeur à 'v'
    for(int successor : graph.getSuccessorList(v)){
      if(d.dfsnum[successor] == 0) sccProcedure(successor, d); //Si le sommet n'a pas été encore visité, nous y allons récursivement
      // Mettre à jour la valeur "low" de v si successor à un ancêtre accessible qui est plus vieux (si son low est plus petit)
      if(d.scc[successor] == 0) d.low[v] = Math.min(d.low[successor], d.low[v]);
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
