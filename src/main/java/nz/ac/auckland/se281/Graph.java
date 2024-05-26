package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This class represents a graph data structure, which is used to store the countries and their
 * adjacent countries.
 */
public class Graph {
  private Map<Country, List<Country>> adjNodes;

  /** Create a new graph. */
  public Graph() {
    this.adjNodes = new HashMap<>();
  }

  /**
   * Add a node to the graph.
   *
   * @param country the country to be added
   */
  public void addNode(Country country) {
    adjNodes.putIfAbsent(country, new ArrayList<>());
  }

  /**
   * Add an edge between two countries.
   *
   * @param country1 the first country
   * @param country2 the second country
   */
  public void addEdge(Country country1, Country country2) {
    addNode(country1);
    addNode(country2);

    if (!adjNodes.get(country1).contains(country2)) {
      adjNodes.get(country1).add(country2);
    }
  }

  /**
   * Perform a breadth-first search to find the shortest path between two countries.
   *
   * @param source source country to start the search
   * @param destination destination country to end the search
   * @returns the shortest path between the source and destination countries
   */
  public List<Country> breadthFirstSearch(Country source, Country destination) {
    // Initialise the data structures
    Map<Country, Country> predecessors = new HashMap<>();
    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();

    // add the source country to the queue
    queue.add(source);
    visited.add(source);

    // Perform the breadth-first search
    while (!queue.isEmpty()) {
      Country node = queue.poll();

      // Get the adjacent countries of the current country
      for (Country neighbour : adjNodes.get(node)) {
        if (neighbour == null) {
          continue;
        }

        // Check if the neighbour country has been visited
        if (!visited.contains(neighbour)) {
          predecessors.put(neighbour, node);
          visited.add(neighbour);
          queue.add(neighbour);
        }

        // Check if the destination country is reached
        if (neighbour.equals(destination)) {
          return constructPath(predecessors, source, destination);
        }
      }
    }

    // If the destination country is not reached, return null
    return null;
  }

  /**
   * Construct the path between two countries.
   *
   * @param predecessors the map of predecessors
   * @param source the source country
   * @param destination the destination country
   * @returns the path between the source and destination countries
   */
  private List<Country> constructPath(
      Map<Country, Country> predecessors, Country source, Country destination) {
    List<Country> path = new LinkedList<>();

    // Construct the path by backtracking from the destination country
    for (Country at = destination; at != null; at = predecessors.get(at)) {
      path.add(0, at);
    }

    // Check if the source country is the first country in the path
    if (path.get(0).equals(source)) {
      return path;
    }

    // If the source country is not the first country in the path, return null
    return null;
  }

  /**
   * Get the shortest path between two countries.
   *
   * @param source the source country
   * @param destination the destination country
   * @returns the shortest path between the source and destination countries
   */
  public List<Country> getShortestPath(Country source, Country destination) {
    // Check if the source and destination countries are the same
    return breadthFirstSearch(source, destination);
  }
}
