package gre.lab1.groupX;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.DirectedGraphReader;
import gre.lab1.graph.GraphCondensation;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) throws IOException {
    // Lecture d'un graphe orienté depuis un fichier
    // DirectedGraph graph = DirectedGraphReader.fromFile("data/file.txt");

    // TODO
    //  - Renommage du package ;
    //  - Écrire le code dans les fichiers Main.java, TarjanAlgorithm.java, ContractionAlgorithm.java
    //    et UNIQUEMENT ceux-ci ;
    //  - Documentation soignée comprenant :
    //    - la javadoc, avec auteurs et description des implémentations ;
    //    - des commentaires sur les différentes parties de vos algorithmes.


    //analyser ensuite les chaînes de Markov proposées et afficherez leur classification.
    //Plus précisément, vous afficherez, pour chaque classe (composante fortement connexe), son
    //statut (persistante ou transitoire), l’ensemble des sommets (états) la formant et, pour les
    //classes transitoires, la liste de ses successeurs dans le graphe réduit.

    DirectedGraph graph = DirectedGraphReader.fromFile("data/exemple_tarjan.txt");
    ContractionAlgorithm ca = new ContractionAlgorithm();

    GraphCondensation gc = ca.compute(graph);

    System.out.println("Nombre de composantes fortement connexes : " + gc.condensation().getNVertices());

    //Si la composante fortememnt concepte n'a pas de successeur persistant, si il y au moins un successeur, alors la
    //composante fortement connexe est transitoire

    //TODO : Enlever les boucles dans les composantes fortement connexes (si un sommet est relié à lui même)

    for (int i = 0; i < gc.mapping().size(); i++) {
      System.out.println("Composante " + (i+1) + " :");
      System.out.println("  - Statut : " + (gc.condensation().getSuccessorList(i).isEmpty() ? "persistante" : "transitoire"));
      System.out.println("  - Sommets : " + printArrayPlusOne(gc.mapping().get(i)));
      if (!gc.condensation().getSuccessorList(i).isEmpty()) {
        System.out.println("  - Successeurs : " + printArrayPlusOne(gc.condensation().getSuccessorList(i)));
      }
    }

  }
  static String printArrayPlusOne(List<Integer> l){
    return l.stream().map(n -> n + 1).toList().toString();
  }

}

