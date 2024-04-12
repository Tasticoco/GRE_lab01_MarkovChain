package gre.lab1.groupD;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.DirectedGraphReader;
import gre.lab1.graph.GraphCondensation;

import java.io.IOException;
import java.util.List;

/**
 * Ce programme permet de trouver les composantes fortement connexes grâce à l'algorithme de Tarjan et
 * d'ensuite les analyser en tant que chaîne de Markov grâce à l'algorithme de contraction qui nous permet d'avoir
 * un graphe de ces composantes fortement connexes.
 * Les graphes sont passés sous forme de fichier .txt avec une structure définie à l'avance (cf. "format données"
 * dans le pdf du labo).
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 */
public class Main {

  final static String FILE_TO_READ = "data/exemple_tarjan.txt";

  public static void main(String[] args) throws IOException {

    DirectedGraph graph = DirectedGraphReader.fromFile(FILE_TO_READ);

    //On initialise un algorithme de contraction avec l'algorithme de Tarjan
    ContractionAlgorithm ca = new ContractionAlgorithm(new TarjanAlgorithm());

    printGraphCondensationResult(ca.compute(graph));
  }

  /**
   * Nous permet d'afficher les informations liés à la chaine de Markov donnée par l'objet GraphCondensation
   * @param gc le graphe à afficher
   */
  static void printGraphCondensationResult(GraphCondensation gc){
    System.out.println("Nombre de composantes fortement connexes : " + gc.condensation().getNVertices());

    for (int i = 0; i < gc.mapping().size(); i++) {
      System.out.println("Composante " + (i+1) + " :");
      //Si la list de successeurs est vide, alors la composante est persistante
      System.out.println("  - Statut : " + (gc.condensation().getSuccessorList(i).isEmpty() ? "persistante" : "transitoire"));
      System.out.println("  - Sommets : " + printArrayPlusOne(gc.mapping().get(i)));
      if (!gc.condensation().getSuccessorList(i).isEmpty()) {
        System.out.println("  - Successeurs : " + printArrayPlusOne(gc.condensation().getSuccessorList(i)));
      }
    }
  }

  /**
   * Permet d'avoir un affichage qui commence à 1 au lieu de 0 pour pouvoir lire les informations plus facilement
   * @param l une liste de int qui représente les index de chacun des sommets
   * @return un String
   */
  static String printArrayPlusOne(List<Integer> l){
    return l.stream().map(n -> n + 1).toList().toString();
  }

}

