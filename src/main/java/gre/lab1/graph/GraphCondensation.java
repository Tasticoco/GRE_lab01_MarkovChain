package gre.lab1.graph;

import java.util.List;

/**
 * Describes the condensation (graphe réduit) of a source graph, with the mapping between the vertices of the
 * condensation and the vertices of the source graph.
 *
 * @param source A {@link DirectedGraph} representing the source graph.
 * @param condensation A {@link DirectedGraph} representing the condensation.
 * @param mapping A mapping of the condensation graph vertices to the source graph vertices.
 */
public record GraphCondensation(DirectedGraph source, DirectedGraph condensation, List<List<Integer>> mapping) {}
