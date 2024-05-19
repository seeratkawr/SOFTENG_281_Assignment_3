package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
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
    adjNodes.get(country1).add(country2);
    adjNodes.get(country2).add(country1);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Country country : adjNodes.keySet()) {
      sb.append(country.getName() + " -> ");
      for (Country neighbour : adjNodes.get(country)) {
        sb.append(neighbour.getName() + " ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  public List<Country> getShortestPath(Country source, Country destination) {
    if (!adjNodes.containsKey(source) || !adjNodes.containsKey(destination)) {
      return Collections.emptyList();
    }

    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();
    Map<Country, Country> previousNode = new HashMap<>();
    queue.add(source);
    visited.add(source);

    while (!queue.isEmpty()) {
      Country current = queue.poll();

      if (current.equals(destination)) {
        break;
      }

      for (Country neighbour : adjNodes.get(current)) {
        if (!visited.contains(neighbour)) {
          queue.add(neighbour);
          visited.add(neighbour);
          previousNode.put(neighbour, current);
        }
      }
    }

    return constructPath(previousNode, source, destination);
  }

  public List<Country> constructPath(
      Map<Country, Country> previousNode, Country source, Country destination) {
    List<Country> path = new ArrayList<>();

    for (Country at = destination; at != null; at = previousNode.get(at)) {
      path.add(at);
    }

    Collections.reverse(path);
    return path;
  }
}
