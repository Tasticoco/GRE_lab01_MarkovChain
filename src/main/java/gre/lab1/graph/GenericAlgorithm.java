package gre.lab1.graph;

/**
 * An algorithm that computes a result from a directed graph.
 * @param <R> The type of the result.
 */
public interface GenericAlgorithm<R> {
  /**
   * Computes the result from a directed graph.
   * @param graph The directed graph.
   * @return The result.
   */
  R compute(DirectedGraph graph);
}
