package gre.lab1.groupD;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.GraphScc;
import gre.lab1.graph.SccAlgorithm;

import java.util.Stack;

/**
 * Classe servant à trouver toutes les composantes fortement connexe d'un graphe.
 * Cette classe implémente l'algorithme de Tarjan -> Plus d'info : <a href="https://fr.wikipedia.org/wiki/Algorithme_de_Tarjan">Algorithme de Tarjan - Wikipedia</a>
 * @author Edwin Haeffner
 * @author Arthur Junod
 */
public final class TarjanAlgorithm implements SccAlgorithm {

  /**
   * Structure de données qui nous permet de manipuler plus facilement les différentes données utiles
   * à l'algorithme de Tarjan.
   *
   * @author Edwin Haeffner
   * @author Arthur Junod
   */
  private static class DataTarjan {
    public Stack<Integer> stack = new Stack<>();  // Pile qui nous permet de garder en mémoire les sommets restant à classer
    public int numDfsnum = 0;                     // Variable à incrémenter pour générer l'ordre de visite de chaque sommet
    public int numCompos = 0;                     // Variable à incrémenter pour générer le numéro de chaque composante fortement connexe
    public int nbVertices;                        // Nombre de sommets

    // Chaque tableau est de la taille du nombre de sommets, de ce fait, on peut accéder à la donnée relative au sommet i avec tab[i]
    public int[] dfsnum;  // Tableau contenant l'ordre de visite de chaque sommet
    public int[] scc;     // Tableau contenant l'appartenance de chaque sommet à une composante fortement connexe
    public int[] low;     // Tableau contenant le plus vieil ancêtre accessible de chaque sommet

    /**
     * Constructeur permettant de préparer les tableaux et récupérer le nombre de sommets
     * @param graph Le graphe sur lequel nous allons appliquer l'algorithme de Tarjan
     *
     */
    public DataTarjan(DirectedGraph graph){
      nbVertices  = graph.getNVertices();
      dfsnum      = new int[nbVertices];
      scc         = new int[nbVertices];
      low         = new int[nbVertices];
    }
  }

  private DirectedGraph graph; // Le graphe sur lequel on applique Tarjan

  /**
   * Applique l'algorithme de Tarjan sur un graphe et permet d'en récupérer le résultat
   *
   * @param graph Le graphe sur lequel on applique Tarjan
   * @return Un nouveau graphe qui a les informations sur les différentes composantes fortement connexes du graphe donné
   */
  @Override
  public GraphScc compute(DirectedGraph graph) {

    this.graph = graph;

    DataTarjan data = new DataTarjan(graph);

    //Effectue la procédure de découverte des composantes fortements connexe sur chaque sommet
    for(int i = 0; i < data.nbVertices; ++i){
      // Vérifie que le sommet n'a pas été déjà traité par l'appel récursif de sccProcedure()
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

    //Pour chaque sommet successeur à 'v'
    for(int successor : graph.getSuccessorList(v)){
      if(d.dfsnum[successor] == 0) sccProcedure(successor, d); //Si le sommet n'a pas été encore visité, nous y allons récursivement
      // Mettre à jour la valeur "low" de v si successor à un ancêtre accessible qui est plus vieux (si son low est plus petit)
      if(d.scc[successor] == 0) d.low[v] = Math.min(d.low[successor], d.low[v]);
    }
    /*
    Si on n'a pas trouvé d'ancêtre accessible plus vieux que soi-même dans les successeurs,
    on crée une composante fortement connexe pour ce sommet.
    */
    if(d.low[v] == d.dfsnum[v]){
      d.numCompos++; //Commence à numéroter les composantes à partir de 1.
      int top;
      do{
        top = d.stack.pop(); // On récupère chaque ancêtre grâce à la pile
        d.scc[top] = d.numCompos; // On donne à chaque ancêtre récupéré le numéro de la composante
      } while (top != v); // jusqu'à arriver au sommet 'v'
    }
  }
}
