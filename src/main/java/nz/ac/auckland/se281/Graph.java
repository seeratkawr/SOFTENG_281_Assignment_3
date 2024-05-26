package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Graph {
  private Map<Country, List<Country>> adjNodes;

  public Graph() {
    this.adjNodes = new HashMap<>();
  }

  public void addNode(Country country) {
    adjNodes.putIfAbsent(country, new ArrayList<>());
  }

  public void addEdge(Country country1, Country country2) {
    addNode(country1);
    addNode(country2);

    if (!adjNodes.get(country1).contains(country2)) {
      adjNodes.get(country1).add(country2);
    }
  }

  public List<Country> breadthFirstTraversal(Country source, Country destination) {
    Map<Country, Country> predecessors = new HashMap<>();
    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();

    queue.add(source);
    visited.add(source);

    while (!queue.isEmpty()) {
      Country node = queue.poll();

      for (Country neighbour : adjNodes.get(node)) {
        if (neighbour == null) {
          continue;
        }
        if (!visited.contains(neighbour)) {
          predecessors.put(neighbour, node);
          visited.add(neighbour);
          queue.add(neighbour);
        }

        if (neighbour.equals(destination)) {
          return constructPath(predecessors, source, destination);
        }
      }
    }

    return null;
  }

  private List<Country> constructPath(
      Map<Country, Country> predecessors, Country source, Country destination) {
    List<Country> path = new LinkedList<>();

    for (Country at = destination; at != null; at = predecessors.get(at)) {
      path.add(0, at);
    }

    if (path.get(0).equals(source)) {
      return path;
    }

    return null;
  }

  public List<Country> getShortestPath(Country source, Country destination) {
    return breadthFirstTraversal(source, destination);
  }
}
