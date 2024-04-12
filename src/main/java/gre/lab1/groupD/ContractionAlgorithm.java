package gre.lab1.groupD;

import gre.lab1.graph.*;

import java.util.LinkedList;
import java.util.List;

/**
 * L'algorithme de contraction permet d'avoir un graphe avec toutes les composantes fortement connexe, et permet aussi
 * pour chaque CFC (SCC en anglais) d'avoir leur contenu.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 */
public class ContractionAlgorithm implements GenericAlgorithm<GraphCondensation> {

  private final SccAlgorithm sccAlgo; //L'agorithme à utiliser pour trouver les scc

  /**
   * Constructeur
   * @param sccAlgo l'algorithme à utiliser pour trouver les composantes fortement connexes
   */
  public ContractionAlgorithm(SccAlgorithm sccAlgo){
    this.sccAlgo = sccAlgo;
  }

  /**
   * Crée l'objet GraphCondensation
   * @param graph Le graphe orienté à traiter.
   * @return un GraphCondensation
   */
  @Override
  public GraphCondensation compute(DirectedGraph graph) {
    GraphScc scc = sccAlgo.compute(graph);
    DirectedGraph condensation = new DirectedGraph(scc.count()); //count = nombre de composantes fortement connexes
    List<List<Integer>> mapping = new LinkedList<>(); //Donne les informations de chaque composantes fortement connexe

    //On ajoute chaque composante fortement connexe au mapping
    for (int i = 0; i < scc.count(); i++) {
      mapping.add(new LinkedList<>());
    }

    //Peuple chaque scc de leurs composantes respectives
    for (int i = 0; i < graph.getNVertices(); i++) {
      int sccIndex = scc.componentOf(i);
      mapping.get(sccIndex-1).add(i);
    }

    int[] listCfc = new int[scc.count()];

    //Pour chaque sommet du graphe initial
    for (int i = 0; i < graph.getNVertices(); i++) {
      int sccIndexI = scc.componentOf(i) - 1; // Indice de la scc du sommet i
      //Pour chaque successeurs de i
      for (int j : graph.getSuccessorList(i)) {
        int sccIndexJ = scc.componentOf(j) - 1; //Indice de la scc du sommet j
        // Si i et j appartiennent à des scc différentes, on ajoute un arc entre les deux scc
        // On vérifie aussi si l'arc n'est pas déjà existant pour éviter les doublons
        if(sccIndexI != sccIndexJ && listCfc[sccIndexJ] != sccIndexI){
          listCfc[sccIndexJ] = sccIndexI;
          condensation.addEdge(sccIndexI, sccIndexJ);
        }
      }
    }

    return new GraphCondensation(graph,condensation, mapping);
  }
}