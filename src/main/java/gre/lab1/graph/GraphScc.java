package gre.lab1.graph;

/**
 * Describes a graph with its strongly connected components.
 *
 * @param graph A {@link DirectedGraph}.
 * @param count The number of strongly connected components.
 * @param components An array of integers representing the component number of each vertex.
 */
public record GraphScc(DirectedGraph graph, int count, int[] components) {
  /**
   * Returns the component number of a vertex.
   * @param vertex The vertex.
   * @return The component number of the vertex.
   */
  public int componentOf(int vertex) {
    return components[vertex];
  }
}